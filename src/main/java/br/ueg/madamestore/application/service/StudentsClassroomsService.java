package br.ueg.madamestore.application.service;

import br.ueg.madamestore.application.dto.FiltroClassroomDTO;
import br.ueg.madamestore.application.dto.FiltroStudentsClassroomsDTO;
import br.ueg.madamestore.application.dto.FrequencyDTO;
import br.ueg.madamestore.application.dto.NotaDTO;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.Classroom;
import br.ueg.madamestore.application.model.Student;
import br.ueg.madamestore.application.model.StudentsClassrooms;
import br.ueg.madamestore.application.model.Venda;
import br.ueg.madamestore.application.repository.StudentsClassroomsRepository;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StudentsClassroomsService {
    @Autowired
    private StudentsClassroomsRepository studentsClassroomsRepository;



    public StudentsClassrooms getByIdClassmAndIdStudent(Long idClassroom, Long idStudent){
        return studentsClassroomsRepository.findByIdClassroomAndIdStudent(idClassroom, idStudent);
    }

    public NotaDTO getNotaByIdStudentAndSubjectName(Long idStudent, String subjectName){

        List<NotaDTO> notaDTOList = new ArrayList<>();
        NotaDTO notaDTO = new NotaDTO();
        notaDTO.setIdStudent(1L);
        notaDTO.setSubject("Gestão de Processos");
        notaDTO.setNota_1va(8.9);
        notaDTO.setNota_2va(8.0);
        notaDTO.setMedia_final(((notaDTO.getNota_1va()*2)+(notaDTO.getNota_2va()*3))/5);

        notaDTOList.add(notaDTO);

        NotaDTO notaDTO2 = new NotaDTO();
        notaDTO2.setIdStudent(1L);
        notaDTO2.setSubject("Tópicos avançados em análise de dados");
        notaDTO2.setNota_1va(7.9);
        notaDTO2.setNota_2va(null);
        notaDTO2.setMedia_final(null);


        notaDTOList.add(notaDTO2);

        NotaDTO notaDTO3 = new NotaDTO();
        notaDTO3.setIdStudent(1L);
        notaDTO3.setSubject("Contabilidade, Economia e Finanças");
        notaDTO3.setNota_1va(null);
        notaDTO3.setNota_2va(null);
        notaDTO3.setMedia_final(null);

        notaDTOList.add(notaDTO3);

        NotaDTO notaDTO4 = new NotaDTO();
        notaDTO4.setIdStudent(1L);
        notaDTO4.setSubject("Direito e Ética em Sistemas de Informação");
        notaDTO4.setNota_1va(4.0);
        notaDTO4.setNota_2va(3.0);
        notaDTO4.setMedia_final(((notaDTO4.getNota_1va()*2)+(notaDTO4.getNota_2va()*3))/5);;


        notaDTOList.add(notaDTO4);

        NotaDTO notaDTO5 = new NotaDTO();
        notaDTO5.setIdStudent(1L);
        notaDTO5.setSubject("O profissional de Informática e a Sociedade");
        notaDTO5.setNota_1va(10.0);
        notaDTO5.setNota_2va(8.9);
        notaDTO5.setMedia_final(((notaDTO5.getNota_1va()*2)+(notaDTO5.getNota_2va()*3))/5);;

        notaDTOList.add(notaDTO5);

        NotaDTO notaReturn = null;
        for (NotaDTO nota : notaDTOList) {
            if (nota.getSubject().equals(subjectName) && nota.getIdStudent() == idStudent) {
                notaReturn = nota;
                break;
            }
        }

        if (notaReturn == null) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return notaReturn;
    }

    public FrequencyDTO getFrequencyByIdStudentAndSubjectName(Long idStudent, String subjectName){

        List<FrequencyDTO> frequencyDTOList = new ArrayList<>();
        FrequencyDTO frequencyDTO = new FrequencyDTO();
        frequencyDTO.setIdStudent(1L);
        frequencyDTO.setSubject("Gestão de Processos");
        frequencyDTO.setFaltas(10);
        frequencyDTO.setSituation("CURSANDO");

        frequencyDTOList.add(frequencyDTO);

        FrequencyDTO frequencyDTO2 = new FrequencyDTO();
        frequencyDTO2.setIdStudent(1L);
        frequencyDTO2.setSubject("Tópicos avançados em análise de dados");
        frequencyDTO2.setFaltas(7);
        frequencyDTO2.setSituation("CURSANDO");

        frequencyDTOList.add(frequencyDTO2);

        FrequencyDTO frequencyDTO3 = new FrequencyDTO();
        frequencyDTO3.setIdStudent(1L);
        frequencyDTO3.setSubject("Contabilidade, Economia e Finanças");
        frequencyDTO3.setFaltas(3);
        frequencyDTO3.setSituation("CURSANDO");

        frequencyDTOList.add(frequencyDTO3);

        FrequencyDTO frequencyDTO4 = new FrequencyDTO();
        frequencyDTO4.setIdStudent(1L);
        frequencyDTO4.setSubject("Direito e Ética em Sistemas de Informação");
        frequencyDTO4.setFaltas(19);
        frequencyDTO4.setSituation("REPROVADO");

        frequencyDTOList.add(frequencyDTO4);

        FrequencyDTO frequencyDTO5 = new FrequencyDTO();
        frequencyDTO5.setIdStudent(1L);
        frequencyDTO5.setSubject("O profissional de Informática e a Sociedade");
        frequencyDTO5.setFaltas(16);
        frequencyDTO5.setSituation("CURSANDO");

        frequencyDTOList.add(frequencyDTO5);

        FrequencyDTO frequencyReturn = null;

        for (FrequencyDTO nota : frequencyDTOList) {
            if (nota.getSubject().equals(subjectName) && nota.getIdStudent() == idStudent) {
                frequencyReturn = nota;
                break;
            }
        }

        if (frequencyReturn == null) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return frequencyReturn;
    }

    public Set<StudentsClassrooms> getByIdClassroom(Long idClassroom){
        return studentsClassroomsRepository.findByIdClassroom(idClassroom);
    }

        public StudentsClassrooms salvar(StudentsClassrooms studentsClassrooms) {


        studentsClassrooms = studentsClassroomsRepository.save(studentsClassrooms);
        studentsClassrooms = studentsClassroomsRepository.findByIdFetch(studentsClassrooms.getId().longValue()).get();
        return studentsClassrooms;
    }

    public List<Classroom> getClassroomByFiltro(FiltroStudentsClassroomsDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);



        List<Classroom> classroom = studentsClassroomsRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(classroom)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return classroom;
    }


    private void validarCamposObrigatoriosFiltro(final FiltroStudentsClassroomsDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;


        if (filtroDTO.getIdStudent()!=null && filtroDTO.getWeekDay()!=null) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }



    public void remover(StudentsClassrooms studentsClassrooms){

        studentsClassroomsRepository.delete(studentsClassrooms);

    }

}
