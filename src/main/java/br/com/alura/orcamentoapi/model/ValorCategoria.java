package br.com.alura.orcamentoapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

public interface ValorCategoria {

//    private CategoriaDespesa categoria;
//    private Float valor;
//
//    public ValorCategoria(CategoriaDespesa categoria, Float valor){
//        this.categoria = categoria;
//        this.valor = valor;
//    }

    @Value("#{target.categoria.descricao}")
    String getCategoria();
    Float getTotal();
}
