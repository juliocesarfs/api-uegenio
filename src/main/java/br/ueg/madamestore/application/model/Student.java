package br.ueg.madamestore.application.model;

import br.ueg.madamestore.application.configuration.Constante;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.enums.converter.StatusEsperaConverter;
import br.ueg.madamestore.application.enums.converter.StatusSimNaoConverter;
import br.ueg.madamestore.application.enums.converter.StatusVendidoConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "TBL_STUDENT", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_STUDENT", sequenceName = "TBL_S_STUDENT", allocationSize = 1, schema = Constante.DATABASE_OWNER)
@ToString
public @Data
class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_STUDENT")
    @Column(name = "ID_STUDENT", nullable = false)
    private Long id;


    @Column(name = "ID_ALEXA", nullable = false, unique = true)
    private String alexaID;


    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentsClassrooms> studentsClassrooms;

}
