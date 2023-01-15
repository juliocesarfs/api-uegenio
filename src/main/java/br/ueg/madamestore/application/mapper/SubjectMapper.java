package br.ueg.madamestore.application.mapper;


import br.ueg.madamestore.application.dto.SubjectDTO;
import br.ueg.madamestore.application.model.Modulo;
import br.ueg.madamestore.application.model.Subject;
import org.mapstruct.Mapper;

/**
 * Classe adapter referente a entidade {@link Modulo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring")
public interface SubjectMapper {
    /**
     * Converte a entidade {@link Subject} em DTO {@link SubjectDTO}
     *
     * @param subject
     * @return
     */

    public SubjectDTO toDTO(Subject subject);

    /**
     * Converte o DTO {@link SubjectDTO} para entidade {@link Subject}
     *
     * @param subjectDTO
     * @return
     */
    public Subject toEntity(SubjectDTO subjectDTO);
}
