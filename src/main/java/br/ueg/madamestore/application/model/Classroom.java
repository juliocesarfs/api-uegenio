package br.ueg.madamestore.application.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import br.ueg.madamestore.application.configuration.Constante;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Set;


@Entity
@Table(name = "classrooms", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_CLASSROOMS", sequenceName = "TBL_S_CLASSROOMS", allocationSize = 1, schema = Constante.DATABASE_OWNER)
public @Data
class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_CLASSROOMS")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "local", length = 100, nullable = false)
    private String local;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUBJECT", referencedColumnName = "id", nullable = false)
    private Subject subject;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "classroom", fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<TeacherClassroom> teachersClassrooms;

    /*
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "classroom", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Time> times;
*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SEMESTER", referencedColumnName = "id", nullable = false)
    @ToStringExclude
    private Semester semester;

}
