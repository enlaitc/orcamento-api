package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.Resumo;
import br.com.alura.orcamentoapi.model.ValorCategoria;
import br.com.alura.orcamentoapi.service.DespesaService;
import br.com.alura.orcamentoapi.service.ReceitaService;
import br.com.alura.orcamentoapi.service.ResumoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class ResumoServiceImpl implements ResumoService {

    public ReceitaService receitaService;
    public DespesaService despesaService;

    @Override
    public Resumo resumoMes(int ano, int mes) {
        BigDecimal vTotalDespesa = despesaService.somaTodasDespesasPorData(ano, mes);
        BigDecimal vTotalReceita = receitaService.somaTodasReceitasPorData(ano, mes);
        List<ValorCategoria> valorCategorias = despesaService.buscaValorTotalPorCategoria(ano, mes);

        return new Resumo(
                vTotalReceita,
                vTotalDespesa,
                (vTotalReceita.subtract(vTotalDespesa)),
                valorCategorias

        );
    }
}
