package br.com.alura.orcamentoapi.model;

public enum CategoriaDespesa {

    ALIMENTAÇÃO("Alimentação"),
    SAÚDE("Saúde"),
    MORADIA("Moradia"),
    TRANSPORTE("Transporte"),
    EDUCAÇÃO("Educacao"),
    LAZER("Lazer"),
    IMPREVISTOS("Imprevistos"),
    OUTRAS("Outras");

    private final String descricao;

    CategoriaDespesa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

}
