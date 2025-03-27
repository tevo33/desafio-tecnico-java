# Sistema de Votação de Restaurantes

Este sistema foi desenvolvido para auxiliar na escolha diária de restaurantes para almoço em equipe, baseado nas estórias descritas no desafio.

Nos últimos anos, iniciamos uma caminhada em direção a diversidade e a inclusão e após o lançamento do nosso Marco Ético temos agora um norteador dos princípios que irão trilhar a nossa trajetória.

1. **Votação de Restaurantes**
   - Cada profissional pode votar apenas uma vez por dia
   - O sistema impede que o mesmo restaurante seja escolhido mais de uma vez na semana
   - O resultado da votação é disponibilizado antes do meio-dia

## Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database (banco de dados em memória)
- Lombok
- Maven
- JUnit 5 (para testes)

### Frontend
- React 18
- TypeScript
- Material-UI
- Axios
- React Router

## Pré-requisitos

- Java 17 ou superior
- Node.js 18 ou superior
- Maven
- npm ou yarn

## Executando a Aplicação

### Backend

1. Navegue até a pasta do servidor:
```bash
cd server
```

2. Compile e execute o projeto:
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
| POST | `/api/votacao/votar` | Registra um voto |
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

## Destaques do Projeto

- **Arquitetura**: Aplicação separada em camadas (controllers, services, repositories, models)
- **API RESTful**: Comunicação entre frontend e backend via API REST
- **Segurança**: Validações para evitar votos duplicados e restaurantes repetidos
- **Interface Responsiva**: Utilização do Material-UI para uma interface moderna e adaptável
- **Testes Automatizados**: Cobertura de testes unitários para validar o funcionamento