package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.Receita;
import br.com.alura.orcamentoapi.repository.ReceitaRepository;
import br.com.alura.orcamentoapi.service.ReceitaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReceitaServiceImpl implements ReceitaService {

    private ReceitaRepository repository;

    @Transactional
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

    @Override
    public List<Receita> buscaTodasReceitas() {
        return repository.findAll();
    }

    @Override
    public Receita buscaReceita(Long receitaId) {
        Optional<Receita> receita = repository.findById(receitaId);

        if(receita.isEmpty()) throw new RuntimeException("Receita não existe");

        return receita.get();
    }

    @Transactional
    @Override
    public ResponseEntity<Receita> atualizaReceita(Long receitaId, Receita receitaUp) {
        if(!repository.existsById(receitaId)) return ResponseEntity.notFound().build();

        receitaUp.setId(receitaId);

        return ResponseEntity.ok(adicionaReceita(receitaUp));
    }

    @Override
    public ResponseEntity<Void> deletaReceita(Long receitaId) {
        if(!repository.existsById(receitaId)) return ResponseEntity.notFound().build();

        repository.deleteById(receitaId);

        return ResponseEntity.noContent().build();
    }


}
