package br.ueg.madamestore.application.solicitation;

import br.ueg.madamestore.application.dto.NotaDTO;
import br.ueg.madamestore.application.dto.ParameterDTO;
import br.ueg.madamestore.application.model.StudentsClassrooms;
import br.ueg.madamestore.application.repository.StudentsClassroomsRepository;
import br.ueg.madamestore.application.service.StudentsClassroomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SolicitationNota implements SolicitationInterface {

    private String userId;

    private String subject;

    private StudentsClassroomsService studentsClassroomsService = new StudentsClassroomsService();

    public SolicitationNota(List<ParameterDTO> parameters) {
        for(ParameterDTO parameter : parameters) {
            if (parameter.getName().equals("userId")) {
                this.userId = parameter.getValue();
            }
            if (parameter.getName().equals("subject")) {
                this.subject = parameter.getValue();
            }
        }
    }

    @Override
    public NotaDTO execute() {

        NotaDTO notaDTO = studentsClassroomsService.getNotaByIdStudentAndSubjectName(Long.parseLong(this.userId), subject);

        System.out.println(notaDTO);

        return notaDTO;
    }
}
