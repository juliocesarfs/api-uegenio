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
@Table(name = "TBL_PARAMETER", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "TBL_S_PARAMETER", sequenceName = "TBL_S_PARAMETER", allocationSize = 1, schema = Constante.DATABASE_OWNER)
@ToString
public @Data class Parameter implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_PARAMETER")
    @Column(name = "ID_PARAMETER", nullable = false)
    private Long id;

    @Column(name="TIPO", nullable = false)
    private String type;

    @Column(name="NOME", nullable = false)
    private String name;
}
