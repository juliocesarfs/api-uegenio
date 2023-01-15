package br.ueg.madamestore.application.controller;


import br.ueg.madamestore.application.dto.TeacherDTO;
import br.ueg.madamestore.application.mapper.TeacherMapper;
import br.ueg.madamestore.application.model.Teacher;
import br.ueg.madamestore.application.service.TeacherService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.ueg.madamestore.api.util.Validation;
import br.ueg.madamestore.application.dto.FiltroTeacherDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.comum.exception.MessageResponse;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Teacher API")
@RestController
@RequestMapping(path = "${app.api.base}/teacher")
public class TeacherController extends AbstractController {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherService teacherService;

    @PostMapping
    @ApiOperation(value = "Inclusão/alteração de professor.",
            notes = "Incluir/Alterar professor.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TeacherDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de professor", required = true) @Valid @RequestBody TeacherDTO teacherDTO) {
        Teacher grupo = teacherMapper.toEntity(teacherDTO);
        return ResponseEntity.ok(teacherMapper.toDTO(teacherService.salvar(grupo)));
    }

    /**
     * Altera a instância de {@link TeacherDTO} na base de dados.
     *
     * @param id
     * @param teacherDTO
     * @return
     */
    @ApiOperation(value = "Altera as informações de Tipo Amigo.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TeacherDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @ApiParam(value = "Código do Tipo Amigo", required = true) @PathVariable final BigDecimal id,
            @ApiParam(value = "Informações do Tipo Amigo", required = true) @Valid @RequestBody TeacherDTO teacherDTO) {
        Validation.max("id", id, 99999999L);
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        teacher.setId(id.longValue());
        Teacher teacherSaved = teacherService.salvar(teacher);
        return ResponseEntity.ok(teacherMapper.toDTO(teacherSaved));
    }

    /**
     * Retorna a instância de {@link TeacherDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @ApiOperation(value = "Retorna as informações do Teacher pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TeacherDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Teacher teacher = teacherService.getById(id.longValue());
        TeacherDTO teacherDTO = teacherMapper.toDTO(teacher);

        return ResponseEntity.ok(teacherDTO);
    }

    /**
     * Retorna a buscar de {@link Teacher} por {@link FiltroTeacherDTO}
     *
     * @param filtroTeacherDTO
     * @return
     */
    @ApiOperation(value = "Pesquisa de Teacher.",
            notes = "Recupera as informações de Teacher conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TeacherDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroTeacherDTO filtroTeacherDTO) {
        List<TeacherDTO> teachersDTO = new ArrayList<>();
        List<Teacher> teachers = teacherService.getTeachersByFiltro(filtroTeacherDTO);
        if(teachers.size() > 0){
            for (Teacher g:
                    teachers) {
                TeacherDTO teacherDTO = teacherMapper.toDTO(g);
                teachersDTO.add(teacherDTO);
            }
        }

        return ResponseEntity.ok(teachersDTO);
    }

    /**
     * Retorna uma lista de {@link TeacherDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Retorna uma lista de Teachers cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TeacherDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getTeachers() {
        List<Teacher> teachers = teacherService.getTodos();
        List<TeacherDTO> teachersDTO = new ArrayList<>();
        for (Teacher teacher : teachers) {
            TeacherDTO teacherDTO = teacherMapper.toDTO(teacher);
            teachersDTO.add(teacherDTO);
        }
        return ResponseEntity.ok(teachersDTO);
    }

    /**
     * Ativa o {@link Grupo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Remove um Teacher pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TeacherDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do Teacher", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Teacher teacher = teacherService.remover(id.longValue());
        return ResponseEntity.ok(teacher);
    }

}

