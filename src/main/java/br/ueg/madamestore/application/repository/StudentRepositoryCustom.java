/*
 * GrupoRepositoryCustom.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.repository;


import br.ueg.madamestore.application.dto.FiltroStudentDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.Student;

import java.util.List;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 *
 * @author UEG
 */
public interface StudentRepositoryCustom {

    /**
     * Retorna uma lista de {@link Student} conforme o filtro de pesquisa informado.
     *
     * @param filtroStudentDTO
     * @return
     */
    public List<Student> findAllByFiltro(FiltroStudentDTO filtroStudentDTO);

}
