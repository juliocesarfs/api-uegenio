package br.ueg.madamestore.application.mapper;



import br.ueg.madamestore.application.dto.HourDTO;

import br.ueg.madamestore.application.model.Hour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link Hour}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { ClassroomMapper.class })
public interface HourMapper {

    /**
     * Converte a entidade {@link Hour} em DTO {@link HourDTO}
     *
     * @param hour
     * @return
     */

    @Mapping(source = "classroom.id", target = "idClassroom")
    public HourDTO toDTO(Hour hour);

    /**
     * Converte o DTO {@link HourDTO} para entidade {@link Hour}
     *
     * @param hourDTO
     * @return
     */
    @Mapping(source = "hourDTO.idClassroom", target = "classroom.id")
    public Hour toEntity(HourDTO hourDTO);


}
