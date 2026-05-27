package br.ufscar.dc.dsw.agendamento_online.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;

public interface IProfissionalDAO extends CrudRepository<Profissional, Long>{

    List<Profissional> findAll();

    List<Profissional> findByEspecialidade(String especialidade);
    
}