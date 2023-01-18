package br.ueg.madamestore.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jfree.data.time.Hour;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Hour")
public @Data
class HourDTO implements Serializable {

    @ApiModelProperty(value = "id da Hour")
    private Long id;

    @ApiModelProperty(value = "id da classroom")
    private String idClassroom;

    @ApiModelProperty(value = "hora de inicio")
    private String initHour;

    @ApiModelProperty(value = "hora final")
    private String finalHour;


    @ApiModelProperty(value = "Id do Semester")
    private String weekDay;

}
