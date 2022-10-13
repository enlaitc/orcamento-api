package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.Usuario;
import br.com.alura.orcamentoapi.repository.UsuarioRepository;
import br.com.alura.orcamentoapi.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    UsuarioRepository repository;

    @Override
    public Usuario buscaUsuarioPorId(Long id, String nome) {
        Optional<Usuario> usuario = repository.findById(id);
        if (usuario.isPresent() && usuario.get().getNome().equals(nome)) return usuario.get();
        throw new RuntimeException("Usuario não existe ou nome invalido");
    }

    @Override
    public Usuario buscaUsuarioPorEmail(String email) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        if (usuario.isPresent()) return usuario.get();
        throw new RuntimeException("Usuario não existe");
    }
}
