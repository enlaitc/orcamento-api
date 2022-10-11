package br.com.alura.orcamentoapi.model.FORM;

import br.com.alura.orcamentoapi.model.CategoriaDespesa;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RequestSaveDespesa {
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private CategoriaDespesa categoria;
    private ResponseUser usuario;
}
