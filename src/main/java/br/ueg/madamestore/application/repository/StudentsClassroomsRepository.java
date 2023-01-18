package br.ueg.madamestore.application.repository;

import br.ueg.madamestore.application.model.StudentsClassrooms;
import br.ueg.madamestore.application.model.Student;
import br.ueg.madamestore.application.model.Classroom;
import br.ueg.madamestore.application.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentsClassroomsRepository extends JpaRepository<StudentsClassrooms, Long> {

    StudentsClassrooms findByClassroom(Classroom classroom);

    @Query("select distinct g from StudentsClassrooms ug " +
            "LEFT JOIN ug.classroom u " +
            "LEFT JOIN ug.student g " +
            "where ug.classroom.id=:idClassroom")
    List<Student> findByIdClassroom(Long idClassroom);

    @Query("SELECT studentsClassrooms from StudentsClassrooms studentsClassrooms " +
            " INNER JOIN FETCH studentsClassrooms.student student " +
            " INNER JOIN FETCH studentsClassrooms.classroom classroom"+
            " WHERE studentsClassrooms.id = :idStudentsClassrooms ")
    public Optional<StudentsClassrooms> findByIdFetch(@Param("idStudentsClassrooms") final Long idStudentsClassrooms);

    @Query("SELECT studentsClassrooms from StudentsClassrooms studentsClassrooms " +
            " WHERE studentsClassrooms.classroom.id = :idClassroom " +
            " AND studentsClassrooms.student.id = :idStudent")
    public StudentsClassrooms findByIdClassroomAndIdStudent(@Param("idClassroom") Long idClassroom, @Param("idStudent") Long idStudent);


}

