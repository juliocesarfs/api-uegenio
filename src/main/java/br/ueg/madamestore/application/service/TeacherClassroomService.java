package br.ueg.madamestore.application.service;

import br.ueg.madamestore.application.model.Holiday;
import br.ueg.madamestore.application.model.Teacher;
import br.ueg.madamestore.application.model.TeacherClassroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.madamestore.application.repository.TeacherClassroomRepository;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TeacherClassroomService {
    @Autowired
    private TeacherClassroomRepository teacherClassroomRepository;

    /***
     * Retorna Classrooms vinculados a um teacher específico
     * @param userId
     * @return
     */
    public List<Teacher> getTeacherClassrooms(Long userId){
        return teacherClassroomRepository.findByIdClassroom(userId);
    }

    public TeacherClassroom salvar(TeacherClassroom holiday) {

        TeacherClassroom grupoSaved = teacherClassroomRepository.save(holiday);
        return grupoSaved;
    }

}
