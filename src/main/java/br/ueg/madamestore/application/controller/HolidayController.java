package br.ueg.madamestore.application.controller;


import br.ueg.madamestore.application.dto.HolidayDTO;
import br.ueg.madamestore.application.mapper.HolidayMapper;
import br.ueg.madamestore.application.model.Holiday;
import br.ueg.madamestore.application.service.HolidayService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.ueg.madamestore.api.util.Validation;
import br.ueg.madamestore.application.dto.FiltroHolidayDTO;
import br.ueg.madamestore.application.model.Grupo;
import br.ueg.madamestore.comum.exception.MessageResponse;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "Holiday API")
@RestController
@RequestMapping(path = "${app.api.base}/holiday")
public class HolidayController extends AbstractController {

    @Autowired
    private HolidayMapper holidayMapper;

    @Autowired
    private HolidayService holidayService;

    @PostMapping
    @ApiOperation(value = "Inclusão/alteração de feriado.",
            notes = "Incluir/Alterar feriado.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = HolidayDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    public ResponseEntity<?> incluir(@ApiParam(value = "Informações de feriado", required = true) @Valid @RequestBody HolidayDTO holidayDTO) {
        Holiday grupo = holidayMapper.toEntity(holidayDTO);
        return ResponseEntity.ok(holidayMapper.toDTO(holidayService.salvar(grupo)));
    }

    /**
     * Altera a instância de {@link HolidayDTO} na base de dados.
     *
     * @param id
     * @param holidayDTO
     * @return
     */
    @ApiOperation(value = "Altera as informações de Tipo Amigo.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = HolidayDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class)
    })
    @PutMapping(path = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> alterar(
            @ApiParam(value = "Código do Tipo Amigo", required = true) @PathVariable final BigDecimal id,
            @ApiParam(value = "Informações do Tipo Amigo", required = true) @Valid @RequestBody HolidayDTO holidayDTO) {
        Validation.max("id", id, 99999999L);
        Holiday holiday = holidayMapper.toEntity(holidayDTO);
        holiday.setId(id.longValue());
        Holiday holidaySaved = holidayService.salvar(holiday);
        return ResponseEntity.ok(holidayMapper.toDTO(holidaySaved));
    }

    /**
     * Retorna a instância de {@link HolidayDTO} pelo id informado.
     *
     * @param id
     * s@return
     */
    @ApiOperation(value = "Retorna as informações do Holiday pelo id informado.", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = HolidayDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getById(@ApiParam(value = "Código do Grupo", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Holiday holiday = holidayService.getById(id.longValue());



        HolidayDTO holidayDTO = holidayMapper.toDTO(holiday);

        return ResponseEntity.ok(holidayDTO);
    }

    /**
     * Retorna a buscar de {@link Holiday} por {@link FiltroHolidayDTO}
     *
     * @param filtroHolidayDTO
     * @return
     */
    @ApiOperation(value = "Pesquisa de Holiday.",
            notes = "Recupera as informações de Holiday conforme dados informados no filtro de busca", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = HolidayDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class) })
    @GetMapping(path = "/filtro", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllByFiltro(@ApiParam(value = "Filtro de pesquisa", required = true) @ModelAttribute final FiltroHolidayDTO filtroHolidayDTO) {
        List<HolidayDTO> holidaysDTO = new ArrayList<>();
        List<Holiday> holidays = holidayService.getHolidaysByFiltro(filtroHolidayDTO);
        if(holidays.size() > 0){
            for (Holiday g:
                    holidays) {



                HolidayDTO holidayDTO = holidayMapper.toDTO(g);
                holidaysDTO.add(holidayDTO);
            }
        }

        return ResponseEntity.ok(holidaysDTO);
    }

    /**
     * Retorna uma lista de {@link HolidayDTO} cadastrados.
     *
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Retorna uma lista de Holidays cadastrados.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = HolidayDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getHolidays() {
        List<Holiday> holidays = holidayService.getTodos();
        List<HolidayDTO> holidaysDTO = new ArrayList<>();
        for (Holiday holiday : holidays) {


            HolidayDTO holidayDTO = holidayMapper.toDTO(holiday);
            holidaysDTO.add(holidayDTO);
        }
        return ResponseEntity.ok(holidaysDTO);
    }

    /**
     * Ativa o {@link Grupo} pelo 'id' informado.
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Remove um Holiday pelo id informado.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = HolidayDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @DeleteMapping(path = "/{id:[\\d]+}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> remover(@ApiParam(value = "Id do Holiday", required = true) @PathVariable final BigDecimal id) {
        Validation.max("id", id, 99999999L);
        Holiday holiday = holidayService.remover(id.longValue());
        return ResponseEntity.ok(holiday);
    }

}

