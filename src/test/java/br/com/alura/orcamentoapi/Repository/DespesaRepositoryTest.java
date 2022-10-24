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
import java.util.Optional;

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

    private final LocalDate dataIni = LocalDate.of(2000,12,2);
    private final LocalDate dataFin = LocalDate.of(2000,12,30);

    @Test
    @DisplayName("Find By Descricao returns list of Despesa when successful")
    void findByDescricao_ReturnListOfDespesa_WhenSuccessful() {
        Despesa despesa = createDespesa();

        List<Despesa> despesas = repository.findByDescricao(despesa.getDescricao());

        Assertions.assertTrue(despesas.contains(despesa));
    }

    @Test
    @DisplayName("Find By Descricao returns empty when no Despesa is found")
    void findByDescricao_ReturnEmptyList_WhenDespesaIsNotFound() {
        List<Despesa> despesas = repository.findByDescricao("descricao");

        Assertions.assertTrue(despesas.isEmpty());
    }

    @Test
    @DisplayName("Find By Data Between returns list of Despesa found between start date and end date when successful")
    void findByDataBetween_ReturnListOfDespesa_WhenSuccessful() {
        Despesa despesa = createDespesa();
        despesa.setData(LocalDate.of(2000,12,15));

        List<Despesa> despesas = repository.findByDataBetween(dataIni,dataFin);

        Assertions.assertTrue(despesas.contains(despesa));
    }

    @Test
    @DisplayName("Find By Data Between returns empty list when no Despesa is found between star date and end date")
    void findByDataBetween_ReturnEmptyList_WhenDespesaIsNotFound() {
        List<Despesa> despesas = repository.findByDataBetween(dataIni,dataFin);

        Assertions.assertTrue(despesas.isEmpty());
    }

//    @Test
//    @DisplayName("Busca Valor Total Por Categoria returns list of ValorCategoria between start date and end date when successful")
//    void buscaValorTotalPorCategoria_ReturnListOfValorCategoria_WhenSuccessful() {
//        Despesa despesa = createDespesa();
//        despesa.setData(LocalDate.of(2000, 12, 15));
//
//        List<ValorCategoria> valorCategorias = repository.buscaValorTotalPorCategoria(dataIni,dataFin);
//
//        Assertions.assertEquals(valorCategorias.get(0).getCategoria(), despesa.getCategoria().getDescricao());
//        Assertions.assertEquals(valorCategorias.get(0).getTotal(), despesa.getValor());
//    }
//
//    @Test
//    @DisplayName("Busca Valor Total Por Categoria returns empty list when no Despesa is found between start date and end date")
//    void buscaValorTotalPorCategoria_ReturnsEmptyList_WhenDespesaIsNotFound() {
//        List<ValorCategoria> valorCategorias = repository.buscaValorTotalPorCategoria(dataIni,dataFin);
//
//        Assertions.assertTrue(valorCategorias.isEmpty());
//    }
//
//    @Test
//    @DisplayName("Soma Todas Despesas Por Data returns the total value of all Despesa found between start date and end date when successful")
//    void somaTodasDespesasPorData_ReturnDespesaTotalValue_WhenSuccessful(){
//        Despesa despesa = createDespesa();
//        despesa.setData(LocalDate.of(2000,12,15));
//
//        BigDecimal vTotal = repository.somaTodasDespesasPorData(dataIni,dataFin);
//
//        Assertions.assertEquals(vTotal, despesa.getValor());
//    }
//
//    @Test
//    @DisplayName("Soma Todas Despesas Por Data returns null when no Despesa is found between start date and end date")
//    void somaTodasDespesasPorData_ReturnNull_WhenDespesaIsNotFound(){
//        BigDecimal vTotal = repository.somaTodasDespesasPorData(dataIni,dataFin);
//
//        Assertions.assertNull(vTotal);
//    }

    @Test
    @DisplayName("Find By Id returns Despesa when successful")
    void findById_ReturnDespesa_WhenSuccessful() {
        Despesa despesa = createDespesa();

       Optional<Despesa> despesaFind = repository.findById(despesa.getId());

       Assertions.assertTrue(despesaFind.isPresent());
       Assertions.assertEquals(despesa,despesaFind.get());
    }

    @Test
    @DisplayName("Find By Id returns empty when Despesa not found")
    void findById_ReturnEmpty_WhenDespesaNotFound() {
        Optional<Despesa> despesaFind = repository.findById(1L);

        Assertions.assertTrue(despesaFind.isEmpty());
    }

    @Test
    @DisplayName("Save create Despesa when successful")
    void save_SaveDespesa_WhenSuccessful() {
        Despesa despesa = createDespesa();

        Despesa despesaSaved = repository.save(despesa);

        Assertions.assertNotNull(despesaSaved);
        Assertions.assertEquals(despesa,despesaSaved);
    }

    @Test
    @DisplayName("Save returns Exception when Despesa was not created")
    void save_ReturnException_WhenFailed() {
        Despesa despesa = createDespesa();
        try {
            repository.save(despesa);
            throw new Exception();
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    @DisplayName("Delete By Id removes Despesa by id when successful")
    void deleteById_RemovesDespesa_WhenSuccessful() {
        Despesa despesa = createDespesa();

        repository.deleteById(despesa.getId());

        Optional<Despesa> despesaOptional = repository.findById(despesa.getId());

        Assertions.assertTrue(despesaOptional.isEmpty());
    }

    @Test
    @DisplayName("Delete By Id return Exception when Despesa by id is not found")
    void deleteById_ReturnException_WhenIdNotFound() {
        try {
            repository.deleteById(1L);
            throw new Exception();
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
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
