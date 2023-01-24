/*
 * UsuarioService.java
 * Copyright (c) UEG.
 *
 *
 *
 *
 */
package br.ueg.madamestore.application.service;

import br.ueg.madamestore.application.configuration.Constante;
import br.ueg.madamestore.application.dto.*;
import br.ueg.madamestore.application.enums.StatusAtivoInativo;
import br.ueg.madamestore.application.enums.StatusEspera;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.enums.StatusVendido;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.*;
import br.ueg.madamestore.application.repository.*;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import br.ueg.madamestore.comum.util.Util;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.ueg.madamestore.application.exception.SistemaMessageCode.ERRO_QUANTIDADE_DE_PRODUTOS_INSUFICIENTE;

/**
 * Classe de négocio referente a entidade {@link Usuario}.
 *
 * @author UEG
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SolicitationService {

    @Autowired
    private SolicitationRepository solicitationRepository;



    public void configurarSolicitationParameters(Solicitation solicitation) {
        for (ParameterSolicitation parameter : solicitation.getParameters()){
            parameter.setSolicitation(solicitation);
        }
    }





    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */




    /**
     * Retorna a instância de {@link Solicitation} conforme o 'id' informado.
     *
     * @param id -
     * @return -
     */
    public Solicitation getById(final Long id) {
        return solicitationRepository.findById(id).orElse(null);
    }

    public Solicitation getByType(final String type) {
        return solicitationRepository.findByType(type).orElse(null);
    }



    public List<Solicitation> getSolicitations() { return solicitationRepository.getTodos(); }

    /**
     * Retorna a instância de {@link Usuario} conforme o 'id' informado.
     *
     * @param id
     * @return
     */
    public Solicitation getByIdFetch(final Long id) {
        return solicitationRepository.findByIdFetch(id).orElse(null);
    }



}
