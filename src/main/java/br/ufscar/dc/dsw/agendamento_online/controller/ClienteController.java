package br.ufscar.dc.dsw.agendamento_online.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Genero;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IClienteService;
import br.ufscar.dc.dsw.agendamento_online.validation.SenhaValidator;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService service;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SenhaValidator senhaValidator;
    
    @GetMapping("/cadastrar")
    public String cadastrar(Cliente cliente, ModelMap model){
        model.addAttribute("generos", Genero.values());
        return "cliente/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model){
        model.addAttribute("clientes",service.buscarTodos());
        return "cliente/listar";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Cliente cliente, BindingResult result, ModelMap model, RedirectAttributes attr){
        senhaValidator.validarObrigatoria(cliente.getSenha(), result);

        if(result.hasErrors()){
            model.addAttribute("generos", Genero.values());
            return "cliente/cadastro";
        }

        cliente.setSenha(encoder.encode(cliente.getSenha()));
        service.salvar(cliente);
        attr.addFlashAttribute("success","cliente.create.success");
        return "redirect:/clientes/listar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("cliente", service.buscarPorId(id));
        model.addAttribute("generos", Genero.values());
        return "cliente/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid Cliente cliente, BindingResult result,
            @RequestParam(value = "novaSenha", required = false) String novaSenha,
            ModelMap model, RedirectAttributes attr){
        senhaValidator.validarOpcional(novaSenha, result);

        if(result.hasErrors()){
            model.addAttribute("generos", Genero.values());
            return "cliente/cadastro";
        }

        if(novaSenha != null && !novaSenha.trim().isEmpty()){
            cliente.setSenha(encoder.encode(novaSenha));
        } else {
            cliente.setSenha(service.buscarPorId(cliente.getId()).getSenha());
        }
        
        service.salvar(cliente);
        attr.addFlashAttribute("success","cliente.edit.success");
        return "redirect:/clientes/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, ModelMap model){
        service.excluir(id);
        model.addAttribute("success", "cliente.delete.success");
        return listar(model);
    }

}
