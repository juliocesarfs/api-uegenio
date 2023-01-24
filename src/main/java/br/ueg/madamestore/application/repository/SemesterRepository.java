package br.ueg.madamestore.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.model.Semester;
import br.ueg.madamestore.application.model.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long>, SemesterRepositoryCustom {

    /**
     * Retorna o número de {@link Semester} pelo 'nome' , desconsiderando o
     * 'TipoSemester' com o 'id' informado.
     *
     * @param nome
     * @param idSemester
     * @return
     */
    @Query("SELECT COUNT(semester) FROM Semester semester " +
            " WHERE lower(semester.nome) LIKE lower(:nome)" +
            " AND (:idSemester IS NULL OR semester.id != :idSemester)")
    public Long countByNomeAndNotId(String nome, Long idSemester);



    /**
     * Listar todos os Semesters
     * @return
     */
    @Query("SELECT semester from Semester semester")
    public List<Semester> getTodos();

    /**
     * Busca uma {@link Semester} pelo id Informado
     *
     * @param idSemester
     * @return
     */
    @Query("SELECT semester from Semester semester " +
            " WHERE semester.id = :idSemester ")
    public Optional<Semester> findByIdFetch( @Param("idSemester") final Long idSemester);


    // ESTATISTICA
    @Query("SELECT semester from Semester semester " +
            " ORDER BY semester.ano DESC"
    )
    public List<Semester> getAllSemestersDesc();
}
