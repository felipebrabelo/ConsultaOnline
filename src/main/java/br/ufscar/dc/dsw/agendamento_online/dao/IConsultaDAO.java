package br.ufscar.dc.dsw.agendamento_online.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.Consulta;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;

@SuppressWarnings("unchecked")
public interface IConsultaDAO extends CrudRepository<Consulta, Long> {

    Consulta findById(long id);

    List<Consulta> findByCliente(Cliente cliente);

    List<Consulta> findByClienteAndDataHoraBetween(Cliente cliente, LocalDateTime inicio, LocalDateTime fim);

    List<Consulta> findByClienteAndDataHoraAfter(Cliente cliente, LocalDateTime dataHora);

    List<Consulta> findByProfissional(Profissional profissional);

    List<Consulta> findByProfissionalAndDataHoraBetween(Profissional profissional, LocalDateTime inicio,
            LocalDateTime fim);

    List<Consulta> findByProfissionalAndDataHoraAfter(Profissional profissional, LocalDateTime dataHora);

    List<Consulta> findAll();

    Consulta save(Consulta consulta);

    void deleteById(Long id);
}
