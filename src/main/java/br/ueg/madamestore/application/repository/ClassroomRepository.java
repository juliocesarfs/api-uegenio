package br.ueg.madamestore.application.repository;

import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.model.Usuario;
import br.ueg.madamestore.application.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Classe de persistência referente a entidade {@link Classroom}.
 *
 * @author UEG
 */
@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long>, ClassroomRepositoryCustom{

    /**
     * Retorna o número de {@link Classroom} pelo 'nome' , desconsiderando o
     * 'Produto' com o 'id' informado.
     *
     *
     * @param idClassroom
     * @return
     */


    /**
     * Listar todos as Classrooms
     * @return
     */
    @Query("SELECT classroom from Classroom classroom " +
            " INNER JOIN FETCH classroom.teachersClassrooms teacher"+
            " INNER JOIN FETCH classroom.semester semester" +
            " INNER JOIN FETCH classroom.subject subject"
    )
    public List<Classroom> getTodos();

    /**
     * Busca uma {@link Classroom} pelo id Informado
     *
     * @param idClassroom
     * @return
     */
    @Query("SELECT classroom from Classroom classroom " +
            " INNER JOIN FETCH classroom.teachersClassrooms teachersClassrooms " +
            " INNER JOIN FETCH classroom.semester semester"+
            " INNER JOIN FETCH classroom.subject subject"+
            " WHERE classroom.id = :idClassroom ")
    public Optional<Classroom> findByIdFetch( @Param("idClassroom") final Long idClassroom);


}
