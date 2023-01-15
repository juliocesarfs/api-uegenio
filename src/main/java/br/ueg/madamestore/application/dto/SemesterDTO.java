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
import java.time.Year;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Semestre")
public @Data
class SemesterDTO implements Serializable {

    @ApiModelProperty(value = "id do Semestre")
    private Long id;

    @ApiModelProperty(value = "Nome do Semestre")
    private String nome;

    @ApiModelProperty(value = "Ano do Semestre")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Year ano;

    @ApiModelProperty(value = "Data inicio")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date initDate;

    @ApiModelProperty(value = "Data final")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date finalDate;

}
