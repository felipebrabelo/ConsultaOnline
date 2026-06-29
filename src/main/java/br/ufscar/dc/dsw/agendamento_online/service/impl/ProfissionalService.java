package br.ufscar.dc.dsw.agendamento_online.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.agendamento_online.dao.IProfissionalDAO;
import br.ufscar.dc.dsw.agendamento_online.domain.FileEntity;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Especialidade;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Papel;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IProfissionalService;


@Service
@Transactional(readOnly = false)
public class ProfissionalService implements IProfissionalService{
    
    @Autowired
    IProfissionalDAO dao;

    public void salvar(Profissional profissional){
        profissional.setPapel(Papel.PROFISSIONAL);
        if (profissional.getId() == null) {
            profissional.setAtivo(true);
        } else {
            Profissional profissionalSalvo = dao.findById(profissional.getId().longValue());
            profissional.setAtivo(profissionalSalvo.getAtivo());
        }
        if (profissional.getCurriculo() == null) {
            profissional.setCurriculo(new FileEntity("Curriculo", "application/pdf", new byte[0]));
        } else {
            if (profissional.getCurriculo().getName() == null || profissional.getCurriculo().getName().isBlank()) {
                profissional.getCurriculo().setName("Curriculo");
            }
            if (profissional.getCurriculo().getType() == null || profissional.getCurriculo().getType().isBlank()) {
                profissional.getCurriculo().setType("application/pdf");
            }
        }
        dao.save(profissional);
    }

    public void excluir(Long id){
        Profissional profissional = dao.findById(id.longValue());
        profissional.setAtivo(false);
        dao.save(profissional);
    }

    @Transactional(readOnly = true)
    public List<Profissional> buscarTodos(){
        return dao.findByAtivoTrue();
    }

    @Transactional(readOnly = true)
    public List<Profissional> buscarPorEspecialidade(Especialidade especialidade) {
        return dao.findByEspecialidadeAndAtivoTrue(especialidade);
    }

    @Transactional(readOnly = true)
    public Profissional buscarPorId(Long id){
        return dao.findById(id.longValue());
    }

}
