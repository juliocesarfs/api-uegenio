/*
 * UsuarioController.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.controller;

import br.ueg.madamestore.api.util.Validation;
import br.ueg.madamestore.application.dto.*;
import br.ueg.madamestore.application.enums.StatusAtivoInativo;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.mapper.*;
import br.ueg.madamestore.application.model.*;
import br.ueg.madamestore.application.service.ClassroomService;
import br.ueg.madamestore.application.service.StudentsClassroomsService;
import br.ueg.madamestore.application.service.UsuarioService;
import br.ueg.madamestore.application.service.StudentService;
import br.ueg.madamestore.comum.exception.MessageResponse;
import io.swagger.annotations.*;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de controle referente a entidade {@link Student}.
 *
 * @author UEG
 */
@Api(tags = "Student API")
@RestController
@RequestMapping("${app.api.base}/student")
public class StudentController extends AbstractController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentsClassroomsMapper studentsClassroomsMapper;

    @Autowired
    private StudentsClassroomsService studentsClassroomsService;

    /**
     * Salva uma instância de {@link Student} na base de dados.
     *
     * @param studentDTO
     * @return
     */
    @ApiOperation(value = "Inclui um novo Usuário na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = StudentDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações da Student", required = true) @Valid @RequestBody StudentDTO studentDTO) {
        Student student = studentMapper.toEntity(studentDTO);

        //studentService.configurarStudentTeacher(student);

        student = studentService.salvar(student);
        studentDTO = studentMapper.toDTO(student);
        return ResponseEntity.ok(studentDTO);
    }

    @ApiOperation(value = "Altera as informações de classroom na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClassroomDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/addClass", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> incluirClassroom(@ApiParam(value = "Informações de classroom", required = true) @Valid @RequestBody StudentDTO studentDTO) {


        System.out.println("=========================================asdasdasdasdasdasdasdasd");
        System.out.println(studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        //student.setId(studentService.getByAlexaID(studentDTO.getAlexaID()).getId().longValue());

        for (StudentsClassroomsDTO stc: studentDTO.getStudentsClassrooms()) {
            Classroom classroom = classroomService.getById(Long.parseLong(stc.getIdClassroom()));

            studentService.configurarStudentClassroom(student, classroom);
        }

        //classroom.setId(id.longValue());
        //classroomService.retiraClassroomAlterarQuantidade(classroom);
        //classroomService.diminuirQuantidadeVendida(classroom);
        student = studentService.salvar(student);
        studentDTO = studentMapper.toDTO(student);

        return ResponseEntity.ok(studentDTO);
    }



    @ApiOperation(value = "Recupera o classroom pela id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClassroomDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(path = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getStudentByAlexaId(@ApiParam(value = "Id do Classroom")@RequestBody StudentDTO studentDTO) {
        System.out.println(studentDTO.getAlexaID());

        Student student = studentService.getByAlexaID(studentDTO.getAlexaID());

        if(student != null)
            studentDTO = studentMapper.toDTO(student);
        else
            studentDTO = null;

        return ResponseEntity.ok(studentDTO);
    }


}