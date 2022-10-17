package br.com.alura.orcamentoapi.controller;

import br.com.alura.orcamentoapi.model.FORM.RequestUsuario;
import br.com.alura.orcamentoapi.security.AutenticacaoService;
import br.com.alura.orcamentoapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
}
