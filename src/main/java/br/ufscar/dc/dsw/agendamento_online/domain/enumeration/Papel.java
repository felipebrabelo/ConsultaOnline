package br.ufscar.dc.dsw.agendamento_online.domain.enumeration;

public enum Papel {

    CLIENTE("Cliente"),
    ADMIN("Administrador"),
    PROFISSIONAL("Profissional");

    private final String descricao;

    Papel(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
