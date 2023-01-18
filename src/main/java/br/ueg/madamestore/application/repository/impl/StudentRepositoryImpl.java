package br.ueg.madamestore.application.repository.impl;

import br.ueg.madamestore.application.dto.FiltroProdutoDTO;
import br.ueg.madamestore.application.dto.FiltroStudentDTO;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.model.Student;
import br.ueg.madamestore.application.repository.StudentRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentRepositoryImpl implements StudentRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Student> findAllByFiltro(FiltroStudentDTO filtroStudentDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT student FROM Student student");
        jpql.append(" INNER JOIN FETCH student.studentsClassrooms studentsClassrooms");
        jpql.append(" WHERE 1=1 ");


        if (filtroStudentDTO.getIdStudent()!=null) {
            jpql.append(" AND student.id = :idStudent ");
            parametros.put("idStudent", filtroStudentDTO.getIdStudent());
        }

        if (filtroStudentDTO.getIdStudent()!=null) {
            jpql.append(" AND student.alexaID = :alexaID ");
            parametros.put("alexaID", filtroStudentDTO.getIdStudent());
        }



        TypedQuery<Student> query = entityManager.createQuery(jpql.toString(), Student.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
