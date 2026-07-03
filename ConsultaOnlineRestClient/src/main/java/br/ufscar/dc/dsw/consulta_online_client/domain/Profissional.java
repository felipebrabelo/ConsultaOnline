package br.ufscar.dc.dsw.consulta_online_client.domain;

import br.ufscar.dc.dsw.consulta_online_client.domain.enumeration.Especialidade;
import br.ufscar.dc.dsw.consulta_online_client.domain.enumeration.Papel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Profissional {

    private Long id;

    @NotBlank(message = "{usuario.nome.notBlank}")
    @Size(min = 3, max = 60, message = "{usuario.nome.size}")
    private String nome;

    @NotBlank(message = "{usuario.email.notBlank}")
    @Email(message = "{usuario.email.email}")
    @Size(max = 254, message = "{usuario.email.size}")
    private String email;

    @Size(max = 64, message = "{usuario.senha.size}")
    private String senha;

    @NotBlank(message = "{usuario.cpf.notBlank}")
    @Pattern(regexp = "\\d{11}", message = "{usuario.cpf.pattern}")
    private String cpf;

    private Papel papel = Papel.PROFISSIONAL;

    private Boolean ativo = true;

    @NotNull(message = "{profissional.especialidade.notNull}")
    private Especialidade especialidade;

    private FileEntity curriculo;

    private String curriculoNome;

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
        if (curriculo != null) {
            this.curriculoNome = curriculo.getName();
        }
    }

    public String getCurriculoNome() {
        return curriculoNome;
    }

    public void setCurriculoNome(String curriculoNome) {
        this.curriculoNome = curriculoNome;
    }

    public void prepararParaEnvio() {
        this.papel = Papel.PROFISSIONAL;
        this.ativo = this.ativo == null ? Boolean.TRUE : this.ativo;
    }
}
