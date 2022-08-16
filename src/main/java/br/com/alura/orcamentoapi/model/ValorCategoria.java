package br.com.alura.orcamentoapi.model;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface ValorCategoria {

    @Value("#{target.categoria.descricao}")
    String getCategoria();

    BigDecimal getTotal();
}
