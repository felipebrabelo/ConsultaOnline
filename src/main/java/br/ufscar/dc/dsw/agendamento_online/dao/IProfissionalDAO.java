package br.ufscar.dc.dsw.agendamento_online.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Especialidade;

public interface IProfissionalDAO extends CrudRepository<Profissional, Long>{

    Profissional findById(long id);

    Profissional findByEmail(String email);

    List<Profissional> findAll();

    List<Profissional> findByAtivoTrue();

    List<Profissional> findByEspecialidade(Especialidade especialidade);

    List<Profissional> findByEspecialidadeAndAtivoTrue(Especialidade especialidade);
    
    @Query("SELECT DISTINCT p FROM Profissional p  JOIN p.consultas c WHERE c.cliente = :cliente")
    List<Profissional> findByCliente(@Param("cliente") Cliente cliente);

    Profissional save(Profissional profissional);

    void deleteById(Long id);
}
