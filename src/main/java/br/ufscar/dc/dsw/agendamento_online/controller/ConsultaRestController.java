package br.ufscar.dc.dsw.agendamento_online.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.Consulta;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IClienteService;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IConsultaService;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IProfissionalService;

@RestController
public class ConsultaRestController {

    @Autowired
    private IConsultaService consultaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IProfissionalService profissionalService;

    @GetMapping(path = "/api/consultas")
    public ResponseEntity<List<Consulta>> lista() {
        List<Consulta> lista = consultaService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/api/consultas/{id}")
    public ResponseEntity<Consulta> lista(@PathVariable("id") long id) {
        Consulta consulta = consultaService.buscarPorId(id);
        if (consulta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(consulta);
    }

    @GetMapping(path = "/api/consultas/clientes/{id}")
    public ResponseEntity<List<Consulta>> listaPorCliente(@PathVariable("id") long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        List<Consulta> lista = consultaService.buscarPorCliente(cliente);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/api/consultas/profissionais/{id}")
    public ResponseEntity<List<Consulta>> listaPorProfissional(@PathVariable("id") long id) {
        Profissional profissional = profissionalService.buscarPorId(id);
        if (profissional == null) {
            return ResponseEntity.notFound().build();
        }

        List<Consulta> lista = consultaService.buscarPorProfissional(profissional);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }
}
