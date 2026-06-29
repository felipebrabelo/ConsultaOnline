# ConsultaOnline

Sistema web para agendamento de consultas online com profissionais liberais. A aplicação permite cadastro e gerenciamento de clientes, profissionais, currículos em PDF e consultas, além de listagem pública de profissionais por especialidade. Clientes podem agendar consultas e profissionais podem acompanhar seus atendimentos e links de videoconferência.

O projeto foi desenvolvido com Spring Boot, Spring MVC, Spring Data JPA, Spring Security, Thymeleaf, MySQL, JavaScript e CSS.

## Requisitos

- Java 17 ou superior.
- MySQL em execução.
- Maven ou Maven Wrapper do projeto.

O projeto inclui Maven Wrapper. Em Linux/macOS:

```bash
./mvnw spring-boot:run
```

Em Windows:

```bat
mvnw.cmd spring-boot:run
```

## Banco De Dados

SGBD utilizado: MySQL.

Nome do banco de dados:

```text
ConsultaOnline
```

A aplicação usa a configuração:

```properties
spring.datasource.url = jdbc:mysql://localhost:3306/ConsultaOnline?createDatabaseIfNotExist=true
spring.datasource.username = aluno
spring.datasource.password = aluno
spring.jpa.hibernate.ddl-auto = create-drop
```

Script SQL mínimo para preparar o usuário local:

```sql
CREATE DATABASE IF NOT EXISTS ConsultaOnline;
CREATE USER IF NOT EXISTS 'aluno'@'localhost' IDENTIFIED BY 'aluno';
GRANT ALL PRIVILEGES ON ConsultaOnline.* TO 'aluno'@'localhost';
FLUSH PRIVILEGES;
```

Observação: `create-drop` recria as tabelas ao iniciar a aplicação e remove as tabelas ao encerrar. Isso facilita testes, mas os dados não são persistidos entre execuções.

## Execução Local

1. Inicie o MySQL.

2. Crie o banco e usuário, se ainda não existirem:

```bash
mysql -u root -p
```

Execute o script SQL mostrado acima.

3. Rode a aplicação:

```bash
./mvnw spring-boot:run
```

4. Acesse:

```text
http://localhost:8080
```

## Dados Populados

Os dados iniciais são populados no `CommandLineRunner` da classe `AgendamentoOnlineApplication`.

Usuário administrador:

| Papel | Email | Senha | CPF |
| --- | --- | --- | --- |
| Administrador | admin@email.com | admin | 11122233344 |

Clientes:

| Papel | Nome | Email | Senha | CPF |
| --- | --- | --- | --- | --- |
| Cliente | Antonio Fagundes | antonio@email.com | antonio123 | 12345678900 |
| Cliente | Fátima Fagundes | fatimaf@email.com | fatima321 | 98765432100 |
| Cliente | Sávio Borges | sbg@email.com | borges123 | 00099911123 |

Profissionais:

| Papel | Nome | Email | Senha | CPF | Especialidade |
| --- | --- | --- | --- | --- | --- |
| Profissional | Rafael Almeida Ferreira | rafael.almeida.psi@email.com | rafael123 | 12300012300 | Psicologia |
| Profissional | Mariana Oliveira Costa | mariana.costa.adv@email.com | mariana123 | 43251986700 | Advocacia |

Também são cadastradas consultas iniciais entre esses clientes e profissionais, com alguns links de videoconferência fictícios.

## Papéis E Acessos

- Visitante: pode visualizar a listagem pública de profissionais e filtrar por especialidade.
- Administrador: pode gerenciar clientes e profissionais.
- Cliente: pode agendar consultas e visualizar suas consultas.
- Profissional: pode visualizar suas consultas.

O login é feito por email e senha.

## Endpoints REST

Os endpoints `/api/**` não exigem autenticação.

Clientes:

```text
POST   /api/iclientes
POST   /api/clientes
GET    /api/clientes
GET    /api/clientes/{id}
PUT    /api/clientes/{id}
DELETE /api/clientes/{id}
```

Profissionais:

```text
POST   /api/profissionais
GET    /api/profissionais
GET    /api/profissionais/{id}
GET    /api/profissionais/especialidades/{nome}
PUT    /api/profissionais/{id}
DELETE /api/profissionais/{id}
```

Consultas:

```text
GET /api/consultas
GET /api/consultas/{id}
GET /api/consultas/clientes/{id}
GET /api/consultas/profissionais/{id}
```

Os endpoints da API foram checados utilizando postman e os resultados estão disponíveis em docs/postman.

## Validações

O projeto valida os principais campos cadastrados/editados:

- email obrigatório, formato válido e único;
- CPF obrigatório, com 11 dígitos e único;
- senha obrigatória no cadastro e opcional na edição;
- nome com tamanho mínimo e máximo;
- telefone com 10 ou 11 dígitos;
- data de nascimento no passado;
- especialidade obrigatória;
- currículo em PDF no cadastro de profissional pela interface web;
- consulta em data futura e iniciando em hora cheia;
- impedimento de consulta duplicada para o mesmo cliente ou profissional no mesmo horário.

## Detalhes Importantes

### Remoção Lógica

Clientes e profissionais não são apagados fisicamente do banco. A remoção é lógica: o campo `ativo` é alterado para `false`.

Consequência: emails e CPFs removidos continuam existindo no banco. Como há validação de unicidade e constraints únicas, não é possível recadastrar outro cliente ou profissional usando o mesmo email ou CPF de um registro removido.

### Email Dummy

O envio de email é simulado. Ao agendar uma consulta, o sistema gera um link fictício de videoconferência e registra no console uma mensagem para o cliente e outra para o profissional.

### Link De Videoconferência Dummy

O link é gerado automaticamente pelo `LinkConferenciaService` no formato:

```text
https://meet.google.com/abc-defg-hij
```

Esse link não cria uma reunião real; ele existe para simular o fluxo exigido pelo sistema.

### Currículo Na API

A interface web envia currículo como arquivo PDF usando `multipart/form-data`. Na API REST, considera-se suficiente enviar o campo `data` do currículo como `byte[]` em Base64 dentro do JSON.

### Internacionalização

A aplicação possui mensagens em português e inglês:

```text
messages.properties
messages_pt_BR.properties
messages_en_US.properties
```

O idioma pode ser trocado clicando nos botões pt e en na navbar.
```
