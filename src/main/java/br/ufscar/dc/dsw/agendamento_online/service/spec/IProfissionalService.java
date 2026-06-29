package br.ufscar.dc.dsw.agendamento_online.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Especialidade;

public interface IProfissionalService {
  public void salvar(Profissional profissional);

    public void excluir(Long id);

    public List<Profissional> buscarTodos();

    public List<Profissional> buscarPorEspecialidade(Especialidade especialidade);

    public Profissional buscarPorId(Long id);
    
} 
