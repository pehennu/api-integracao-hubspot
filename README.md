# Desafio Meetime - HubSpot Integration

**API REST em Java** para integrar com a **API do HubSpot**, implementando autenticação via **OAuth 2.0**, mais especificamente com o fluxo de **authorization code flow**, a implementação de endpoint de integração com a API e o recebimento de notificações via **webhooks**.

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter:

- Uma **[conta gratuita de desenvolvedor HubSpot](https://app.hubspot.com/signup-hubspot/developers?step=landing_page)**
- **[Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)** ou superior (o projeto está usando a versão 17)
- **[Maven 3.8](https://maven.apache.org/download.cgi)** ou superior (o projeto está usando a versão 3.9.9)
- **Spring Boot 3.4.x** ou superior (o projeto está usando a versão 3.4.4)

Você pode verificar as versões instaladas com os seguintes comandos:
```bash
$ java -version
$ mvn -version
```

## Instruções Para Executar a Aplicação

1. Clone o repositório:
   ```bash
   $ git clone https://github.com/RaquelAmbroziof/hubspot-integration.git
   ```
2. Navegue até o diretório da aplicação:
   ```bash
   $ cd hubspot-integration
   ```
3. Limpe e compile a aplicação:
   ```bash
   $ mvn clean install
   ```
4. Rode o projeto:
   ```bash
   $ mvn spring-boot:run
   ```
## Endpoints

| Método | Rota                      | Descrição                                                         |
|--------|---------------------------|-------------------------------------------------------------------|
| GET    | `/api/auth/authorize-url` | Retorna a URL de autorização.                                     |
| GET    | `/api/auth/callback`      | Recebe o código de autorização fornecido pelo HubSpot e retorna o token de acesso. |
| POST    | `/api/contact/create`     | Recebe o código de autorização fornecido pelo HubSpot e retorna o token de acesso. |

## Como Testar a Aplicação
(Utilize insomnia ou postman para ter mais facilidade)

1. Com a aplicação rodando, faça uma requisição GET para `http://localhost:8080/api/auth/authorize-url`. Isso gerará a URL de autorização.
2. Crie uma nova conta ou faça login para autorizar.
3. Após autorizar, o HubSpot redirecionará você para uma página onde o código de autorização será exibido. Copie o código de autorização.
5. Faça uma requisição GET para `http://localhost:8080/api/auth/callback?code={seuCodigo}`, substituindo `{seuCodigo}` pelo código copiado no passo anterior.
6. O HubSpot retornará o token de acesso. Copie o token em seguida, faça uma requisição POST para `http://localhost:8080/api/contact/create` para criar um novo contato.
   {
   "properties": {
   "firstname": "João",
   "lastname": "Silva",
   "email": "joao@exemplo.com"
   }
   }

7. Inclua o token de acesso no cabeçalho da requisição.
8. Inclua o body e envie a requisição.

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
- **Documentação da API**: A documentação da API poderia ser melhorada usando ferramentas como Swagger/OpenAPI com a adição de exemplos de requisições e respostas, além de uma descrição mais detalhada de cada endpoint.
- **Melhorar Gerenciamento dos Tokens**: Gerenciar melhor os tokens com permanência em Banco de Dados ou cache (como Redis). Isso permitiria a aplicação lidar com tokens expirados de maneira mais eficiente e evitar reautenticações desnecessárias.
- **Melhorias no Gerenciamento de Erros**: Embora o sistema já trate de erros de forma básica, poderia implementar uma estratégia mais robusta de gerenciamento de erros, com códigos de status HTTP mais detalhados e mensagens de erro mais informativas.
