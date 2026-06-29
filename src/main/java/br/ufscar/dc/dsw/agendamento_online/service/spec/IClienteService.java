package br.ufscar.dc.dsw.agendamento_online.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;

public interface IClienteService {
    
    public void salvar(Cliente cliente);

    public void excluir(Long id);

    public List<Cliente> buscarTodos();

    public Cliente buscarPorId(Long id);
}
