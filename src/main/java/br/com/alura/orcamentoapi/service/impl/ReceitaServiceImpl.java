package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.Receita;
import br.com.alura.orcamentoapi.repository.ReceitaRepository;
import br.com.alura.orcamentoapi.service.ReceitaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class ReceitaServiceImpl implements ReceitaService {

    private ReceitaRepository repository;

    @Transactional
    @Override
    public Receita adicionaReceita(Receita receita) {
        LocalDate data = receita.getData();

        repository.findByDescricao(receita.getDescricao()).forEach(r -> {
            if (r.getData().getMonthValue() == data.getMonthValue() && r.getData().getYear() == data.getYear()) {
                throw new RuntimeException("Mes e data repetidos.");
            }
        });

        return repository.save(receita);
    }

    @Override
    public List<Receita> buscaTodasReceitas() {
        return repository.findAll();
    }

    @Override
    public Receita buscaReceitaPorId(Long receitaId) {
        receitaExiste(receitaId);

        return repository.findById(receitaId).get();
    }

    @Override
    public List<Receita> buscaReceitaPorDesc(String receitaDesc) {
        return repository.findByDescricao(receitaDesc);
    }

    @Override
    public List<Receita> buscaTodasReceitasPorMes(int ano, int mes) {
        int diaFin = LocalDate.of(ano, mes, 1).lengthOfMonth();

        return repository.findByDataBetween(
                LocalDate.of(ano, mes,1)
                ,LocalDate.of(ano, mes,diaFin));

    }

    @Transactional
    @Override
    public ResponseEntity<Receita> atualizaReceita(Long receitaId, Receita receitaUp) {
        receitaExiste(receitaId);

        receitaUp.setId(receitaId);

        return ResponseEntity.ok(adicionaReceita(receitaUp));
    }

    @Override
    public ResponseEntity<Void> deletaReceita(Long receitaId) {
        receitaExiste(receitaId);

        repository.deleteById(receitaId);

        return ResponseEntity.noContent().build();
    }

    public BigDecimal somaTodasReceitasPorData(int ano, int mes) {
        int diaFin = LocalDate.of(ano, mes, 1).lengthOfMonth();

        return repository.somaTodasReceitasPorData(
                LocalDate.of(ano, mes, 1)
                , LocalDate.of(ano, mes, diaFin)
        );
    }

    public void receitaExiste(Long receitaId) {
        if (!repository.existsById(receitaId)) throw new RuntimeException("Receita não existe.");
    }

}
