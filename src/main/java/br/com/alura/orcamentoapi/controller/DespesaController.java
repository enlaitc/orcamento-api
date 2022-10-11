package br.com.alura.orcamentoapi.controller;

import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.FORM.RequestDespesa;
import br.com.alura.orcamentoapi.service.DespesaService;
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

@Tag(name = "Despesas")
@SecurityRequirement(name = "BearerJWT")
@AllArgsConstructor
@RestController
@RequestMapping("/despesas")
public class DespesaController {

    public DespesaService service;

    @Operation(summary = "Adiciona Despesa")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDespesa adicionaDespesa(@RequestBody RequestDespesa despesa){
        return service.adicionaDespesa(despesa);
    }

    @Operation(summary = "Busca todas as despesas")
    @GetMapping
    public Page<RequestDespesa> buscaTodasDespesas(@ParameterObject @PageableDefault(sort = "data") Pageable pageable){
        return service.buscaTodasDespesas(pageable);
    }

    @Operation(summary = "Despesas por id")
    @GetMapping("/id/{despesaId}")
    public ResponseEntity<RequestDespesa> buscaDespesaPorId(@PathVariable Long despesaId){
        return service.buscaDespesaPorId(despesaId);
    }

    @Operation(summary = "Despesas por descrição")
    @GetMapping("/desc/{despesaDesc}")
    public ResponseEntity<List<RequestDespesa>> buscaDespesaPorDesc(@PathVariable String despesaDesc){
        return service.buscaDespesaPorDesc(despesaDesc);
    }

    @Operation(summary = "Atualiza uma despesa")
    @PutMapping("/{despesaId}")
    public ResponseEntity<RequestDespesa> atualizaDespesa(@PathVariable Long despesaId, @RequestBody RequestDespesa despesaUp){
        return service.atualizaDespesa(despesaId,despesaUp);
    }

    @Operation(summary = "Deleta uma despesa")
    @DeleteMapping("/{despesaId}")
    public ResponseEntity<Void> deletaDespesa(@PathVariable Long despesaId){
        return service.deletaDespesa(despesaId);
    }

    @Operation(summary = "Despesas por ano/mes")
    @GetMapping("/{ano}/{mes}")
    public List<Despesa> buscaTodasDespesasPorMes(@PathVariable int ano, @PathVariable int mes){
        return service.buscaTodasDespesasPorMes(ano, mes);
    }

}
