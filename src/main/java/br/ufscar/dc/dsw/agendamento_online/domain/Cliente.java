package br.ufscar.dc.dsw.agendamento_online.domain;

import java.time.LocalDate;
import java.util.List;

import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Genero;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente extends Usuario {

    @Column(unique = true, length = 11)
    private String telefone;

    @Column(nullable = false, name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @OneToMany(mappedBy = "cliente")
    private List<Consulta> consultas;

    public Cliente() {

    }

    public Cliente(String telefone, LocalDate dataNascimento, Genero genero) {
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    public Cliente(String nome, String email, String senha, String cpf, String telefone, LocalDate dataNascimento,
            Genero genero) {
        super(nome, email, senha, cpf);
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", telefone='" + telefone + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", genero=" + genero +
                '}';
    }
}
