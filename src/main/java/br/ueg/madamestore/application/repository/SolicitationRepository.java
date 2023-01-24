package br.ueg.madamestore.application.repository;

import br.ueg.madamestore.application.model.Produto;
import br.ueg.madamestore.application.model.Student;
import br.ueg.madamestore.application.model.Usuario;
import br.ueg.madamestore.application.model.Solicitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Classe de persistência referente a entidade {@link Solicitation}.
 *
 * @author UEG
 */
@Repository
public interface SolicitationRepository extends JpaRepository<Solicitation, Long>{

    /**
     * Retorna o número de {@link Solicitation} pelo 'nome' , desconsiderando o
     * 'Produto' com o 'id' informado.
     *
     *
     * @param idSolicitation
     * @return
     */


    /**
     * Listar todos as Solicitations
     * @return
     */
    @Query("SELECT solicitation from Solicitation solicitation " +
            " INNER JOIN FETCH solicitation.parameters parameters"
    )
    public List<Solicitation> getTodos();

    /**
     * Busca uma {@link Solicitation} pelo id Informado
     *
     * @param idSolicitation
     * @return
     */
    @Query("SELECT solicitation from Solicitation solicitation " +
            " INNER JOIN FETCH solicitation.parameters parameters " +
            " WHERE solicitation.id = :idSolicitation ")
    public Optional<Solicitation> findByIdFetch( @Param("idSolicitation") final Long idSolicitation);


    @Query("SELECT solicitation from Solicitation solicitation " +
            " INNER JOIN FETCH solicitation.parameters parameters " +
            " WHERE solicitation.type = :typeSolicitation ")
    public Optional<Solicitation> findByType( @Param("typeSolicitation") final String typeSolicitation);
}
