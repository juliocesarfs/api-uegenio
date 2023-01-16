package br.ueg.madamestore.application.repository.impl;

import br.ueg.madamestore.application.dto.FiltroProdutoDTO;
import br.ueg.madamestore.application.dto.FiltroClassroomDTO;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.model.Produto;
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
        jpql.append(" INNER JOIN FETCH classroom.subject subject");
        jpql.append(" WHERE 1=1 ");


        if (filtroClassroomDTO.getIdClassroom()!=null) {
            jpql.append(" AND classroom.teacher.id = :idTeacher ");
            parametros.put("idTeacher", filtroClassroomDTO.getIdClassroom());
        }

        if (filtroClassroomDTO.getSubject()!=null) {
            jpql.append(" AND classroom.subject LIKE '%' || :subject || '%' ");
            parametros.put("subject", filtroClassroomDTO.getSubject());
        }

        if (filtroClassroomDTO.getLocal()!=null) {
            jpql.append(" AND classroom.local LIKE '%' || :local || '%' ");
            parametros.put("local", filtroClassroomDTO.getLocal());
        }





        TypedQuery<Classroom> query = entityManager.createQuery(jpql.toString(), Classroom.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
