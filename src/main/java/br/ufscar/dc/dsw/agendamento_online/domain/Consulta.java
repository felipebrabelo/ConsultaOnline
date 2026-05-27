package br.ufscar.dc.dsw.agendamento_online.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "consulta", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "cliente_id", "data_hora" }),
        @UniqueConstraint(columnNames = { "profissional_id", "data_hora" })
})
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "data_hora")
    private LocalDateTime dataHora;

    @Column(length = 500)
    private String descricao;

    @Column(length = 2048, name = "link_conferencia")
    private String linkConferencia;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    public Consulta() {

    }

    public Consulta(LocalDateTime dataHora, String descricao, String linkConferencia, Cliente cliente,
            Profissional profissional) {
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.linkConferencia = linkConferencia;
        this.cliente = cliente;
        this.profissional = profissional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLinkConferencia() {
        return linkConferencia;
    }

    public void setLinkConferencia(String linkConferencia) {
        this.linkConferencia = linkConferencia;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", dataHora=" + dataHora +
                ", descricao='" + descricao + '\'' +
                ", linkConferencia='" + linkConferencia + '\'' +
                ", cliente=" + cliente.getNome() +
                ", profissional=" + profissional.getNome() +
                '}';
    }
}
