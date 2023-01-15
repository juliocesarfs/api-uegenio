package br.ueg.madamestore.application.repository.impl;

import br.ueg.madamestore.application.dto.FiltroClassroomDTO;
import br.ueg.madamestore.application.model.Classroom;
import br.ueg.madamestore.application.repository.ClassroomRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ClassroomRepositoryImpl implements ClassroomRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Classroom> findAllByFiltro(FiltroClassroomDTO filtroClassroomDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT classroom FROM Classroom classroom");
        jpql.append(" INNER JOIN FETCH classroom.teachersClassrooms teachersClassrooms");
        jpql.append(" INNER JOIN FETCH classroom.semester semester");
        jpql.append(" WHERE 1=1 ");




        if (!Util.isEmpty(filtroClassroomDTO.getSubject())) {
            jpql.append(" AND UPPER(classroom.subject.nome) LIKE UPPER('%' || :nome || '%')  ");
            parametros.put("nome", filtroClassroomDTO.getSubject());
        }

        /*
        if (filtroClassroomDTO.getTeacher() != null) {
            jpql.append(" AND classroom.valorTotal = :valorTotal ");
            parametros.put("valorTotal", filtroClassroomDTO.getValorTotal());
        }


        if (filtroClassroomDTO.getDataClassroom() != null) {
            jpql.append(" AND classroom.dataClassroom > :dataClassroom ");
            parametros.put("dataClassroom", filtroClassroomDTO.getDataClassroom());
        }
        */

        TypedQuery<Classroom> query = entityManager.createQuery(jpql.toString(), Classroom.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
