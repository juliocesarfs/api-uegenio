package br.ueg.madamestore.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jfree.data.time.Hour;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Solicitation")
public @Data
class SolicitationDTO implements Serializable {

    @ApiModelProperty(value = "id da Solicitation")
    private Long id;

    @ApiModelProperty(value = "nome do Teacher")
    private List<ParameterDTO> parameters;

    @ApiModelProperty(value = "tipo da solicitação")
    private String type;

}
