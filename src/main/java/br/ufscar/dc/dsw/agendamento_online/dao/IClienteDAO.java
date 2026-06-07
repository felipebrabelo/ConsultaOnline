package br.ufscar.dc.dsw.agendamento_online.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;


public interface IClienteDAO extends CrudRepository<Cliente, Long>{

    @Query("SELECT DISTINCT c FROM Cliente c  JOIN c.consultas cs WHERE cs.profissional = :profissional")
    List<Cliente> findByProfissional(@Param("profissional") Profissional profissional);
    
}
