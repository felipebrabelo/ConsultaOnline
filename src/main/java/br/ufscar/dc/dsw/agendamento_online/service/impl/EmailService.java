package br.ufscar.dc.dsw.agendamento_online.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.ufscar.dc.dsw.agendamento_online.domain.Consulta;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void enviarConfirmacaoAgendamento(Consulta consulta) {
        logger.info("Email dummy enviado para cliente {}. Consulta: {}. Profissional: {}. Link: {}",
                consulta.getCliente().getEmail(),
                consulta.getDataHora(),
                consulta.getProfissional().getNome(),
                consulta.getLinkConferencia());

        logger.info("Email dummy enviado para profissional {}. Consulta: {}. Cliente: {}. Link: {}",
                consulta.getProfissional().getEmail(),
                consulta.getDataHora(),
                consulta.getCliente().getNome(),
                consulta.getLinkConferencia());
    }
}
