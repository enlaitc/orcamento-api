package br.com.alura.orcamentoapi.service;

import br.com.alura.orcamentoapi.model.FORM.RequestUsuario;
import br.com.alura.orcamentoapi.model.Usuario;

public interface UsuarioService {
    Usuario buscaUsuarioPorId(Long id, String nome);

    Usuario buscaUsuarioPorEmail(String email);

    RequestUsuario adicionaUsuario(RequestUsuario rUsuario);

    RequestUsuario deletaUsuario(Long usuarioId);

    RequestUsuario atualizaUsuario(Long usuarioId, RequestUsuario rUsuario);

    RequestUsuario adicionaUsuarioComBCrypt (RequestUsuario rUsuario);
}
