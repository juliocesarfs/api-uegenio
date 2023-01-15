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
@ApiModel(value = "Entidade de transferência de Teacher classroom")
public @Data class TeacherClassroomDTO implements Serializable {

    @ApiModelProperty(value = "Código do Teacher Classroom")
    private String id;

    @ApiModelProperty(value = "Código da aula")
    private String idClassroom;

    @ApiModelProperty(value = "Código do teacher")
    private Long idTeacher;


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
