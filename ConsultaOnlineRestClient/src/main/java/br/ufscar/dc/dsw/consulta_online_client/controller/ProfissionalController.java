package br.ufscar.dc.dsw.consulta_online_client.controller;

import java.nio.charset.StandardCharsets;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.consulta_online_client.domain.enumeration.Especialidade;
import br.ufscar.dc.dsw.consulta_online_client.domain.FileEntity;
import br.ufscar.dc.dsw.consulta_online_client.domain.Profissional;
import br.ufscar.dc.dsw.consulta_online_client.service.spec.IProfissionalRestService;
import br.ufscar.dc.dsw.consulta_online_client.validation.CurriculoValidator;
import br.ufscar.dc.dsw.consulta_online_client.validation.SenhaValidator;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private IProfissionalRestService service;

    @Autowired
    private SenhaValidator senhaValidator;

    @Autowired
    private CurriculoValidator curriculoValidator;

    @GetMapping("/cadastrar")
    public String cadastrar(@ModelAttribute("profissional") Profissional profissional, ModelMap model) {
        carregarEspecialidades(model);
        return "profissional/cadastro";
    }

    @GetMapping("/listar")
    public String listar(Especialidade especialidade, ModelMap model) {
        try {
            model.addAttribute("profissionais", service.buscarPorEspecialidade(especialidade));
        } catch (RestClientException e) {
            model.addAttribute("profissionais", java.util.Collections.emptyList());
            model.addAttribute("fail", "profissional.api.fail");
        }
        model.addAttribute("especialidadeSelecionada", especialidade);
        carregarEspecialidades(model);
        return "profissional/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("profissional") Profissional profissional,
            BindingResult result,
            @RequestParam(value = "curriculoArquivo", required = false) MultipartFile curriculoArquivo,
            ModelMap model, RedirectAttributes attr) {
        senhaValidator.validarObrigatoria(profissional.getSenha(), result);

        FileEntity curriculo = curriculoValidator.criarCurriculo(curriculoArquivo, result, true);
        if (curriculo != null) {
            profissional.setCurriculo(curriculo);
        }
        if (result.hasErrors()) {
            carregarEspecialidades(model);
            return "profissional/cadastro";
        }

        try {
            service.criar(profissional);
            attr.addFlashAttribute("success", "profissional.create.success");
            return "redirect:/profissionais/listar";
        } catch (RestClientException e) {
            model.addAttribute("fail", "profissional.create.fail");
            carregarEspecialidades(model);
            return "profissional/cadastro";
        }
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model, RedirectAttributes attr) {
        Profissional profissional;
        try {
            profissional = service.buscarPorId(id);
        } catch (RestClientException e) {
            attr.addFlashAttribute("fail", "profissional.api.fail");
            return "redirect:/profissionais/listar";
        }
        if (profissional == null) {
            attr.addFlashAttribute("fail", "profissional.notFound");
            return "redirect:/profissionais/listar";
        }
        model.addAttribute("profissional", profissional);
        carregarEspecialidades(model);
        return "profissional/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid @ModelAttribute("profissional") Profissional profissional,
            BindingResult result,
            @RequestParam(value = "novaSenha", required = false) String novaSenha,
            @RequestParam(value = "curriculoArquivo", required = false) MultipartFile curriculoArquivo,
            ModelMap model, RedirectAttributes attr) {
        senhaValidator.validarOpcional(novaSenha, result);

        FileEntity curriculo = curriculoValidator.criarCurriculo(curriculoArquivo, result, false);
        if (curriculo != null) {
            profissional.setCurriculo(curriculo);
        }
        if (result.hasErrors()) {
            carregarEspecialidades(model);
            return "profissional/cadastro";
        }

        try {
            if (novaSenha != null && !novaSenha.trim().isEmpty()) {
                profissional.setSenha(novaSenha);
            } else {
                profissional.setSenha(null);
            }
            service.atualizar(profissional.getId(), profissional);
            attr.addFlashAttribute("success", "profissional.edit.success");
            return "redirect:/profissionais/listar";
        } catch (RestClientException e) {
            model.addAttribute("fail", "profissional.edit.fail");
            carregarEspecialidades(model);
            return "profissional/cadastro";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        try {
            service.excluir(id);
            attr.addFlashAttribute("success", "profissional.delete.success");
        } catch (RestClientException e) {
            attr.addFlashAttribute("fail", "profissional.delete.fail");
        }
        return "redirect:/profissionais/listar";
    }

    @GetMapping("/{id}/curriculo")
    public ResponseEntity<byte[]> verCurriculo(@PathVariable("id") Long id) {
        Profissional profissional;
        try {
            profissional = service.buscarPorId(id);
        } catch (RestClientException e) {
            return ResponseEntity.internalServerError().build();
        }
        FileEntity curriculo = profissional == null ? null : profissional.getCurriculo();

        if (curriculo == null || curriculo.getData() == null || curriculo.getData().length == 0) {
            return ResponseEntity.notFound().build();
        }

        String nomeArquivo = curriculo.getName() == null || curriculo.getName().isBlank()
                ? "curriculo.pdf"
                : curriculo.getName();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename(nomeArquivo, StandardCharsets.UTF_8)
                                .build()
                                .toString())
                .body(curriculo.getData());
    }

    private void carregarEspecialidades(ModelMap model) {
        model.addAttribute("especialidades", Especialidade.values());
    }
}
