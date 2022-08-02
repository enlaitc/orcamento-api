package br.com.alura.orcamentoapi.repository;

import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByDescricao(String descricao);
    List<Despesa> findByData(LocalDate data);
}
