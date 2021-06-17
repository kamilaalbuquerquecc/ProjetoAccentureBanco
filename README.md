# ProjetoAccentureBanco
Projeto para conclusão de curso JAVA-SpringBoot

## Sistema Bancário BANK
O novo banco on-line BANK precisa desenvolver inicialmente um módulo
essencial do sistema bancário, esse módulo consiste de uma API que irá
armazenar as seguintes transações bancárias: Sacar, Depositar e Transferir
numerário.

## Estrutura básica
- Um projeto: BANK;
- No projeto temos 4 classes RestController que são responsáveis por implementar os endpoints da API Rest.
- Quatro repositórios são utilizados: AgenciaRepository, ClienteRepository, ContaCorrenteRepository e ExtratoRepository, que são responsáveis por manipular as entidades Agencia, - Cliente, ContaCorrente e Extrato no banco de dados MySQL;
- O modelo é composto pelas classes Agencia.java, Cliente.java, ContaCorrete.java e Extrato.java que podem ser encontradas no pacote model;
- Na classe CustomErrorType criamos os metodos que usamos para personalizar as exceções;
- No package de testes criamos 3 clases com JUnit que testam criar, atualizar e deletar objetos dos repositórios.
- Não há implementação de frontend, mas o projeto fornece uma interface de acesso à API via swagger.

## User Stories implementadas
- Eu, como administrador, gostaria de criar uma Agência, informando seu nome, endereço e telefone;
- Eu, como administrador, gostaria de adicionar um Cliente, informando o idAgencia, seu nome, CPF e telefone;
- Eu, como administrador, gostaria de criar uma Conta, informando o idCliente, idAgencia e um numero de conta;
- Eu, como administrador, gostaria de visualizar todo o extrato do sistema;
- Eu, como administrador, gostaria de atualizar dados do Cliente e Agencia;
- Eu, como cliente, gostaria de fazer um depósito;
- Eu, como cliente, gostaria de fazer um saque;
- Eu, como cliente, gostaria de fazer uma transferência;
- Eu, como cliente, gostaria de ver o extrato da minha conta;
- Eu, como cliente, gostaria de recalculaar meu saldo;

## Tecnologias
Algumas dependências:  

- Spring Web
- Spring Data JPA
- MySQL Web
- Spring Boot DevTools

## Endereços úteis

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## COMANDOS ÚTEIS

git init

git status

git add .

git commit -m "comentário"

git push

