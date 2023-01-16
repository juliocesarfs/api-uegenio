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

@Entity
@Table(name = "TBL_TEACHERS_CLASSROOMS", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "TBL_S_TEACHERS_CLASSROOMS", sequenceName = "TBL_S_TEACHERS_CLASSROOMS", allocationSize = 1, schema = Constante.DATABASE_OWNER)
@ToString
public @Data class TeachersClassrooms implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_TEACHERS_CLASSROOMS")
    @Column(name = "ID_TEACHERS_CLASSROOMS", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLASSROOM", referencedColumnName = "ID_CLASSROOM", nullable = false)
    @ToStringExclude
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TEACHER", referencedColumnName = "ID_TEACHER", nullable = false)
    private Teacher teacher;
}
