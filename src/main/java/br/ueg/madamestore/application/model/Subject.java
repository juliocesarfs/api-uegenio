package br.ueg.madamestore.application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import br.ueg.madamestore.application.configuration.Constante;

@Entity
@Table(name = "subjects", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_SUBJECTS", sequenceName = "TBL_S_SUBJECTS", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_SUBJECTS")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String nome;

}
