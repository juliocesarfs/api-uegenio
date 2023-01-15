package br.ueg.madamestore.application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import br.ueg.madamestore.application.configuration.Constante;

import java.util.Set;

@Entity
@Table(name = "teachers", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_TEACHERS", sequenceName = "TBL_S_TEACHERS", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_TEACHERS")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String nome;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

}
