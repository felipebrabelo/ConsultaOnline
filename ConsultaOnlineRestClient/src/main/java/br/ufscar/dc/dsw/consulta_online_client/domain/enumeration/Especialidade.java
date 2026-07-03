package br.ufscar.dc.dsw.consulta_online_client.domain.enumeration;

public enum Especialidade {
    MEDICINA("Medicina"),
    PSICOLOGIA("Psicologia"),
    ADVOCACIA("Advocacia"),
    NUTRICAO("Nutrição"),
    CONTABILIDADE("Contabilidade"),
    FONOAUDIOLOGIA("Fonoaudiologia"),
    CONSULTORIA_FINANCEIRA("Consultoria Financeira"),
    TUTORIA("Tutoria");

    private final String descricao;

    Especialidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
