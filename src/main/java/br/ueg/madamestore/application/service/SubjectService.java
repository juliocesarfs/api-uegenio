package br.ueg.madamestore.application.service;


import br.ueg.madamestore.application.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.madamestore.application.dto.FiltroSubjectDTO;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.Subject;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import br.ueg.madamestore.comum.util.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    /**
     * Retorna uma lista de {@link Subject} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Subject> getSubjectsByFiltro(final FiltroSubjectDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Subject> grupos = subjectRepository.findAllByFiltro(filtroDTO);

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
    private void validarCamposObrigatoriosFiltro(final FiltroSubjectDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getNome())) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorna uma lista de {@link Subject} cadatrados .
     *
     * @return
     */
    public List<Subject> getTodos() {
        List<Subject> subjects = subjectRepository.findAll();

        if (CollectionUtil.isEmpty(subjects)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return subjects;
    }

    /**
     * Retorno um a {@link Subject} pelo Id informado.
     * @param id - id to tipo Produto
     * @return
     */
    public Subject getById(Long id){
        Optional<Subject> subjectOptional = subjectRepository.findById(id);

        if(!subjectOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return subjectOptional.get();
    }

    /**
     * Salva a instânica de {@link Subject} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param subject
     * @return
     */
    public Subject salvar(Subject subject) {
        validarCamposObrigatorios(subject);
        validarDuplicidade(subject);

        Subject grupoSaved = subjectRepository.save(subject);
        return grupoSaved;
    }

    public Subject remover(Long id){
        Subject subject = this.getById(id);

        subjectRepository.delete(subject);

        return subject;
    }



    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param subject
     */
    private void validarCamposObrigatorios(final Subject subject) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(subject.getNome())) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o Subject a ser salvo já existe na base de dados.
     *
     * @param subject
     */
    private void validarDuplicidade(final Subject subject) {
        Long count = subjectRepository.countByNomeAndNotId(subject.getNome(), subject.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_TIPO_AMIGO_DUPLICADO);
        }
    }

}
