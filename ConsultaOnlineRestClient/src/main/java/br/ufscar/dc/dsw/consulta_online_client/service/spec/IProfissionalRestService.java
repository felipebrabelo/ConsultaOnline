package br.ufscar.dc.dsw.consulta_online_client.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.consulta_online_client.domain.Profissional;
import br.ufscar.dc.dsw.consulta_online_client.domain.enumeration.Especialidade;

public interface IProfissionalRestService {

    List<Profissional> buscarTodos();

    List<Profissional> buscarPorEspecialidade(Especialidade especialidade);

    Profissional buscarPorId(Long id);

    void criar(Profissional profissional);

    void atualizar(Long id, Profissional profissional);

    boolean excluir(Long id);
}
