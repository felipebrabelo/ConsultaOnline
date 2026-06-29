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

import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Especialidade;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IProfissionalService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

@RestController
public class ProfissionalRestController {

    @Autowired
    private IProfissionalService service;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private Validator validator;

    @GetMapping(path = "/api/profissionais")
    public ResponseEntity<List<Profissional>> lista() {
        List<Profissional> lista = service.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/api/profissionais/{id}")
    public ResponseEntity<Profissional> lista(@PathVariable("id") long id) {
        Profissional profissional = service.buscarPorId(id);
        if (profissional == null || !Boolean.TRUE.equals(profissional.getAtivo())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profissional);
    }

    @GetMapping(path = "/api/profissionais/especialidades/{nome}")
    public ResponseEntity<List<Profissional>> listaPorEspecialidade(@PathVariable("nome") String nome) {
        Especialidade especialidade = getEspecialidade(nome);
        if (especialidade == null) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Profissional> lista = service.buscarPorEspecialidade(especialidade);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping(path = "/api/profissionais")
    @ResponseBody
    public ResponseEntity<Profissional> cria(@Valid @RequestBody Profissional profissional, BindingResult result) {
        if (result.hasErrors() || profissional.getSenha() == null || profissional.getSenha().isBlank()) {
            return ResponseEntity.badRequest().body(null);
        }

        profissional.setSenha(encoder.encode(profissional.getSenha()));
        service.salvar(profissional);
        return ResponseEntity.ok(profissional);
    }

    @PutMapping(path = "/api/profissionais/{id}")
    public ResponseEntity<Profissional> atualiza(@PathVariable("id") long id,
            @RequestBody Profissional profissional) {
        Profissional p = service.buscarPorId(id);
        if (p == null || !Boolean.TRUE.equals(p.getAtivo())) {
            return ResponseEntity.notFound().build();
        }

        profissional.setId(id);
        if (profissional.getCurriculo() == null) {
            profissional.setCurriculo(p.getCurriculo());
        }

        Set<ConstraintViolation<Profissional>> violations = validator.validate(profissional);
        if (!violations.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        if (profissional.getSenha() == null || profissional.getSenha().isBlank()) {
            profissional.setSenha(p.getSenha());
        } else {
            profissional.setSenha(encoder.encode(profissional.getSenha()));
        }
        service.salvar(profissional);
        return ResponseEntity.ok(profissional);
    }

    @DeleteMapping(path = "/api/profissionais/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {
        Profissional profissional = service.buscarPorId(id);
        if (profissional == null || !Boolean.TRUE.equals(profissional.getAtivo())) {
            return ResponseEntity.notFound().build();
        }

        service.excluir(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    private Especialidade getEspecialidade(String nome) {
        try {
            return Especialidade.valueOf(nome.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
