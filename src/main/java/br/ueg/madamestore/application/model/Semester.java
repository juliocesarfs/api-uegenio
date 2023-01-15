package br.ueg.madamestore.application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import br.ueg.madamestore.application.configuration.Constante;

import java.sql.Date;
import java.time.Year;
import java.util.Set;


@Entity
@Table(name = "semesters", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_SEMESTERS", sequenceName = "TBL_S_SEMESTERS", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_SEMESTERS")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String nome;

    @Column(name = "year",nullable = false)
    private Year ano;

    @Column(name = "DATA_INICIO",nullable = false)
    private Date initDate;

    @Column(name = "DATA_FINAL",nullable = false)
    private Date finalDate;

    /*
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "semester", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Holiday> holidays;
     */
}
