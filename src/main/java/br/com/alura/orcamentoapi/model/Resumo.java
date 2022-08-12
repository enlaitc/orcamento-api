package br.com.alura.orcamentoapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Resumo {

    private Float vTotalReceitas;
    private Float vTotalDespesas;
    private Float saldoFinal;
    private List<ValorCategoria> valorCategoria;

    public Resumo(Float vTotalReceitas, Float vTotalDespesas, Float saldoFinal, List<ValorCategoria> valorCategoria) {
        this.vTotalReceitas = vTotalReceitas;
        this.vTotalDespesas = vTotalDespesas;
        this.saldoFinal = saldoFinal;
        this.valorCategoria = valorCategoria;
    }
}
