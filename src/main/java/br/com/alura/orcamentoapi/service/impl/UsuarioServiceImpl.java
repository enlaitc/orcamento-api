package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.FORM.RequestUsuario;
import br.com.alura.orcamentoapi.model.Usuario;
import br.com.alura.orcamentoapi.repository.UsuarioRepository;
import br.com.alura.orcamentoapi.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Override
    public RequestUsuario adicionaUsuario(RequestUsuario rUsuario) {
        Long id = rUsuario.getId() == 0 ? null : rUsuario.getId();
        Usuario usuario = new Usuario(
                id,
                rUsuario.getNome(),
                rUsuario.getEmail(),
                rUsuario.getSenha(),
                null,
                null,
                null
        );

       rUsuario.setId(repository.save(usuario).getId());

       return rUsuario;
    }

    @Override
    public RequestUsuario adicionaUsuarioComBCrypt(RequestUsuario rUsuario){
        rUsuario.setSenha(bCryptPasswordEncoder.encode(rUsuario.getSenha()));

        Long id = rUsuario.getId() == 0 ? null : rUsuario.getId();
        Usuario usuario = new Usuario(
                id,
                rUsuario.getNome(),
                rUsuario.getEmail(),
                rUsuario.getSenha(),
                null,
                null,
                null
        );

        rUsuario.setId(repository.save(usuario).getId());

        return rUsuario;
    }

    @Override
    public RequestUsuario deletaUsuario(Long usuarioId) {
        return null;
    }

    @Override
    public RequestUsuario atualizaUsuario(Long usuarioId, RequestUsuario rUsuario) {
        return null;
    }

}
