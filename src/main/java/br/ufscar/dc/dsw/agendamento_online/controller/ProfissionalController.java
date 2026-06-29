package br.ufscar.dc.dsw.agendamento_online.controller;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.agendamento_online.domain.FileEntity;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Especialidade;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IProfissionalService;
import br.ufscar.dc.dsw.agendamento_online.validation.CurriculoValidator;
import br.ufscar.dc.dsw.agendamento_online.validation.SenhaValidator;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private IProfissionalService service;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SenhaValidator senhaValidator;

    @Autowired
    private CurriculoValidator curriculoValidator;
    
    @GetMapping("/cadastrar")
    public String cadastrar(Profissional profissional, ModelMap model){
        model.addAttribute("especialidades", Especialidade.values());
        return "profissional/cadastro";
    }

    @GetMapping("/listar")
    public String listar(Especialidade especialidade, ModelMap model){
        if (especialidade == null) {
            model.addAttribute("profissionais", service.buscarTodos());
        } else {
            model.addAttribute("profissionais", service.buscarPorEspecialidade(especialidade));
        }
        model.addAttribute("especialidades", Especialidade.values());
        model.addAttribute("especialidadeSelecionada", especialidade);
        return "profissional/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Profissional profissional, BindingResult result,
            @RequestParam(value = "curriculoArquivo", required = false) MultipartFile curriculoArquivo,
            ModelMap model, RedirectAttributes attr){
        senhaValidator.validarObrigatoria(profissional.getSenha(), result);

        FileEntity curriculo = curriculoValidator.criarCurriculo(curriculoArquivo, result, true);
        if (curriculo != null) {
            profissional.setCurriculo(curriculo);
        }

        if(result.hasErrors()){
            model.addAttribute("especialidades", Especialidade.values());
            return "profissional/cadastro";
        }

        profissional.setSenha(encoder.encode(profissional.getSenha()));
        service.salvar(profissional);
        attr.addFlashAttribute("success","profissional.create.success");
        return "redirect:/profissionais/listar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("profissional", service.buscarPorId(id));
        model.addAttribute("especialidades", Especialidade.values());
        return "profissional/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid Profissional profissional, BindingResult result,
            @RequestParam(value = "novaSenha", required = false) String novaSenha,
            @RequestParam(value = "curriculoArquivo", required = false) MultipartFile curriculoArquivo,
            ModelMap model, RedirectAttributes attr){

        Profissional profissionalSalvo = service.buscarPorId(profissional.getId());
        senhaValidator.validarOpcional(novaSenha, result);

        FileEntity curriculo = curriculoValidator.criarCurriculo(curriculoArquivo, result, false);
        profissional.setCurriculo(curriculo != null ? curriculo : profissionalSalvo.getCurriculo());

        if(result.hasErrors()){
            model.addAttribute("especialidades", Especialidade.values());
            return "profissional/cadastro";
        }

        if(novaSenha != null && !novaSenha.trim().isEmpty()){
            profissional.setSenha(encoder.encode(novaSenha));
        } else {
            profissional.setSenha(profissionalSalvo.getSenha());
        }
        
        service.salvar(profissional);
        attr.addFlashAttribute("success","profissional.edit.success");
        return "redirect:/profissionais/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, ModelMap model){
        service.excluir(id);
        model.addAttribute("success", "profissional.delete.success");
        return listar(null, model);
    }

    @GetMapping("/{id}/curriculo")
    public ResponseEntity<byte[]> verCurriculo(@PathVariable("id") Long id) {
        FileEntity curriculo = service.buscarPorId(id).getCurriculo();

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

}
