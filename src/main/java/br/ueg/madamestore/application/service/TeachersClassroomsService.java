package br.ueg.madamestore.application.service;

import br.ueg.madamestore.application.model.Teacher;
import br.ueg.madamestore.application.repository.TeachersClassroomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TeachersClassroomsService {
    @Autowired
    private TeachersClassroomsRepository teachersClassroomsRepository;

    /***
     * Retorna Grupos vinculados a um usuário específico
     * @param classroomId
     * @return
     */
    public List<Teacher> getItensClassroom(Long classroomId){
        return teachersClassroomsRepository.findByIdClassroom(classroomId);
    }

}
