package br.ueg.madamestore.application.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.ueg.madamestore.application.dto.TeacherDTO;
import br.ueg.madamestore.application.model.Teacher;

/**
 * Classe adapter referente a entidade {@link Teacher}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring")
public interface TeacherMapper {

    public TeacherDTO toDTO(Teacher teacher);


    public Teacher toEntity(TeacherDTO teacherDTO);
}
