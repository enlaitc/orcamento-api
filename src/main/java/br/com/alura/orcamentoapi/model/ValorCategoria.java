package br.com.alura.orcamentoapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValorCategoria {

    private CategoriaDespesa categoria;
    private Float valor;

    public ValorCategoria(CategoriaDespesa categoria, Float valor){
        this.categoria = categoria;
        this.valor = valor;
    }
}
