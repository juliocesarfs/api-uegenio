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

import br.ueg.madamestore.application.dto.FiltroHolidayDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.application.model.Holiday;

/**
 * Classe de persistência referente a entidade {@link Grupo}.
 *
 * @author UEG
 */
public interface HolidayRepositoryCustom {

    /**
     * Retorna uma lista de {@link Holiday} conforme o filtro de pesquisa informado.
     *
     * @param filtroHolidayDTO
     * @return
     */
    public List<Holiday> findAllByFiltro(FiltroHolidayDTO filtroHolidayDTO);


}
