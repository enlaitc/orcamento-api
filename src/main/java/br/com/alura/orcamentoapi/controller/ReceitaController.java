package br.com.alura.orcamentoapi.controller;

import br.com.alura.orcamentoapi.model.Receita;
import br.com.alura.orcamentoapi.service.ReceitaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    public ReceitaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Receita adicionaReceita(@RequestBody Receita receita){
        return service.adicionaReceita(receita);
    }

    @GetMapping
    public List<Receita> buscaTodasReceitas(){
        return service.buscaTodasReceitas();
    }

    @GetMapping("/id/{receitaId}")
    public Receita buscaReceitaPorId(@PathVariable Long receitaId){
        return service.buscaReceitaPorId(receitaId);
    }

    @GetMapping("/desc/{receitaDesc}")
    public List<Receita> buscaReceitaPorDesc(@PathVariable String receitaDesc){
        return service.buscaReceitaPorDesc(receitaDesc);
    }

    @PutMapping("/{receitaId}")
    public ResponseEntity<Receita> atualizaReceita(@PathVariable Long receitaId, @RequestBody Receita receitaUp){
        return service.atualizaReceita(receitaId,receitaUp);
    }

    @DeleteMapping("/{receitaId}")
    public ResponseEntity<Void> deletaReceita(@PathVariable Long receitaId){
        return service.deletaReceita(receitaId);
    }

}
