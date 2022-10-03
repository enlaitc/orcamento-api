package br.com.alura.orcamentoapi.controller;

import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.service.DespesaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "BearerJWT")
@AllArgsConstructor
@RestController
@RequestMapping("/despesas")
public class DespesaController {

    public DespesaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Despesa adicionaDespesa(@RequestBody Despesa despesa){
        return service.adicionaDespesa(despesa);
    }

    @GetMapping
    public Page<Despesa> buscaTodasDespesas(@ParameterObject @PageableDefault(sort = "data") Pageable pageable){
        return service.buscaTodasDespesas(pageable);
    }

    @GetMapping("/id/{despesaId}")
    public Despesa buscaDespesaPorId(@PathVariable Long despesaId){
        return service.buscaDespesaPorId(despesaId);
    }

    @GetMapping("/desc/{despesaDesc}")
    public List<Despesa> buscaDespesaPorDesc(@PathVariable String despesaDesc){
        return service.buscaDespesaPorDesc(despesaDesc);
    }

    @PutMapping("/{despesaId}")
    public ResponseEntity<Despesa> atualizaDespesa(@PathVariable Long despesaId, @RequestBody Despesa despesaUp){
        return service.atualizaDespesa(despesaId,despesaUp);
    }

    @DeleteMapping("/{despesaId}")
    public ResponseEntity<Void> deletaDespesa(@PathVariable Long despesaId){
        return service.deletaDespesa(despesaId);
    }

    @GetMapping("/{ano}/{mes}")
    public List<Despesa> buscaTodasDespesasPorMes(@PathVariable int ano, @PathVariable int mes){
        return service.buscaTodasDespesasPorMes(ano, mes);
    }

}
