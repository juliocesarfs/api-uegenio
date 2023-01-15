package br.ueg.madamestore.application.repository;

import br.ueg.madamestore.application.enums.StatusAtivoInativo;
import br.ueg.madamestore.application.model.Classroom;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.TeacherClassroom;
import br.ueg.madamestore.application.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherClassroomRepository extends JpaRepository<TeacherClassroom, Long> {

    TeacherClassroom findByClassroom(Classroom classroom);

    @Query("select distinct g from TeacherClassroom ug " +
            "LEFT JOIN ug.classroom u " +
            "LEFT JOIN ug.teacher g " +
            "where ug.classroom.id=:idClassroom")
    List<Teacher> findByIdClassroom(Long idClassroom);
}

