package br.ufscar.dc.dsw.agendamento_online.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class LinkConferenciaService {

    public String gerarLink() {
        String codigo = UUID.randomUUID().toString().replace("-", "");
        return String.format("https://meet.google.com/%s-%s-%s",
                codigo.substring(0, 3),
                codigo.substring(3, 7),
                codigo.substring(7, 10));
    }
}
