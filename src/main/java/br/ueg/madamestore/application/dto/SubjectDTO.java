package br.ueg.madamestore.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Subject")
public @Data
class SubjectDTO implements Serializable {

    @ApiModelProperty(value = "id da disciplina")
    private Long id;

    @ApiModelProperty(value = "Nome da disciplina")
    private String nome;
}
