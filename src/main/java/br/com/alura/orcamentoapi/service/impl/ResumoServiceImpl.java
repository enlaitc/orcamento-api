package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.*;
import br.com.alura.orcamentoapi.repository.DespesaRepository;
import br.com.alura.orcamentoapi.service.DespesaService;
import br.com.alura.orcamentoapi.service.ReceitaService;
import br.com.alura.orcamentoapi.service.ResumoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class ResumoServiceImpl implements ResumoService {

    public ReceitaService receitaService;
    public DespesaService despesaService;
    public DespesaRepository despesaRepository;

    @Override
    public Resumo resumoMes(int ano, int mes) {
        List<Despesa> vDespesa = despesaService.buscaTodasDespesasPorMes(ano, mes);
        Float vTotalDespesa = 0.0F;
        List<Receita> vReceita = receitaService.buscaTodasReceitasPorMes(ano, mes);
        Float vTotalReceita = 0.0F;
        int diaFin = LocalDate.of(ano, mes, 1).lengthOfMonth();

        List<ValorCategoria> valorCategorias = despesaRepository.buscaTodosValoresPorCategoria(
                LocalDate.of(ano, mes,1)
                ,LocalDate.of(ano, mes,diaFin)
        );

        for (Despesa d:vDespesa) {
            vTotalDespesa += d.getValor();

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
