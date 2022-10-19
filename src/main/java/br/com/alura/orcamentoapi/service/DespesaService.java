package br.com.alura.orcamentoapi.service;

import br.com.alura.orcamentoapi.model.FORM.RequestDespesa;
import br.com.alura.orcamentoapi.model.Usuario;
import br.com.alura.orcamentoapi.model.ValorCategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface DespesaService {

    RequestDespesa adicionaDespesa(RequestDespesa despesa);

    ResponseEntity<Page<RequestDespesa>> buscaTodasDespesas(Pageable pageable);

    ResponseEntity<RequestDespesa> buscaDespesaPorId(Long despesaId);

    ResponseEntity<List<RequestDespesa>> buscaDespesaPorDesc(String despesaDesc);

    ResponseEntity<List<RequestDespesa>> buscaTodasDespesasPorMes(int ano, int mes);

    ResponseEntity<RequestDespesa> atualizaDespesa(Long despesaId, RequestDespesa despesaUp);

    ResponseEntity<Void> deletaDespesa(Long despesaId);

    List<ValorCategoria> buscaValorTotalPorCategoria(int ano, int mes, Usuario usuario);

    BigDecimal somaTodasDespesasPorData(int ano, int mes, Usuario usuario);
}
