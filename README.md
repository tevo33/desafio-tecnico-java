# A DBServer

Somos uma softwarehouse que constrói entregas sob medida. Há 27 anos trabalhamos com foco nas pessoas, guiados por nossos valores, crescendo lado a lado com nossos colaboradores. Nosso lema é fazer a coisa certa do jeito certo através do design e do build.

Nos últimos anos, iniciamos uma caminhada em direção a diversidade e a inclusão e após o lançamento do nosso Marco Ético temos agora um norteador dos princípios que irão trilhar a nossa trajetória.

Estamos orgulhosos de promover um ambiente de trabalho livre de discriminação. Acreditamos que a diversidade de experiências, perspectivas e histórico transforma nosso ambiente de trabalho. Estamos empenhados a construir esse legado com a sua parceria! 
 
Conheça nosso Marco ético: http://www.dbserver.com.br/marcoetico/
</br></br></br>

## Desafio
Os times da DBServer enfrentam um grande problema. Como eles são muito democráticos, todos os dias eles gastam 30 minutos decidindo onde eles almoçarão.
Vamos fazer um pequeno sistema que auxilie essa tomada de decisão!

## Estórias

### Estória 1
*Eu como profissional faminto</br>
Quero votar no meu restaurante favorito </br>
Para que eu consiga democraticamente levar meus colegas a comer onde eu gosto.* </br>

Critério de Aceitação:
- Um profissional só pode votar em um restaurante por dia.

### Estória 2
*Eu como facilitador do processo de votação </br>
Quero que um restaurante não possa ser repetido durante a semana </br>
Para que não precise ouvir reclamações infinitas!*</br>

Critério de Aceitação:
- O mesmo restaurante não pode ser escolhido mais de uma vez durante a semana.

### Estória 3
*Eu como profissional faminto </br>
Quero saber antes do meio dia qual foi o restaurante escolhido </br>
Para que eu possa me despir de preconceitos e preparar o psicológico.* </br>

Critério de Aceitação:
- Mostrar de alguma forma o resultado da votação.
</br></br>

## Instruções:
- Você deve disponibilizar seu projeto no GitHub (Não é preciso fazer fork deste repositório).
- Lembre-se de utilizar commits pequenos e frequentes. Gostaríamos de entender sua linha de raciocínio. 
- Você deve usar Maven ou Gradle para buildar a aplicação;
- Sua aplicação deve iniciar com um comando. Exemplo ```mvn exec:java```, ```mvn jetty:run```, ```mvn spring-boot:run```, etc.
- A aplicação deve ter um banco de dados (Pode ser utilizado um Banco de Dados em memória, como o H2);
- A Base de dados e as tabelas, devem ser criadas pela ferramenta de build ou pela aplicação;
- A aplicação deve possuir uma interface gráfica ou uma API de serviços;
- Testes automatizados são importantíssimos e garantem qualidade e escalabilidade segura para as aplicações. Desta maneira, gostaríamos que a sua apresentasse testes de unidade e de integração.
- Crie um Readme que inclua:
  - Requisitos de ambiente necessários para compilar e rodar o software
  - Instruções de como utilizar o sistema.
  - O que vale destacar no código implementado?
  - O que poderia ser feito para melhorar o sistema?
  - Quais foram as suas dificuldades na implementação (Se elas existiram).
  - Algo a mais que você tenha a dizer. =D

</br></br>
Obrigado por dedicar parte do seu tempo! Esperamos ansiosamente a sua resposta! 

# Sistema de Votação de Restaurantes

Este sistema foi desenvolvido para auxiliar na escolha diária de restaurantes para almoço em equipe, baseado nas estórias descritas no desafio.

## Funcionalidades

1. **Votação de Restaurantes**
   - Cada profissional pode votar apenas uma vez por dia
   - O sistema impede que o mesmo restaurante seja escolhido mais de uma vez na semana
   - O resultado da votação é disponibilizado antes do meio-dia
   
2. **Sistema de Mensageria**
   - Toda vez que um usuário vota, o sistema envia uma mensagem para o Kafka
   - Um consumidor processa a mensagem e envia um email de confirmação para o usuário
   - O email contém os detalhes do voto registrado

## Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database (banco de dados em memória)
- Spring Kafka
- Spring Mail
- Lombok
- Maven
- JUnit 5 (para testes)

### Frontend
- React 18
- TypeScript
- Material-UI
- Axios
- React Router

### Infraestrutura
- Apache Kafka
- Zookeeper
- Docker & Docker Compose

## Pré-requisitos

- Java 17 ou superior
- Node.js 18 ou superior
- Maven
- npm ou yarn
- Docker e Docker Compose (para rodar o Kafka e Zookeeper)

## Executando a Aplicação

### Infraestrutura (Kafka e Zookeeper)

1. Inicie os containers do Kafka e Zookeeper:
```bash
docker-compose up -d
```

Isso irá iniciar o Zookeeper, o Kafka e uma interface web para o Kafka (disponível em `http://localhost:8090`).

### Backend

1. Navegue até a pasta do servidor:
```bash
cd server
```

2. Configure as variáveis de ambiente para o envio de emails (ou edite o arquivo `application.yml`):
```bash
export EMAIL_USERNAME=seu-email@gmail.com
export EMAIL_PASSWORD=sua-senha-de-app
```

3. Compile e execute o projeto:
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080/api`

### Frontend

1. Navegue até a pasta do cliente:
```bash
cd client
```

2. Instale as dependências:
```bash
npm install
```

3. Execute a aplicação:
```bash
npm start
```

O frontend estará disponível em `http://localhost:3000`

## API REST

### Endpoints Principais

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/api/restaurantes` | Lista todos os restaurantes |
| GET | `/api/restaurantes/disponiveis` | Lista restaurantes disponíveis para votação (não escolhidos na semana) |
| GET | `/api/profissionais` | Lista todos os profissionais |
| POST | `/api/votacao/votar` | Registra um voto e envia email de confirmação |
| GET | `/api/votacao/resultado` | Obtém o resultado da votação do dia |
| GET | `/api/votacao/votos/hoje` | Obtém os votos do dia |

## Testes

### Backend

Para executar os testes do backend:
```bash
cd server
mvn test
```

### Frontend

Para executar os testes do frontend:
```bash
cd client
npm test
```

## Arquitetura do Sistema de Mensageria

O sistema de mensageria utiliza o Apache Kafka como middleware de mensagens:

1. **Produtor**: Quando um usuário vota, o sistema gera uma mensagem com os detalhes do voto e envia para o tópico `voto-topic`.

2. **Consumidor**: Um serviço consome as mensagens do tópico e processa o envio de emails.

3. **Email**: Utiliza o Spring Mail para enviar emails de confirmação com os detalhes do voto.

Esta arquitetura proporciona:
- Desacoplamento entre o registro do voto e o envio de emails
- Tolerância a falhas (se o serviço de email estiver indisponível, os votos continuam sendo registrados)
- Escalabilidade (múltiplos consumidores podem processar mensagens em paralelo)

## Destaques do Projeto

- **Arquitetura**: Aplicação separada em camadas (controllers, services, repositories, models)
- **API RESTful**: Comunicação entre frontend e backend via API REST
- **Segurança**: Validações para evitar votos duplicados e restaurantes repetidos
- **Interface Responsiva**: Utilização do Material-UI para uma interface moderna e adaptável
- **Testes Automatizados**: Cobertura de testes unitários para validar o funcionamento
- **Mensageria**: Utilização do Kafka para comunicação assíncrona 