package br.ueg.madamestore.application.mapper;


import br.ueg.madamestore.application.dto.TeacherDTO;
import br.ueg.madamestore.application.model.Modulo;
import br.ueg.madamestore.application.model.Teacher;
import org.mapstruct.Mapper;

/**
 * Classe adapter referente a entidade {@link Modulo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring")
public interface TeacherMapper {
    /**
     * Converte a entidade {@link Teacher} em DTO {@link TeacherDTO}
     *
     * @param teacher
     * @return
     */

    public TeacherDTO toDTO(Teacher teacher);

    /**
     * Converte o DTO {@link TeacherDTO} para entidade {@link Teacher}
     *
     * @param teacherDTO
     * @return
     */
    public Teacher toEntity(TeacherDTO teacherDTO);
}
