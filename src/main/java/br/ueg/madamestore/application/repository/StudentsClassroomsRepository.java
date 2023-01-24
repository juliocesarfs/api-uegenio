package br.ueg.madamestore.application.repository;

import br.ueg.madamestore.application.model.StudentsClassrooms;
import br.ueg.madamestore.application.model.Student;
import br.ueg.madamestore.application.model.Classroom;
import br.ueg.madamestore.application.model.Venda;
import br.ueg.madamestore.application.service.StudentsClassroomsService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StudentsClassroomsRepository extends JpaRepository<StudentsClassrooms, Long>, StudentsClassroomsRepositoryCustom {

    StudentsClassrooms findByClassroom(Classroom classroom);

    @Query("select studentsClassrooms from StudentsClassrooms studentsClassrooms " +
            "where studentsClassrooms.classroom.id = :idClassroom")
    Set<StudentsClassrooms> findByIdClassroom(Long idClassroom);

    @Query("SELECT studentsClassrooms from StudentsClassrooms studentsClassrooms " +
            " INNER JOIN FETCH studentsClassrooms.student student " +
            " INNER JOIN FETCH studentsClassrooms.classroom classroom"+
            " WHERE studentsClassrooms.id = :idStudentsClassrooms ")
    public Optional<StudentsClassrooms> findByIdFetch(@Param("idStudentsClassrooms") final Long idStudentsClassrooms);

    @Query("SELECT studentsClassrooms from StudentsClassrooms studentsClassrooms " +
            " WHERE studentsClassrooms.classroom.id = :idClassroom " +
            " AND studentsClassrooms.student.id = :idStudent")
    public StudentsClassrooms findByIdClassroomAndIdStudent(@Param("idClassroom") Long idClassroom, @Param("idStudent") Long idStudent);

    @Query("SELECT studentsClassrooms from StudentsClassrooms studentsClassrooms " +
            " INNER JOIN FETCH studentsClassrooms.classroom classroom "+
            " WHERE studentsClassrooms.student.id = :idStudent " +
            " AND classroom.subject.nome = :subjectName")
    public StudentsClassrooms findByIdStudentAndSubjectName(@Param("idStudent") Long idStudent, @Param("subjectName") String subjectName);


}

