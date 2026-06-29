package br.ufscar.dc.dsw.agendamento_online.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.Consulta;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;
import br.ufscar.dc.dsw.agendamento_online.security.UsuarioDetails;
import br.ufscar.dc.dsw.agendamento_online.service.impl.EmailService;
import br.ufscar.dc.dsw.agendamento_online.service.impl.LinkConferenciaService;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IConsultaService;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IProfissionalService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private static final DateTimeFormatter CAMPO_DATA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Autowired
    private IConsultaService consultaService;

    @Autowired
    private IProfissionalService profissionalService;

    @Autowired
    private LinkConferenciaService linkConferenciaService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/agendar/{profissionalId}")
    public String agendar(@PathVariable("profissionalId") Long profissionalId, Consulta consulta, ModelMap model) {

        Profissional profissional = profissionalService.buscarPorId(profissionalId);

        consulta.setProfissional(profissional);
        prepararFormulario(consulta, model);

        return "consulta/cadastro";

    }

    @PostMapping("/salvar")
    public String salvar(@Valid Consulta consulta, BindingResult result,
            @AuthenticationPrincipal UsuarioDetails usuarioDetails, ModelMap model, RedirectAttributes attr) {
        carregarProfissional(consulta);

        if (result.hasErrors()) {
            prepararFormulario(consulta, model);
            return "consulta/cadastro";
        }

        try {
            Cliente cliente = (Cliente) usuarioDetails.getUsuario();
            consulta.setCliente(cliente);
            consulta.setLinkConferencia(linkConferenciaService.gerarLink());

            consultaService.salvar(consulta);
            emailService.enviarConfirmacaoAgendamento(consulta);

            attr.addFlashAttribute("success", "consulta.create.success");
            return "redirect:/consultas/cliente?futuras=true";
        } catch (IllegalArgumentException e) {
            result.rejectValue("dataHora", e.getMessage());
            prepararFormulario(consulta, model);
            return "consulta/cadastro";
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("dataHora", "consulta.dataHora.unavailable");
            prepararFormulario(consulta, model);
            return "consulta/cadastro";
        }
    }

    @GetMapping("/cliente")
    public String listarConsultasCliente(
            @AuthenticationPrincipal UsuarioDetails usuarioDetails,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            Boolean futuras,
            ModelMap model) {

        Cliente cliente = (Cliente) usuarioDetails.getUsuario();

        if (data != null) {
            model.addAttribute("consultas", consultaService.buscarPorClienteNaData(cliente, data));
        } else if (Boolean.TRUE.equals(futuras)) {
            model.addAttribute("consultas", consultaService.buscarFuturasPorCliente(cliente));
        } else {
            model.addAttribute("consultas", consultaService.buscarPorCliente(cliente));
        }
        model.addAttribute("dataSelecionada", data);
        model.addAttribute("futuras", Boolean.TRUE.equals(futuras));

        return "consulta/cliente";
    }

    @GetMapping("/profissional")
    public String listarConsultasProfissional(
            @AuthenticationPrincipal UsuarioDetails usuarioDetails,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            Boolean futuras,
            ModelMap model) {

        Profissional profissional = (Profissional) usuarioDetails.getUsuario();

        if (data != null) {
            model.addAttribute("consultas", consultaService.buscarPorProfissionalNaData(profissional, data));
        } else if (Boolean.TRUE.equals(futuras)) {
            model.addAttribute("consultas", consultaService.buscarFuturasPorProfissional(profissional));
        } else {
            model.addAttribute("consultas", consultaService.buscarPorProfissional(profissional));
        }
        model.addAttribute("dataSelecionada", data);
        model.addAttribute("futuras", Boolean.TRUE.equals(futuras));

        return "consulta/profissional";
    }

    private void prepararFormulario(Consulta consulta, ModelMap model) {
        model.addAttribute("profissional", consulta.getProfissional());
        model.addAttribute("minDataHora", proximaHoraCheia().format(CAMPO_DATA_HORA));
    }

    private LocalDateTime proximaHoraCheia() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(1);
    }

    private void carregarProfissional(Consulta consulta) {
        if (consulta.getProfissional() != null && consulta.getProfissional().getId() != null) {
            consulta.setProfissional(profissionalService.buscarPorId(consulta.getProfissional().getId()));
        }
    }

}
