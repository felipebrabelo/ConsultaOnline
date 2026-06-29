package br.ufscar.dc.dsw.agendamento_online.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IClienteService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

@RestController
public class ClienteRestController {

    @Autowired
    private IClienteService service;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private Validator validator;

    @GetMapping(path = "/api/clientes")
    public ResponseEntity<List<Cliente>> lista() {
        List<Cliente> lista = service.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/api/clientes/{id}")
    public ResponseEntity<Cliente> lista(@PathVariable("id") long id) {
        Cliente cliente = service.buscarPorId(id);
        if (cliente == null || !Boolean.TRUE.equals(cliente.getAtivo())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping(path = { "/api/clientes", "/api/iclientes" })
    @ResponseBody
    public ResponseEntity<Cliente> cria(@Valid @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors() || cliente.getSenha() == null || cliente.getSenha().isBlank()) {
            return ResponseEntity.badRequest().body(null);
        }

        cliente.setSenha(encoder.encode(cliente.getSenha()));
        service.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping(path = "/api/clientes/{id}")
    public ResponseEntity<Cliente> atualiza(@PathVariable("id") long id, @RequestBody Cliente cliente) {
        Cliente c = service.buscarPorId(id);
        if (c == null || !Boolean.TRUE.equals(c.getAtivo())) {
            return ResponseEntity.notFound().build();
        }

        cliente.setId(id);
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        if (cliente.getSenha() == null || cliente.getSenha().isBlank()) {
            cliente.setSenha(c.getSenha());
        } else {
            cliente.setSenha(encoder.encode(cliente.getSenha()));
        }
        service.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping(path = "/api/clientes/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {
        Cliente cliente = service.buscarPorId(id);
        if (cliente == null || !Boolean.TRUE.equals(cliente.getAtivo())) {
            return ResponseEntity.notFound().build();
        }

        service.excluir(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
}
