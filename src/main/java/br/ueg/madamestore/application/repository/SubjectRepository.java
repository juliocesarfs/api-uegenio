package br.ueg.madamestore.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.model.Subject;
import br.ueg.madamestore.application.model.Usuario;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>, SubjectRepositoryCustom{

    /**
     * Retorna o número de {@link Subject} pelo 'nome' , desconsiderando o
     * 'Subject' com o 'id' informado.
     *
     * @param nome
     * @param idSubject
     * @return
     */
    @Query("SELECT COUNT(subject) FROM Subject subject " +
            " WHERE lower(subject.nome) LIKE lower(:nome)" +
            " AND (:idSubject IS NULL OR subject.id != :idSubject)")
    public Long countByNomeAndNotId(String nome, Long idSubject);

}
