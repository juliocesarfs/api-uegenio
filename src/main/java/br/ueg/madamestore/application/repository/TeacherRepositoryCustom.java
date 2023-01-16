/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.repository;


import java.util.List;

import br.ueg.madamestore.application.dto.FiltroAmigoDTO;
import br.ueg.madamestore.application.dto.FiltroTeacherDTO;
import br.ueg.madamestore.application.model.Amigo;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.Teacher;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 *
 * @author UEG
 */
public interface TeacherRepositoryCustom {

    /**
     * Retorna uma lista de {@link br.ueg.madamestore.application.model.Teacher} conforme o filtro de pesquisa informado.
     *
     * @param filtroTeacherDTO
     * @return
     */
    public List<Teacher> findAllByFiltro(FiltroTeacherDTO filtroTeacherDTO);



}
