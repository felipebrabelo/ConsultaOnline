package br.ufscar.dc.dsw.agendamento_online.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufscar.dc.dsw.agendamento_online.domain.enumeration.Papel;
import br.ufscar.dc.dsw.agendamento_online.validation.UniqueCPF;
import br.ufscar.dc.dsw.agendamento_online.validation.UniqueEmail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@UniqueEmail
@UniqueCPF
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    @NotBlank(message = "{usuario.nome.notBlank}")
    @Size(min = 3, max = 60, message = "{usuario.nome.size}")
    private String nome;

    @Column(nullable = false, unique = true, length = 254)
    @NotBlank(message = "{usuario.email.notBlank}")
    @Email(message = "{usuario.email.email}")
    @Size(max = 254, message = "{usuario.email.size}")
    private String email;

    @Column(nullable = false, length = 150)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    @Column(nullable = false, unique = true, length = 11)
    @NotBlank(message = "{usuario.cpf.notBlank}")
    @Pattern(regexp = "\\d{11}", message = "{usuario.cpf.pattern}")
    private String cpf;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Papel papel;

    @Column(nullable = false)
    private Boolean ativo = true;

    public Usuario() {

    }

    public Usuario(String nome, String email, String senha, String cpf, Papel papel) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.papel = papel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
