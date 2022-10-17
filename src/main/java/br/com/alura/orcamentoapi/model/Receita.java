package br.com.alura.orcamentoapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receita")
    private Long id;

    @NotBlank
    private String descricao;

    private BigDecimal valor;

    private LocalDate data;

    @ManyToOne
    private Usuario user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Receita receita = (Receita) o;
        return id != null && Objects.equals(id, receita.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
