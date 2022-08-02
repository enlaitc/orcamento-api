package br.com.alura.orcamentoapi.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@Entity
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despesa")
    private Long id;

    @NotBlank
    private String descricao;

    private Float valor;

    private LocalDate data;

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
