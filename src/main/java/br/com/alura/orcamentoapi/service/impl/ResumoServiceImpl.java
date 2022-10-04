package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.Resumo;
import br.com.alura.orcamentoapi.model.ValorCategoria;
import br.com.alura.orcamentoapi.service.DespesaService;
import br.com.alura.orcamentoapi.service.ReceitaService;
import br.com.alura.orcamentoapi.service.ResumoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class ResumoServiceImpl implements ResumoService {

    final ReceitaService receitaService;
    final DespesaService despesaService;

    @Override
    public ResponseEntity<Resumo> resumoMes(int ano, int mes) {
        try{
            BigDecimal vTotalDespesa = despesaService.somaTodasDespesasPorData(ano, mes);
            BigDecimal vTotalReceita = receitaService.somaTodasReceitasPorData(ano, mes);
            List<ValorCategoria> valorCategorias = despesaService.buscaValorTotalPorCategoria(ano, mes);

            Resumo resumo = new Resumo(
                    vTotalReceita,
                    vTotalDespesa,
                    (vTotalReceita.subtract(vTotalDespesa)),
                    valorCategorias

            );

            return ResponseEntity.ok(resumo);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }
}
