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
import br.ueg.madamestore.application.mapper.VendidoMapper;
import br.ueg.madamestore.application.model.*;
import br.ueg.madamestore.application.service.UsuarioService;
import br.ueg.madamestore.application.service.SolicitationService;
import br.ueg.madamestore.application.solicitation.SolicitationInterface;
import br.ueg.madamestore.comum.exception.MessageResponse;
import io.swagger.annotations.*;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Classe de controle referente a entidade {@link Solicitation}.
 *
 * @author UEG
 */
@Api(tags = "Solicitation API")
@RestController
@RequestMapping("${app.api.base}/solicitation")
public class SolicitationController extends AbstractController {

    @Autowired
    private SolicitationService solicitationService;



    @ApiOperation(value = "Recupera o classroom pela id.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = SolicitationDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = MessageResponse.class)
    })
    @GetMapping(path = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getSolicitation(@ApiParam(value = "solicitation dto") @Valid @RequestBody SolicitationDTO solicitationDTO) {


        Solicitation solicitation = solicitationService.getByType(solicitationDTO.getType());

        if(solicitation != null) {
            Class myClass = null;
            try {
                myClass = Class.forName("br.ueg.madamestore.application.solicitation."+solicitation.getClass_implementation());

                Constructor constructor = myClass.getConstructor(List.class);
                Object instance = constructor.newInstance(solicitationDTO.getParameters());

                SolicitationInterface solicitationInterface = (SolicitationInterface) instance;
                Object result = solicitationInterface.execute();

                return ResponseEntity.ok(result);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }

        return ResponseEntity.ok(null);
    }


}
