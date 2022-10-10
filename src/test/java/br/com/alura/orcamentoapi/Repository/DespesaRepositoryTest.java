package br.com.alura.orcamentoapi.Repository;

import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.repository.DespesaRepository;
import org.junit.jupiter.api.Assertions;
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
public class DespesaRepositoryTest {

    @Autowired
    private DespesaRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void deveriaBuscarUmaOuVariasDespesasPelaDescricao() {
        String descricao = "conta";

        Despesa conta = new Despesa();
        conta.setDescricao(descricao);
        conta.setValor(BigDecimal.ONE);
        conta.setData(LocalDate.now());
        em.persist(conta);

        List<Despesa> despesas = repository.findByDescricao(descricao);

        Assertions.assertFalse(despesas.stream().noneMatch(d -> d.getDescricao().equals(descricao)));
    }

    @Test
    public void naoDeveriaBuscarUmaOuVariasDespesasPelaDescricao() {
        String descricao = "inexistente";

        List<Despesa> despesas = repository.findByDescricao(descricao);

        Assertions.assertTrue(despesas.stream().noneMatch(d -> d.getDescricao().equals(descricao)));
    }
}
