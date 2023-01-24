/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.repository;


import br.ueg.madamestore.application.dto.FiltroClassroomDTO;
import br.ueg.madamestore.application.dto.FiltroStudentsClassroomsDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.Classroom;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 *
 * @author UEG
 */
public interface StudentsClassroomsRepositoryCustom {

    /**
     * Retorna uma lista de {@link Classroom} conforme o filtro de pesquisa informado.
     *
     * @param filtroStudentsClassroomsDTO
     * @return
     */
    public List<Classroom> findAllByFiltro(FiltroStudentsClassroomsDTO filtroStudentsClassroomsDTO);



}
