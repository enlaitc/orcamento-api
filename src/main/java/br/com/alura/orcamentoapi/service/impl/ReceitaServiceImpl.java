package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.Receita;
import br.com.alura.orcamentoapi.repository.ReceitaRepository;
import br.com.alura.orcamentoapi.service.ReceitaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class ReceitaServiceImpl implements ReceitaService {

    private ReceitaRepository repository;

    @Override
    public Receita adicionaReceita(Receita receita){
        LocalDate data = receita.getData();
        String desc = receita.getDescricao();

        List<Receita> receitaExist = repository.findByDescricao(desc);

        if(!receitaExist.isEmpty()){
            receitaExist.forEach(r -> {
                if(r.getData().getMonthValue() == data.getMonthValue() && r.getData().getYear() == data.getYear() ){
                    throw new RuntimeException("Mes e data repetidos.");
                }
            });
        }

        return repository.save(receita);
    }


}
