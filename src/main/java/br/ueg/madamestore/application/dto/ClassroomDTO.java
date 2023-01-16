package br.ueg.madamestore.application.dto;

import br.ueg.madamestore.application.model.TeachersClassrooms;
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
@ApiModel(value = "Entidade de transferência de Classroom")
public @Data
class ClassroomDTO implements Serializable {

    @ApiModelProperty(value = "id da Classroom")
    private Long id;

    @ApiModelProperty(value = "nome do Teacher")
    private List<TeacherDTO> teacher;

    @ApiModelProperty(value = "teachersClassrooms")
    private List<TeachersClassroomsDTO> teachersClassrooms;

    @ApiModelProperty(value = "Id do Semester")
    private Long idSemester;

    @ApiModelProperty(value = "nome do cliente")
    private String nomeSemester;

    @ApiModelProperty(value = "nome do cliente")
    private String local;

    @ApiModelProperty(value = "Id do Semester")
    private Long idSubject;

    @ApiModelProperty(value = "nome do cliente")
    private String nomeSubject;

}
