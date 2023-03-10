package br.ueg.madamestore.application.model;

import br.ueg.madamestore.application.configuration.Constante;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TBL_CLASSROOM", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_CLASSROOM", sequenceName = "TBL_S_CLASSROOM", allocationSize = 1, schema = Constante.DATABASE_OWNER)
@ToString
public @Data
class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_CLASSROOM")
    @Column(name = "ID_CLASSROOM", nullable = false)
    private Long id;


    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_SEMESTER", referencedColumnName = "id", nullable = false)
    private Semester semester;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_SUBJECT", referencedColumnName = "id", nullable = false)
    private Subject subject;


    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "classroom", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeachersClassrooms> teachersClassrooms;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "classroom", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentsClassrooms> studentsClassrooms;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "classroom", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Hour> hours;

    @Column(name="LOCAL", nullable = false)
    private String local;
}
