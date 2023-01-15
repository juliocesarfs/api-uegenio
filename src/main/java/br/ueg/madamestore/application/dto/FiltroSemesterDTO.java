package br.ueg.madamestore.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Dados do filtro de pesquisa do Prouto")
public @Data class FiltroSemesterDTO implements Serializable {

    @ApiModelProperty(value = "Nome do Semester")
    private String nome;


    @ApiModelProperty(value = "Ano do Semester")
    private Year ano;

}
