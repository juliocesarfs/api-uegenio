package br.ueg.madamestore.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.ueg.madamestore.application.dto.FiltroAmigoDTO;
import br.ueg.madamestore.application.dto.FiltroTeacherDTO;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.exception.SistemaMessageCode;
import br.ueg.madamestore.application.model.Amigo;
import br.ueg.madamestore.application.model.Teacher;
import br.ueg.madamestore.application.repository.AmigoRepository;
import br.ueg.madamestore.application.repository.TeacherRepository;
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
    private TeacherRepository teachersRepository;

    /**
     * Retorna uma lista de {@link Teacher} conforme o filtro de pesquisa informado.
     *
     * @param filtroDTO
     * @return
     */
    public List<Teacher> getTeachersByFiltro(final FiltroTeacherDTO filtroDTO) {
        validarCamposObrigatoriosFiltro(filtroDTO);

        List<Teacher> teachers = teachersRepository.findAllByFiltro(filtroDTO);

        if (CollectionUtil.isEmpty(teachers)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO_FILTROS);
        }

        return teachers;
    }

    /**
     * Verifica se pelo menos um campo de pesquisa foi informado, e se informado o
     * nome do Teacher
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
        List<Teacher> teachers = teachersRepository.getTodos() ;

        if (CollectionUtil.isEmpty(teachers)) {
            throw new BusinessException(SistemaMessageCode.ERRO_NENHUM_REGISTRO_ENCONTRADO);
        }
        return teachers;
    }

    /**
     * Retorno um a {@link Teacher} pelo Id informado.
     * @param id - id to Teacher
     * @return
     */
    public Teacher getById(Long id){
        Optional<Teacher> teacherOptional = teachersRepository.findByIdFetch(id);

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

        teachersRepository.save(teacher);

        Teacher teacherSaved = this.getById(teacher.getId());
        return teacherSaved;
    }

    public Teacher remover(Long id){
        Teacher teacher = this.getById(id);

        teachersRepository.delete(teacher);

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
     * Verifica se o TipoTeacher a ser salvo já existe na base de dados.
     *
     * @param teacher
     */
    private void validarDuplicidade(final Teacher teacher) {
        Long count = teachersRepository.countByNomeAndNotId(teacher.getNome(), teacher.getId());

        if (count > BigDecimal.ZERO.longValue()) {
            throw new BusinessException(SistemaMessageCode.ERRO_PROFESSOR_DUPLICADO);
        }
    }


}
