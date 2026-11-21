# TODO List - Acelera ZG Trilha Java

Projeto desenvolvido como parte do desafio técnico do Acelera ZG. Consiste em um gerenciador de tarefas interativo
executado via terminal (CLI). O sistema foi projetado com foco em código limpo, separação de responsabilidades (Clean
Architecture) e versionamento semântico.

## Tecnologias Utilizadas

* **Java** (Vanilla, sem uso de frameworks externos)
* **Gradle** (Gerenciamento de dependências e build)
* **Git e GitHub** (Controle de versão seguindo o padrão SemVer)

## Funcionalidades (CRUD Completo)

* **Adicionar Tarefa:** Criação de tarefas (nascem com status `TODO` por padrão).
* **Listar Tarefas:** Visualização de todas as tarefas cadastradas.
* **Atualizar Tarefa:** Alteração de atributos e evolução do status (`TODO`, `DOING`, `DONE`).
* **Remover Tarefa:** Exclusão de tarefas da memória.
* **Rebalanceamento Automático:** Tarefas são ordenadas automaticamente por prioridade (1 a 5) e, em caso de empate,
  pela data de término mais próxima.
* **Filtros Avançados:** Busca de tarefas específicas por **Categoria**, **Prioridade** ou **Status**.

## Como Executar

### Via IDE (Recomendado)

1. Abra a pasta do projeto em sua IDE de preferência (IntelliJ IDEA, Eclipse, etc.).
2. Aguarde a sincronização do Gradle.
3. Navegue até a classe principal localizada em `src/main/java/.../Main.java`.
4. Execute o método `main`.
5. Interaja com o menu do sistema diretamente pelo console da IDE.

### Via Terminal

1. Abra o terminal na raiz do projeto.
2. Compile e execute o projeto utilizando o Gradle Wrapper:
    * **Windows:** `gradlew run`
    * **Linux/Mac:** `./gradlew run`

## Arquitetura e Solução

Para garantir um código escalável e de fácil manutenção, o projeto foi estruturado seguindo o princípio de Separação de
Preocupações (inspirado no padrão MVC):

* **View (`MenuTerminalView`):** Responsável exclusivamente pela interação com o usuário, leitura de dados via teclado
  (`Scanner`) e exibição de mensagens no console. Não contém regras de negócio.
* **Service/Controller (`GerenciadorDeTarefas`):** O "cérebro" da aplicação. Isola toda a lógica de negócio, manipulação
  da lista, rebalanceamento por prioridade e filtros.
* **Model (`Tarefa`, `Status`):** Representam os dados e o estado da aplicação.

* ## Atualização: Testes Unitários do CRUD

Foram implementados testes unitários utilizando **Spock Framework** e **Groovy** para validar as operações de **CRUD (
Create, Read, Update e Delete)** do sistema de gerenciamento de tarefas.

### Principais Alterações

* **Create:** Teste para verificar a criação e inserção de novas tarefas na lista.
* **Read:** Teste para validar a recuperação das tarefas cadastradas.
* **Update:** Teste para verificar a atualização de uma tarefa existente.
* **Delete:** Teste para validar a remoção de uma tarefa cadastrada.
* **Testes Unitários com Spock:** As operações foram testadas de forma independente, verificando o comportamento
  esperado de cada ação do CRUD.

* ## Atualização: Sistema de Alarmes para Tarefas

O projeto foi atualizado com a implementação de um sistema de alarmes para as tarefas, permitindo configurar um período
de aviso durante a criação de cada tarefa.

### Principais Alterações

* **Configuração de Alarme:** Adicionada a opção de configurar um alarme durante o cadastro de uma nova tarefa,
  permitindo definir o tempo para o aviso.
* **Alarmes Personalizados:** Cada tarefa pode possuir seu próprio horário de alarme, de acordo com o período definido
  pelo usuário.
* **Agendamento de Avisos:** Implementado o uso de `ScheduledExecutorService` no `GerenciadorTarefasService` para
  disparar os avisos automaticamente quando o período configurado chegar.
* **Exibição do Alarme:** O horário configurado para o alarme passou a ser exibido junto com os dados da tarefa ao
  listar as tarefas.
