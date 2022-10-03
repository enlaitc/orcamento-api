package br.com.alura.orcamentoapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter
@Entity
public class Despesa {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despesa")
    private Long id;

    @NotBlank
    private String descricao;

    private BigDecimal valor;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private CategoriaDespesa categoria;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Despesa despesa = (Despesa) o;
        return id != null && Objects.equals(id, despesa.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
