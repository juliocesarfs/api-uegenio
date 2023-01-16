package br.ueg.madamestore.application.controller;


import br.ueg.madamestore.application.dto.SemesterDTO;
import br.ueg.madamestore.application.dto.SubjectDTO;
import br.ueg.madamestore.application.dto.TeacherDTO;
import br.ueg.madamestore.application.mapper.SubjectMapper;
import br.ueg.madamestore.application.model.Semester;
import br.ueg.madamestore.application.model.Subject;
import br.ueg.madamestore.application.service.SubjectService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.ueg.madamestore.api.util.Validation;
import br.ueg.madamestore.application.dto.FiltroSubjectDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.comum.exception.MessageResponse;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Subject API")
@RestController
@RequestMapping(path = "${app.api.base}/subject")
public class SubjectController extends AbstractController {

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    @ApiOperation(value = "Inclusão/alteração de disciplina.",
            notes = "Incluir/Alterar disciplina.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SubjectDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de disciplina", required = true) @Valid @RequestBody SubjectDTO subjectDTO) {
        Subject grupo = subjectMapper.toEntity(subjectDTO);
        return ResponseEntity.ok(subjectMapper.toDTO(subjectService.salvar(grupo)));
    }

    /**
     * Altera a instância de {@link SubjectDTO} na base de dados.
     *
     * @param id
     * @param subjectDTO
     * @return
     */
    @ApiOperation(value = "Altera as informações de Tipo Amigo.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SubjectDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @ApiParam(value = "Código do Tipo Amigo", required = true) @PathVariable final BigDecimal id,
            @ApiParam(value = "Informações do Tipo Amigo", required = true) @Valid @RequestBody SubjectDTO subjectDTO) {
        Validation.max("id", id, 99999999L);
        Subject subject = subjectMapper.toEntity(subjectDTO);
        subject.setId(id.longValue());
        Subject subjectSaved = subjectService.salvar(subject);
        return ResponseEntity.ok(subjectMapper.toDTO(subjectSaved));
    }

    @ApiOperation(value = "Retorna uma lista de semesters cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = TeacherDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(path = "/ativos", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getSubjectsAtivos() {
        List<Subject> subjects = subjectService.getTodos();
        List<SubjectDTO> subjectsDTO = new ArrayList<>();
        for (Subject subject : subjects) {
            SubjectDTO subjectDTO = subjectMapper.toDTO(subject);
            subjectsDTO.add(subjectDTO);
        }
        return ResponseEntity.ok(subjectsDTO);
    }

    /**
     * Retorna a instância de {@link SubjectDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @ApiOperation(value = "Retorna as informações do Subject pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SubjectDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Subject subject = subjectService.getById(id.longValue());
        SubjectDTO subjectDTO = subjectMapper.toDTO(subject);

        return ResponseEntity.ok(subjectDTO);
    }

    /**
     * Retorna a buscar de {@link Subject} por {@link FiltroSubjectDTO}
     *
     * @param filtroSubjectDTO
     * @return
     */
    @ApiOperation(value = "Pesquisa de Subject.",
            notes = "Recupera as informações de Subject conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SubjectDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroSubjectDTO filtroSubjectDTO) {
        List<SubjectDTO> subjectsDTO = new ArrayList<>();
        List<Subject> subjects = subjectService.getSubjectsByFiltro(filtroSubjectDTO);
        if(subjects.size() > 0){
            for (Subject g:
                    subjects) {
                SubjectDTO subjectDTO = subjectMapper.toDTO(g);
                subjectsDTO.add(subjectDTO);
            }
        }

        return ResponseEntity.ok(subjectsDTO);
    }

    /**
     * Retorna uma lista de {@link SubjectDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Retorna uma lista de Subjects cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SubjectDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getSubjects() {
        List<Subject> subjects = subjectService.getTodos();
        List<SubjectDTO> subjectsDTO = new ArrayList<>();
        for (Subject subject : subjects) {
            SubjectDTO subjectDTO = subjectMapper.toDTO(subject);
            subjectsDTO.add(subjectDTO);
        }
        return ResponseEntity.ok(subjectsDTO);
    }

    /**
     * Ativa o {@link Grupo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Remove um Subject pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SubjectDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do Subject", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Subject subject = subjectService.remover(id.longValue());
        return ResponseEntity.ok(subject);
    }

}