* **Tratamento de Datas:** Melhorado o tratamento das datas de término, permitindo a entrada no formato `dd/MM/yyyy` e
  também sem as barras.

## Atualização: Frontend desenvolvido para a trilha de JavaScript.

A interface foi criada para permitir o gerenciamento das tarefas de forma simples e organizada. As tarefas continuam
armazenadas localmente no navegador; há uma API de backend independente exclusivamente para o envio de notificações por
e-mail.

## Tecnologias utilizadas

* **HTML5** — estrutura da aplicação
* **CSS3** — estilização e responsividade
* **JavaScript** — lógica e interação da aplicação
* **LocalStorage** — persistência dos dados no navegador

## Funcionalidades

* Cadastro de tarefas
* Listagem de tarefas
* Edição de tarefas
* Remoção de tarefas
* Persistência das tarefas com LocalStorage
* Definição de status:

    * `TODO`
    * `DOING`
    * `DONE`
* Alteração de status para múltiplas tarefas
* Filtros por:

    * Categoria
    * Prioridade
    * Status
* Combinação de filtros
* Configuração de horário para alarme
* Mensagens de confirmação para as ações
* Formatação da data para o padrão brasileiro
* Modo de alto contraste com preferência salva no LocalStorage
* Notificações por e-mail ao criar uma tarefa ou concluí-la

## Estrutura

```text
src/main/java/br/com/zg/todolist/frontend/
├── index.html
├── css/style.css
├── images/
└── js/
    ├── app.js
    ├── tarefas.js
    ├── alarmes.js
    ├── notificacoesEmail.js
    ├── filtros.js
    └── ui.js
```

## Organização do JavaScript

O JavaScript foi dividido em módulos para facilitar a manutenção:

### `app.js`

Responsável por inicializar e integrar os módulos da aplicação.

### `tarefas.js`

Responsável pelas funcionalidades relacionadas às tarefas:

* Cadastro
* Listagem
* Edição
* Remoção
* LocalStorage
* Alarmes
* Alteração de status em múltiplas tarefas

### `filtros.js`

Responsável pelos filtros de:

* Categoria
* Prioridade
* Status

Também realiza a combinação dos filtros e informa a quantidade de tarefas encontradas.

### `ui.js`

Responsável pelos elementos gerais da interface:

* Navegação entre as seções
* Mensagens de sucesso
* Modo de alto contraste

## Atualização:

1. Persistência de tarefas no LocalStorage

As tarefas cadastradas são armazenadas no LocalStorage do navegador, permitindo que os dados permaneçam salvos mesmo
após recarregar ou fechar a página.

2. Alteração de status de múltiplas tarefas

É possível selecionar várias tarefas ao mesmo tempo e definir um novo status para todas as tarefas selecionadas. Os
status disponíveis são:

TODO
DOING
DONE

Após a alteração, o novo status é atualizado e salvo no LocalStorage.

## Desafio de Regex

Foi implementada uma etapa de validação dos dados do frontend da TODO List utilizando Expressões Regulares (Regex).

As validações desenvolvidas foram:

* Validação da prioridade da tarefa, aceitando valores de 1 a 5.
* Validação do formato da data de término.
* Verificação para impedir o cadastro de uma data de término anterior à data atual.

As funções de validação foram separadas no arquivo `regex/validacao.js`, mantendo as regras de validação organizadas e
separadas da lógica principal da aplicação.

## Atualização: Refatoração com Clean Code

O projeto passou por uma extensa refatoração focada na qualidade, legibilidade e manutenibilidade do código, aplicando
os princípios do **Clean Code** (DRY, Fail Fast, Nomes Autoexplicativos, Funções Pequenas e Regra do Escoteiro) em todo
o ecossistema:

* **Backend (Java):**
    * Implementação da regra do *Fail Fast* com lançamento de exceções claras no serviço.
    * Substituição de laços de repetição tradicionais por **Java Streams API**, tornando as buscas mais declarativas.
    * Extração de validações e regras de negócio para métodos privados menores, respeitando a Responsabilidade Única.
    * Otimização da classe `Tarefa` com a extração de formatadores de data para constantes (melhoria de performance e
      memória).

