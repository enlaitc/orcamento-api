package br.com.alura.orcamentoapi.util;

import br.com.alura.orcamentoapi.model.CategoriaDespesa;
import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.FORM.RequestDespesa;
import br.com.alura.orcamentoapi.model.FORM.ResponseUser;
import br.com.alura.orcamentoapi.model.Usuario;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DespesaCreate {

    public static RequestDespesa mockRequestDespesa(){
        RequestDespesa rDespesa = Mockito.mock(RequestDespesa.class);
        Mockito.when(rDespesa.getId()).thenReturn(1L);
        Mockito.when(rDespesa.getDescricao()).thenReturn("descricao");
        Mockito.when(rDespesa.getValor()).thenReturn(BigDecimal.TEN);
        Mockito.when(rDespesa.getData()).thenReturn(LocalDate.now());
        Mockito.when(rDespesa.getCategoria()).thenReturn(CategoriaDespesa.OUTRAS);
        Mockito.when(rDespesa.getUsuario()).thenReturn(new ResponseUser(1L,"UsuarioTeste"));
        return rDespesa;
    }

    public static Despesa mockDespesa(){
        Despesa despesa = Mockito.mock(Despesa.class);
        Mockito.when(despesa.getId()).thenReturn(1L);
        Mockito.when(despesa.getDescricao()).thenReturn("descricao");
        Mockito.when(despesa.getValor()).thenReturn(BigDecimal.TEN);
        Mockito.when(despesa.getData()).thenReturn(LocalDate.now());
        Mockito.when(despesa.getCategoria()).thenReturn(CategoriaDespesa.OUTRAS);
        Mockito.when(despesa.getUser()).thenReturn(createUsuario());
        return despesa;
    }

    public static Usuario mockUsuario(){
        Usuario usuario = Mockito.mock(Usuario.class);
        Mockito.when(usuario.getId()).thenReturn(1L);
        Mockito.when(usuario.getNome()).thenReturn("UsuarioTeste");
        Mockito.when(usuario.getSenha()).thenReturn("senha");
        Mockito.when(usuario.getDespesas()).thenReturn(null);
        Mockito.when(usuario.getReceitas()).thenReturn(null);
        Mockito.when(usuario.getPerfis()).thenReturn(null);
        Mockito.when(usuario.getPerfis()).thenReturn(null);
        return usuario;
    }

    public static Despesa createDespesa() {
        Despesa despesa = new Despesa();
        despesa.setId(1L);
        despesa.setDescricao("descricao");
        despesa.setValor(BigDecimal.ONE);
        despesa.setData(LocalDate.now());
        despesa.setCategoria(CategoriaDespesa.OUTRAS);
        despesa.setUser(createUsuario());

        return despesa;
    }

    public static RequestDespesa createRequestDespesa() {
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

    public static Usuario createUsuario() {
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
