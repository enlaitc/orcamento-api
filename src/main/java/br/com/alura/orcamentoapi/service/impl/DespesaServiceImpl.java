package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.CategoriaDespesa;
import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.repository.DespesaRepository;
import br.com.alura.orcamentoapi.service.DespesaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class DespesaServiceImpl implements DespesaService {

    private DespesaRepository repository;

    @Transactional
    @Override
    public Despesa adicionaDespesa(Despesa despesa) {
        LocalDate data = despesa.getData();
        if(despesa.getCategoria() == null) despesa.setCategoria(CategoriaDespesa.OUTRAS);

        repository.findByDescricao(despesa.getDescricao()).forEach(d -> {
            if (d.getData().getMonthValue() == data.getMonthValue() && d.getData().getYear() == data.getYear()) {
                throw new RuntimeException("Mes e data repetidos.");
            }
        });

        return repository.save(despesa);
    }

    @Override
    public List<Despesa> buscaTodasDespesas() {
        return repository.findAll();
    }

    @Override
    public Despesa buscaDespesaPorId(Long despesaId) {
        despesaExiste(despesaId);

        return repository.findById(despesaId).get();
    }

    @Override
    public List<Despesa> buscaDespesaPorDesc(String despesaDesc) {
       return repository.findByDescricao(despesaDesc);
    }

    @Override
    public List<Despesa> buscaTodasDespesasPorMes(int ano, int mes) {
        int diaFin = LocalDate.of(ano, mes, 1).lengthOfMonth();

        return repository.findByDataBetween(
                LocalDate.of(ano, mes,1)
                ,LocalDate.of(ano, mes,diaFin));
    }

    @Transactional
    @Override
    public ResponseEntity<Despesa> atualizaDespesa(Long despesaId, Despesa despesaUp) {
        despesaExiste(despesaId);

        despesaUp.setId(despesaId);

        return ResponseEntity.ok(adicionaDespesa(despesaUp));
    }

    @Override
    public ResponseEntity<Void> deletaDespesa(Long despesaId) {
        despesaExiste(despesaId);

        repository.deleteById(despesaId);

        return ResponseEntity.noContent().build();
    }

    public void despesaExiste(Long despedaId) {
        if (!repository.existsById(despedaId)) throw new RuntimeException("Despesa não existe.");
    }

}
