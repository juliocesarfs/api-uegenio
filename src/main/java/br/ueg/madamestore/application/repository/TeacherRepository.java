package br.ueg.madamestore.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.model.Teacher;
import br.ueg.madamestore.application.model.Usuario;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>, TeacherRepositoryCustom{

    /**
     * Retorna o número de {@link Teacher} pelo 'nome' , desconsiderando o
     * 'Teacher' com o 'id' informado.
     *
     * @param nome
     * @param idTeacher
     * @return
     */
    @Query("SELECT COUNT(teacher) FROM Teacher teacher " +
            " WHERE lower(teacher.nome) LIKE lower(:nome)" +
            " AND (:idTeacher IS NULL OR teacher.id != :idTeacher)")
    public Long countByNomeAndNotId(String nome, Long idTeacher);

}
