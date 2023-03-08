

# UEGênio API - Assistente Virtual para alunos de Sistemas de Informação

## Sobre

Esse projeto foi desenvolvido como requisito parcial de avaliação do trabalho de conclusão de curso de Sistemas de Informação da [Universidade Estadual de Goiás - Câmpus Central](https://ueg.br/campuscentral/).<br/>
Tendo como principal objetivo auxiliar a jornada acadêmica do Estudande de Sistemas de Informação da UEG.

## Requerimentos
Para executar esse projeto é necessário que você tenha algumas ferramentas instaladas no seu dispositivo.

### java SDK
O download do Java está disponível [aqui](https://www.oracle.com/br/java/technologies/downloads/).

### PostgreSQL
O download do Postgres está disponível [aqui](https://www.postgresql.org/download/).

### Maven
O download do Maven está disponível [aqui](https://maven.apache.org/download.cgi).

## Execução
Instale as dependências através do comando 
> mvn install
Confira no arquivo application.properties se o username e password do seu banco de dados estão corretas, assim como a porta onde ele está localizado.

Rode o sistema a partir da Classe *ModeloApplication*

O app será executado em modo de desenvolvimento.<br />
na porta [http://localhost:8081](http://localhost:8081)

## Tecnologias

  * Java Spring boot
  * Hibernate
  * Annotations
  * Lombok
  * JWT
  * PostgreSQL

## Recursos
    - [X] Administrador
        - [X] Login
        - [X] Logout
    - [X] Semestre
        - [X] Cadastro
        - [X] Alteração
        - [X] Exclusão
    - [X] Professor
        - [X] Cadastro
        - [X] Alteração
        - [X] Exclusão
    - [X] Disciplina
        - [X] Cadastro
        - [X] Alteração
        - [X] Exclusão
    - [X] Feriado
        - [X] Cadastro
        - [X] Alteração
        - [X] Exclusão
    - [X] Aula
        - [X] Cadastro
        - [X] Alteração
        - [X] Exclusão
