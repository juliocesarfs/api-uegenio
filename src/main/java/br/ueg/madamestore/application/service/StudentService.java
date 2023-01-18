/*
 * UsuarioService.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.service;

import br.ueg.madamestore.application.configuration.Constante;
import br.ueg.madamestore.application.dto.*;
import br.ueg.madamestore.application.enums.StatusAtivoInativo;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.*;
import br.ueg.madamestore.application.repository.*;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import br.ueg.madamestore.comum.util.Util;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.ueg.madamestore.application.exception.SistemaMessageCode.ERRO_QUANTIDADE_DE_PRODUTOS_INSUFICIENTE;

/**
 * Classe de négocio referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private ClassroomService classroomService;






    @Autowired
    private StudentsClassroomsRepository studentsStudentsRepository;

    /**
     * Persiste os dados do {@link Student}.
     *
     * @param student
     * @return
     */

    public Student salvar(Student student) {


        //configurarStudentClassroom(student);
        //validaTotalQuantidade(student);
        buscarClassroom(student);
        validarCamposObrigatorios(student);
        student = studentRepository.save(student);
        student = studentRepository.findByIdFetch(student.getId()).get();
        return student;
    }

    private void buscarClassroom(Student student) {

        if (student.getStudentsClassrooms() != null) {
            for (StudentsClassrooms studentsClassrooms : student.getStudentsClassrooms()) {
                Classroom classroom;

                classroom = classroomRepository.getOne(studentsClassrooms.getClassroom().getId());

                if(classroom!=null){
                    studentsClassrooms.setClassroom(classroom);
                }
            }
        }
    }


    /**
     * Configura o {@link Student} dentro de  {@link TelefoneUsuario} para salvar.
     *
     * @param student
     */
    public void configurarStudentClassroom(Student student, Classroom classroom) {
        if (student.getStudentsClassrooms() != null) {
            for (StudentsClassrooms studentsClassrooms : student.getStudentsClassrooms()){
                studentsClassrooms.setStudent(student);
                studentsClassrooms.setClassroom(classroom);
            }
        }

    }



    /**
     * Verifica se os campos obrigatorios de {@link Student} foram preenchidos.
     *
     * @param student
     */
    private void validarCamposObrigatorios(final Student student) {
        boolean invalido = Boolean.FALSE;

        if (student.getAlexaID()== null) {
            invalido = Boolean.TRUE;
        }


        if (invalido) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }



    /**
     * Retorna a Lista de {@link StudentDTO} conforme o filtro pesquisado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Student> getStudentByFiltro(FiltroStudentDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);



        List<Student> student = studentRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(student)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return student;
    }

    public Student remover(Long id) {
        Student student = this.getById(id);

        for (StudentsClassrooms stc: student.getStudentsClassrooms()) {
            Classroom classroom = classroomService.getById(stc.getId());

            configurarStudentClassroom(student, classroom);
        }
        buscarClassroom(student);
        studentRepository.delete(student);

        return student;
    }



    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroStudentDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;


        if (filtroDTO.getIdStudent()!=null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getAlexaID()!=null) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }





    /**
     * Retorna a instância de {@link Student} conforme o 'id' informado.
     *
     * @param id -
     * @return -
     */
    public Student getById(final Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student getByAlexaID(String alexaID) {
        return studentRepository.findByAlexaId(alexaID).orElse(null);
    }

    public List<Student> getStudents() { return studentRepository.getTodos(); }

    /**
     * Retorna a instância de {@link Usuario} conforme o 'id' informado.
     *
     * @param id
     * @return
     */
    public Student getByIdFetch(final Long id) {
        return studentRepository.findByIdFetch(id).orElse(null);
    }
}
