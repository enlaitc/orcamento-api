package br.com.alura.orcamentoapi.service;

import br.com.alura.orcamentoapi.model.FORM.RequestUsuario;
import br.com.alura.orcamentoapi.model.FORM.ResponseUser;
import br.com.alura.orcamentoapi.model.Usuario;

public interface UsuarioService {
    Usuario buscaUsuarioPorId(Long id, String nome);

    Usuario buscaUsuarioPorEmail(String email);

    ResponseUser buscaUserPorEmail(String email);

    void deletaUsuario(Long usuarioId);

    RequestUsuario atualizaUsuario(Long usuarioId, String password, RequestUsuario rUsuario);

    RequestUsuario adicionaUsuarioComBCrypt (RequestUsuario rUsuario);
}
