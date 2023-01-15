package br.ueg.madamestore.application.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.dto.FiltroHolidayDTO;
import br.ueg.madamestore.application.model.Holiday;
import br.ueg.madamestore.application.repository.HolidayRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HolidayRepositoryImpl implements HolidayRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Holiday> findAllByFiltro(FiltroHolidayDTO filtroHolidayDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT holiday FROM Holiday holiday");

        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroHolidayDTO.getNome())) {
            jpql.append(" AND UPPER(holiday.nome) LIKE UPPER('%' || :nome || '%')  ");
            parametros.put("nome", filtroHolidayDTO.getNome());
        }

        TypedQuery<Holiday> query = entityManager.createQuery(jpql.toString(), Holiday.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
