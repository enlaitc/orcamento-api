package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.CategoriaDespesa;
import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.FORM.RequestDespesa;
import br.com.alura.orcamentoapi.model.Usuario;
import br.com.alura.orcamentoapi.model.ValorCategoria;
import br.com.alura.orcamentoapi.repository.DespesaRepository;
import br.com.alura.orcamentoapi.service.UsuarioService;
import br.com.alura.orcamentoapi.util.Creator;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        RequestDespesa rDespesa = Creator.mockRequestDespesaGenerico();
        Usuario usuario = Creator.mockUsuarioGenerico();
        Despesa despesa = Creator.mockDespesaGenerico();
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
    @DisplayName("Save throw RunTimeException when descricao already exist in this month")
    void save_ThrowRunTimeException_WhenDescricaoAlreadyExistInThisMonth() {
        RequestDespesa rDespesa = Creator.mockRequestDespesaGenerico();
        Despesa despesa = Creator.mockDespesaGenerico();

        Mockito.when(repositoryMock.findByDescricao(ArgumentMatchers.any())).thenReturn(List.of(despesa));

        Assertions.assertThrows(RuntimeException.class, () -> service.adicionaDespesa(rDespesa), "Mes e data repetidos.");

        Mockito.verify(usuarioServiceMock, Mockito.never()).buscaUsuarioPorId(ArgumentMatchers.any(),ArgumentMatchers.any());
        Mockito.verify(repositoryMock, Mockito.never()).save(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Busca Todas Despesas return all Despesa when Successful")
    void buscaTodasDespesas_ReturnAllDespesa_WhenSuccessful() {
        Page<Despesa> despesa = new PageImpl<>(List.of(Creator.mockDespesaGenerico()));
        Page<RequestDespesa> rDespesaExpected = new PageImpl<>(List.of(Creator.mockRequestDespesaGenerico()));

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
        Despesa despesaReturn = Creator.mockDespesaGenerico();
        RequestDespesa rDespesaExpected = Creator.mockRequestDespesaGenerico();

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
        List<Despesa> despesas = List.of(Creator.mockDespesaGenerico());
        List<RequestDespesa> listaReturn = List.of(Creator.mockRequestDespesaGenerico());

        Mockito.when(repositoryMock.findByDescricao(descricao)).thenReturn(despesas);

        ResponseEntity<List<RequestDespesa>> result = service.buscaDespesaPorDesc(descricao);

        Mockito.verify(repositoryMock, Mockito.times(1)).findByDescricao(descricao);
        Assertions.assertEquals(result.getBody().get(0).getId(), listaReturn.get(0).getId());
    }

    @Test
    @DisplayName("Busca Todas Despesas Por Mes return list of Despesa by year and month")
    void buscaTodasDespesasPorMes_ReturnListOfDespesa_WhenSuccessful() {
        List<Despesa> despesas = List.of(Creator.mockDespesaGenerico());
        List<RequestDespesa> listaReturn = List.of(Creator.mockRequestDespesaGenerico());
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
        Despesa despesa = Creator.mockDespesaGenerico();
        Long despesaId = despesa.getId();
        RequestDespesa despesaUp = Creator.mockRequestDespesaGenerico();
        despesaUp.setValor(BigDecimal.TEN);

        Mockito.when(repositoryMock.findById(despesaId)).thenReturn(Optional.of(despesa));
        Mockito.when(repositoryMock.save(ArgumentMatchers.any(Despesa.class))).thenReturn(despesa);

        ResponseEntity<RequestDespesa> result = service.atualizaDespesa(despesaId, despesaUp);

        Mockito.verify(repositoryMock, Mockito.times(1)).findById(despesaId);
        Mockito.verify(repositoryMock, Mockito.times(1)).save(ArgumentMatchers.any(Despesa.class));
        Assertions.assertEquals(result.getBody().getId(), despesaUp.getId());
    }

    @Test
    @DisplayName("Delete Despesa removes Despesa when successful")
    void deletaDespesa_RemovesDespesa_WhenSuccessful() {
        Despesa despesa = Creator.mockDespesaGenerico();

        Mockito.when(repositoryMock.findById(despesa.getId())).thenReturn(Optional.of(despesa));

        Assertions.assertDoesNotThrow(() -> service.deletaDespesa(despesa.getId()));

        Mockito.verify(repositoryMock, Mockito.times(1)).deleteById(ArgumentMatchers.any(Long.class));
    }

    @Test
    @DisplayName("Busca Valor Total Por Categoria returns list of ValorCategoria between start date and end date when successful")
    void buscaValorTotalPorCategoria_ReturnListOfValorCategoria_WhenSuccessful() {
        int ano = LocalDate.now().getYear();
        int mes = LocalDate.now().getMonthValue();
        Usuario usuario = Creator.mockUsuarioGenerico();
        ValorCategoria valorCategoria = Mockito.mock(ValorCategoria.class);
        Mockito.when(valorCategoria.getCategoria()).thenReturn(CategoriaDespesa.OUTRAS.getDescricao());
        Mockito.when(valorCategoria.getTotal()).thenReturn(BigDecimal.TEN);

        Mockito.when(repositoryMock.buscaValorTotalPorCategoria(ArgumentMatchers.isA(LocalDate.class), ArgumentMatchers.isA(LocalDate.class), ArgumentMatchers.eq(usuario)))
                .thenReturn(List.of(valorCategoria));

        List<ValorCategoria> result = service.buscaValorTotalPorCategoria(ano, mes, usuario);

        Mockito.verify(repositoryMock, Mockito.times(1))
                .buscaValorTotalPorCategoria(ArgumentMatchers.isA(LocalDate.class), ArgumentMatchers.isA(LocalDate.class), ArgumentMatchers.eq(usuario));
        Assertions.assertEquals(result.get(0).getCategoria(), valorCategoria.getCategoria());
        Assertions.assertEquals(result.get(0).getTotal(), valorCategoria.getTotal());
    }

    @Test
    @DisplayName("Soma Todas Despesas Por Data returns the total value of all Despesa found between start date and end date when successful")
    void somaTodasDespesasPorData_ReturnDespesaTotalValue_WhenSuccessful() {
        int ano = LocalDate.now().getYear();
        int mes = LocalDate.now().getMonthValue();
        Usuario usuario = Creator.mockUsuarioGenerico();
        BigDecimal total = BigDecimal.TEN;

        Mockito.when(repositoryMock.somaTodasDespesasPorData(ArgumentMatchers.isA(LocalDate.class), ArgumentMatchers.isA(LocalDate.class), ArgumentMatchers.eq(usuario)))
                .thenReturn(total);

        BigDecimal result = service.somaTodasDespesasPorData(ano, mes, usuario);

        Mockito.verify(repositoryMock, Mockito.times(1))
                .somaTodasDespesasPorData(ArgumentMatchers.isA(LocalDate.class), ArgumentMatchers.isA(LocalDate.class), ArgumentMatchers.eq(usuario));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, total);
    }

    @Test
    @DisplayName("Soma Todas Despesas Por Data returns total 0 when Despesa not found")
    void somaTodasDespesasPorData_ReturnTotal0_WhenNotFound() {
        Mockito.when(repositoryMock.somaTodasDespesasPorData(ArgumentMatchers.isA(LocalDate.class), ArgumentMatchers.isA(LocalDate.class), ArgumentMatchers.isA(Usuario.class)))
                .thenReturn(null);

        BigDecimal result = service.somaTodasDespesasPorData(2000, 1, Mockito.mock(Usuario.class));

        Mockito.verify(repositoryMock, Mockito.times(1)).somaTodasDespesasPorData(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(BigDecimal.ZERO, result);
    }
}