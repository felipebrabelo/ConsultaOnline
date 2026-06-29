package br.ufscar.dc.dsw.agendamento_online.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.agendamento_online.dao.IConsultaDAO;
import br.ufscar.dc.dsw.agendamento_online.domain.Cliente;
import br.ufscar.dc.dsw.agendamento_online.domain.Consulta;
import br.ufscar.dc.dsw.agendamento_online.domain.Profissional;
import br.ufscar.dc.dsw.agendamento_online.service.spec.IConsultaService;

@Service
public class ConsultaService implements IConsultaService {

    @Autowired
    private IConsultaDAO dao;

    public void salvar(Consulta consulta) {
        validarHorario(consulta);
        dao.save(consulta);
    }

    @Transactional(readOnly = true)
    public List<Consulta> buscarTodos() {
        return dao.findAll();
    }

    private void validarHorario(Consulta consulta) {
        LocalDateTime dataHora = consulta.getDataHora();

        if (dataHora == null) {
            throw new IllegalArgumentException("consulta.dataHora.required");
        }

        if (!dataHora.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("consulta.dataHora.future");
        }

        if (dataHora.getMinute() != 0 || dataHora.getSecond() != 0 || dataHora.getNano() != 0) {
            throw new IllegalArgumentException("consulta.dataHora.fullHour");
        }
    }

    @Transactional(readOnly = true)
    public List<Consulta> buscarPorCliente(Cliente cliente) {
        return dao.findByCliente(cliente);
    }

    @Transactional(readOnly = true)
    public List<Consulta> buscarPorProfissional(Profissional profissional) {
        return dao.findByProfissional(profissional);
    }

    @Transactional(readOnly = true)
    public List<Consulta> buscarFuturasPorCliente(Cliente cliente) {
        return dao.findByClienteAndDataHoraAfter(cliente, LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public List<Consulta> buscarFuturasPorProfissional(Profissional profissional) {
        return dao.findByProfissionalAndDataHoraAfter(profissional, LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public List<Consulta> buscarPorClienteNaData(Cliente cliente, LocalDate data) {
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.plusDays(1).atStartOfDay().minusNanos(1);

        return dao.findByClienteAndDataHoraBetween(cliente, inicio, fim);
    }

    @Transactional(readOnly = true)
    public List<Consulta> buscarPorProfissionalNaData(Profissional profissional, LocalDate data) {
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.plusDays(1).atStartOfDay().minusNanos(1);

        return dao.findByProfissionalAndDataHoraBetween(profissional, inicio, fim);
    }

    @Transactional(readOnly = true)
    public Consulta buscarPorId(Long id){
        return dao.findById(id.longValue());
    }
}
