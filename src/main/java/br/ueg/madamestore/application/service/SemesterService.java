package br.ueg.madamestore.application.service;


import br.ueg.madamestore.application.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.madamestore.application.dto.FiltroSemesterDTO;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.Semester;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import br.ueg.madamestore.comum.util.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    /**
     * Retorna uma lista de {@link Semester} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Semester> getSemestersByFiltro(final FiltroSemesterDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Semester> grupos = semesterRepository.findAllByFiltro(filtroDTO);

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
    private void validarCamposObrigatoriosFiltro(final FiltroSemesterDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getNome())) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorna uma lista de {@link Semester} cadatrados .
     *
     * @return
     */
    public List<Semester> getTodos() {
        List<Semester> semesters = semesterRepository.findAll();

        if (CollectionUtil.isEmpty(semesters)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return semesters;
    }

    /**
     * Retorno um a {@link Semester} pelo Id informado.
     * @param id - id to tipo Produto
     * @return
     */
    public Semester getById(Long id){
        Optional<Semester> semesterOptional = semesterRepository.findById(id);

        if(!semesterOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return semesterOptional.get();
    }

    /**
     * Salva a instânica de {@link Semester} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param semester
     * @return
     */
    public Semester salvar(Semester semester) {
        validarCamposObrigatorios(semester);
        validarDuplicidade(semester);

        Semester grupoSaved = semesterRepository.save(semester);
        return grupoSaved;
    }

    public Semester remover(Long id){
        Semester semester = this.getById(id);

        semesterRepository.delete(semester);

        return semester;
    }



    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param semester
     */
    private void validarCamposObrigatorios(final Semester semester) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(semester.getNome())) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o Semester a ser salvo já existe na base de dados.
     *
     * @param semester
     */
    private void validarDuplicidade(final Semester semester) {
        Long count = semesterRepository.countByNomeAndNotId(semester.getNome(), semester.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_TIPO_AMIGO_DUPLICADO);
        }
    }

}
