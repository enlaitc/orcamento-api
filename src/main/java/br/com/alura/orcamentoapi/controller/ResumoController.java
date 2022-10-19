package br.com.alura.orcamentoapi.controller;

import br.com.alura.orcamentoapi.model.Resumo;
import br.com.alura.orcamentoapi.service.ResumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Resumo")
@SecurityRequirement(name = "BearerJWT")
@AllArgsConstructor
@RestController
@RequestMapping("/resumo")
public class ResumoController {

    public ResumoService service;

    @Operation(summary = "Resumo do mês", description = "Mostra o valor total de receitas e despesas, o valor gasto por cada categoria e o saldo final. ")
    @GetMapping("/{ano}/{mes}/{userId}/{userName}")
    public ResponseEntity<Resumo> resumoMes (@PathVariable int ano , @PathVariable int mes, @PathVariable Long userId, @PathVariable String userName){
        return service.resumoMes(ano, mes, userId, userName);
    }

}
