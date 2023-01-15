package br.ueg.madamestore.application.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.dto.FiltroTeacherDTO;
import br.ueg.madamestore.application.model.Teacher;
import br.ueg.madamestore.application.repository.TeacherRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeacherRepositoryImpl implements TeacherRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Teacher> findAllByFiltro(FiltroTeacherDTO filtroTeacherDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT teacher FROM Teacher teacher");

        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroTeacherDTO.getNome())) {
            jpql.append(" AND UPPER(teacher.nome) LIKE UPPER('%' || :nome || '%')  ");
            parametros.put("nome", filtroTeacherDTO.getNome());
        }

        TypedQuery<Teacher> query = entityManager.createQuery(jpql.toString(), Teacher.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
