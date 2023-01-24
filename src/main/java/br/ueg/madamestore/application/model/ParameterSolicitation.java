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
@Table(name = "TBL_PARAMETER_SOLICITATION", schema = Constante.DATABASE_OWNER)
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "TBL_S_PARAMETER_SOLICITATION", sequenceName = "TBL_S_PARAMETER_SOLICITATION", allocationSize = 1, schema = Constante.DATABASE_OWNER)
@ToString
public @Data class ParameterSolicitation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_S_PARAMETER_SOLICITATION")
    @Column(name = "ID_PARAMETER_SOLICITATION", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SOLICITATION", referencedColumnName = "ID_SOLICITATION", nullable = false)
    @ToStringExclude
    private Solicitation solicitation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PARAMETER", referencedColumnName = "ID_PARAMETER")
    @ToStringExclude
    private Parameter parameter;

}
