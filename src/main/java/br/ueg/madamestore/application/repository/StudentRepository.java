package br.ueg.madamestore.application.repository;

import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.model.Usuario;
import br.ueg.madamestore.application.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Classe de persistência referente a entidade {@link Student}.
 *
 * @author UEG
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom{

    /**
     * Retorna o número de {@link Student} pelo 'nome' , desconsiderando o
     * 'Produto' com o 'id' informado.
     *
     *
     * @param idStudent
     * @return
     */


    /**
     * Listar todos as Students
     * @return
     */
    @Query("SELECT student from Student student " +
            " INNER JOIN FETCH student.studentsClassrooms classroom"
    )
    public List<Student> getTodos();

    /**
     * Busca uma {@link Student} pelo id Informado
     *
     * @param idStudent
     * @return
     */
    @Query("SELECT student from Student student " +
            " WHERE student.id = :idStudent ")
    public Optional<Student> findByIdFetch( @Param("idStudent") final Long idStudent);

    @Query("SELECT student from Student student " +
            " WHERE student.alexaID = :alexaID ")
    public Optional<Student> findByAlexaId( @Param("alexaID") String alexaID);


}
