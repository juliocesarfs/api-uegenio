package br.ueg.madamestore.application.mapper;


import br.ueg.madamestore.application.dto.ClassroomDTO;
import br.ueg.madamestore.application.model.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link Classroom}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { TeacherMapper.class, TeacherClassroomMapper.class })
public interface ClassroomMapper {

    /**
     * Converte a entidade {@link Classroom} em DTO {@link ClassroomDTO}
     *
     * @param classroom
     * @return
     */

    @Mapping(source = "subject.id", target = "idSubject")
    @Mapping(source = "subject.nome", target = "nomeSubject")
    public ClassroomDTO toDTO(Classroom classroom);

    /**
     * Converte o DTO {@link ClassroomDTO} para entidade {@link Classroom}
     *
     * @param classroomDTO
     * @return
     */
    @Mapping(source = "classroomDTO.idSubject", target = "subject.id")
    @Mapping(source = "classroomDTO.nomeSubject", target = "subject.nome")
    @Mapping(source = "classroomDTO.idSemester", target = "semester.id")
    @Mapping(source = "classroomDTO.teachersClassrooms", target = "teachersClassrooms")
    public Classroom toEntity(ClassroomDTO classroomDTO);


}
