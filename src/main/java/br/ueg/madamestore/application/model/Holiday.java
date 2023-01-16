package br.ueg.madamestore.application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import br.ueg.madamestore.application.configuration.Constante;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.sql.Date;


@Entity
@Table(name = "holidays", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_HOLIDAYS", sequenceName = "TBL_S_HOLIDAYS", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_HOLIDAYS")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String nome;

    @Column(name = "DATA_INICIO",nullable = false)
    private Date initDate;

    @Column(name = "DATA_FINAL",nullable = false)
    private Date finalDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SEMESTER", referencedColumnName = "id", nullable = true)
    @ToStringExclude
    private Semester semester;

}
