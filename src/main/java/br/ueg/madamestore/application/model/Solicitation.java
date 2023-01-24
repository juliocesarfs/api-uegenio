package br.ueg.madamestore.application.model;

import br.ueg.madamestore.application.configuration.Constante;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TBL_SOLICITATION", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode()
@SequenceGenerator(name = "TBL_S_SOLICITATION", sequenceName = "TBL_S_SOLICITATION", allocationSize = 1, schema = Constante.DATABASE_OWNER)
@ToString
public @Data
class Solicitation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_SOLICITATION")
    @Column(name = "ID_SOLICITATION", nullable = false)
    private Long id;


    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "solicitation", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ParameterSolicitation> parameters;


    @Column(name="descricao", nullable = false)
    private String description;

    @Column(name="tipo", nullable = false, unique = true)
    private String type;

    @Column(name="classe_implementacao", nullable = false)
    private String class_implementation;

}
