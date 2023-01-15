/*
 * ClassroomService.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.service;

import br.ueg.madamestore.application.model.TeacherClassroom;
import br.ueg.madamestore.application.model.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.madamestore.application.configuration.Constante;
import br.ueg.madamestore.application.dto.FiltroClassroomDTO;
import br.ueg.madamestore.application.dto.ClassroomDTO;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.Classroom;
import br.ueg.madamestore.application.repository.ClassroomRepository;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import br.ueg.madamestore.comum.util.Util;


import java.util.List;

/**
 * Classe de négocio referente a entidade {@link Classroom}.
 *
 * @author UEG
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;




    /**
     * Persiste os dados do {@link Classroom}.
     *
     * @param classroom
     * @return
     */
    public Classroom salvar(Classroom classroom) {
        validarCamposObrigatorios(classroom);

        classroom = classroomRepository.save(classroom);
        return classroom;
    }

    /**
     * Configura o {@link Classroom} dentro de {@link TeacherClassroom}.
     *
     * @param classroom
     */
    public void configurarClassroomTeachersAndTimes(Classroom classroom) {
        for (TeacherClassroom teacherClassroom : classroom.getTeachersClassrooms()) {
            teacherClassroom.setClassroom(classroom);
        }

        /*
        for (Times time : classroom.get()) {
            telefoneClassroom.setClassroom(classroom);
        }
         */
    }




    /**
     * Verifica se os campos obrigatorios de {@link Classroom} foram preenchidos.
     *
     * @param classroom
     */
    private void validarCamposObrigatorios(final Classroom classroom) {
        boolean invalido = Boolean.FALSE;

        if (classroom.getSubject() == null)
            invalido = Boolean.TRUE;

        if (classroom.getSemester() == null)
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
    public List<Classroom> getClassroomsByFiltro(FiltroClassroomDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Classroom> classrooms = classroomRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(classrooms)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return classrooms;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroClassroomDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getSubject())) {
            vazio = Boolean.FALSE;

            if (filtroDTO.getSubject().trim().length() < Integer.parseInt(Constante.TAMANHO_MINIMO_PESQUISA_NOME)) {
                throw new BusinessException(SistemaMessageCode.ERRO_TAMANHO_INSUFICIENTE_FILTRO_NOME);
            }
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
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

    /**
     * Retorna a instância de {@link Classroom} conforme o 'id' informado.
     *
     * @param id
     * @return
     */
    public Classroom getByIdFetch(final Long id) {
        return classroomRepository.findByIdFetch(id).orElse(null);
    }

    public Classroom remover(Long id){
        Classroom classroom = this.getById(id);

        classroomRepository.delete(classroom);

        return classroom;
    }

}
