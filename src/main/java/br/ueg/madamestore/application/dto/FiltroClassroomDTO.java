package br.ueg.madamestore.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa de Classroom")
public @Data class FiltroClassroomDTO implements Serializable {

    @ApiModelProperty(value = "Id da Classroom")
    private Long idClassroom;

    @ApiModelProperty(value = "disciplina")
    private String subject;


}
