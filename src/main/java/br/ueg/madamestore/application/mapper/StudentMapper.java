package br.ueg.madamestore.application.mapper;


import br.ueg.madamestore.application.dto.StudentsClassroomsDTO;
import br.ueg.madamestore.application.dto.TeacherDTO;
import br.ueg.madamestore.application.dto.UsuarioDTO;
import br.ueg.madamestore.application.dto.StudentDTO;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.model.Teacher;
import br.ueg.madamestore.application.model.Usuario;
import br.ueg.madamestore.application.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Classe adapter referente a entidade {@link Student}.
 *
 * @author UEG
 */
@Mapper(componentModel = "spring", uses = { ClassroomMapper.class, StudentsClassroomsMapper.class })
public interface StudentMapper {

    /**
     * Converte a entidade {@link Student} em DTO {@link StudentDTO}
     *
     * @param student
     * @return
     */


    public StudentDTO toDTO(Student student);

    /**
     * Converte o DTO {@link StudentDTO} para entidade {@link Student}
     *
     * @param studentDTO
     * @return
     */

    public Student toEntity(StudentDTO studentDTO);


}
