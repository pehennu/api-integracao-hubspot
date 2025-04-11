# Desafio Meetime - HubSpot Integration

**API REST em Java** para integrar com a **API do HubSpot**, implementando autenticação via **OAuth 2.0**, mais especificamente com o fluxo de **authorization code flow**, a implementação de endpoint de integração com a API e o recebimento de notificações via **webhooks**.

## Pré-requisitos

- Java JDK 11 ou superior
- Maven
- Conta de desenvolvedor no HubSpot

## **Configuração**
1. Clone o repositório.
2. Configure as variáveis de ambiente.
3. Execute o projeto.
   ```
## Endpoints

| Método | Rota                      | Descrição                                                                          |
|--------|---------------------------|------------------------------------------------------------------------------------|
| GET    | `/api/auth/authorize-url` | Retorna a URL de autorização.                                                      |
| GET    | `/api/auth/callback`      | Recebe o código de autorização fornecido pelo HubSpot e retorna o token de acesso. |
| POST   | `/api/contact/create`     | Cria um contato baseado no json enviado.                                           |
| GET    | `/api/contact/list`       | Retorna uma lista de contatos previamente criados.                                 |

## Como Testar a Aplicação
(Utilize insomnia ou postman para ter mais facilidade)

1. **Gere o Token de Acesso**
    - Acesse /auth/authorize para obter a URL de autorização e o código que será redirecionado para /auth/callback.
    - Use o código retornado para obter o token em /auth/callback.
2. **Crie um Contato**
    - Envie uma requisição POST para /api/contact/create com o token no header Authorization e os dados do contato no body (JSON).
   {
     "properties": {
       "firstname": "Pedro",
       "lastname": "Nunes",
       "email": "nunesp@exemplo.com"
     }
   }
3. **Retorne uma lista d contatos**
    - Envie uma requisição GET para /api/contact/list com o token no header Authorization.

## Swagger
- Com o projeto rodando, acesse a documentação via swagger http://localhost:8080/swagger-ui.html

## Decisões Técnicas

Durante o desenvolvimento da integração com a **API do HubSpot**, tomei algumas decisões, em geral, escolhi pelas ferramentes em suas versões mais recentes e estáveis:

- **Java 17 e Spring Boot**: Utilizei Java 22 por ser uma versão atual e estável
- **Maven**: Adotei do Maven para gerenciamento de dependências e automação do build.
- **Dependências Importantes**:
   - **Spring Boot Security** e **Spring Boot Web**: Para garantir a segurança e criar a estrutura de uma API RESTful.
   - **WebClient e Spring WebFlux**: Utilizados para realizar requisições HTTP assíncronas e não bloqueantes, melhorando a escalabilidade e a performance.
   - **Lombok**: Para reduzir o código boilerplate, tornando o código mais limpo e legível.
   - **Jackson Databind**: Para manipulação de dados JSON, essencial para interagir com a API do HubSpot.
- **Gerenciamento de Tokens**: Criei uma classe específica para gerenciar o token de acesso. Essa classe armazena e reutiliza o token durante as requisições subsequentes, evitando a necessidade de reautenticação a cada solicitação.
- **Segurança**: Garantir a segurança dos dados foi uma prioridade, usando OAuth 2.0 e cabeçalhos de autorização.
- **Spring Cloud**: Adotei o **Spring Cloud** para facilitar a integração com arquiteturas distribuídas, caso seja necessário expandir a aplicação no futuro.

Essas decisões visam otimizar a segurança, a escalabilidade e a simplicidade do sistema.

## Melhorias Futuras

Abaixo estão algumas possíveis melhorias para o futuro:

- **Testes**:  Implementar testes unitários e de integração com a API do HubSpot.
- **Melhorar Gerenciamento dos Tokens**: Gerenciar melhor os tokens com permanência em Banco de Dados ou cache (como Redis). Isso permitiria a aplicação lidar com tokens expirados de maneira mais eficiente e evitar reautenticações desnecessárias.
- **Melhorias no Gerenciamento de Erros**: Embora o sistema já trate de erros de forma básica, poderia implementar uma estratégia mais robusta de gerenciamento de erros, com códigos de status HTTP mais detalhados e mensagens de erro mais informativas.
