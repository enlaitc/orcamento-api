package br.com.alura.orcamentoapi.model;

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

    public Resumo(BigDecimal vTotalReceitas, BigDecimal vTotalDespesas, BigDecimal saldoFinal, List<ValorCategoria> valorCategoria) {
        this.vTotalReceitas = vTotalReceitas;
        this.vTotalDespesas = vTotalDespesas;
        this.saldoFinal = saldoFinal;
        this.valorCategoria = valorCategoria;
    }
}
