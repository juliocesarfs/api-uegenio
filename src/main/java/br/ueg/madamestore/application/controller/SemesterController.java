package br.ueg.madamestore.application.controller;


import br.ueg.madamestore.application.dto.SemesterDTO;
import br.ueg.madamestore.application.mapper.SemesterMapper;
import br.ueg.madamestore.application.model.Semester;
import br.ueg.madamestore.application.service.SemesterService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.ueg.madamestore.api.util.Validation;
import br.ueg.madamestore.application.dto.FiltroSemesterDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.comum.exception.MessageResponse;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Semester API")
@RestController
@RequestMapping(path = "${app.api.base}/semester")
public class SemesterController extends AbstractController {

    @Autowired
    private SemesterMapper semesterMapper;

    @Autowired
    private SemesterService semesterService;

    @PostMapping
    @ApiOperation(value = "Inclusão/alteração de disciplina.",
            notes = "Incluir/Alterar disciplina.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SemesterDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de disciplina", required = true) @Valid @RequestBody SemesterDTO semesterDTO) {
        Semester grupo = semesterMapper.toEntity(semesterDTO);
        return ResponseEntity.ok(semesterMapper.toDTO(semesterService.salvar(grupo)));
    }

    /**
     * Altera a instância de {@link SemesterDTO} na base de dados.
     *
     * @param id
     * @param semesterDTO
     * @return
     */
    @ApiOperation(value = "Altera as informações de Tipo Amigo.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SemesterDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @ApiParam(value = "Código do Tipo Amigo", required = true) @PathVariable final BigDecimal id,
            @ApiParam(value = "Informações do Tipo Amigo", required = true) @Valid @RequestBody SemesterDTO semesterDTO) {
        Validation.max("id", id, 99999999L);
        Semester semester = semesterMapper.toEntity(semesterDTO);
        semester.setId(id.longValue());
        Semester semesterSaved = semesterService.salvar(semester);
        return ResponseEntity.ok(semesterMapper.toDTO(semesterSaved));
    }

    /**
     * Retorna a instância de {@link SemesterDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @ApiOperation(value = "Retorna as informações do Semester pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SemesterDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Semester semester = semesterService.getById(id.longValue());
        SemesterDTO semesterDTO = semesterMapper.toDTO(semester);

        return ResponseEntity.ok(semesterDTO);
    }

    /**
     * Retorna a buscar de {@link Semester} por {@link FiltroSemesterDTO}
     *
     * @param filtroSemesterDTO
     * @return
     */
    @ApiOperation(value = "Pesquisa de Semester.",
            notes = "Recupera as informações de Semester conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SemesterDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroSemesterDTO filtroSemesterDTO) {
        List<SemesterDTO> semestersDTO = new ArrayList<>();
        List<Semester> semesters = semesterService.getSemestersByFiltro(filtroSemesterDTO);
        if(semesters.size() > 0){
            for (Semester g:
                    semesters) {
                SemesterDTO semesterDTO = semesterMapper.toDTO(g);
                semestersDTO.add(semesterDTO);
            }
        }

        return ResponseEntity.ok(semestersDTO);
    }

    /**
     * Retorna uma lista de {@link SemesterDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Retorna uma lista de Semesters cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SemesterDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getSemesters() {
        List<Semester> semesters = semesterService.getTodos();
        List<SemesterDTO> semestersDTO = new ArrayList<>();
        for (Semester semester : semesters) {
            SemesterDTO semesterDTO = semesterMapper.toDTO(semester);
            semestersDTO.add(semesterDTO);
        }
        return ResponseEntity.ok(semestersDTO);
    }

    /**
     * Ativa o {@link Grupo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Remove um Semester pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SemesterDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do Semester", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Semester semester = semesterService.remover(id.longValue());
        return ResponseEntity.ok(semester);
    }

}

