/*
 * UsuarioGrupo.java
 * Copyright UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.model;

import br.ueg.madamestore.application.configuration.Constante;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "TBL_HOURS_CLASSROOMS", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "TBL_S_HOURS_CLASSROOMS", sequenceName = "TBL_S_HOURS_CLASSROOMS", allocationSize = 1, schema = Constante.DATABASE_OWNER)
@ToString
public @Data class Hour implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_HOURS_CLASSROOMS")
    @Column(name = "ID_HOURS_CLASSROOMS", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLASSROOM", referencedColumnName = "ID_CLASSROOM", nullable = false)
    @ToStringExclude
    private Classroom classroom;

    @Column(name="HORA_INICIO", nullable = false)
    private String initHour;

    @Column(name="HORA_FINAL", nullable = false)
    private String finalHour;

    @Column(name="DIA_DA_SEMANA", nullable = false)
    private String weekDay;
}
