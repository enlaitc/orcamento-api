package br.com.alura.orcamentoapi.model.FORM;

import br.com.alura.orcamentoapi.model.Receita;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestReceita {
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private ResponseUser usuario;

    public static RequestReceita converter(Receita receita){
        return new RequestReceita(
                receita.getId(),
                receita.getDescricao(),
                receita.getValor(),
                receita.getData(),
                new ResponseUser(
                        receita.getUser().getId(),
                        receita.getUser().getNome()
                )
        );
    }
}
