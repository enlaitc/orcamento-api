package br.com.alura.orcamentoapi.service.impl;

import br.com.alura.orcamentoapi.model.FORM.ResponseUser;
import br.com.alura.orcamentoapi.model.Resumo;
import br.com.alura.orcamentoapi.model.Usuario;
import br.com.alura.orcamentoapi.model.ValorCategoria;
import br.com.alura.orcamentoapi.service.DespesaService;
import br.com.alura.orcamentoapi.service.ReceitaService;
import br.com.alura.orcamentoapi.service.ResumoService;
import br.com.alura.orcamentoapi.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class ResumoServiceImpl implements ResumoService {

    final ReceitaService receitaService;
    final DespesaService despesaService;
    final UsuarioService usuarioService;

    @Override
    public ResponseEntity<Resumo> resumoMes(int ano, int mes, Long userId, String userName) {
        Usuario usuario = usuarioService.buscaUsuarioPorId(userId, userName);

        BigDecimal vTotalDespesa = despesaService.somaTodasDespesasPorData(ano, mes, usuario);
        BigDecimal vTotalReceita = receitaService.somaTodasReceitasPorData(ano, mes, usuario);
        List<ValorCategoria> valorCategorias = despesaService.buscaValorTotalPorCategoria(ano, mes, usuario);

        Resumo resumo = new Resumo(
                vTotalReceita,
                vTotalDespesa,
                (vTotalReceita.subtract(vTotalDespesa)),
                valorCategorias,
                new ResponseUser(
                        userId,
                        userName
                )

        );

        return ResponseEntity.ok(resumo);

    }
}
