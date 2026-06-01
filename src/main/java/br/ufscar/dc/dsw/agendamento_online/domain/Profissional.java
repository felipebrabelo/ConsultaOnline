package br.ufscar.dc.dsw.agendamento_online.domain;

import java.util.List;

import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Especialidade;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "profissional")
public class Profissional extends Usuario {

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] curriculo;

    @OneToMany(mappedBy = "profissional")
    private List<Consulta> consultas;

    public Profissional() {

    }

    public Profissional(Especialidade especialidade, byte[] curriculo) {
        this.especialidade = especialidade;
        this.curriculo = curriculo;
    }

    public Profissional(String nome, String email, String senha, String cpf, Especialidade especialidade, byte[] curriculo) {
        super(nome, email, senha, cpf);
        this.especialidade = especialidade;
        this.curriculo = curriculo;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public byte[] getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(byte[] curriculo) {
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
                ", curriculoBytes=" + curriculo.length +
                '}';
    }
}
