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
@ApiModel(value = "Entidade de transferência de Holiday")
public @Data
class HolidayDTO implements Serializable {

    @ApiModelProperty(value = "id da feriado")
    private Long id;

    @ApiModelProperty(value = "id da feriado")
    private Long idSemester;

    @ApiModelProperty(value = "Nome da feriado")
    private String nome;

    @ApiModelProperty(value = "Data inicio")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date initDate;

    @ApiModelProperty(value = "Data final")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date finalDate;


}
