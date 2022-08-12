package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.*;
import br.com.alura.orcamentoapi.service.DespesaService;
import br.com.alura.orcamentoapi.service.ReceitaService;
import br.com.alura.orcamentoapi.service.ResumoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class ResumoServiceImpl implements ResumoService {

    public ReceitaService receitaService;
    public DespesaService despesaService;

    @Override
    public Resumo resumoMes(int ano, int mes) {
        List<Despesa> vDespesa = despesaService.buscaTodasDespesasPorMes(ano, mes);
        Float vTotalDespesa = 0.0F;
        List<Receita> vReceita = receitaService.buscaTodasReceitasPorMes(ano, mes);
        Float vTotalReceita = 0.0F;
        List<ValorCategoria> valorCategorias = new ArrayList<>();

        for (Despesa d:vDespesa) {
            vTotalDespesa += d.getValor();
            ValorCategoria v = new ValorCategoria(d.getCategoria(),despesaService.valorPorCategoria(d.getCategoria(),ano,mes));
            valorCategorias.add(v);

        }

        for (Receita r:vReceita) {
            vTotalReceita += r.getValor();
        }

        return new Resumo(
                vTotalReceita,
                vTotalDespesa,
                (vTotalReceita - vTotalDespesa),
                valorCategorias

        );
    }
}
