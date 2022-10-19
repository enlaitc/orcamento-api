package br.com.alura.orcamentoapi.model;

import br.com.alura.orcamentoapi.model.FORM.ResponseUser;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Resumo {

    private BigDecimal vTotalReceitas;
    private BigDecimal vTotalDespesas;
    private BigDecimal saldoFinal;
    private List<ValorCategoria> valorCategoria;
    private ResponseUser user;

    public Resumo(BigDecimal vTotalReceitas, BigDecimal vTotalDespesas, BigDecimal saldoFinal, List<ValorCategoria> valorCategoria, ResponseUser user) {
        this.vTotalReceitas = vTotalReceitas;
        this.vTotalDespesas = vTotalDespesas;
        this.saldoFinal = saldoFinal;
        this.valorCategoria = valorCategoria;
        this.user = user;
    }
}
