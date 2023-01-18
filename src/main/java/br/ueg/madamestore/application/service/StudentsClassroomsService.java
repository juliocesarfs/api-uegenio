package br.ueg.madamestore.application.service;

import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.model.Classroom;
import br.ueg.madamestore.application.model.Student;
import br.ueg.madamestore.application.model.StudentsClassrooms;
import br.ueg.madamestore.application.model.Venda;
import br.ueg.madamestore.application.repository.StudentsClassroomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StudentsClassroomsService {
    @Autowired
    private StudentsClassroomsRepository studentsClassroomsRepository;

    /***
     * Retorna Grupos vinculados a um usuário específico
     * @param classroomId
     * @return
     */
    public List<Student> getItensClassroom(Long classroomId){
        return studentsClassroomsRepository.findByIdClassroom(classroomId);
    }

    public StudentsClassrooms getByIdClassmAndIdStudent(Long idClassroom, Long idStudent){
        return studentsClassroomsRepository.findByIdClassroomAndIdStudent(idClassroom, idStudent);
    }

    public StudentsClassrooms salvar(StudentsClassrooms studentsClassrooms) {


        studentsClassrooms = studentsClassroomsRepository.save(studentsClassrooms);
        studentsClassrooms = studentsClassroomsRepository.findByIdFetch(studentsClassrooms.getId().longValue()).get();
        return studentsClassrooms;
    }

    public void remover(StudentsClassrooms studentsClassrooms){



        studentsClassroomsRepository.delete(studentsClassrooms);


    }

}