* **Frontend (JavaScript):**
    * Divisão de "funções deus" (como a de cadastro geral) em funções menores e de escopo único.
    * Centralização da manipulação do DOM e eventos, eliminando repetição de código na aplicação de filtros e
      renderização de cards.
    * Tratamento seguro para garantir que eventos não quebrem a aplicação caso os elementos ainda não estejam
      carregados.

* **Interface e UI (HTML/CSS):**
    * **Acessibilidade:** Inclusão de atributos semânticos como `aria-live="polite"` para suporte a leitores de tela e
      correção das tipagens de botões (`type="button"`).
    * **CSS Limpo e DRY:** Uso intensivo de Variáveis CSS (`:root`) para a paleta de cores. O arquivo de estilo foi
      reduzido drasticamente, permitindo que a troca para o modo "Alto Contraste" ocorra apenas reatribuindo variáveis,
      sem repetição de blocos inteiros.

## Atualização: Refatoração com SOLID

* Single Responsibility Principle (SRP): Extração da lógica de tempo e threads para a nova classe AlarmeService. O
  GerenciadorTarefas passou a cuidar exclusivamente das regras de negócio e do CRUD de tarefas, deixando de acumular a
  função de orquestrar notificações.

* Open/Closed Principle (OCP): Remoção dos múltiplos métodos de busca engessados (como listarPorStatus ou
  listarPorCategoria). Foi implementado o método filtrarTarefas (Predicate<Tarefa>), permitindo que a aplicação crie
  novos critérios de busca dinamicamente sem nunca precisar alterar ou abrir o código da classe gerenciadora.

* Interface Segregation Principle (ISP): Criação de contratos focados e específicos (IGerenciadorTarefas e
  IAlarmeService), garantindo que as classes consumidoras assinem e utilizem apenas os comportamentos que realmente
  necessitam.

* Dependency Inversion Principle (DIP): Remoção das instâncias diretas (new) de dentro das classes de serviço e
  visualização. O MenuTerminal e o GerenciadorTarefas agora dependem de abstrações (Interfaces) injetadas via construtor
  pela classe central Main.

* Desacoplamento de Entidades: A classe Tarefa parou de gerenciar categorias como simples Strings. Ela foi refatorada
  para receber um objeto autônomo Categoria diretamente em seu construtor, fortalecendo a coesão do modelo de domínio e
  permitindo evolução independente das categorias.

## Atualização - Refatoração com Design Patterns

A arquitetura foi redesenhada utilizando 6 padrões de projeto, divididos em três categorias:

### Criacionais

1. **Builder (`TarefaBuilder`):** Substituiu construtores extensos (*Telescoping Constructor*) por uma interface
   fluente, facilitando a montagem de tarefas com atributos opcionais.
2. **Factory Method (`TarefaFactory` e `CategoriaFactory`):** Removeu o acoplamento da View e dos Services com a
   instanciação direta (`new`). A criação de objetos agora é responsabilidade exclusiva das fábricas.

### Estruturais

3. **Flyweight (`CategoriaFactory`):** Implementação de cache (Mapa) para objetos do tipo `Categoria`. Solicitações de
   categorias com o mesmo nome retornam a mesma instância em memória, otimizando recursos.

### Comportamentais

4. **State (`Status`):** Encapsulou a regra de progressão de status (TODO -> DOING -> DONE) no próprio enum, eliminando
   condicionais (`if/else`) nos serviços e respeitando o princípio Open/Closed (OCP).
5. **Strategy (`EstrategiaOrdenacao`):** Permitiu a injeção dinâmica da regra de ordenação e filtragem das tarefas no
   `GerenciadorTarefasService`, tornando o algoritmo intercambiável em tempo de execução.
6. **Observer (`AlarmeObserver`):** Desacoplou a lógica de notificação do `AlarmeService`. O serviço agora atua apenas
   como *Subject*, enquanto os *Observers* (Console, Som, Popup) reagem aos disparos de forma independente.

## Testes Unitários

A aplicação conta com uma suíte de testes robusta (100% verde), incluindo o uso de Mocks para isolar componentes. Foram
implementados testes específicos para comprovar o funcionamento mecânico dos padrões **Flyweight**, **State**,
**Strategy** e **Observer**.

# Atualização - Refatoração Arquitetural (MVC)

