package br.com.alura.orcamentoapi.repository;

import br.com.alura.orcamentoapi.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByDescricao(String descricao);
    List<Receita> findByDataBetween(LocalDate dataIni, LocalDate dataFin);
}
