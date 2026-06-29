package br.ufscar.dc.dsw.agendamento_online.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.agendamento_online.dao.IClienteDAO;
import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Papel;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IClienteService;


@Service
@Transactional(readOnly = false)
public class ClienteService implements IClienteService{
    
    @Autowired
    IClienteDAO dao;

    public void salvar(Cliente cliente){
        cliente.setPapel(Papel.CLIENTE);
        if (cliente.getId() == null) {
            cliente.setAtivo(true);
        } else {
            Cliente clienteSalvo = dao.findById(cliente.getId().longValue());
            cliente.setAtivo(clienteSalvo.getAtivo());
        }
        dao.save(cliente);
    }

    public void excluir(Long id){
        Cliente cliente = dao.findById(id.longValue());
        cliente.setAtivo(false);
        dao.save(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarTodos(){
        return dao.findByAtivoTrue();
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id){
        return dao.findById(id.longValue());
    }


    
}