O objetivo principal desta arquitetura é garantir alta coesão, baixo acoplamento e preparar o terreno para uma futura
migração para frameworks robustos, como o Spring Boot.

## Estrutura e Arquitetura MVC

A aplicação foi fisicamente separada em ambientes e pacotes com responsabilidades estritas:

* **Frontend (View Web):**
* Diretório `frontend/` com a implementação em Vanilla JavaScript, HTML e CSS.
* Opera de forma independente como o "V" do MVC para a web.
* **Backend (Java):**
* **`view`:** Contém a `MenuTerminalView`, uma interface secundária de console para testes locais. **Regra de Ouro
  aplicada:** A View nunca interage diretamente com o Model, toda comunicação é feita via Controller.
* **`controller`:** Camada responsável por receber as requisições da View, rotear os comandos e orquestrar as ações.
  Implementada por `TarefaController` e `CategoriaController`.
* **`model`:** Representa o coração da aplicação. Contém todas as regras de negócio, validações, entidades (`Tarefa`,
  `Categoria`, `Status`), serviços (`GerenciadorTarefasService`, `AlarmeService`) e a implementação dos Design Patterns.

Reestruturação de Pacotes (Separação Física): O backend foi reorganizado em pacotes independentes e paralelos (model,
controller e view). Corrigiu-se o aninhamento incorreto onde o Controller estava dentro do Model.

Criação dos Controllers (CRUD): Foram implementados o TarefaController e o CategoriaController para centralizar o
recebimento das requisições e orquestrar as chamadas para os serviços do Model.

Isolamento Total da View (MenuTerminalView): Foram removidas todas as chamadas diretas aos serviços
(GerenciadorTarefasService) e fábricas (CategoriaFactory) que existiam dentro da View. A interface passou a se comunicar
estrita e exclusivamente através dos Controllers.

Resolução de Ambiguidade e Injeção nos Testes: Os testes em Groovy (MenuTerminalTest e TarefaControllerTest) foram
refatorados para respeitar a nova injeção de dependências dos Controllers, corrigindo erros de sobrecarga de métodos
(assertDoesNotThrow) e garantindo cobertura 100% verde com Mocks isolados.

## Atualização - Notificações por e-mail com Resend

A versão atual integra o TODO List ao **Resend**, serviço de envio de e-mails. O destinatário é configurado no backend.
A criação e as alterações das tarefas continuam salvas no navegador mesmo se o envio falhar.

### Configuração do serviço

