# Task Tracker CLI - Java 

Um gerenciador de tarefas simples que roda diretamente no terminal. Este projeto foi desenvolvido como um desafio de programação para praticar fundamentos de Java, manipulação de arquivos (I/O) e o formato JSON sem o uso de bibliotecas externas.

## Tecnologias e Conceitos Utilizados
- **Java 21** (NIO.2 para manipulação de arquivos).
- **Programação Orientada a Objetos** (Classes de modelo, Enums, Sobrecarga de construtores).
- **Manipulação de JSON Manual** (Parsing de strings e serialização sem Jackson/GSON).
- **Interface de Linha de Comando (CLI)** usando argumentos posicionais.

## Funcionalidades
O sistema permite realizar as seguintes operações:
- **Adicionar** tarefas com ID automático e timestamps.
- **Listar** todas as tarefas ou filtrar por status (`todo`, `in-progress`, `done`).
- **Atualizar** a descrição de uma tarefa existente.
- **Deletar** tarefas pelo ID.
- **Alterar Status** das tarefas para "Em progresso" ou "Concluído".

## Como Executar

### Pré-requisitos
- JDK 11 ou superior instalado.

### Compilação
No terminal, dentro da pasta do projeto, execute:
```bash
javac TaskTracker.java Task.java Status.java
Exemplos de Uso
Bash
# Adicionar uma nova tarefa
java TaskTracker add "Estudar Java NIO"

# Listar todas as tarefas
java TaskTracker list

# Listar apenas tarefas concluídas
java TaskTracker list done

# Atualizar a descrição de uma tarefa
java TaskTracker update 1 "Estudar Java NIO e Spring"

# Mudar status para em progresso
java TaskTracker mark-in-progress 1

# Deletar uma tarefa
java TaskTracker delete 1
Estrutura de Dados
As tarefas são persistidas em um arquivo chamado tasks.json na raiz do projeto, utilizando o seguinte formato:

JSON
{"id":1,"description":"Estudar Java","status":"TODO","createdAt":"2026-02-23T19:00:00","updatedAt":"2026-02-23T19:00:00"}
