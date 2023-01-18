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
@Table(name = "TBL_STUDENTS_CLASSROOMS", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "TBL_S_STUDENTS_CLASSROOMS", sequenceName = "TBL_S_STUDENTS_CLASSROOMS", allocationSize = 1, schema = Constante.DATABASE_OWNER)
@ToString
public @Data class StudentsClassrooms implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_STUDENTS_CLASSROOMS")
    @Column(name = "ID_STUDENTS_CLASSROOMS", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLASSROOM", referencedColumnName = "ID_CLASSROOM", nullable = false)
    @ToStringExclude
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_STUDENT", referencedColumnName = "ID_STUDENT")
    @ToStringExclude
    private Student student;

    @Column(name = "NOTA_1VA", nullable = true)
    private String nota1VA;

    @Column(name = "NOTA_2VA", nullable = true)
    private String nota2VA;

    @Column(name = "MEDIA_FINAL", nullable = true)
    private String mediaFinal;

}
