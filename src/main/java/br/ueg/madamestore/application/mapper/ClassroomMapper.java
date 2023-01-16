package br.ueg.madamestore.application.mapper;


import br.ueg.madamestore.application.dto.TeacherDTO;
import br.ueg.madamestore.application.dto.UsuarioDTO;
import br.ueg.madamestore.application.dto.ClassroomDTO;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.model.Teacher;
import br.ueg.madamestore.application.model.Usuario;
import br.ueg.madamestore.application.model.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Classe adapter referente a entidade {@link Classroom}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { TeacherMapper.class, TeachersClassroomsMapper.class})
public interface ClassroomMapper {

    /**
     * Converte a entidade {@link Classroom} em DTO {@link ClassroomDTO}
     *
     * @param classroom
     * @return
     */

    @Mapping(source = "semester.id", target = "idSemester")
    @Mapping(source = "semester.nome", target = "nomeSemester")
    @Mapping(source = "subject.id", target = "idSubject")
    @Mapping(source = "subject.nome", target = "nomeSubject")
    public ClassroomDTO toDTO(Classroom classroom);

    /**
     * Converte o DTO {@link ClassroomDTO} para entidade {@link Classroom}
     *
     * @param classroomDTO
     * @return
     */
    @Mapping(source = "classroomDTO.idSemester", target = "semester.id")
    @Mapping(source = "classroomDTO.idSubject", target = "subject.id")
    public Classroom toEntity(ClassroomDTO classroomDTO);


}
