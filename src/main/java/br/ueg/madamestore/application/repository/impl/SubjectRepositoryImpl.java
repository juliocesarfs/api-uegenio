package br.ueg.madamestore.application.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.dto.FiltroSubjectDTO;
import br.ueg.madamestore.application.model.Subject;
import br.ueg.madamestore.application.repository.SubjectRepositoryCustom;
import br.ueg.madamestore.comum.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SubjectRepositoryImpl implements SubjectRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Subject> findAllByFiltro(FiltroSubjectDTO filtroSubjectDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT subject FROM Subject subject");

        jpql.append(" WHERE 1=1 ");

        if (!Util.isEmpty(filtroSubjectDTO.getNome())) {
            jpql.append(" AND UPPER(subject.nome) LIKE UPPER('%' || :nome || '%')  ");
            parametros.put("nome", filtroSubjectDTO.getNome());
        }

        TypedQuery<Subject> query = entityManager.createQuery(jpql.toString(), Subject.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
