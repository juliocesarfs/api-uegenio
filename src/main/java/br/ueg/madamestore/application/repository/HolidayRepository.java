package br.ueg.madamestore.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.ueg.madamestore.application.model.Holiday;
import br.ueg.madamestore.application.model.Usuario;

/**
 * Classe de persistência referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long>, HolidayRepositoryCustom {

    /**
     * Retorna o número de {@link Holiday} pelo 'nome' , desconsiderando o
     * 'Holiday' com o 'id' informado.
     *
     * @param nome
     * @param idHoliday
     * @return
     */
    @Query("SELECT COUNT(holiday) FROM Holiday holiday " +
            " WHERE lower(holiday.nome) LIKE lower(:nome)" +
            " AND (:idHoliday IS NULL OR holiday.id != :idHoliday)")
    public Long countByNomeAndNotId(String nome, Long idHoliday);

}
