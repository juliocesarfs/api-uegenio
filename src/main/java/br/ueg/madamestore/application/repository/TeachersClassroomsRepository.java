package br.ueg.madamestore.application.repository;

import br.ueg.madamestore.application.model.TeachersClassrooms;
import br.ueg.madamestore.application.model.Teacher;
import br.ueg.madamestore.application.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachersClassroomsRepository extends JpaRepository<TeachersClassrooms, Long> {

    TeachersClassrooms findByClassroom(Classroom venda);

    @Query("select distinct g from TeachersClassrooms ug " +
            "LEFT JOIN ug.classroom u " +
            "LEFT JOIN ug.teacher g " +
            "where ug.classroom.id=:idClassroom")
    List<Teacher> findByIdClassroom(Long idClassroom);


}

