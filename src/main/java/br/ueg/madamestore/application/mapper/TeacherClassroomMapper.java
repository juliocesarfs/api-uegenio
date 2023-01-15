/*
 * UsuarioGrupoMapper.java
 * Copyright UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.mapper;

import br.ueg.madamestore.application.dto.TeacherClassroomDTO;
import br.ueg.madamestore.application.model.TeacherClassroom;
import br.ueg.madamestore.application.model.UsuarioGrupo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link UsuarioGrupo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = {TeacherMapper.class, ClassroomMapper.class })
public interface TeacherClassroomMapper {
    /**
     * Converte a entidade {@link TeacherClassroom} em DTO {@link br.ueg.madamestore.application.dto.TeacherClassroomDTO}.
     *
     * @param teacherClassroom
     * @return
     */
    @Mapping(source = "classroom.id", target = "idClassroom")
    @Mapping(source = "teacher.id", target = "idTeacher")
    public TeacherClassroomDTO toDTO(TeacherClassroom teacherClassroom);

    /**
     * Converte o DTO {@link TeacherClassroomDTO} para entidade {@link TeacherClassroom}.
     *
     * @param teacherClassroomDTO
     * @return
     */
    @Mapping(source = "teacherClassroomDTO.idClassroom", target = "classroom.id")
    @Mapping(source = "teacherClassroomDTO.idTeacher", target = "teacher.id")
    public TeacherClassroom toEntity(TeacherClassroomDTO teacherClassroomDTO);
}
