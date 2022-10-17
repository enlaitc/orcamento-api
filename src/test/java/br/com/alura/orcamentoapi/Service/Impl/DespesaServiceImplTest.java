package br.com.alura.orcamentoapi.Service.Impl;

import br.com.alura.orcamentoapi.model.CategoriaDespesa;
import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.FORM.RequestDespesa;
import br.com.alura.orcamentoapi.model.FORM.ResponseUser;
import br.com.alura.orcamentoapi.model.Usuario;
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
        despesa.setData(LocalDate.of(1999,12,1));

        Mockito.when(usuarioService.buscaUsuarioPorId(rDespesa.getUsuario().getId(), rDespesa.getUsuario().getNome())).thenReturn(usuario);
        Mockito.when(repository.findByDescricao(ArgumentMatchers.any())).thenReturn(List.of(despesa));
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(despesa);

        service.adicionaDespesa(rDespesa);

        Mockito.verify(usuarioService, Mockito.times(1)).buscaUsuarioPorId(rDespesa.getUsuario().getId(),rDespesa.getUsuario().getNome());
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
    @DisplayName("Testes de Despesa Repository")
    void buscaDespesaPorDesc() {
    }

    @Test
    @DisplayName("Testes de Despesa Repository")
    void buscaTodasDespesasPorMes() {
    }

    @Test
    @DisplayName("Testes de Despesa Repository")
    void atualizaDespesa() {
    }

    @Test
    @DisplayName("Testes de Despesa Repository")
    void deletaDespesa() {
    }

    @Test
    @DisplayName("Testes de Despesa Repository")
    void buscaValorTotalPorCategoria() {
    }

    @Test
    @DisplayName("Testes de Despesa Repository")
    void somaTodasDespesasPorData() {
    }

    @Test
    @DisplayName("Testes de Despesa Repository")
    void devolveDespesaSeExistir() {
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