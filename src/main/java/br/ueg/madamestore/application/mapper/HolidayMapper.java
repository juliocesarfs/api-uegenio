package br.ueg.madamestore.application.mapper;


import br.ueg.madamestore.application.dto.HolidayDTO;
import br.ueg.madamestore.application.model.Modulo;
import br.ueg.madamestore.application.model.Holiday;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Classe adapter referente a entidade {@link Modulo}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { SemesterMapper.class })
public interface HolidayMapper {
    /**
     * Converte a entidade {@link Holiday} em DTO {@link HolidayDTO}
     *
     * @param holiday
     * @return
     */

    @Mapping(source = "semester.id", target = "idSemester")
    public HolidayDTO toDTO(Holiday holiday);

    /**
     * Converte o DTO {@link HolidayDTO} para entidade {@link Holiday}
     *
     * @param holidayDTO
     * @return
     */

    @Mapping(source = "holidayDTO.idSemester", target = "semester.id")
    public Holiday toEntity(HolidayDTO holidayDTO);
}
