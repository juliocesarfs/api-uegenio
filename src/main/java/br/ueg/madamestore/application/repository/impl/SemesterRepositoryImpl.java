package br.ueg.madamestore.application.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.dto.FiltroSemesterDTO;
import br.ueg.madamestore.application.model.Semester;
import br.ueg.madamestore.application.repository.SemesterRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SemesterRepositoryImpl implements SemesterRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Semester> findAllByFiltro(FiltroSemesterDTO filtroSemesterDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT semester FROM Semester semester");

        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroSemesterDTO.getNome())) {
            jpql.append(" AND UPPER(semester.nome) LIKE UPPER('%' || :nome || '%')  ");
            parametros.put("nome", filtroSemesterDTO.getNome());
        }

        if (filtroSemesterDTO.getDate()!=null) {
            jpql.append(" AND semester.initDate <= :date ");
            jpql.append(" AND semester.finalDate >= :date ");
            parametros.put("date", filtroSemesterDTO.getDate());
        }

        TypedQuery<Semester> query = entityManager.createQuery(jpql.toString(), Semester.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
