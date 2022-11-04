package br.com.alura.orcamentoapi.model.FORM;

import br.com.alura.orcamentoapi.model.Receita;
import br.com.alura.orcamentoapi.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestUsuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;

    public static RequestUsuario converter(Usuario usuario) {
        return new RequestUsuario(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha()
        );
    }
}
