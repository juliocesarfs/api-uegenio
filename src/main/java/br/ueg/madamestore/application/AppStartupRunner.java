package br.ueg.madamestore.application;


import br.ueg.madamestore.application.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.ueg.madamestore.application.enums.StatusAtivoInativo;
import br.ueg.madamestore.application.enums.StatusSimNao;
import br.ueg.madamestore.application.repository.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe para ser executado após o Spring Inicializar.
 * Verificado o modo de start para spring.jpa.hibernate.ddl-auto==create-drop
 */

@Component
public class AppStartupRunner implements ApplicationRunner {

    public static final String NONE="none";
    public static final String CREATE_DROP="create-drop";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";

    private static final Logger LOG =
            LoggerFactory.getLogger(AppStartupRunner.class);


    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Autowired
    ModuloRepository moduloRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TipoAmigoRepository tipoAmigoRepository;

    @Autowired
    AmigoRepository amigoRepository;

    @Autowired
    MensagemRepository mensagemRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOG.info("Application started with option names : {}",
                args.getOptionNames());
        LOG.info("spring.jpa.hibernate.ddl-auto={}",ddlAuto);

        if(this.ddlAuto.trim().equals(CREATE_DROP) ||
                this.ddlAuto.trim().equals(CREATE) ||
                this.ddlAuto.trim().equals(UPDATE)
        ){
            this.initiateDemoInstance();
        }
    }

    private void initiateDemoInstance() {
        LOG.info("initiateDemoInstance");
        Modulo moduloUsuario = createModuloCrud("USUARIO", "Manter Usuário");

        Modulo moduloGrupo = createModuloCrud("GRUPO", "Manter Grupo");

        Modulo moduloProduto = createModuloCrud("PRODUTO", "Manter Produto");
        Modulo moduloProdutoFuncionaro = createModuloFuncionario("PRODUTO", "Gerenciar Produto");


        Modulo moduloTipoProduto = createModuloCrud("TIPOPRODUTO", "Manter Tipo Produto");
        Modulo moduloTipoProdutoFuncionario = createModuloFuncionario("TIPOPRODUTO", "Gerenciar Tipo Produto");
        
        Modulo moduloVenda = createModuloCrud("VENDA", "Manter Venda");
        Modulo moduloVendaFuncionario = createModuloFuncionario("VENDA", "Gerenciar Venda");
        Modulo moduloAmigo=createModuloAmigo();


        Modulo moduloMensagem= createModuloCrud("MENSAGEM","Manter Mensagem");




        Modulo moduloCliente = createModuloCrud("CLIENTE", "Manter Cliente");
        Modulo moduloClienteFuncionario = createModuloFuncionario("CLIENTE", "Gerenciar Cliente");



        Grupo grupoAdmin = createGrupoAdmin(Arrays.asList(moduloUsuario, moduloProduto, moduloTipoProduto, moduloVenda, moduloCliente,moduloAmigo,moduloMensagem));

        Grupo grupoGerente = createCrupoGerente(Arrays.asList(moduloProduto, moduloTipoProduto, moduloVenda, moduloCliente));

        Grupo grupoFuncionario = createCrupoFuncionario(Arrays.asList(moduloTipoProdutoFuncionario, moduloProdutoFuncionaro, moduloVendaFuncionario, moduloClienteFuncionario));

        createUsuarioAdmin(grupoAdmin);
        createCliente();
    }

    /**
     * cria dados de amigo para tese
     */
    private void createAmigos() {

        TipoAmigo tipoAmigo = tipoAmigoRepository.findById(1L).get();
        TipoAmigo tipoConhecido = tipoAmigoRepository.findById(2L).get();

        Amigo amigo = new Amigo();
        amigo.setAmigo(StatusSimNao.SIM);
        amigo.setDataAmizade(LocalDate.now());
        amigo.setNome("Primeiro Amigo");
        amigo.setTipo(tipoAmigo);

        amigoRepository.save(amigo);

        Amigo conhecido = new Amigo();
        conhecido.setAmigo(StatusSimNao.SIM);
        conhecido.setDataAmizade(LocalDate.now());
        conhecido.setNome("Primeiro Conhecido");
        conhecido.setTipo(tipoConhecido);

        amigoRepository.save(conhecido);



    }

    /**
     * Cria dados de tipos de amigos para teste
     */
    private void createTipoAmigos() {
        TipoAmigo tipoAmigo=new TipoAmigo();
        tipoAmigo.setNome("Amigo");
        tipoAmigoRepository.save(tipoAmigo);

        TipoAmigo tipoConhecido = new TipoAmigo();
        tipoConhecido.setNome("Conhecido");
        tipoAmigoRepository.save(tipoConhecido);

        TipoAmigo tipoMelhorAmigo = new TipoAmigo();
        tipoMelhorAmigo.setNome("Melhor Amigo");
        tipoAmigoRepository.save(tipoMelhorAmigo);
    }

    /**
     * Cria o Modulo de tipo de amigo e salva.
     * @return tipo amigo salvo no banco.
     */
    private Modulo createModuloTipoAmigo() {
        Modulo moduloTipoAmigo = new Modulo();

        moduloTipoAmigo.setMnemonico("TIPOAMIGO");
        moduloTipoAmigo.setNome("Manter Tipo Amigo ");
        moduloTipoAmigo.setStatus(StatusAtivoInativo.ATIVO);
        moduloTipoAmigo = moduloRepository.save(moduloTipoAmigo);

        Set<Funcionalidade> funcionalidades = getFuncionalidadesAdminGerente().stream()
                .filter(
                        funcionalidade -> !funcionalidade.getMnemonico().equals("ATIVAR_INATIVAR")
                ).collect(Collectors.toSet());

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("REMOVER");
        fManter.setNome("Remover");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);


        for(Funcionalidade funcionalidade: funcionalidades){
            funcionalidade.setModulo(moduloTipoAmigo);
        }

        moduloTipoAmigo.setFuncionalidades(funcionalidades);
        moduloTipoAmigo = moduloRepository.save(moduloTipoAmigo);

        return moduloTipoAmigo;
    }

    /**
     * Cria o Modulo de amigo e salva.
     * @return tipo amigo salvo no banco.
     */
    private Modulo createModuloAmigo() {
        Modulo moduloAmigo = new Modulo();

        moduloAmigo.setMnemonico("AMIGO");
        moduloAmigo.setNome("Manter Amigo ");
        moduloAmigo.setStatus(StatusAtivoInativo.ATIVO);
        moduloAmigo = moduloRepository.save(moduloAmigo);

        Set<Funcionalidade> funcionalidades = getFuncionalidadesAdminGerente().stream()
                .filter(
                        funcionalidade -> !funcionalidade.getMnemonico().equals("ATIVAR_INATIVAR")
                ).collect(Collectors.toSet());

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("REMOVER");
        fManter.setNome("Remover");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        Funcionalidade fAmigo = new Funcionalidade();
        fAmigo.setMnemonico("STATUS");
        fAmigo.setNome("É Amigo");
        fAmigo.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fAmigo);


        for(Funcionalidade funcionalidade: funcionalidades){
            funcionalidade.setModulo(moduloAmigo);
        }

        moduloAmigo.setFuncionalidades(funcionalidades);
        moduloAmigo = moduloRepository.save(moduloAmigo);

        return moduloAmigo;
    }

    private void createUsuarioAdmin(Grupo grupo) {
        Usuario usuario = new Usuario();
        usuario.setStatus(StatusAtivoInativo.ATIVO);
        usuario.setDataCadastrado(LocalDate.now());
        usuario.setDataAtualizado(LocalDate.now());
        usuario.setTelefones(new HashSet<>());
        usuario.setCpf("10010010017");
        usuario.setNome("Administrador");
        usuario.setEmail("admin@teste.com.br");
        usuario.setSenha(new BCryptPasswordEncoder().encode("admin"));

        usuario = usuarioRepository.save(usuario);

        Set<UsuarioGrupo> usuarioGrupos = new HashSet<>();
        usuarioGrupos.add(new UsuarioGrupo(null,usuario,grupo));
        usuario.setGrupos(usuarioGrupos);
        usuario = usuarioRepository.save(usuario);
    }
    private void createCliente() {
        LocalDate.now();
        Cliente cliente = new Cliente();
        cliente.setEmail("sememail");
        cliente.setNome("Sem Cliente");
        cliente.setTelefone("62991261326");
        cliente.setDataCadastro(LocalDate.of(2000,04,12));

        cliente = clienteRepository.save(cliente);

    }

    private Grupo createGrupoAdmin(List<Modulo> modulos) {
        Grupo grupo = new Grupo();
        grupo.setNome("Administradores");
        grupo.setAdministrador(StatusSimNao.SIM);
        grupo.setDescricao("Grupo de Administradores");
        grupo.setStatus(StatusAtivoInativo.ATIVO);
        grupo = grupoRepository.save(grupo);
        final Grupo lGrupo = grupo;
        grupo.setGrupoFuncionalidades(new HashSet<>());

        modulos.forEach(modulo -> {
            lGrupo.getGrupoFuncionalidades().addAll(
                    modulo.getFuncionalidades().stream().map(
                            funcionalidade -> new GrupoFuncionalidade(null, lGrupo, funcionalidade)
                    ).collect(Collectors.toSet())
            );
        });

        grupoRepository.save(grupo);
        return grupo;
    }

    private Grupo createCrupoGerente(List<Modulo> modulos) {
        
        Grupo grupo = new Grupo();
        grupo.setNome("Gerentes");
        grupo.setAdministrador(StatusSimNao.SIM);
        grupo.setDescricao("Grupo de gerentes");
        grupo.setStatus(StatusAtivoInativo.ATIVO);
        grupo = grupoRepository.save(grupo);
        final Grupo lGrupo = grupo;
        
        grupo.setGrupoFuncionalidades(new HashSet<>());

        modulos.forEach(modulo -> {
            lGrupo.getGrupoFuncionalidades().addAll(
                modulo.getFuncionalidades().stream().map(
                    funcionalidade -> new GrupoFuncionalidade(null, lGrupo, funcionalidade)
                ).collect(Collectors.toSet())
            );
        });

        
        grupoRepository.save(grupo);
        return grupo; 
    }



    private Grupo createCrupoFuncionario(List<Modulo> modulos) {
        Grupo grupo = new Grupo();
        grupo.setNome("Funcionários");
        grupo.setAdministrador(StatusSimNao.SIM);
        grupo.setDescricao("Grupo de funcionários");
        grupo.setStatus(StatusAtivoInativo.ATIVO);
        grupo = grupoRepository.save(grupo);
        final Grupo lGrupo = grupo;

        grupo.setGrupoFuncionalidades(new HashSet<>());

        modulos.forEach(modulo -> {
            lGrupo.getGrupoFuncionalidades().addAll(
                    modulo.getFuncionalidades().stream().map(
                            funcionalidade -> new GrupoFuncionalidade(null, lGrupo, funcionalidade)
                    ).collect(Collectors.toSet())
            );
        });


        grupoRepository.save(grupo);
        return grupo;
    }

    private Modulo createModuloCrud(String moduloMNemonico, String moduloNome) {
        Modulo moduloUsuario = new Modulo();

        moduloUsuario.setMnemonico(moduloMNemonico);
        moduloUsuario.setNome(moduloNome);
        moduloUsuario.setStatus(StatusAtivoInativo.ATIVO);
        moduloUsuario = moduloRepository.save(moduloUsuario);

        final Modulo lModuloUsuario = moduloUsuario;
        Set<Funcionalidade> funcionaldiades = getFuncionalidadesAdminGerente();

        for(Funcionalidade funcionalidade: funcionaldiades){
            funcionalidade.setModulo(moduloUsuario);
        }

        moduloUsuario.setFuncionalidades(funcionaldiades);
        moduloUsuario = moduloRepository.save(moduloUsuario);
        return moduloUsuario;
    }

    private Modulo createModuloFuncionario(String moduloMNemonico, String moduloNome) {
        Modulo moduloUsuario = new Modulo();

        moduloUsuario.setMnemonico(moduloMNemonico);
        moduloUsuario.setNome(moduloNome);
        moduloUsuario.setStatus(StatusAtivoInativo.ATIVO);
        moduloUsuario = moduloRepository.save(moduloUsuario);

        final Modulo lModuloUsuario = moduloUsuario;
        Set<Funcionalidade> funcionaldiades = getFuncionalidadesFuncionario();

        for(Funcionalidade funcionalidade: funcionaldiades){
            funcionalidade.setModulo(moduloUsuario);
        }

        moduloUsuario.setFuncionalidades(funcionaldiades);
        moduloUsuario = moduloRepository.save(moduloUsuario);
        return moduloUsuario;
    }

    private Modulo createModuloTipoProduto() {
        Modulo moduloTipoProduto = new Modulo();

        moduloTipoProduto.setMnemonico("TIPOPRODUTO");
        moduloTipoProduto.setNome("Manter Tipo Produto ");
        moduloTipoProduto.setStatus(StatusAtivoInativo.ATIVO);
        moduloTipoProduto = moduloRepository.save(moduloTipoProduto);

        Set<Funcionalidade> funcionalidades = getFuncionalidadesAdminGerente().stream()
                .filter(
                        funcionalidade -> !funcionalidade.getMnemonico().equals("ATIVAR_INATIVAR")
                ).collect(Collectors.toSet());

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("REMOVER");
        fManter.setNome("Remover");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);


        for(Funcionalidade funcionalidade: funcionalidades){
            funcionalidade.setModulo(moduloTipoProduto);
        }

        moduloTipoProduto.setFuncionalidades(funcionalidades);
        moduloTipoProduto = moduloRepository.save(moduloTipoProduto);

        return moduloTipoProduto;
    }

    /**
     * retorna Funcionalidades padrão de um CRRUD
     * @return
     */
    private Set<Funcionalidade> getFuncionalidadesAdminGerente() {
        Set<Funcionalidade> funcionalidades = new HashSet<>();

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("INCLUIR");
        fManter.setNome("Incluir");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("ALTERAR");
        fManter.setNome("Alterar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("ATIVAR_INATIVAR");
        fManter.setNome("Ativar/Inativar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("PESQUISAR");
        fManter.setNome("Pesquisar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("VISUALIZAR");
        fManter.setNome("Visualizar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("REMOVER");
        fManter.setNome("Remover");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        return funcionalidades;
    }

    private Set<Funcionalidade> getFuncionalidadesFuncionario() {
        Set<Funcionalidade> funcionalidades = new HashSet<>();

        Funcionalidade fManter = new Funcionalidade();
        fManter.setMnemonico("INCLUIR");
        fManter.setNome("Incluir");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("PESQUISAR");
        fManter.setNome("Pesquisar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        fManter = new Funcionalidade();
        fManter.setMnemonico("VISUALIZAR");
        fManter.setNome("Visualizar");
        fManter.setStatus(StatusAtivoInativo.ATIVO);
        funcionalidades.add(fManter);

        return funcionalidades;
    }
}
