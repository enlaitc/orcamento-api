package br.com.alura.orcamentoapi.controller;

import br.com.alura.orcamentoapi.model.FORM.RequestReceita;
import br.com.alura.orcamentoapi.model.Receita;
import br.com.alura.orcamentoapi.service.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Receitas")
@SecurityRequirement(name = "BearerJWT")
@AllArgsConstructor
@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    public ReceitaService service;

    @Operation(summary = "Adiciona uma receita")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestReceita adicionaReceita(@RequestBody RequestReceita receita){
        return service.adicionaReceita(receita);
    }

    @Operation(summary = "Busca todas as receitas")
    @GetMapping
    public ResponseEntity<Page<RequestReceita>> buscaTodasReceitas(@ParameterObject @PageableDefault(sort = "data") Pageable pageable){
        return service.buscaTodasReceitas(pageable);
    }

    @Operation(summary = "Receitas por id")
    @GetMapping("/id/{receitaId}")
    public ResponseEntity<RequestReceita> buscaReceitaPorId(@PathVariable Long receitaId){
        return service.buscaReceitaPorId(receitaId);
    }

    @Operation(summary = "Receitas por descrição")
    @GetMapping("/desc/{receitaDesc}")
    public ResponseEntity<List<RequestReceita>> buscaReceitaPorDesc(@PathVariable String receitaDesc){
        return service.buscaReceitaPorDesc(receitaDesc);
    }

    @Operation(summary = "Atualiza uma receita")
    @PutMapping("/{receitaId}")
    public ResponseEntity<RequestReceita> atualizaReceita(@PathVariable Long receitaId, @RequestBody RequestReceita receitaUp){
        return service.atualizaReceita(receitaId,receitaUp);
    }

    @Operation(summary = "Deleta uma receita")
    @DeleteMapping("/{receitaId}")
    public ResponseEntity<Void> deletaReceita(@PathVariable Long receitaId){
        return service.deletaReceita(receitaId);
    }

    @Operation(summary = "Receitas por ano/mes")
    @GetMapping("/{ano}/{mes}")
    public ResponseEntity<List<RequestReceita>> buscaTodasReceitasPorMes(@PathVariable int ano, @PathVariable int mes){
        return service.buscaTodasReceitasPorMes(ano, mes);
    }

}
