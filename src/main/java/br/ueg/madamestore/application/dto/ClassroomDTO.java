package br.ueg.madamestore.application.dto;

import br.ueg.madamestore.application.model.Semester;
import br.ueg.madamestore.application.model.Subject;
import br.ueg.madamestore.application.model.TeacherClassroom;
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

    @ApiModelProperty(value = "teachersClassrooms")
    private List teachersClassrooms;

    @ApiModelProperty(value = "teachersClassrooms")
    private List idTeacher;

    @ApiModelProperty(value = "local da aula")
    private String local;

    /*
    @ApiModelProperty(value = "horarios da aula")
    private List<TimeDTO> times;
*/
    @ApiModelProperty(value = "Id da disciplina")
    private Long idSubject;

    @ApiModelProperty(value = "Id do semestre")
    private Long idSemester;

    @ApiModelProperty(value = "Nome da disciplina")
    private String nomeSubject;

}
