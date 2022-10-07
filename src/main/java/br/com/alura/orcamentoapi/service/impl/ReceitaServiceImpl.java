package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.Receita;
import br.com.alura.orcamentoapi.repository.ReceitaRepository;
import br.com.alura.orcamentoapi.service.ReceitaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
    public Page<Receita> buscaTodasReceitas(Pageable pageable) {
        return repository.findAll(pageable);
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

        BigDecimal soma = repository.somaTodasReceitasPorData(
                LocalDate.of(ano, mes, 1)
                , LocalDate.of(ano, mes, diaFin)
        );

        if(soma != null) return soma;

        return new BigDecimal(0);
    }

    public void receitaExiste(Long receitaId) {
        repository.findById(receitaId)
                .orElseThrow(() -> new EntityNotFoundException("Receita não encontrada"));
    }

}
