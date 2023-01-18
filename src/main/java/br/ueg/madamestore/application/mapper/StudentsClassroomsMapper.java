/*
 * UsuarioGrupoMapper.java
 * Copyright UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.mapper;

import br.ueg.madamestore.application.dto.StudentsClassroomsDTO;
import br.ueg.madamestore.application.model.StudentsClassrooms;
import br.ueg.madamestore.application.model.UsuarioGrupo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link UsuarioGrupo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class, ClassroomMapper.class })
public interface StudentsClassroomsMapper {
    /**
     * Converte a entidade {@link StudentsClassrooms} em DTO {@link br.ueg.madamestore.application.dto.StudentsClassroomsDTO}.
     *
     * @param studentsClassrooms
     * @return
     */
    @Mapping(source = "classroom.id", target = "idClassroom")
    @Mapping(source = "student.id", target = "idStudent")
    @Mapping(source = "classroom.subject.nome", target = "nomeSubject")
    public StudentsClassroomsDTO toDTO(StudentsClassrooms studentsClassrooms);

    /**
     * Converte o DTO {@link StudentsClassroomsDTO} para entidade {@link StudentsClassrooms}.
     *
     * @param studentsClassroomsDTO
     * @return
     */
    @Mapping(source = "studentsClassroomsDTO.idClassroom", target = "classroom.id")
    @Mapping(source = "studentsClassroomsDTO.nomeSubject", target = "classroom.subject.nome")
    @Mapping(source = "studentsClassroomsDTO.idStudent", target = "student.id")
    public StudentsClassrooms toEntity(StudentsClassroomsDTO studentsClassroomsDTO);
}
