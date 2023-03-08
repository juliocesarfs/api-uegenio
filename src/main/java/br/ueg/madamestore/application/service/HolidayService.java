package br.ueg.madamestore.application.service;


import br.ueg.madamestore.application.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.madamestore.application.dto.FiltroHolidayDTO;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.Holiday;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import br.ueg.madamestore.comum.util.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    /**
     * Retorna uma lista de {@link Holiday} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Holiday> getHolidaysByFiltro(final FiltroHolidayDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Holiday> grupos = holidayRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(grupos)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return grupos;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Grupo, verifica se tem pelo meno 4 caracteres.
     *
     * @param filtroDTO
     */
    private void validarCamposObrigatoriosFiltro(final FiltroHolidayDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getNome())) {
            vazio = Boolean.FALSE;
        }

        if (filtroDTO.getDate()!=null) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorna uma lista de {@link Holiday} cadatrados .
     *
     * @return
     */
    public List<Holiday> getTodos() {
        List<Holiday> holidays = holidayRepository.findAll();

        if (CollectionUtil.isEmpty(holidays)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return holidays;
    }

    /**
     * Retorno um a {@link Holiday} pelo Id informado.
     * @param id - id to tipo Produto
     * @return
     */
    public Holiday getById(Long id){
        Optional<Holiday> holidayOptional = Optional.of(holidayRepository.findById(id).get());

        if(!holidayOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return holidayOptional.get();
    }

    /**
     * Salva a instânica de {@link Holiday} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param holiday
     * @return
     */
    public Holiday salvar(Holiday holiday) {
        validarCamposObrigatorios(holiday);
        validarDuplicidade(holiday);

        Holiday grupoSaved = holidayRepository.save(holiday);
        return grupoSaved;
    }

    public Holiday remover(Long id){
        Holiday holiday = this.getById(id);

        holidayRepository.delete(holiday);

        return holiday;
    }



    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param holiday
     */
    private void validarCamposObrigatorios(final Holiday holiday) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(holiday.getNome())) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o Holiday a ser salvo já existe na base de dados.
     *
     * @param holiday
     */
    private void validarDuplicidade(final Holiday holiday) {
        Long count = holidayRepository.countByNomeAndNotId(holiday.getNome(), holiday.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_FERIADO_DUPLICADO);
        }
    }

}
