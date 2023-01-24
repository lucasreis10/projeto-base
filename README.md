# projeto-base


# Links
- [Local](http://localhost:8080/swagger)
- [Github](https://github.com/lucasreis10/projeto-base)
- [Pipeline CI](https://github.com/lucasreis10/projeto-base/actions)


## Pre requisitos para execução do projeto

É necessario instalar as seguintes ferramentas para executar o projeto local:

* [JDK-17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [docker](https://docs.docker.com/engine/install/)
* [docker-compose](https://docs.docker.com/compose/install/)


# Comandos importantes


### Antes de startar a aplicação rode o banco de dados com o comando abaixo:

```shell script
docker-compose up
```
<i>HOST MySQL: http://localhost:3306 </i>
<i>Database: projeto_base </i>
<i>Username: root </i>
<i>Password: 123456 </i>


### Subir aplicação usando gradle

```shell script 
./gradlew bootRun
```

### Rodar testes

```shell script 
 ./gradlew test -i
```
<i>Importante ter docker instalado para execução de teste de integração com [testcontainers](https://www.testcontainers.org/) </i>


### Padrão de testes utlizados

* [XUnit Test Patterns - Four Phase Test](http://xunitpatterns.com/Four%20Phase%20Test.html)
* [XUnit Test Patterns - Creation Method](http://xunitpatterns.com/Creation%20Method.html)