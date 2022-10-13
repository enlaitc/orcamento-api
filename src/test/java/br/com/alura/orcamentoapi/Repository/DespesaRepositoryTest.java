package br.com.alura.orcamentoapi.Repository;

import br.com.alura.orcamentoapi.model.CategoriaDespesa;
import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.FORM.RequestDespesa;
import br.com.alura.orcamentoapi.model.FORM.ResponseUser;
import br.com.alura.orcamentoapi.model.Usuario;
import br.com.alura.orcamentoapi.model.ValorCategoria;
import br.com.alura.orcamentoapi.repository.DespesaRepository;
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
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@DisplayName("Testes de Despesa Repository")
class DespesaRepositoryTest {

    @Autowired
    private DespesaRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    void get_RetornaListaDeDespesasPorDescricao_WhenSuccessful() {
        String descricao = "conta";
        Despesa despesa = createDespesa();
        despesa.setDescricao(descricao);

        List<Despesa> despesas = repository.findByDescricao(descricao);

        Assertions.assertTrue(despesas.stream().anyMatch(d -> d.getDescricao().equals(descricao)));
    }

    @Test
    void get_NaoRetornaListaDeDespesasPorDescricao_WhenFailed() {
        String descricao = "inexistente";

        List<Despesa> despesas = repository.findByDescricao(descricao);

        Assertions.assertTrue(despesas.stream().noneMatch(d -> d.getDescricao().equals(descricao)));
    }

    @Test
    void get_RetornaListaDeDespesasPorDataEntreDataInicialEDataFinal_WhenSuccessful() {
        LocalDate dataIni = LocalDate.of(2000,12,2);
        LocalDate dataFin = LocalDate.of(2000,12,30);

        Despesa despesa = createDespesa();
        despesa.setData(LocalDate.of(2000,12,15));
        em.persist(despesa);

        List<Despesa> despesas = repository.findByDataBetween(dataIni,dataFin);

        Assertions.assertTrue(despesas.stream().anyMatch(d -> d.getData().equals(despesa.getData())));
    }

    @Test
    void get_naoRetornaListaDeDespesasPorDataEntreDataInicialEDataFinal_WhenFailed() {
        LocalDate dataIni = LocalDate.of(2000,12,2);
        LocalDate dataFin = LocalDate.of(2000,12,30);

        Despesa despesa = createDespesa();
        despesa.setData(LocalDate.of(2000,12,1));
        em.persist(despesa);

        List<Despesa> despesas = repository.findByDataBetween(dataIni,dataFin);

        Assertions.assertTrue(despesas.isEmpty());
    }

    @Test
    void get_RetornaValorTotalPorCategoriaECategoriasEntreDataInicialEDataFinal_WhenSuccessful() {
        LocalDate dataIni = LocalDate.of(2000,12,2);
        LocalDate dataFin = LocalDate.of(2000,12,30);

        Despesa despesa = createDespesa();
        em.persist(despesa);

        List<ValorCategoria> valorCategorias = repository.buscaValorTotalPorCategoria(dataIni,dataFin);

        Assertions.assertTrue(valorCategorias.stream().allMatch(
                c -> c.getCategoria().equals(despesa.getCategoria().getDescricao())
                && c.getTotal().equals(despesa.getValor())));
    }

    @Test
    void get_NaoRetornaValorTotalPorCategoriaECategoriasEntreDataInicialEDataFinal_WhenFailed() {
        LocalDate dataIni = LocalDate.of(2000,12,2);
        LocalDate dataFin = LocalDate.of(2000,12,30);

        Despesa despesa = createDespesa();
        despesa.setData(LocalDate.of(2000, 12, 1));
        em.persist(despesa);

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
