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
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.mapper.EsperaMapper;
import br.ueg.madamestore.application.mapper.ClassroomMapper;
import br.ueg.madamestore.application.mapper.TeacherClassroomMapper;
import br.ueg.madamestore.application.mapper.VendidoMapper;
import br.ueg.madamestore.application.model.*;
import br.ueg.madamestore.application.repository.TeacherRepository;
import br.ueg.madamestore.application.service.*;
import br.ueg.madamestore.comum.exception.MessageResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * Classe de controle referente a entidade {@link Classroom}.
 *
 * @author UEG
 */
@Api(tags = "Classroom API")
@RestController
@RequestMapping("${app.api.base}/classroom")
public class ClassroomController extends AbstractController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private ClassroomMapper classroomMapper;

    @Autowired
    private TeacherClassroomMapper teacherClassroomMapper;

    @Autowired
    private EsperaMapper esperaMapper;

    @Autowired
    private VendidoMapper vendidoMapper;

    @Autowired
    private TeacherClassroomService teacherClassroomService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private SubjectService subjectService;




    /**
     * Salva uma instância de {@link Classroom} na base de dados.
     *
     * @param classroomDTO
     * @return
     */
    @ApiOperation(value = "Inclui um novo Usuário na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClassroomDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações da Classroom", required = true) @Valid @RequestBody ClassroomDTO classroomDTO) {



        System.out.println("SEEEEXOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO ======================");
        System.out.println(classroomDTO);




        Set<TeacherClassroom> teacherClassrooms = new HashSet<>();
        for (Object idT: classroomDTO.getIdTeacher()) {

            Number idtLong = (Number) idT;
            System.out.println(idtLong);
            Teacher teacher = teacherService.getById(idtLong.longValue());

            TeacherClassroom teacherClassroom = new TeacherClassroom();

            teacherClassroom.setTeacher(teacher);

            teacherClassrooms.add(teacherClassroomService.salvar(teacherClassroom));

            System.out.println(teacherClassrooms);
        }

        Subject subject = subjectService.getById(classroomDTO.getIdSubject());
        Semester semester = semesterService.getById(classroomDTO.getIdSemester());

        Classroom classroom = new Classroom();

        classroom.setLocal(classroomDTO.getLocal());
        classroom.setSemester(semester);
        classroom.setSubject(subject);
        classroom.setTeachersClassrooms(teacherClassrooms);
        System.out.println(classroom);

        classroomService.salvar(classroom);

        for(TeacherClassroom tc: teacherClassrooms) {
            tc.setClassroom(classroom);

            teacherClassroomService.salvar(tc);
        }




        return ResponseEntity.ok(classroomDTO);

        //classroomService.configurarClassroomProduto(classroom);

        //classroom = classroomService.salvar(classroom);
        //classroomDTO = classroomMapper.toDTO(classroom);
        //return ResponseEntity.ok(classroomDTO);
    }

    /**
     * Altera a instância de {@link Classroom} na base de dados.
     *
     * @param id
     * @param classroomDTO
     * @return
     */
    @ApiOperation(value = "Altera as informações de classroom na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClassroomDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(@ApiParam(value = "Código do Usuário", required = true) @PathVariable final BigDecimal id, @ApiParam(value = "Informações de classroom", required = true) @Valid @RequestBody ClassroomDTO classroomDTO) {
        Validation.max("id", id, 99999999L);
        Classroom classroom = classroomMapper.toEntity(classroomDTO);
        classroom.setId(id.longValue());
        //classroomService.retiraClassroomAlterarQuantidade(classroom);
        //classroomService.diminuirQuantidadeVendida(classroom);
        classroomService.salvar(classroom);
        return ResponseEntity.ok(classroomDTO);
    }

    /**
     * Retorna a instância de {@link ClassroomDTO} conforme o 'id'
     * informado.
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Recupera o classroom pela id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClassroomDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getClassroomById(@ApiParam(value = "Id do Classroom") @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Classroom classroom = classroomService.getByIdFetch(id.longValue());
        ClassroomDTO classroomTO = new ClassroomDTO();

        if(classroom != null)
            classroomTO = classroomMapper.toDTO(classroom);

        return ResponseEntity.ok(classroomTO);
    }

    /**
     * Altera a instância de {@link Classroom} na base de dados.
     *
     * @param id
     * @param classroomDTO
     * @return
     */
    @ApiOperation(value = "Altera as informações de classroom na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClassroomDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/alterar/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> alterarProduto(@ApiParam(value = "Código do Usuário", required = true) @PathVariable final BigDecimal id, @ApiParam(value = "Informações de classroom", required = true) @Valid @RequestBody ClassroomDTO classroomDTO) {
        Validation.max("id", id, 99999999L);
        Classroom classroom = classroomMapper.toEntity(classroomDTO);
        classroom.setId(id.longValue());
        return ResponseEntity.ok(classroomDTO);
    }


    /**
     * Retorna a lista de {@link ClassroomDTO} de acordo com os filtros
     * informados.
     *
     * @param filtroDTO
     * @return
     */
    @ApiOperation(value = "Recupera as classrooms pelo Filtro Informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClassroomDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(path = "/filtro-ativo", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getClassroomAtivosByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @Valid @ModelAttribute("filtroDTO") FiltroClassroomDTO filtroDTO) {

        List<ClassroomDTO> classroomsDTO = new ArrayList<>();
        List<Classroom> classrooms = classroomService.getClassroomsByFiltro(filtroDTO);
        if(classrooms != null){
            for (Classroom classroom: classrooms) {
                classroomsDTO.add(classroomMapper.toDTO(classroom));
            }
        }

        return ResponseEntity.ok(classroomsDTO);
    }

    /**
     * Retorna a lista de {@link ClassroomDTO} de acordo com os filtros
     * informados.
     *
     * @param filtroDTO
     * @return
     */
    @ApiOperation(value = "Recupera os classrooms pelo Filtro Informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClassroomDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getClassroomByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @Valid @ModelAttribute("filtroDTO") final FiltroClassroomDTO filtroDTO) {
        List<Classroom> classrooms = classroomService.getClassroomsByFiltro(filtroDTO);
        List<ClassroomDTO> classroomsDTO = new ArrayList<>();
        if(classrooms.size() > 0){
            for (Classroom g:

                    classrooms) {
                ClassroomDTO classroomDTO = classroomMapper.toDTO(g);
                classroomsDTO.add(classroomDTO);
            }
        }

        return ResponseEntity.ok(classroomsDTO);
    }


    @ApiOperation(value = "Remove uma classroom pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ProdutoDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id da classroom", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);

        Classroom classroom = classroomService.remover(id.longValue());

        return ResponseEntity.ok(classroomMapper.toDTO(classroom));
    }

    @ApiOperation(value = "Inclui um novo Usuário na base de dados.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClassroomDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @PostMapping(path = "/incluir", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> incluirClassroom(@ApiParam(value = "Informações da Classroom", required = true) @Valid @RequestBody ClassroomDTO classroomDTO) {

        Classroom classroom=classroomMapper.toEntity(classroomDTO);

        //classroomService.configurarClassroomProduto(classroom);

        classroom = classroomService.salvar(classroom);
        classroomDTO = classroomMapper.toDTO(classroom);
        return ResponseEntity.ok(classroomDTO);
    }
}
