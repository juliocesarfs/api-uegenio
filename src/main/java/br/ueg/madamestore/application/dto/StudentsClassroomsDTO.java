/*
 * UsuarioTO.java
 * Copyright UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.dto;

import br.ueg.madamestore.application.model.UsuarioGrupo;
import br.ueg.madamestore.comum.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Classe de transferência referente a entidade {@link UsuarioGrupo}.
 *
 * @author UEG
 */
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Usuario Grupos")
public @Data class StudentsClassroomsDTO implements Serializable {

    @ApiModelProperty(value = "Código do students classrooms")
    private String id;

    @ApiModelProperty(value = "Código do classroom")
    private String idClassroom;

    @ApiModelProperty(value = "Código do student")
    private String idStudent;

    @ApiModelProperty(value = "Código do student")
    private String alexaID;

    @ApiModelProperty(value = "Nome da disciplina")
    private String nomeSubject;

    @ApiModelProperty(value = "Nota 1 va")
    private String nota1VA;

    @ApiModelProperty(value = "Nota 2 va")
    private String nota2VA;

    @ApiModelProperty(value = "Nota 2 va")
    private String mediaFinal;


    /**
     * @return the id
     */
    @JsonIgnore
    public Long getIdLong() {
        Long idLong = null;

        if (!Util.isEmpty(id)) {
            idLong = Long.parseLong(id);
        }
        return idLong;
    }
}
