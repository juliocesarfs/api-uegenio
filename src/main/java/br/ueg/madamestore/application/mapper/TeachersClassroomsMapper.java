/*
 * UsuarioGrupoMapper.java
 * Copyright UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.mapper;

import br.ueg.madamestore.application.dto.TeachersClassroomsDTO;
import br.ueg.madamestore.application.model.TeachersClassrooms;
import br.ueg.madamestore.application.model.UsuarioGrupo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link UsuarioGrupo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = {TeacherMapper.class, ClassroomMapper.class })
public interface TeachersClassroomsMapper {
    /**
     * Converte a entidade {@link TeachersClassrooms} em DTO {@link br.ueg.madamestore.application.dto.TeachersClassroomsDTO}.
     *
     * @param teachersClassrooms
     * @return
     */
    @Mapping(source = "classroom.id", target = "idClassroom")
    @Mapping(source = "teacher.id", target = "idTeacher")
    @Mapping(source = "teacher.nome", target = "nomeTeacher")
    public TeachersClassroomsDTO toDTO(TeachersClassrooms teachersClassrooms);

    /**
     * Converte o DTO {@link TeachersClassroomsDTO} para entidade {@link TeachersClassrooms}.
     *
     * @param teachersClassroomsDTO
     * @return
     */
    @Mapping(source = "teachersClassroomsDTO.idClassroom", target = "classroom.id")
    @Mapping(source = "teachersClassroomsDTO.idTeacher", target = "teacher.id")
    public TeachersClassrooms toEntity(TeachersClassroomsDTO teachersClassroomsDTO);
}
