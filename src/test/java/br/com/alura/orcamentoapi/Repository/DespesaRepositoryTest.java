package br.com.alura.orcamentoapi.Repository;

import br.com.alura.orcamentoapi.model.CategoriaDespesa;
import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.ValorCategoria;
import br.com.alura.orcamentoapi.repository.DespesaRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DisplayName("Testes de Despesa Repository")
@Log4j2
class DespesaRepositoryTest {

    @Autowired
    private DespesaRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Find By Descricao returns list of Despesa when successful")
    void findByDescricao_ReturnListOfDespesa_WhenSuccessful() {
        Despesa despesa = createDespesa();

        List<Despesa> despesas = repository.findByDescricao(despesa.getDescricao());

        Assertions.assertTrue(despesas.contains(despesa));
    }

    @Test
    @DisplayName("Find By Descricao returns empty when no Despesa is not found")
    void findByDescricao_ReturnEmptyList_WhenDespesaIsNotFound() {
        List<Despesa> despesas = repository.findByDescricao("descricao");

        Assertions.assertTrue(despesas.isEmpty());
    }

    @Test
    @DisplayName("Find By Data Between returns list of Despesa between start date and end date when successful")
    void findByDataBetween_ReturnListOfDespesa_WhenSuccessful() {
        LocalDate dataIni = LocalDate.of(2000,12,2);
        LocalDate dataFin = LocalDate.of(2000,12,30);

        Despesa despesa = createDespesa();
        despesa.setData(LocalDate.of(2000,12,15));

        List<Despesa> despesas = repository.findByDataBetween(dataIni,dataFin);

        Assertions.assertTrue(despesas.contains(despesa));
    }

    @Test
    @DisplayName("Find By Data Between returns empty list when no Despesa is between star date and end date")
    void findByDataBetween_ReturnEmptyList_WhenSuccessful() {
        LocalDate dataIni = LocalDate.of(2000,12,2);
        LocalDate dataFin = LocalDate.of(2000,12,30);

        List<Despesa> despesas = repository.findByDataBetween(dataIni,dataFin);

        Assertions.assertTrue(despesas.isEmpty());
    }

    @Test
    @DisplayName("Busca Valor Total Por Categoria returns list of ValorCategoria between start date and end date when successful")
    void buscaValorTotalPorCategoria_ReturnListOfValorCategoria_WhenSuccessful() {
        LocalDate dataIni = LocalDate.of(2000,12,2);
        LocalDate dataFin = LocalDate.of(2000,12,30);

        Despesa despesa = createDespesa();
        despesa.setData(LocalDate.of(2000, 12, 15));

        List<ValorCategoria> valorCategorias = repository.buscaValorTotalPorCategoria(dataIni,dataFin);

        Assertions.assertEquals(valorCategorias.get(0).getCategoria(), despesa.getCategoria().getDescricao());
        Assertions.assertEquals(valorCategorias.get(0).getTotal(), despesa.getValor());
    }

    @Test
    void get_NaoRetornaValorTotalPorCategoriaECategoriasEntreDataInicialEDataFinal_WhenFailed() {
        LocalDate dataIni = LocalDate.of(2000,12,2);
        LocalDate dataFin = LocalDate.of(2000,12,30);

        Despesa despesa = createDespesa();
        despesa.setData(LocalDate.of(2000, 12, 1));

        List<ValorCategoria> valorCategorias = repository.buscaValorTotalPorCategoria(dataIni,dataFin);

        Assertions.assertFalse(valorCategorias.stream().anyMatch(
                c -> c.getCategoria().equals(despesa.getCategoria().getDescricao())
                        && c.getTotal().equals(despesa.getValor())));
    }

    private Despesa createDespesa() {
        Despesa despesa = new Despesa();
        despesa.setDescricao("descricao");
        despesa.setValor(BigDecimal.ONE);
        despesa.setData(LocalDate.now());
        despesa.setCategoria(CategoriaDespesa.OUTRAS);
        em.persist(despesa);

        return despesa;
    }
}
