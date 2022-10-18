package br.com.alura.orcamentoapi.controller;

import br.com.alura.orcamentoapi.model.FORM.RequestUsuario;
import br.com.alura.orcamentoapi.model.FORM.ResponseUser;
import br.com.alura.orcamentoapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuarios")
@SecurityRequirement(name = "BearerJWT")
@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    public UsuarioService service;

    @Operation(summary = "Registro de Usuario")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestUsuario adicionaUsuarioComBCrypt(@RequestBody RequestUsuario rUsuario){
        return service.adicionaUsuarioComBCrypt(rUsuario);
    }

    @Operation(summary = "Busca Usuario por email")
    @GetMapping("/{email}")
    public ResponseUser buscaUserPorEmail(@PathVariable String email){
        return service.buscaUserPorEmail(email);
    }

    @Operation(summary = "Deleta Usuario por Id")
    @DeleteMapping("/{usuarioId}")
    public void deletaUsuario(@PathVariable Long usuarioId){
        service.deletaUsuario(usuarioId);
    }

    @Operation(summary = "Atualiza Usuario")
    @PutMapping("/{usuarioId}/{password}")
    public RequestUsuario atualizaUsuario(@PathVariable Long usuarioId, @PathVariable String password, @RequestBody RequestUsuario rUsuario){
        return service.atualizaUsuario(usuarioId, password, rUsuario);
    }
}
