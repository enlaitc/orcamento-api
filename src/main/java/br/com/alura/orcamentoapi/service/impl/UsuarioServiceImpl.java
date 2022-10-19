package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.FORM.RequestUsuario;
import br.com.alura.orcamentoapi.model.FORM.ResponseUser;
import br.com.alura.orcamentoapi.model.Usuario;
import br.com.alura.orcamentoapi.repository.UsuarioRepository;
import br.com.alura.orcamentoapi.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.persistence.EntityNotFoundException;
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
        throw new EntityNotFoundException("Usuario não existe ou nome invalido");
    }

    @Override
    public Usuario buscaUsuarioPorEmail(String email) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        if (usuario.isPresent()) return usuario.get();
        throw new EntityNotFoundException("Usuario não econtrado");
    }

    @Override
    public ResponseUser buscaUserPorEmail(String email) {
        if(repository.findByEmail(email).isEmpty()) throw new EntityNotFoundException("Usuario não econtrado");
        Usuario usuario = repository.findByEmail(email).get();

        return new ResponseUser(
                usuario.getId(),
                usuario.getNome()
        );
    }

    @Override
    public RequestUsuario adicionaUsuarioComBCrypt(RequestUsuario rUsuario){
        rUsuario.setSenha(bCryptPasswordEncoder.encode(rUsuario.getSenha()));

        Usuario usuario = new Usuario(
                null,
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
    public void deletaUsuario(Long usuarioId) {
        repository.deleteById(usuarioId);
    }

    @Override
    public RequestUsuario atualizaUsuario(Long usuarioId, String password, RequestUsuario rUsuario) {
        Optional<Usuario> usuario = repository.findById(usuarioId);
        if (usuario.isEmpty() || !bCryptPasswordEncoder.matches(password, usuario.get().getPassword())){
            throw new EntityNotFoundException("Usuario não econtrado");
        }

        rUsuario.setId(usuarioId);
        rUsuario.setSenha(bCryptPasswordEncoder.encode(rUsuario.getSenha()));

        Usuario usuarioUp = new Usuario(
                rUsuario.getId(),
                rUsuario.getNome(),
                rUsuario.getEmail(),
                rUsuario.getSenha(),
                usuario.get().getPerfis(),
                usuario.get().getReceitas(),
                usuario.get().getDespesas()
        );

        repository.save(usuarioUp);

        return rUsuario;
    }

}
