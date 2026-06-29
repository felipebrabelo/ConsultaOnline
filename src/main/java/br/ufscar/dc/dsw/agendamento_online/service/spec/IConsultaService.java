package br.ufscar.dc.dsw.agendamento_online.service.spec;

import java.time.LocalDate;
import java.util.List;

import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.Consulta;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;

public interface IConsultaService {

    void salvar(Consulta consulta);

    List<Consulta> buscarTodos();

    List<Consulta> buscarPorCliente(Cliente cliente);

    List<Consulta> buscarPorProfissional(Profissional profissional);

    List<Consulta> buscarFuturasPorCliente(Cliente cliente);

    List<Consulta> buscarFuturasPorProfissional(Profissional profissional);

    List<Consulta> buscarPorClienteNaData(Cliente cliente, LocalDate data);

    List<Consulta> buscarPorProfissionalNaData(Profissional profissional, LocalDate data); 

    public Consulta buscarPorId(Long id);
}
