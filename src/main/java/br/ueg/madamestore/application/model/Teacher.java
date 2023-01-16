package br.ueg.madamestore.application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import br.ueg.madamestore.application.configuration.Constante;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.converter.StatusSimNaoConverter;

import java.time.LocalDate;

@Entity
@Table(name = "TBL_TEACHER", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_TEACHER", sequenceName = "TBL_S_TEACHER", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_TEACHER")
    @Column(name = "ID_TEACHER", nullable = false)
    private Long id;

    @Column(name = "NOME_TEACHER", length = 100, nullable = false)
    private String nome;

}
