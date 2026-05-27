package br.ufscar.dc.dsw.agendamento_online.dao;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;

public interface IClienteDAO extends CrudRepository<Cliente, Long>{

    
}
