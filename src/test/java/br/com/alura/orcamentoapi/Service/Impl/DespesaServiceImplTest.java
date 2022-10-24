package br.com.alura.orcamentoapi.Service.Impl;

import br.com.alura.orcamentoapi.model.CategoriaDespesa;
import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.FORM.RequestDespesa;
import br.com.alura.orcamentoapi.model.FORM.ResponseUser;
import br.com.alura.orcamentoapi.model.Usuario;
import br.com.alura.orcamentoapi.repository.DespesaRepository;
import br.com.alura.orcamentoapi.service.UsuarioService;
import br.com.alura.orcamentoapi.service.impl.DespesaServiceImpl;
import br.com.alura.orcamentoapi.util.DespesaCreate;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Collections;
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
    private DespesaRepository repositoryMock;

    @Mock
    private UsuarioService usuarioServiceMock;

    @Test
    @DisplayName("Save create Despesa with RequestDespesa when successful")
    void save_SaveDespesa_WhenSuccessful() {
        RequestDespesa rDespesa = DespesaCreate.mockRequestDespesa();
        Usuario usuario = DespesaCreate.mockUsuario();
        Despesa despesa = DespesaCreate.mockDespesa();
        despesa.setData(LocalDate.of(1999, 12, 1));

        Mockito.when(usuarioServiceMock.buscaUsuarioPorId(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(usuario);
        Mockito.when(repositoryMock.findByDescricao(ArgumentMatchers.any())).thenReturn(Collections.emptyList());
        Mockito.when(repositoryMock.save(ArgumentMatchers.any())).thenReturn(despesa);

        RequestDespesa result = service.adicionaDespesa(rDespesa);

        Mockito.verify(usuarioServiceMock, Mockito.times(1)).buscaUsuarioPorId(rDespesa.getUsuario().getId(), rDespesa.getUsuario().getNome());
        Mockito.verify(repositoryMock, Mockito.times(1)).save(ArgumentMatchers.any());
        Assertions.assertEquals(result, rDespesa);
    }

    @Test
    @DisplayName("Busca Todas Despesas return all Despesa when Successful")
    void buscaTodasDespesas_ReturnAllDespesa_WhenSuccessful() {
        Page<Despesa> despesa = new PageImpl<>(List.of(DespesaCreate.mockDespesa()));
        Page<RequestDespesa> rDespesaExpected = new PageImpl<>(List.of(DespesaCreate.mockRequestDespesa()));

        Mockito.when(repositoryMock.findAll(ArgumentMatchers.isA(Pageable.class))).thenReturn(despesa);

        ResponseEntity<Page<RequestDespesa>> result = service.buscaTodasDespesas(despesa.getPageable());

        Mockito.verify(repositoryMock, Mockito.times(1)).findAll(ArgumentMatchers.isA(Pageable.class));
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(result.getBody().getContent().get(0).getId(),rDespesaExpected.getContent().get(0).getId());
    }

    @Test
    @DisplayName("Busca Despesa Por Id return Despesa when Successful")
    void buscaDespesaPorId_ReturnDespesa_WhenSuccessful() {
        Long despesaId = 99L;
        Despesa despesaReturn = DespesaCreate.mockDespesa();
        RequestDespesa rDespesaExpected = DespesaCreate.mockRequestDespesa();

        Mockito.when(repositoryMock.findById(despesaId)).thenReturn(Optional.of(despesaReturn));

        ResponseEntity<RequestDespesa> result = service.buscaDespesaPorId(despesaId);

        Mockito.verify(repositoryMock, Mockito.times(1)).findById(despesaId);
        Assertions.assertTrue(result.hasBody());
        Assertions.assertEquals(result.getBody().getId(),rDespesaExpected.getId());
    }

    @Test
    @DisplayName("Busca Despesa Por Desc return list of Despesa by descricao when successful")
    void buscaDespesaPorDesc_ReturnListOfDespesa_WhenSuccessful() {
        String descricao = "descricao";
        List<Despesa> despesas = List.of(DespesaCreate.mockDespesa());
        List<RequestDespesa> listaReturn = List.of(DespesaCreate.mockRequestDespesa());

        Mockito.when(repositoryMock.findByDescricao(descricao)).thenReturn(despesas);

        ResponseEntity<List<RequestDespesa>> result = service.buscaDespesaPorDesc(descricao);

        Mockito.verify(repositoryMock, Mockito.times(1)).findByDescricao(descricao);
        Assertions.assertEquals(result.getBody().get(0).getId(), listaReturn.get(0).getId());
    }

    @Test
    @DisplayName("Busca Todas Despesas Por Mes return list of Despesa by year and month")
    void buscaTodasDespesasPorMes_ReturnListOfDespesa_WhenSuccessful() {
        List<Despesa> despesas = List.of(DespesaCreate.mockDespesa());
        List<RequestDespesa> listaReturn = List.of(DespesaCreate.mockRequestDespesa());
        int ano = LocalDate.now().getYear();
        int mes = LocalDate.now().getMonthValue();

        Mockito.when(repositoryMock.findByDataBetween(ArgumentMatchers.isA(LocalDate.class), ArgumentMatchers.isA(LocalDate.class))).thenReturn(despesas);

        ResponseEntity<List<RequestDespesa>> result = service.buscaTodasDespesasPorMes(ano, mes);

        Mockito.verify(repositoryMock, Mockito.times(1)).findByDataBetween(ArgumentMatchers.isA(LocalDate.class), ArgumentMatchers.isA(LocalDate.class));
        Assertions.assertEquals(result.getBody().get(0).getId(), listaReturn.get(0).getId());
    }

    @Test
    @DisplayName("Atualiza Despesa updates an Despesa when successful")
    void atualizaDespesa_UpdatesDespesa_WhenSuccessful() {
        Despesa despesa = createDespesa();
        Long despesaId = despesa.getId();
        RequestDespesa despesaUp = createRequestDespesa();
        despesaUp.setValor(BigDecimal.TEN);

        Mockito.when(repositoryMock.findById(despesaId)).thenReturn(Optional.of(despesa));
        Mockito.when(repositoryMock.save(ArgumentMatchers.any())).thenReturn(despesa);

        ResponseEntity<RequestDespesa> result = service.atualizaDespesa(despesaId, despesaUp);

        Mockito.verify(repositoryMock, Mockito.times(1)).findById(despesaId);
        Mockito.verify(repositoryMock, Mockito.times(1)).save(despesa);
        Assertions.assertEquals(result.getBody().getId(), despesaUp.getId());
    }

    @Test
    @DisplayName("Delete Despesa removes Despesa when successful")
    void deletaDespesa_RemovesDespesa_WhenSuccessful() {
        Despesa despesa = createDespesa();

        Mockito.when(repositoryMock.findById(despesa.getId())).thenReturn(Optional.of(despesa));

        service.deletaDespesa(despesa.getId());

        Mockito.verify(repositoryMock, Mockito.times(1)).deleteById(ArgumentMatchers.any(Long.class));
    }

//    @Test
//    @DisplayName("Busca Valor Total Por Categoria returns list of ValorCategoria between start date and end date when successful")
//    void buscaValorTotalPorCategoria_ReturnListOfValorCategoria_WhenSuccessful() {
//        int ano = LocalDate.now().getYear();
//        int mes = LocalDate.now().getMonthValue();
//        int dia = LocalDate.now().lengthOfMonth();
//        LocalDate dataFin = LocalDate.of(ano, mes, dia);
//        LocalDate dataIni = LocalDate.of(ano, mes, 1);
//
//        ValorCategoria valorCategoria = new ValorCategoria() {
//            @Override
//            public String getCategoria() {
//                return CategoriaDespesa.OUTRAS.getDescricao();
//            }
//
//            @Override
//            public BigDecimal getTotal() {
//                return BigDecimal.TEN;
//            }
//        };
//        Mockito.when(repository.buscaValorTotalPorCategoria(dataIni, dataFin)).thenReturn(List.of(valorCategoria));
//
//        List<ValorCategoria> result = service.buscaValorTotalPorCategoria(ano, mes);
//
//        Mockito.verify(repository).buscaValorTotalPorCategoria(dataIni, dataFin);
//        Assertions.assertEquals(result.get(0).getCategoria(), valorCategoria.getCategoria());
//        Assertions.assertEquals(result.get(0).getTotal(), valorCategoria.getTotal());
//
//
//    }
//
//    @Test
//    @DisplayName("Soma Todas Despesas Por Data returns the total value of all Despesa found between start date and end date when successful")
//    void somaTodasDespesasPorData_ReturnDespesaTotalValue_WhenSuccessful() {
//        int ano = LocalDate.now().getYear();
//        int mes = LocalDate.now().getMonthValue();
//        int dia = LocalDate.now().lengthOfMonth();
//        LocalDate dataFin = LocalDate.of(ano, mes, dia);
//        LocalDate dataIni = LocalDate.of(ano, mes, 1);
//        BigDecimal total = BigDecimal.TEN;
//
//        Mockito.when(repository.somaTodasDespesasPorData(dataIni, dataFin)).thenReturn(total);
//
//        BigDecimal result = service.somaTodasDespesasPorData(ano, mes);
//
//        Mockito.verify(repository, Mockito.times(1)).somaTodasDespesasPorData(dataIni, dataFin);
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(result, total);
//    }
//
//    @Test
//    @DisplayName("Soma Todas Despesas Por Data returns total 0 when Despesa not found")
//    void somaTodasDespesasPorData_ReturnTotal0_WhenNotFound() {
//        Mockito.when(repository.somaTodasDespesasPorData(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(null);
//
//        BigDecimal result = service.somaTodasDespesasPorData(2000, 1);
//
//        Mockito.verify(repository, Mockito.times(1)).somaTodasDespesasPorData(ArgumentMatchers.any(), ArgumentMatchers.any());
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(BigDecimal.ZERO, result);
//    }

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