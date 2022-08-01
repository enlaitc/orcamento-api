package br.com.alura.orcamentoapi.controller;

import br.com.alura.orcamentoapi.model.Receita;
import br.com.alura.orcamentoapi.service.ReceitaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