1. Crie uma conta no [Resend](https://resend.com), gere uma chave de API e configure um endereço remetente permitido
   pelo serviço (em geral, um domínio verificado).
2. Na raiz do projeto, configure as variáveis de ambiente no terminal que iniciará a API:

   ```bash
   export RESEND_API_KEY='sua-chave-do-resend'
   export TODO_EMAIL_FROM='TODO List <notificacoes@seu-dominio-verificado.com>'
   export TODO_EMAIL_TO='seu-email@exemplo.com'
   export TODO_EMAIL_API_TOKEN="$(openssl rand -hex 32)"
   export TODO_EMAIL_ALLOWED_ORIGIN='http://localhost:5500'
   ```

   Guarde o valor gerado em `TODO_EMAIL_API_TOKEN` para informá-lo na interface. Não compartilhe nem publique a chave do
   Resend ou o token.

3. Inicie o backend:

   ```bash
   ./gradlew executarApiNotificacoesEmail
   ```

   Por padrão, a API escuta em `127.0.0.1:8081`. O remetente e o destinatário são definidos no servidor; a chave do
   Resend não é enviada ao navegador.

4. Em outro terminal, sirva o frontend:

   ```bash
   python3 -m http.server 5500 --directory src/main/java/br/com/zg/todolist/frontend
   ```

   Acesse `http://localhost:5500`, abra **Configurar e-mail** no menu e informe o token `TODO_EMAIL_API_TOKEN`.

### Como foi implementada

- O backend Java expõe `POST /api/notificacoes/email`, valida o token Bearer, a origem e os dados recebidos, e encaminha
  os e-mails ao Resend.
- A chave privada do Resend e os endereços de remetente e destinatário ficam configurados no backend por variáveis de
  ambiente.
- No frontend, `notificacoesEmail.js` faz as chamadas à API; `tarefas.js` dispara avisos ao criar, editar, concluir e
  nos prazos configurados.
- As tarefas e o registro de alertas enviados ficam no `localStorage`; por isso, os lembretes agendados dependem de a
  página estar aberta ou ser acessada.

O token informado na página é mantido somente na memória enquanto ela estiver aberta; após atualizar ou fechar a página,
será necessário informá-lo novamente. Como o projeto ainda não tem autenticação de usuários, essa configuração serve
para estudo local e não deve ser publicada: um token Bearer utilizado pelo navegador pode ser extraído por quem acessa a
aplicação. Para disponibilização pública, implemente autenticação de usuário e controle de acesso no backend. CORS não
substitui autenticação. Em ambiente externo, a API também deve ser servida por HTTPS.

A API recebe `POST /api/notificacoes/email` e envia estes e-mails:

- **Tarefa criada:** imediatamente após cadastrar uma tarefa.
- **Tarefa editada:** ao salvar alterações em uma tarefa; se a alteração mudar o status para `DONE`, é enviado o aviso
  de conclusão.
- **Tarefa concluída:** quando o status muda para `DONE`, inclusive em alterações em lote.
- **Tarefa a fazer em três dias:** quando uma tarefa com status `TODO` vence em três dias; é enviado uma vez por tarefa
  e data.
- **Prazo para hoje:** quando uma tarefa ainda não concluída vence no dia da verificação; é enviado uma vez por tarefa e
  data.
- **Prazo próximo:** um dia antes da data de término de uma tarefa ainda não concluída; é enviado uma vez por tarefa e
  data.
- **Tarefa atrasada:** na primeira verificação após o prazo de uma tarefa ainda não concluída; é enviado uma vez por
  tarefa e data.
- **Relatório semanal:** na primeira verificação de segunda-feira com tarefas cadastradas; lista as tarefas e seus
  parâmetros (descrição, status, prazo, prioridade, categoria e alarme), uma vez por semana.

As verificações de prazo e o relatório semanal acontecem quando o token é configurado, quando as tarefas são salvas e a
cada hora enquanto a página está aberta. O relatório só é enviado na segunda-feira; se a página permanecer fechada
durante todo esse dia, ele não será enviado naquela semana. O histórico de alertas já enviados é guardado no
`localStorage` do navegador. Como as tarefas e o agendamento ainda são locais, nenhum alerta é garantido com a página
fechada; ao abrir a página, os atrasos pendentes podem ser enviados. As notificações de prazo e atraso se aplicam a
tarefas com status diferente de `DONE`.

Para notificações individuais, o corpo usa `evento`, `tituloTarefa`, `status` e `dataTermino` (formato `AAAA-MM-DD`). O
relatório usa `evento: "RELATORIO_SEMANAL"` e uma lista `tarefas` com os parâmetros das atividades.

## Atualização - Notificações via Telegram

A nova versão do TODO List conta com integração ao **Telegram**, permitindo receber notificações automáticas sobre
eventos das tarefas:

- **Tarefa criada:** avisa quando uma nova tarefa é cadastrada.
- **Tarefa editada:** informa quando uma tarefa existente é atualizada.
- **Prazo próximo:** envia um lembrete quando uma tarefa está com vencimento previsto para o dia seguinte.

### Detalhes da implementação:

Desenvolvimento de uma API Java independente para receber e processar as solicitações de notificação.

Integração com a Telegram Bot API, utilizando um bot para realizar o envio das mensagens.

Criação de um manipulador HTTP para receber requisições POST, validar os dados recebidos e encaminhar as notificações.

Implementação de classes para gerar e formatar mensagens de acordo com cada evento.

Integração do frontend com a API de notificações por meio de requisições HTTP.

Configuração das credenciais do bot por variáveis de ambiente (TELEGRAM_BOT_TOKEN e TELEGRAM_CHAT_ID).

Tratamento de erros durante a comunicação e o envio das mensagens.

## Suíte de Testes

O projeto conta com uma suíte de testes unitários estruturada com **Spock Framework** e **Groovy**, integrada ao backend
Java. Os testes cobrem filtros combinados, transições individuais de status e gatilhos de notificações isolados com
mocks.

Para executar toda a suíte no terminal, use:

```bash
./gradlew test
```

### Requisitos Opcionais

- **Skill de Code Review:** criação da diretriz customizada em `skills/code-review/SKILL.md` e aplicação prática na
  revisão da suíte de testes.
- **Ampliação de Cobertura & Reuso de Skill:** implementação de testes unitários para o código legado da
  `CategoriaFactory`, utilizando exclusivamente a skill de especificação como guia e vinculando a entrega a uma Issue.
- **Integração MCP do GitHub:** uso de ferramentas de contexto do repositório para contornar limitações técnicas e
  manter a rastreabilidade de Issues e Pull Requests.
- **Comparação de Modelos:** avaliação comparativa de Code Review entre o agente principal e um modelo alternativo,
  documentada em `RELATORIO.md`.

# Atualização — Backend com Spring Framework e API REST

O TODO-List recebeu uma atualização arquitetural em seu backend, passando a utilizar o **Spring Framework** e a
arquitetura de **APIs RESTful**.

A nova implementação permite que as operações de gerenciamento de tarefas sejam realizadas por meio de requisições HTTP,
com persistência em banco de dados PostgreSQL utilizando JPA/Hibernate.

Nesta etapa, o desenvolvimento está concentrado exclusivamente no backend. Os endpoints foram preparados para permitir
uma futura integração com o frontend.

## Tecnologias utilizadas

| Tecnologia                     | Finalidade                                |
|--------------------------------|-------------------------------------------|
| Java 21                        | Linguagem de programação                  |
| Spring Boot 4.2.0              | Configuração e inicialização da aplicação |
| Spring Web MVC                 | Desenvolvimento dos endpoints REST        |
| Spring Data JPA                | Camada de persistência                    |
| Hibernate                      | Mapeamento objeto-relacional (ORM)        |
| PostgreSQL                     | Banco de dados relacional                 |
| Gradle 9.6.0                   | Gerenciamento de dependências e build     |
| Jakarta Validation             | Validação dos dados recebidos             |
| Springdoc OpenAPI / Swagger UI | Documentação e exploração da API          |
| JUnit 6                        | Testes automatizados                      |
| Mockito                        | Simulação de dependências nos testes      |
| MockMvc                        | Testes dos endpoints HTTP                 |

## Arquitetura e padrões utilizados

A nova implementação segue uma organização baseada em MVC, com separação de responsabilidades entre as camadas.

**Controller:** recebe as requisições HTTP, encaminha as operações ao Service e retorna as respostas da API.

**Service:** concentra as regras de negócio e coordena as operações realizadas sobre as tarefas.

**Repository:** utiliza Spring Data JPA para disponibilizar as operações de persistência no PostgreSQL.

**Model:** representa as entidades de domínio `Tarefa` e `Categoria`, mapeadas para as tabelas correspondentes.

**DTO (Data Transfer Object):** define as estruturas de entrada e saída da API, evitando a exposição direta das
entidades JPA nas respostas HTTP.

Também são utilizados:

- **Builder:** construção de objetos `Tarefa`.
- **Dependency Injection:** gerenciamento das dependências pelo Spring.
- **Repository Pattern:** abstração do acesso aos dados.
- **Transactional:** gerenciamento das transações de persistência.
- **Optimistic Locking:** controle de concorrência por meio de `@Version`.

A implementação Spring foi organizada separadamente do backend legado, preservando os componentes anteriores e
compartilhando os modelos de domínio.

## Estrutura do backend Spring

```text
backend/
├── model/
│   ├── Categoria.java
│   ├── Status.java
│   └── Tarefa.java
│
└── spring/
    ├── config/
    │   └── OpenApiConfig.java
    │
    ├── controller/
    │   ├── TarefaRestController.java
    │   └── ApiExceptionHandler.java
    │
    ├── dto/
    │   ├── TarefaRequest.java
    │   ├── TarefaResponse.java
    │   └── ErroResponse.java
    │
    ├── repository/
    │   ├── TarefaRepository.java
    │   └── CategoriaRepository.java
    │
    └── service/
        ├── TarefaService.java
        └── CategoriaService.java
```

## Operações disponíveis na API

A API disponibiliza operações CRUD para o gerenciamento de tarefas.

| Método HTTP | Endpoint        | Descrição                           |
|-------------|-----------------|-------------------------------------|
| GET         | `/tarefas`      | Lista todas as tarefas              |
| GET         | `/tarefas/{id}` | Busca uma tarefa pelo identificador |
| POST        | `/tarefas`      | Cadastra uma nova tarefa            |
| PUT         | `/tarefas/{id}` | Atualiza uma tarefa existente       |
| DELETE      | `/tarefas/{id}` | Exclui uma tarefa                   |

### Códigos de resposta HTTP

| Código          | Descrição                          |
|-----------------|------------------------------------|
| 200 OK          | Operação realizada com sucesso     |
| 201 Created     | Tarefa cadastrada com sucesso      |
| 204 No Content  | Tarefa excluída com sucesso        |
| 400 Bad Request | Dados ou requisição inválidos      |
| 404 Not Found   | Tarefa ou categoria não encontrada |

## Persistência de dados

A aplicação utiliza PostgreSQL como banco de dados e Spring Data JPA com Hibernate para o mapeamento e a persistência
das entidades.

O banco utilizado nesta implementação é `todolist_bd`, que possui as tabelas:

- `categoria`: armazena as categorias das tarefas.
- `tarefa`: armazena os dados das tarefas e seu relacionamento com categorias.

As entidades utilizam geração automática de identificadores e controle de versão com `@Version`.

A configuração do Hibernate utiliza `ddl-auto=validate`, permitindo verificar a compatibilidade entre as entidades e a
estrutura existente do banco sem recriar ou modificar automaticamente as tabelas.

## Como executar o projeto

### Pré-requisitos

- JDK 21.
- PostgreSQL.
- IntelliJ IDEA ou outra IDE compatível com Gradle.

### 1. Clonar o repositório

```bash
git clone URL_DO_REPOSITORIO
```

Acesse a pasta do projeto:

```bash
cd NOME_DO_PROJETO
```

### 2. Preparar o banco de dados

Crie ou utilize o banco PostgreSQL `todolist_bd`.

As tabelas `categoria` e `tarefa` devem estar disponíveis conforme o mapeamento das entidades.

Caso ainda não tenha aplicado a migração de geração automática dos identificadores, execute o script:

```text
database/migrations/001_add_identity_to_categoria_e_tarefa.sql
```

### 3. Configurar as credenciais

A aplicação utiliza variáveis de ambiente para acessar o PostgreSQL:

```text
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
```

Configure essas variáveis na configuração de execução do IntelliJ IDEA.

### 4. Executar a aplicação

Pelo terminal:

```bash
./gradlew bootRun
```

Ou execute a classe principal pelo IntelliJ:

```text
br.com.zg.todolist.TodoListApplication
```

A API estará disponível em:

```text
http://localhost:8080
```

## Documentação da API

A documentação utiliza OpenAPI e Swagger UI, permitindo visualizar os endpoints, consultar os parâmetros e experimentar
as requisições diretamente pelo navegador.

**Observação:** Para acessar os endpoints e a documentação Swagger,
é necessário executar a aplicação localmente. A API estará disponível
em `http://localhost:8080` enquanto o servidor Spring Boot estiver ativo.

**Swagger UI:**

http://localhost:8080/swagger-ui.html

**OpenAPI JSON:**

http://localhost:8080/v3/api-docs

---

## Testes automatizados

O projeto utiliza JUnit e Mockito para verificar as regras de negócio e o comportamento dos controllers.

O MockMvc permite testar os endpoints HTTP sem a necessidade de iniciar um servidor web real.

Os testes estão organizados nas seguintes classes:

- `TarefaRestControllerTest`: verifica as respostas HTTP, os códigos de status e as validações dos endpoints.
- `TarefaServiceTest`: verifica as operações e regras de negócio relacionadas às tarefas.
- `CategoriaServiceTest`: verifica as operações de consulta de categorias.

---

**Observação:** o frontend permanece independente nesta atualização. O backend disponibiliza os endpoints REST
necessários para uma futura comunicação entre cliente e servidor.

## 👨‍💻 Autor

**Sóstenes Marques Maciel**