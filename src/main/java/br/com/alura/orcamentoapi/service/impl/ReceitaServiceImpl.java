package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.FORM.RequestReceita;
import br.com.alura.orcamentoapi.model.Receita;
import br.com.alura.orcamentoapi.model.Usuario;
import br.com.alura.orcamentoapi.repository.ReceitaRepository;
import br.com.alura.orcamentoapi.service.ReceitaService;
import br.com.alura.orcamentoapi.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ReceitaServiceImpl implements ReceitaService {

    private ReceitaRepository repository;
    private UsuarioService usuarioService;

    @Transactional
    @Override
    public RequestReceita adicionaReceita(RequestReceita receita) {
        verificaSeExisteAMesmaDescricaoNoMesEAno(receita.getDescricao(), receita.getData());

        Usuario usuario = usuarioService.buscaUsuarioPorId(receita.getUsuario().getId(), receita.getUsuario().getNome());
        Long id = receita.getId() == 0 ? null : receita.getId();

        Receita parseDespesa = new Receita(
                id,
                receita.getDescricao(),
                receita.getValor(),
                receita.getData(),
                usuario
        );
        receita.setId(repository.save(parseDespesa).getId());

        return receita;
    }

    @Override
    public ResponseEntity<Page<RequestReceita>> buscaTodasReceitas(Pageable pageable) {
        Page<Receita> despesas = repository.findAll(pageable);

        return ResponseEntity.ok(despesas.map(RequestReceita::converter));
    }

    @Override
    public ResponseEntity<RequestReceita> buscaReceitaPorId(Long receitaId) {
        Receita receita = devolveReceitaSeExistir(receitaId);

        return ResponseEntity.ok(RequestReceita.converter(receita));
    }

    @Override
    public ResponseEntity<List<RequestReceita>> buscaReceitaPorDesc(String receitaDesc) {
        List<Receita> receitas = repository.findByDescricao(receitaDesc);
        List<RequestReceita> lista = new ArrayList<>();

        receitas.forEach(r -> lista.add(RequestReceita.converter(r)));

        return ResponseEntity.ok(lista);
    }

    @Override
    public ResponseEntity<List<RequestReceita>> buscaTodasReceitasPorMes(int ano, int mes) {
        int diaFin = LocalDate.of(ano, mes, 1).lengthOfMonth();

        List<Receita> receitas = repository.findByDataBetween(
                LocalDate.of(ano, mes, 1)
                , LocalDate.of(ano, mes, diaFin));

        List<RequestReceita> lista = new ArrayList<>();
        receitas.forEach(r -> lista.add(RequestReceita.converter(r)));

        return ResponseEntity.ok(lista);
    }

    @Transactional
    @Override
    public ResponseEntity<RequestReceita> atualizaReceita(Long receitaId, RequestReceita receitaUp) {
        devolveReceitaSeExistir(receitaId);

        receitaUp.setId(receitaId);

        return ResponseEntity.ok(adicionaReceita(receitaUp));
    }

    @Override
    public ResponseEntity<Void> deletaReceita(Long receitaId) {
        devolveReceitaSeExistir(receitaId);

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

    private Receita devolveReceitaSeExistir(Long receitaId) {
        return repository.findById(receitaId)
                .orElseThrow(() -> new EntityNotFoundException("Receita não encontrada"));
    }

    private void verificaSeExisteAMesmaDescricaoNoMesEAno(String descricao, LocalDate data){
        repository.findByDescricao(descricao).forEach(d -> {
            if (d.getData().getMonthValue() == data.getMonthValue() && d.getData().getYear() == data.getYear()) {
                throw new RuntimeException("Mes e data repetidos.");
            }
        });
    }

}
