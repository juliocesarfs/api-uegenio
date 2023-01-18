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
import br.ueg.madamestore.application.mapper.*;
import br.ueg.madamestore.application.model.*;
import br.ueg.madamestore.application.service.ClassroomService;
import br.ueg.madamestore.application.service.StudentService;
import br.ueg.madamestore.application.service.StudentsClassroomsService;
import br.ueg.madamestore.application.service.StudentsClassroomsService;
import br.ueg.madamestore.comum.exception.MessageResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * Classe de controle referente a entidade {@link StudentsClassrooms}.
 *
 * @author UEG
 */
@Api(tags = "StudentsClassrooms API")
@RestController
@RequestMapping("${app.api.base}/studentsClassrooms")
public class StudentsClassroomsController extends AbstractController {

    @Autowired
    private StudentsClassroomsService studentsClassroomsService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private StudentService studentService;


    @Autowired
    private StudentsClassroomsMapper studentsClassroomsMapper;



    /**
     * Salva uma instância de {@link StudentsClassrooms} na base de dados.
     *
     * @param studentsClassroomsDTO
     * @return
     */
    @ApiOperation(value = "Inclui um novo Usuário na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = StudentsClassroomsDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações da StudentsClassrooms", required = true) @Valid @RequestBody StudentsClassroomsDTO studentsClassroomsDTO) {

        StudentsClassrooms studentsClassrooms = studentsClassroomsMapper.toEntity(studentsClassroomsDTO);

        Classroom classroom = classroomService.getBySubjectName(studentsClassroomsDTO.getNomeSubject());
        Student student = studentService.getByAlexaID(studentsClassroomsDTO.getAlexaID());

        studentsClassrooms.setClassroom(classroom);
        studentsClassrooms.setStudent(student);

        System.out.println("=================================================asdasdasdasddasd");
        System.out.println(studentsClassroomsDTO);
        System.out.println("=================================================asdasdasdasddasd");
        System.out.println(studentsClassrooms);
        //studentsClassroomsService.configurarStudentsClassroomsTeacher(studentsClassrooms);

        studentsClassrooms = studentsClassroomsService.salvar(studentsClassrooms);
        studentsClassroomsDTO = studentsClassroomsMapper.toDTO(studentsClassrooms);
        return ResponseEntity.ok(studentsClassroomsDTO);
    }

    @ApiOperation(value = "Remove uma classroom pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TeacherDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id da classroom", required = true) @Valid @RequestBody StudentsClassroomsDTO studentsClassroomsDTO) {

        StudentsClassrooms studentsClassrooms = studentsClassroomsMapper.toEntity(studentsClassroomsDTO);

        studentsClassrooms = studentsClassroomsService.getByIdClassmAndIdStudent(Long.parseLong(studentsClassroomsDTO.getIdClassroom()), Long.parseLong(studentsClassroomsDTO.getIdStudent()));

        studentsClassroomsService.remover(studentsClassrooms);

        return ResponseEntity.ok(studentsClassroomsDTO);
    }
}