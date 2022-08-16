package br.com.alura.orcamentoapi.controller;

import br.com.alura.orcamentoapi.model.Resumo;
import br.com.alura.orcamentoapi.service.ResumoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/resumo")
public class ResumoController {

    public ResumoService service;

    @GetMapping("/{ano}/{mes}")
    public Resumo resumoMes (@PathVariable int ano ,@PathVariable int mes){
        return service.resumoMes(ano, mes);
    }

}
