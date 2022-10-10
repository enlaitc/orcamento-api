package br.com.alura.orcamentoapi.Repository;

import br.com.alura.orcamentoapi.model.Usuario;
import br.com.alura.orcamentoapi.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    public void deveriaRetornarUsuarioPorEmail(){
        String email = "thiago@email.com";
        Optional<Usuario> usuario = repository.findByEmail(email);

        Assertions.assertNotNull(usuario);
        Assertions.assertEquals(email, usuario.get().getEmail());
    }

    @Test
    public void naoDeveriaRetornarUsuarioPorEmail(){
        String email = "invalido@email.com";
        Optional<Usuario> usuario = repository.findByEmail(email);

        Assertions.assertEquals(Optional.empty(),usuario);
    }
}
