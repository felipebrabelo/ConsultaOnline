package br.ufscar.dc.dsw.consulta_online_client.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import br.ufscar.dc.dsw.consulta_online_client.domain.Profissional;
import br.ufscar.dc.dsw.consulta_online_client.domain.enumeration.Especialidade;
import br.ufscar.dc.dsw.consulta_online_client.service.spec.IProfissionalRestService;

@Service
public class ProfissionalRestService implements IProfissionalRestService {

    RestClient restClient = RestClient.create("http://localhost:8080");

    @Override
    public List<Profissional> buscarTodos() {
        List<Profissional> profissionais = restClient.get()
                .uri("/api/profissionais")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Profissional>>() {
                });
        return profissionais;
    }

    @Override
    public List<Profissional> buscarPorEspecialidade(Especialidade especialidade) {
        if (especialidade == null) {
            return buscarTodos();
        }

        List<Profissional> profissionais = restClient.get()
                .uri("/api/profissionais/especialidades/" + especialidade.name())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Profissional>>() {
                });
        return profissionais;
    }

    @Override
    public Profissional buscarPorId(Long id) {
        Profissional profissional = restClient.get()
                .uri("/api/profissionais/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Profissional.class);
        return profissional;
    }

    @Override
    public void criar(Profissional profissional) {
        profissional.prepararParaEnvio();
        restClient.post()
                .uri("/api/profissionais")
                .contentType(MediaType.APPLICATION_JSON)
                .body(profissional)
                .retrieve()
                .toEntity(Profissional.class);
    }

    @Override
    public void atualizar(Long id, Profissional profissional) {
        profissional.prepararParaEnvio();
        restClient.put()
                .uri("/api/profissionais/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(profissional)
                .retrieve()
                .toEntity(Profissional.class);
    }

    @Override
    public boolean excluir(Long id) {
        ResponseEntity<Boolean> res = restClient.delete()
                .uri("/api/profissionais/" + id)
                .retrieve()
                .toEntity(Boolean.class);
        HttpStatusCode status = res.getStatusCode();
        return status.is2xxSuccessful();
    }
}
