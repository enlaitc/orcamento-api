package br.com.alura.orcamentoapi.service;

import br.com.alura.orcamentoapi.model.FORM.RequestReceita;
import br.com.alura.orcamentoapi.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ReceitaService {

    RequestReceita adicionaReceita(RequestReceita receita);

    ResponseEntity<Page<RequestReceita>> buscaTodasReceitas(Pageable pageable);

    ResponseEntity<RequestReceita> buscaReceitaPorId(Long receitaId);

    ResponseEntity<List<RequestReceita>> buscaReceitaPorDesc(String receitaDesc);

    ResponseEntity<List<RequestReceita>> buscaTodasReceitasPorMes(int ano, int mes);

    ResponseEntity<RequestReceita> atualizaReceita(Long receitaId, RequestReceita body);

    ResponseEntity<Void> deletaReceita(Long receitaId);

    BigDecimal somaTodasReceitasPorData(int ano, int mes);
}
