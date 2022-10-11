package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.Despesa;
import br.com.alura.orcamentoapi.model.FORM.RequestDespesa;
import br.com.alura.orcamentoapi.model.Usuario;
import br.com.alura.orcamentoapi.model.ValorCategoria;
import br.com.alura.orcamentoapi.repository.DespesaRepository;
import br.com.alura.orcamentoapi.service.DespesaService;
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
public class DespesaServiceImpl implements DespesaService {

    private DespesaRepository repository;
    private UsuarioService usuarioService;

    @Transactional
    @Override
    public RequestDespesa adicionaDespesa(RequestDespesa despesa) {
        verificaSeExisteAMesmaDescricaoNoMesEAno(despesa.getDescricao(), despesa.getData());

        Usuario usuario = usuarioService.buscaUsuarioPorId(despesa.getUsuario().getId(), despesa.getUsuario().getNome());
        Long id = despesa.getId() == 0 ? null : despesa.getId();

        Despesa parseDespesa = new Despesa(
                id,
                despesa.getDescricao(),
                despesa.getValor(),
                despesa.getData(),
                despesa.getCategoria(),
                usuario
        );
        despesa.setId(repository.save(parseDespesa).getId());

        return despesa;

    }

    @Override
    public Page<RequestDespesa> buscaTodasDespesas(Pageable pageable) {
        Page<Despesa> despesas = repository.findAll(pageable);

        return despesas.map(RequestDespesa::converter);
    }

    @Override
    public ResponseEntity<RequestDespesa> buscaDespesaPorId(Long despesaId) {
        Despesa despesa = devolveDespesaSeExistir(despesaId);

        return ResponseEntity.ok(RequestDespesa.converter(despesa));
    }

    @Override
    public ResponseEntity<List<RequestDespesa>> buscaDespesaPorDesc(String despesaDesc) {
        List<Despesa> despesas = repository.findByDescricao(despesaDesc);
        List<RequestDespesa> lista = new ArrayList<>();

        despesas.forEach(d -> lista.add(RequestDespesa.converter(d)));

        return ResponseEntity.ok(lista);
    }

    @Override
    public List<Despesa> buscaTodasDespesasPorMes(int ano, int mes) {
        int diaFin = LocalDate.of(ano, mes, 1).lengthOfMonth();

        return repository.findByDataBetween(
                LocalDate.of(ano, mes, 1)
                , LocalDate.of(ano, mes, diaFin));
    }

    @Transactional
    @Override
    public ResponseEntity<RequestDespesa> atualizaDespesa(Long despesaId, RequestDespesa despesaUp) {
        devolveDespesaSeExistir(despesaId);

        despesaUp.setId(despesaId);

        return ResponseEntity.ok(adicionaDespesa(despesaUp));
    }

    @Override
    public ResponseEntity<Void> deletaDespesa(Long despesaId) {
        devolveDespesaSeExistir(despesaId);

        repository.deleteById(despesaId);

        return ResponseEntity.noContent().build();
    }

    public List<ValorCategoria> buscaValorTotalPorCategoria(int ano, int mes) {
        int diaFin = LocalDate.of(ano, mes, 1).lengthOfMonth();

        return repository.buscaValorTotalPorCategoria(
                LocalDate.of(ano, mes, 1)
                , LocalDate.of(ano, mes, diaFin)
        );
    }

    public BigDecimal somaTodasDespesasPorData(int ano, int mes) {
        int diaFin = LocalDate.of(ano, mes, 1).lengthOfMonth();

        BigDecimal soma =  repository.somaTodasDespesasPorData(
                LocalDate.of(ano, mes, 1)
                , LocalDate.of(ano, mes, diaFin)
        );

        if(soma != null) return soma;

        return new BigDecimal(0);
    }

    public Despesa devolveDespesaSeExistir(Long despesaId) {
        return repository.findById(despesaId)
                .orElseThrow(() -> new EntityNotFoundException("Despesa não encontrada"));
    }

    private void verificaSeExisteAMesmaDescricaoNoMesEAno(String descricao, LocalDate data){
        repository.findByDescricao(descricao).forEach(d -> {
            if (d.getData().getMonthValue() == data.getMonthValue() && d.getData().getYear() == data.getYear()) {
                throw new RuntimeException("Mes e data repetidos.");
            }
        });
    }

}
