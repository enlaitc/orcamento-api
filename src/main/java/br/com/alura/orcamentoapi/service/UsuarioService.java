package br.com.alura.orcamentoapi.service;

import br.com.alura.orcamentoapi.model.Usuario;

public interface UsuarioService {
    Usuario buscaUsuarioPorId(Long id, String nome);
    Usuario buscaUsuarioPorEmail(String email);
}
