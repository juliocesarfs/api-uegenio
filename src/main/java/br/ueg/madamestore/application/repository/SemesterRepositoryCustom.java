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

import br.ueg.madamestore.application.dto.FiltroSemesterDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.Semester;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 *
 * @author UEG
 */
public interface SemesterRepositoryCustom {

    /**
     * Retorna uma lista de {@link Semester} conforme o filtro de pesquisa informado.
     *
     * @param filtroSemesterDTO
     * @return
     */
    public List<Semester> findAllByFiltro(FiltroSemesterDTO filtroSemesterDTO);



}
