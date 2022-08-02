package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.Receita;
import br.com.alura.orcamentoapi.repository.DespesaRepository;
import br.com.alura.orcamentoapi.repository.ReceitaRepository;
import br.com.alura.orcamentoapi.service.DespesaService;
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
public class DespesaServiceImpl implements DespesaService {

    private DespesaRepository repository;

    @Transactional
    @Override
    public Despesa adicionaDespesa(Despesa despesa){
        LocalDate data = despesa.getData();
        String desc = despesa.getDescricao();

        List<Despesa> despesaExist = repository.findByDescricao(desc);

        if(!despesaExist.isEmpty()){
            despesaExist.forEach(r -> {
                if(r.getData().getMonthValue() == data.getMonthValue() && r.getData().getYear() == data.getYear() ){
                    throw new RuntimeException("Mes e data repetidos.");
                }
            });
        }

        return repository.save(despesa);
    }

    @Override
    public List<Despesa> buscaTodasDespesas() {
        return repository.findAll();
    }

    @Override
    public Despesa buscaDespesa(Long despesaId) {
        Optional<Despesa> despesa = repository.findById(despesaId);

        if(despesa.isEmpty()) throw new RuntimeException("Despesa não existe");

        return despesa.get();
    }

    @Transactional
    @Override
    public ResponseEntity<Despesa> atualizaDespesa(Long despedaId, Despesa despesaUp) {
        if(!repository.existsById(despedaId)) return ResponseEntity.notFound().build();

        despesaUp.setId(despedaId);

        return ResponseEntity.ok(adicionaDespesa(despesaUp));
    }

    @Override
    public ResponseEntity<Void> deletaDespesa(Long despesaId) {
        if(!repository.existsById(despesaId)) return ResponseEntity.notFound().build();

        repository.deleteById(despesaId);

        return ResponseEntity.noContent().build();
    }


}
