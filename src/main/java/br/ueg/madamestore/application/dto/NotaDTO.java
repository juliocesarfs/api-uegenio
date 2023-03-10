package br.ueg.madamestore.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Date;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferĂȘncia de Nota")
public @Data
class NotaDTO implements Serializable {

    @ApiModelProperty(value = "Nome da feriado")
    private Long idStudent;

    @ApiModelProperty(value = "Nome da feriado")
    private String subject;

    @ApiModelProperty(value = "Nome da feriado")
    private Double nota_1va;

    @ApiModelProperty(value = "Nome da feriado")
    private Double nota_2va;

    @ApiModelProperty(value = "Nome da feriado")
    private Double media_final;

    @ApiModelProperty(value = "Nome da feriado")
    private String situation;
}
