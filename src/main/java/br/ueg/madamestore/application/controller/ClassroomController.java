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
import br.ueg.madamestore.application.mapper.EsperaMapper;
import br.ueg.madamestore.application.mapper.UsuarioMapper;
import br.ueg.madamestore.application.mapper.ClassroomMapper;
import br.ueg.madamestore.application.mapper.VendidoMapper;
import br.ueg.madamestore.application.model.Amigo;
import br.ueg.madamestore.application.model.Teacher;
import br.ueg.madamestore.application.model.Usuario;
import br.ueg.madamestore.application.model.Classroom;
import br.ueg.madamestore.application.service.UsuarioService;
import br.ueg.madamestore.application.service.ClassroomService;
import br.ueg.madamestore.comum.exception.MessageResponse;
import io.swagger.annotations.*;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        Classroom classroom=classroomMapper.toEntity(classroomDTO);


        System.out.println("=================================================asdasdasdasddasd");
        System.out.println(classroomDTO);
        System.out.println("=================================================asdasdasdasddasd");
        System.out.println(classroom);
        //classroomService.configurarClassroomTeacher(classroom);

        classroom = classroomService.salvar(classroom);
        classroomDTO = classroomMapper.toDTO(classroom);
        return ResponseEntity.ok(classroomDTO);
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
        classroomService.configurarClassroomTeacher(classroom);
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
    public  ResponseEntity<?> alterarTeacher(@ApiParam(value = "Código do Usuário", required = true) @PathVariable final BigDecimal id, @ApiParam(value = "Informações de classroom", required = true) @Valid @RequestBody ClassroomDTO classroomDTO) {
        Validation.max("id", id, 99999999L);
        Classroom classroom = classroomMapper.toEntity(classroomDTO);
        classroomService.configurarClassroomTeacher(classroom);
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

        System.out.println("=============================================");
        System.out.println(filtroDTO);
        List<ClassroomDTO> classroomsDTO = new ArrayList<>();
        List<Classroom> classrooms = classroomService.getClassroomByFiltro(filtroDTO);
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
        System.out.println("=============================================asdasdasdasdasdasdasdasdasdasdasd");
        System.out.println(filtroDTO);

        List<Classroom> classrooms = classroomService.getClassroomByFiltro(filtroDTO);


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

    @ApiOperation(value = "Recupera os classrooms pelo Filtro Informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClassroomDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(path = "/all", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAll() {
        List<Classroom> classrooms = classroomService.getClassrooms();
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
            @ApiResponse(code = 200, message = "Success", response = TeacherDTO.class),
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


        //classroomService.configurarClassroomTeacher(classroom);

        classroom = classroomService.salvar(classroom);
        classroomDTO = classroomMapper.toDTO(classroom);
        return ResponseEntity.ok(classroomDTO);
    }

}
