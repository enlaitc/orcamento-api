package br.com.alura.orcamentoapi.service;

import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.ValorCategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface DespesaService {

    Despesa adicionaDespesa(Despesa despesa);

    Page<Despesa> buscaTodasDespesas(Pageable pageable);

    Despesa buscaDespesaPorId(Long despesaId);

    List<Despesa> buscaDespesaPorDesc(String despesaDesc);

    List<Despesa> buscaTodasDespesasPorMes(int ano, int mes);

    ResponseEntity<Despesa> atualizaDespesa(Long despesaId, Despesa despesaUp);

    ResponseEntity<Void> deletaDespesa(Long despesaId);

    List<ValorCategoria> buscaValorTotalPorCategoria(int ano, int mes);

    BigDecimal somaTodasDespesasPorData(int ano, int mes);
}
