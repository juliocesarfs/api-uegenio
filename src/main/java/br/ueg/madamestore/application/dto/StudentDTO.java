package br.ueg.madamestore.application.dto;

import br.ueg.madamestore.application.model.StudentsClassrooms;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Entidade de transferência de Student")
public @Data
class StudentDTO implements Serializable {

    @ApiModelProperty(value = "id da Student")
    private Long id;

    @ApiModelProperty(value = "id da Student")
    private String alexaID;

    @ApiModelProperty(value = "studentsClassrooms")
    private List<StudentsClassroomsDTO> studentsClassrooms;


}
