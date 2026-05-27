package br.ufscar.dc.dsw.agendamento_online.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.Consulta;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;

@SuppressWarnings("unchecked")
public interface IConsultaDAO extends CrudRepository<Consulta, Long>{

    Consulta findById(long id);

    List<Consulta> findByCliente(Cliente cliente);

    List<Consulta> findByProfissional(Profissional profissional);

    List<Consulta> findAll();

    Consulta save(Consulta consulta);

    void deleteById(Long id);
}
