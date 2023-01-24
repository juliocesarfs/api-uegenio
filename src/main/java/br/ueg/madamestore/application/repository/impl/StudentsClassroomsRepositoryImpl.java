package br.ueg.madamestore.application.repository.impl;

import br.ueg.madamestore.application.dto.FiltroProdutoDTO;
import br.ueg.madamestore.application.dto.FiltroClassroomDTO;
import br.ueg.madamestore.application.dto.FiltroStudentsClassroomsDTO;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.model.Classroom;
import br.ueg.madamestore.application.repository.ClassroomRepositoryCustom;
import br.ueg.madamestore.application.repository.StudentsClassroomsRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentsClassroomsRepositoryImpl implements StudentsClassroomsRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Classroom> findAllByFiltro(FiltroStudentsClassroomsDTO filtroStudentsClassroomsDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT classroom FROM Classroom classroom");
        jpql.append(" INNER JOIN FETCH classroom.studentsClassrooms studentsClassrooms");
        jpql.append(" INNER JOIN FETCH classroom.hours hours");
        jpql.append(" INNER JOIN FETCH classroom.subject subject");
        jpql.append(" INNER JOIN FETCH classroom.semester semester");
        jpql.append(" WHERE 1=1 ");


        if (filtroStudentsClassroomsDTO.getIdStudent()!=null) {
            jpql.append(" AND studentsClassrooms.student.id = :idStudent ");
            parametros.put("idStudent", filtroStudentsClassroomsDTO.getIdStudent());
        }

        if (filtroStudentsClassroomsDTO.getWeekDay()!=null) {
            jpql.append(" AND UPPER(hours.weekDay) LIKE UPPER('%' || :weekDay || '%') ");
            parametros.put("weekDay", filtroStudentsClassroomsDTO.getWeekDay());
        }

        if (filtroStudentsClassroomsDTO.getDate()!=null) {
            jpql.append(" AND semester.initDate <= :date ");
            jpql.append(" AND semester.finalDate >= :date ");
            parametros.put("date", filtroStudentsClassroomsDTO.getDate());
        }


        TypedQuery<Classroom> query = entityManager.createQuery(jpql.toString(), Classroom.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }


}
