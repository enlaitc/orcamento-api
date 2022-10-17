package br.com.alura.orcamentoapi.Service.Impl;

import br.com.alura.orcamentoapi.model.CategoriaDespesa;
import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.FORM.RequestDespesa;
import br.com.alura.orcamentoapi.model.FORM.ResponseUser;
import br.com.alura.orcamentoapi.model.Usuario;
import br.com.alura.orcamentoapi.model.ValorCategoria;
import br.com.alura.orcamentoapi.repository.DespesaRepository;
import br.com.alura.orcamentoapi.service.UsuarioService;
import br.com.alura.orcamentoapi.service.impl.DespesaServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DisplayName("Testes de Service Impl Despesa")
@Log4j2
class DespesaServiceImplTest {

    @InjectMocks
    private DespesaServiceImpl service;

    @Mock
    private DespesaRepository repository;

    @Mock
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Save create Despesa with RequestDespesa when successful")
    void save_SaveDespesa_WhenSuccessful() {
        RequestDespesa rDespesa = createRequestDespesa();
        Usuario usuario = createUsuario();
        Despesa despesa = createDespesa();
        despesa.setData(LocalDate.of(1999, 12, 1));

        Mockito.when(usuarioService.buscaUsuarioPorId(rDespesa.getUsuario().getId(), rDespesa.getUsuario().getNome())).thenReturn(usuario);
        Mockito.when(repository.findByDescricao(ArgumentMatchers.any())).thenReturn(List.of(despesa));
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(despesa);

        service.adicionaDespesa(rDespesa);

        Mockito.verify(usuarioService, Mockito.times(1)).buscaUsuarioPorId(rDespesa.getUsuario().getId(), rDespesa.getUsuario().getNome());
        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Busca Todas Despesas return all Despesa when Successful")
    void buscaTodasDespesas_ReturnAllDEspesa_WhenSuccessful() {
        Page<Despesa> despesa = new PageImpl<>(List.of(createDespesa()));
        Mockito.when(repository.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(despesa);

        ResponseEntity<Page<RequestDespesa>> ex = service.buscaTodasDespesas(despesa.getPageable());

        Mockito.verify(repository, Mockito.times(1)).findAll(ArgumentMatchers.isA(Pageable.class));
        Assertions.assertNotNull(ex.getBody());
        Assertions.assertFalse(ex.getBody().toList().isEmpty());
        Assertions.assertEquals(200, ex.getStatusCode().value());
        Assertions.assertEquals(despesa.toList().get(0).getDescricao(), ex.getBody().toList().get(0).getDescricao());
    }

    @Test
    @DisplayName("Busca Despesa Por Id return Despesa when Successful")
    void buscaDespesaPorId_ReturnDespesa_WhenSuccessful() {
        Long despesaId = 99L;
        Optional<Despesa> despesaReturn = Optional.of(createDespesa());
        Mockito.when(repository.findById(despesaId)).thenReturn(despesaReturn);

        ResponseEntity<RequestDespesa> ex = service.buscaDespesaPorId(despesaId);

        Assertions.assertEquals(1L, ex.getBody().getId());
        Mockito.verify(repository, Mockito.times(1)).findById(despesaId);
    }

    @Test
    @DisplayName("Busca Despesa Por Desc return list of Despesa by descricao when successful")
    void buscaDespesaPorDesc_ReturnListOfDespesa_WhenSuccessful() {
        String descricao = "descricao";
        List<Despesa> despesas = List.of(createDespesa());
        List<RequestDespesa> listaReturn = List.of(createRequestDespesa());

        Mockito.when(repository.findByDescricao(descricao)).thenReturn(despesas);

        ResponseEntity<List<RequestDespesa>> ex = service.buscaDespesaPorDesc(descricao);

        Mockito.verify(repository, Mockito.times(1)).findByDescricao(descricao);
        Assertions.assertEquals(ex.getBody().get(0).getId(), listaReturn.get(0).getId());
        Assertions.assertEquals(200, ex.getStatusCode().value());
    }

    @Test
    @DisplayName("Busca Todas Despesas Por Mes return list of Despesa by year and month")
    void buscaTodasDespesasPorMes_ReturnListOfDespesa_WhenSuccessful() {
        List<Despesa> despesas = List.of(createDespesa());
        List<RequestDespesa> listaReturn = List.of(createRequestDespesa());
        int ano = LocalDate.now().getYear();
        int mes = LocalDate.now().getMonthValue();
        int dia = LocalDate.now().lengthOfMonth();
        LocalDate dataFin = LocalDate.of(ano, mes, dia);
        LocalDate dataIni = LocalDate.of(ano, mes, 1);

        Mockito.when(repository.findByDataBetween(dataIni, dataFin)).thenReturn(despesas);

        ResponseEntity<List<RequestDespesa>> ex = service.buscaTodasDespesasPorMes(ano, mes);

        Mockito.verify(repository, Mockito.times(1)).findByDataBetween(dataIni, dataFin);
        Assertions.assertEquals(ex.getBody().get(0).getId(), listaReturn.get(0).getId());
        Assertions.assertEquals(200, ex.getStatusCode().value());

    }

    @Test
    @DisplayName("Atualiza Despesa updates an Despesa when successful")
    void atualizaDespesa_UpdatesDespesa_WhenSuccessful() {
        Despesa despesa = createDespesa();
        Long despesaId = despesa.getId();
        RequestDespesa despesaUp = createRequestDespesa();
        despesaUp.setValor(BigDecimal.TEN);

        Mockito.when(repository.findById(despesaId)).thenReturn(Optional.of(despesa));
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(despesa);

        ResponseEntity<RequestDespesa> ex = service.atualizaDespesa(despesaId, despesaUp);

        Mockito.verify(repository, Mockito.times(1)).findById(despesaId);
        Mockito.verify(repository, Mockito.times(1)).save(despesa);
        Assertions.assertEquals(ex.getBody().getId(), despesaUp.getId());
    }

    @Test
    @DisplayName("Delete Despesa removes Despesa when successful")
    void deletaDespesa_RemovesDespesa_WhenSuccessful() {
        Despesa despesa = createDespesa();

        Mockito.when(repository.findById(despesa.getId())).thenReturn(Optional.of(despesa));

        service.deletaDespesa(despesa.getId());

        Mockito.verify(repository, Mockito.times(1)).deleteById(ArgumentMatchers.any(Long.class));
    }

    @Test
    @DisplayName("Busca Valor Total Por Categoria returns list of ValorCategoria between start date and end date when successful")
    void buscaValorTotalPorCategoria_ReturnListOfValorCategoria_WhenSuccessful() {
        int ano = LocalDate.now().getYear();
        int mes = LocalDate.now().getMonthValue();
        int dia = LocalDate.now().lengthOfMonth();
        LocalDate dataFin = LocalDate.of(ano, mes, dia);
        LocalDate dataIni = LocalDate.of(ano, mes, 1);

        ValorCategoria valorCategoria = new ValorCategoria() {
            @Override
            public String getCategoria() {
                return CategoriaDespesa.OUTRAS.getDescricao();
            }

            @Override
            public BigDecimal getTotal() {
                return BigDecimal.TEN;
            }
        };
        Mockito.when(repository.buscaValorTotalPorCategoria(dataIni, dataFin)).thenReturn(List.of(valorCategoria));

        List<ValorCategoria> ex = service.buscaValorTotalPorCategoria(ano, mes);

        Mockito.verify(repository).buscaValorTotalPorCategoria(dataIni, dataFin);
        Assertions.assertEquals(ex.get(0).getCategoria(), valorCategoria.getCategoria());
        Assertions.assertEquals(ex.get(0).getTotal(), valorCategoria.getTotal());


    }

    @Test
    @DisplayName("Soma Todas Despesas Por Data returns the total value of all Despesa found between start date and end date when successful")
    void somaTodasDespesasPorData_ReturnDespesaTotalValue_WhenSuccessful() {
        int ano = LocalDate.now().getYear();
        int mes = LocalDate.now().getMonthValue();
        int dia = LocalDate.now().lengthOfMonth();
        LocalDate dataFin = LocalDate.of(ano, mes, dia);
        LocalDate dataIni = LocalDate.of(ano, mes, 1);
        BigDecimal total = BigDecimal.TEN;

        Mockito.when(repository.somaTodasDespesasPorData(dataIni, dataFin)).thenReturn(total);

        BigDecimal ex = service.somaTodasDespesasPorData(ano, mes);

        Mockito.verify(repository, Mockito.times(1)).somaTodasDespesasPorData(dataIni, dataFin);
        Assertions.assertNotNull(ex);
        Assertions.assertEquals(ex, total);
    }

    @Test
    @DisplayName("Soma Todas Despesas Por Data returns total 0 when Despesa not found")
    void somaTodasDespesasPorData_ReturnTotal0_WhenNotFound() {
        Mockito.when(repository.somaTodasDespesasPorData(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(null);

        BigDecimal ex = service.somaTodasDespesasPorData(2000, 1);

        Mockito.verify(repository, Mockito.times(1)).somaTodasDespesasPorData(ArgumentMatchers.any(), ArgumentMatchers.any());
        Assertions.assertNotNull(ex);
        Assertions.assertEquals(BigDecimal.ZERO, ex);
    }

    private Despesa createDespesa() {
        Despesa despesa = new Despesa();
        despesa.setId(1L);
        despesa.setDescricao("descricao");
        despesa.setValor(BigDecimal.ONE);
        despesa.setData(LocalDate.now());
        despesa.setCategoria(CategoriaDespesa.OUTRAS);
        despesa.setUser(createUsuario());

        return despesa;
    }

    private RequestDespesa createRequestDespesa() {
        RequestDespesa despesa = new RequestDespesa();
        despesa.setId(1L);
        despesa.setDescricao("descricao");
        despesa.setValor(BigDecimal.ONE);
        despesa.setData(LocalDate.now());
        despesa.setCategoria(CategoriaDespesa.OUTRAS);
        despesa.setUsuario(new ResponseUser(
                createUsuario().getId(),
                createUsuario().getNome()
        ));

        return despesa;
    }

    private Usuario createUsuario() {
        Usuario usuario = new Usuario(
                1L,
                "UsuarioTeste",
                "email@email.com",
                "123",
                null,
                null,
                null
        );

        return usuario;
    }
}