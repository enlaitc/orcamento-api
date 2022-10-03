package br.com.alura.orcamentoapi.service;

import br.com.alura.orcamentoapi.model.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ReceitaService {

    Receita adicionaReceita(Receita receita);

    Page<Receita> buscaTodasReceitas(Pageable pageable);

    Receita buscaReceitaPorId(Long receitaId);

    List<Receita> buscaReceitaPorDesc(String receitaDesc);

    List<Receita> buscaTodasReceitasPorMes(int ano, int mes);

    ResponseEntity<Receita> atualizaReceita(Long receitaId, Receita body);

    ResponseEntity<Void> deletaReceita(Long receitaId);

    BigDecimal somaTodasReceitasPorData(int ano, int mes);
}
