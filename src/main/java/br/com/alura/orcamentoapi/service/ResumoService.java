package br.com.alura.orcamentoapi.service;

import br.com.alura.orcamentoapi.model.Resumo;
import org.springframework.http.ResponseEntity;

public interface ResumoService {

    ResponseEntity<Resumo> resumoMes (int ano, int mes);
}
