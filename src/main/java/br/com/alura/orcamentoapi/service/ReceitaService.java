package br.com.alura.orcamentoapi.service;

import br.com.alura.orcamentoapi.model.Receita;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReceitaService {

    Receita adicionaReceita(Receita receita);
    List<Receita> buscaTodasReceitas();
    Receita buscaReceita(Long receitaId);
    ResponseEntity<Receita> atualizaReceita(Long receitaId, Receita body);
    ResponseEntity<Void> deletaReceita(Long receitaId);
}
