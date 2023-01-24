package br.ueg.madamestore.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Parameter")
public @Data
class ParameterDTO implements Serializable {

    @ApiModelProperty(value = "id da Parameter")
    private Long id;

    @ApiModelProperty(value = "id da classroom")
    private String idSolicitation;

    @ApiModelProperty(value = "hora de inicio")
    private String type;

    @ApiModelProperty(value = "hora final")
    private String name;

    @ApiModelProperty(value = "hora final")
    private String value;

}
