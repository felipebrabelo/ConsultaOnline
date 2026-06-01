package br.ufscar.dc.dsw.agendamento_online.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Especialidade;

public interface IProfissionalDAO extends CrudRepository<Profissional, Long>{

    List<Profissional> findAll();

    List<Profissional> findByEspecialidade(Especialidade especialidade);
    
}