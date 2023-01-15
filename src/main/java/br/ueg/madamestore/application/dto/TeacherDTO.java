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
@ApiModel(value = "Entidade de transferência de Teacher")
public @Data
class TeacherDTO implements Serializable {

    @ApiModelProperty(value = "id da professor")
    private Long id;

    @ApiModelProperty(value = "Nome da professor")
    private String nome;

    @ApiModelProperty(value = "Nome da professor")
    private String email;
}
