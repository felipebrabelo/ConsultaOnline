package br.ufscar.dc.dsw.agendamento_online.domain.enumeration;

public enum Papel {

    ROLE_CLIENTE("Cliente"),
    ROLE_ADMIN("Administrador"),
    ROLE_PROFISSIONAL("Profissional");

    private final String descricao;

    Papel(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
