package br.ufscar.dc.dsw.agendamento_online.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;
import br.ufscar.dc.dsw.agendamento_online.domain.Usuario;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Especialidade;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Papel;
import br.ufscar.dc.dsw.agendamento_online.security.UsuarioDetails;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IConsultaService;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IProfissionalService;

@Controller
public class HomeController {

    @Autowired
    private IProfissionalService profissionalService;

    @Autowired
    private IConsultaService consultaService;

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UsuarioDetails usuarioDetails, Especialidade especialidade,
            ModelMap model) {
        if (usuarioDetails == null) {
            if (especialidade == null) {
                model.addAttribute("profissionais", profissionalService.buscarTodos());
            } else {
                model.addAttribute("profissionais", profissionalService.buscarPorEspecialidade(especialidade));
            }

            model.addAttribute("especialidades", Especialidade.values());
            model.addAttribute("especialidadeSelecionada", especialidade);
            return "profissional/listar";
        }

        Usuario usuario = usuarioDetails.getUsuario();

        if (usuario.getPapel() == Papel.CLIENTE) {
            model.addAttribute("consultas", consultaService.buscarFuturasPorCliente((Cliente) usuario));
            model.addAttribute("futuras", true);
            return "consulta/cliente";
        }

        if (usuario.getPapel() == Papel.PROFISSIONAL) {
            model.addAttribute("consultas", consultaService.buscarFuturasPorProfissional((Profissional) usuario));
            model.addAttribute("futuras", true);
            return "consulta/profissional";
        }

        return "index";
    }
}
