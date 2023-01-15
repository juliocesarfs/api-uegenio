package br.ueg.madamestore.application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import br.ueg.madamestore.application.configuration.Constante;
import org.jfree.data.time.Hour;

import java.sql.Date;
import java.time.Year;
import java.util.Set;


@Entity
@Table(name = "times", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_TIMES", sequenceName = "TBL_S_TIMES", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_TIMES")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "DIA_SEMANA", length = 100, nullable = false)
    private String dia_semana;

    @Column(name = "HORA_INICIO",nullable = false)
    private Hour initHour;

    @Column(name = "HORA_FINAL",nullable = false)
    private Hour finalHour;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLASSROOM", referencedColumnName = "id", nullable = true)
    private Classroom classroom;
}
