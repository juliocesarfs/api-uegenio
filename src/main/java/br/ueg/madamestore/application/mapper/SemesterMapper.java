package br.ueg.madamestore.application.mapper;


import br.ueg.madamestore.application.dto.SemesterDTO;
import br.ueg.madamestore.application.model.Modulo;
import br.ueg.madamestore.application.model.Semester;
import org.mapstruct.Mapper;

/**
 * Classe adapter referente a entidade {@link Modulo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring")
public interface SemesterMapper {
    /**
     * Converte a entidade {@link Semester} em DTO {@link SemesterDTO}
     *
     * @param semester
     * @return
     */

    public SemesterDTO toDTO(Semester semester);

    /**
     * Converte o DTO {@link SemesterDTO} para entidade {@link Semester}
     *
     * @param semesterDTO
     * @return
     */
    public Semester toEntity(SemesterDTO semesterDTO);
}
