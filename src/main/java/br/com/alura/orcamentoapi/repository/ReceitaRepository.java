package br.com.alura.orcamentoapi.repository;

import br.com.alura.orcamentoapi.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByDescricao(String descricao);

    List<Receita> findByDataBetween(LocalDate dataIni, LocalDate dataFin);

    @Query("SELECT SUM(d.valor) FROM Receita d WHERE d.data BETWEEN :dataIni AND :dataFin")
    BigDecimal somaTodasReceitasPorData(LocalDate dataIni, LocalDate dataFin);
}
