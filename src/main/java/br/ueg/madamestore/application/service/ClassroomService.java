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
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeachersClassroomsRepository teachersClassroomsRepository;

    @Autowired
    private AuthService authService;

    /**
     * Persiste os dados do {@link Classroom}.
     *
     * @param classroom
     * @return
     */


    public Classroom salvar(Classroom classroom) {


        configurarClassroomTeacher(classroom);
        //validaTotalQuantidade(classroom);
        buscarTeacher(classroom);
        buscarSemester(classroom);
        buscarSubject(classroom);
        validarCamposObrigatorios(classroom);
        classroom= classroomRepository.save(classroom);
        classroom = classroomRepository.findByIdFetch(classroom.getId()).get();
        return classroom;
    }



    private void buscarSemester(Classroom classroom) {
        Semester semester;
        semester= semesterRepository.getOne(classroom.getSemester().getId());

        if(semester==null){
            throw new BusinessException(SistemaMessageCode.ERRO_CLIENTE_NAO_ENCONTRADO);
        }
        classroom.setSemester(semester);
    }

    private void buscarSubject(Classroom classroom) {
        Subject subject;
        subject= subjectRepository.getOne(classroom.getSubject().getId());

        if(subject==null){
            throw new BusinessException(SistemaMessageCode.ERRO_CLIENTE_NAO_ENCONTRADO);
        }
        classroom.setSubject(subject);
    }

    private void buscarTeacher(Classroom classroom) {

        for (TeachersClassrooms teachersClassrooms : classroom.getTeachersClassrooms()) {
            Teacher teacher;

            teacher= teacherRepository.getOne(teachersClassrooms.getTeacher().getId());
            if(teacher==null){
                throw new BusinessException(SistemaMessageCode.ERRO_PRODUTO_NAO_ENCONTRADO);
            }
            teachersClassrooms.setTeacher(teacher);
        }
    }


    /**
     * Configura o {@link Classroom} dentro de  {@link TelefoneUsuario} para salvar.
     *
     * @param classroom
     */
    public void configurarClassroomTeacher(Classroom classroom) {
        for (TeachersClassrooms teachersClassrooms : classroom.getTeachersClassrooms()){
            teachersClassrooms.setClassroom(classroom);
        }


    }

    public void configurarStudentClassroom(Classroom classroom) {
        for (TeachersClassrooms teachersClassrooms : classroom.getTeachersClassrooms()){
            teachersClassrooms.setClassroom(classroom);
        }

    }



    /**
     * Verifica se os campos obrigatorios de {@link Classroom} foram preenchidos.
     *
     * @param classroom
     */
    private void validarCamposObrigatorios(final Classroom classroom) {
        boolean invalido = Boolean.FALSE;

        if (classroom.getSemester()== null) {
            invalido = Boolean.TRUE;
        }

        if (classroom.getTeachersClassrooms() == null)
            invalido = Boolean.TRUE;

        if (classroom.getSubject() == null)
            invalido = Boolean.TRUE;

        if (classroom.getLocal() == null)
            invalido = Boolean.TRUE;



        if (invalido) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }



    /**
     * Retorna a Lista de {@link ClassroomDTO} conforme o filtro pesquisado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Classroom> getClassroomByFiltro(FiltroClassroomDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);



        List<Classroom> classroom = classroomRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(classroom)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return classroom;
    }

    public Classroom remover(Long id){
        Classroom classroom = this.getById(id);

        configurarClassroomTeacher(classroom);
        buscarTeacher(classroom);
        buscarSemester(classroom);
        buscarSubject(classroom);
        classroomRepository.delete(classroom);


        return classroom;
    }



    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroClassroomDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;


        if (filtroDTO.getSubject()!=null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getTeacher()!=null) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getWeekDay()!=null) {
            vazio = Boolean.FALSE;
        }





        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }



    /**
     * Retorna uma lista de {@link Classroom} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Classroom> getTeachersByFiltro(final FiltroClassroomDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Classroom> classroom = classroomRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(classroom)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return classroom;
    }




    /**
     * Retorna a instância de {@link Classroom} conforme o 'id' informado.
     *
     * @param id -
     * @return -
     */
    public Classroom getById(final Long id) {
        return classroomRepository.findById(id).orElse(null);
    }

    public Classroom getBySubjectName(String nomeSubject) {
        return classroomRepository.findBySubjectName(nomeSubject).orElse(null);
    }

    public List<Classroom> getClassrooms() { return classroomRepository.getTodos(); }

    /**
     * Retorna a instância de {@link Usuario} conforme o 'id' informado.
     *
     * @param id
     * @return
     */
    public Classroom getByIdFetch(final Long id) {
        return classroomRepository.findByIdFetch(id).orElse(null);
    }







}
