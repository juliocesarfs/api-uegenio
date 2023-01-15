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
@Table(name = "teacher_classroom", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "TBL_S_TEACHER_CLASSROOM", sequenceName = "TBL_S_TEACHER_CLASSROOM", allocationSize = 1, schema = Constante.DATABASE_OWNER)
@ToString
public @Data class TeacherClassroom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_TEACHER_CLASSROOM")
    @Column(name = "ID_TEACHER_CLASSROOM", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLASSROOM", referencedColumnName = "id", nullable = true)
    @ToStringExclude
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TEACHER", referencedColumnName = "id", nullable = false)
    private Teacher teacher;
}
