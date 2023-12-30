
_Projeto de cadastro de usuários_.

Projeto backend de cadastro de usuários com as seguintes operações:

- `login (com retorno de token de autorização)`
- `cadastro de cep`
- `consulta de cep`
- `cadastro de usuário`
- `alteração de usuário`
- `remoção lógica de usuário`
- `consulta de usuário`

## Iniciando...

- `git clone https://github.com/marcelohkimura/cadastro`
- `cd cadastro`

Agora você poderá executar os vários comandos abaixo.

## Pré-requisitos

- `mvn --version`<br>
  você deverá ver a indicação da versão do Maven instalada e
  a versão do JDK, dentre outras. Observe que o JDK é obrigatório, assim como
  a definição das variáveis de ambiente **JAVA_HOME** e **M2_HOME**.
  
  É um projeto desenvolvido com:

- `Spring Boot 3`
- `JDK 17`
- `Maven 3.9.6`
- `MySQL 8.2.0`

## Limpar, compilar, executar testes de unidade e cobertura

- `mvn clean`<br>
  remove diretório _target_

- `mvn compile`<br>
  compila o projeto, deposita resultados no diretório _target_

- `mvn test`<br>
  executa todos os testes do projeto. Foram criados testes de integração que são
  chamados na camada de controller para testar todo o ecossistema

## Produzindo código executável

- `mvn package`<br>
  gera arquivo _exemplo.jar_ no diretório _target_. Observe que
  o arquivo gerado não é executável. Um arquivo jar é um arquivo no formato
  zip. Você pode verificar o conteúde deste arquivo ao executar o comando `jar vft exemplo.jar`.

## Criação do Banco de Dados

Na pasta src/test/resources existem dois scripts:

- `data.sql` - Criação das tabelas<br>
- `schema.sql` - Criação do primeiro usuário para acessar o sistema<br>

**Esses dois scripts também são utilizados na execução dos testes integrados com o banco H2 em tempo de compilação**

A execução e exemplos de chamadas são fornecidos na seção seguinte.

## Executando a aplicação e a RESTFul API

- `mvn spring-boot:run`<br>
  coloca em execução a API na porta padrão (8080). Para fazer uso de porta
  diferente use `java -jar -Dserver.port=9876 target/api.jar`, por exemplo. 

**Para os testes foi utilizado o aplicativo postman e as chamadas podem ser vistas no arquivo src/main/resources/cadastro.json**
