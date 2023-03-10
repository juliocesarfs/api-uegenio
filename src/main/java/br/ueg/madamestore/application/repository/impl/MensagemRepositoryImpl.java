package br.ueg.madamestore.application.repository.impl;

import br.ueg.madamestore.application.dto.FiltroMensagemDTO;
import br.ueg.madamestore.application.model.Mensagem;
import br.ueg.madamestore.application.repository.MensagemRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MensagemRepositoryImpl implements MensagemRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Mensagem> findAllByFiltro(FiltroMensagemDTO filtroMensagemDTO) {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder jpql = new StringBuilder();
        jpql.append(" SELECT DISTINCT mensagem FROM Mensagem mensagem");
        jpql.append(" JOIN FETCH mensagem.produto");
        jpql.append(" WHERE 1=1 ");


        if (filtroMensagemDTO.getDataAlteracao() != null) {
            jpql.append(" AND mensagem.dataAlteracao > :dataAlteracao ");
            parametros.put("dataAlteracao", filtroMensagemDTO.getDataAlteracao());
        }








        TypedQuery<Mensagem> query = entityManager.createQuery(jpql.toString(), Mensagem.class);
        parametros.entrySet().forEach(parametro -> query.setParameter(parametro.getKey(), parametro.getValue()));
        return query.getResultList();
    }
}
