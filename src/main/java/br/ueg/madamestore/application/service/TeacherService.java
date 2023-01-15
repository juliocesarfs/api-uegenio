package br.ueg.madamestore.application.service;


import br.ueg.madamestore.application.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.madamestore.application.dto.FiltroTeacherDTO;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.Teacher;
import br.ueg.madamestore.comum.exception.BusinessException;
import br.ueg.madamestore.comum.util.CollectionUtil;
import br.ueg.madamestore.comum.util.Util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * Retorna uma lista de {@link Teacher} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Teacher> getTeachersByFiltro(final FiltroTeacherDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Teacher> grupos = teacherRepository.findAllByFiltro(filtroDTO);

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
    private void validarCamposObrigatoriosFiltro(final FiltroTeacherDTO filtroDTO) {
        boolean vazio = Boolean.TRUE;

        if (!Util.isEmpty(filtroDTO.getNome())) {
            vazio = Boolean.FALSE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_FILTRO_INFORMAR_OUTRO);
        }
    }

    /**
     * Retorna uma lista de {@link Teacher} cadatrados .
     *
     * @return
     */
    public List<Teacher> getTodos() {
        List<Teacher> teachers = teacherRepository.findAll();

        if (CollectionUtil.isEmpty(teachers)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return teachers;
    }

    /**
     * Retorno um a {@link Teacher} pelo Id informado.
     * @param id - id to tipo Produto
     * @return
     */
    public Teacher getById(Long id){
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);

        if(!teacherOptional.isPresent()){
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return teacherOptional.get();
    }

    /**
     * Salva a instânica de {@link Teacher} na base de dados conforme os critérios
     * especificados na aplicação.
     *
     * @param teacher
     * @return
     */
    public Teacher salvar(Teacher teacher) {
        validarCamposObrigatorios(teacher);
        validarDuplicidade(teacher);

        Teacher grupoSaved = teacherRepository.save(teacher);
        return grupoSaved;
    }

    public Teacher remover(Long id){
        Teacher teacher = this.getById(id);

        teacherRepository.delete(teacher);

        return teacher;
    }



    /**
     * Verifica se os Campos Obrigatórios foram preenchidos.
     *
     * @param teacher
     */
    private void validarCamposObrigatorios(final Teacher teacher) {
        boolean vazio = Boolean.FALSE;

        if (Util.isEmpty(teacher.getNome())) {
            vazio = Boolean.TRUE;
        }

        if (vazio) {
            throw new BusinessException(SistemaMessageCode.ERRO_CAMPOS_OBRIGATORIOS);
        }
    }

    /**
     * Verifica se o Teacher a ser salvo já existe na base de dados.
     *
     * @param teacher
     */
    private void validarDuplicidade(final Teacher teacher) {
        Long count = teacherRepository.countByNomeAndNotId(teacher.getNome(), teacher.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_TIPO_AMIGO_DUPLICADO);
        }
    }

}
