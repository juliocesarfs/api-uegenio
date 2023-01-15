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

import br.ueg.madamestore.application.dto.FiltroSubjectDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.Subject;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 *
 * @author UEG
 */
public interface SubjectRepositoryCustom {

    /**
     * Retorna uma lista de {@link Subject} conforme o filtro de pesquisa informado.
     *
     * @param filtroSubjectDTO
     * @return
     */
    public List<Subject> findAllByFiltro(FiltroSubjectDTO filtroSubjectDTO);



}
