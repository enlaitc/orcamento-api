package br.com.alura.orcamentoapi.repository;

import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.ValorCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByDescricao(String descricao);

    List<Despesa> findByDataBetween(LocalDate dataIni, LocalDate dataFin);

    @Query("SELECT d.categoria AS categoria , SUM(d.valor) AS total FROM Despesa d WHERE d.data >= :dataIni AND d.data <= :dataFin GROUP BY d.categoria")
    List<ValorCategoria> buscaValorTotalPorCategoria(LocalDate dataIni, LocalDate dataFin);

    @Query("SELECT SUM(d.valor) FROM Despesa d WHERE d.data BETWEEN :dataIni AND :dataFin")
    BigDecimal somaTodasDespesasPorData(LocalDate dataIni, LocalDate dataFin);

}
