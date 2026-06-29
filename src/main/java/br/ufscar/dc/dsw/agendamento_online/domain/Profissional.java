package br.ufscar.dc.dsw.agendamento_online.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Especialidade;
import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Papel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Profissional")
public class Profissional extends Usuario {

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{profissional.especialidade.notNull}")
    private Especialidade especialidade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "curriculo_id", nullable = false)
    private FileEntity curriculo;

    @OneToMany(mappedBy = "profissional")
    @JsonIgnore
    private List<Consulta> consultas;

    public Profissional() {

    }

    public Profissional(Especialidade especialidade, FileEntity curriculo) {
        this.especialidade = especialidade;
        this.curriculo = curriculo;
    }

    public Profissional(String nome, String email, String senha, String cpf, Especialidade especialidade,
            FileEntity curriculo) {
        super(nome, email, senha, cpf, Papel.PROFISSIONAL);
        this.especialidade = especialidade;
        this.curriculo = curriculo;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public FileEntity getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(FileEntity curriculo) {
        this.curriculo = curriculo;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    @Override
    public String toString() {
        return "Profissional{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", especialidade='" + especialidade.getDescricao() + '\'' +
                ", curriculo=" + curriculo.getName() +
                '}';
    }
}
