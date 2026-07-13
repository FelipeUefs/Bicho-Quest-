# 🎓 BixoQuest: Da Matrícula à Formatura
## 👨‍💻 Autores

- Felipe Gomes e Nicolas Cerqueira
- Engenharia de Computação - UEFS  

---
**BixoQuest** é um RPG de simulação universitária desenvolvido em **Java** para a disciplina **EXA863 - MI - Programação** da Universidade Estadual de Feira de Santana (UEFS).

O jogo coloca o jogador no papel de um estudante de Engenharia de Computação que precisa sobreviver aos desafios da vida universitária até alcançar a formatura.

---

## 📖 Sobre o Projeto

Em *BixoQuest*, o jogador enfrenta situações reais da vida acadêmica, como:

- Estudar para provas  
- Gerenciar tempo e energia  
- Lidar com prazos e eventos inesperados  
- Manter saúde física e mental  
- Administrar dinheiro  

🎯 **Objetivo:**  
Concluir o curso com sucesso, cumprindo os requisitos acadêmicos e chegando à formatura.

---

## 🧠 Mecânicas do Jogo

### 👤 Atributos do Jogador
- Energia  
- Conhecimento  
- Motivação  
- Saúde  
- Dinheiro  
- Desempenho acadêmico  
- Progresso no curso  

---

### 🎮 Ações Disponíveis
- Cursar disciplinas  
- Explorar o campus  
- Comprar lanches  
- Utilizar transporte (ônibus)  
- Pedir ajuda no colegiado  
- Realizar provas e trabalhos  
- Interagir com animais do campus 🐶🐱  

---

### 📍 Locais do Jogo
- Colegiado  
- Cantina  
- Sala de aula  
- Laboratório  
- Ponto de ônibus  

---

## 🎲 Eventos do Jogo

### ⚡ Eventos Aleatórios
- Prova surpresa  
- Fila gigante na cantina  
- Materiais caros inesperados  
- Greve ou paralisação  
- Milagre acadêmico (bônus em provas)  

---

### 📌 Eventos Obrigatórios
- Avaliações  
- Aprovação/Reprovação  
- Formatura  

---

## ⏳ Sistema de Turnos

O jogo funciona em ciclos de tempo (ex: semanas ou semestres), onde cada ação impacta os atributos do jogador.

---

## 🏗️ Arquitetura do Projeto

O sistema segue boas práticas de engenharia de software:

- Programação Orientada a Objetos (POO)  
- Padrão arquitetural **MVC (Model-View-Controller)**  
- Testes de unidade com JUnit  
- Persistência de dados (save/load)  

---

## 📁 Organização do Projeto

O projeto segue uma estrutura modular organizada por responsabilidades:
```
src/
├── main/java/
│   ├── controller/
│   │   ├── Eventos.java
│   │   ├── GerenciadorDeDados.java
│   │   ├── GerenciadorEventos.java
│   │   └── Prova.java
│   │
│   ├── localidades/
│   │   ├── Cantina.java
│   │   ├── Colegiado.java
│   │   ├── Laboratorio.java
│   │   ├── Local.java
│   │   ├── Lojinha.java
│   │   ├── Mapa.java
│   │   ├── PontoDeOnibus.java
│   │   └── SalaDeAula.java
│   │
│   ├── servivo/
│   │   ├── Cachorro.java
│   │   ├── Colegas.java
│   │   ├── Gato.java
│   │   ├── Jogador.java
│   │   ├── Maeli.java
│   │   ├── Personagem.java
│   │   └── Professores.java
│   │
│   └── Main.java
│
├── resources/
│
└── test/java/
    ├── controller/
    │   ├── EventosTest.java
    │   ├── GerenciadorDeDadosTest.java
    │   └── GerenciadorEventosTest.java
    │
    ├── localidades/
    │   ├── ColegiadoTest.java
    │   └── SalaDeAulaTest.java
    │
    ├── servivo/
    │   ├── JogadorTest.java
    │   └── MaeliTest.java
    │
    └── TestMain.java
```
---

## 🧩 Descrição dos Pacotes

### 📦 `controller`
Responsável pela lógica do jogo:
- Controle das ações  
- Gerenciamento de eventos  
- Persistência de dados  

---

### 📦 `localidades`
Define os ambientes do jogo:
- Locais exploráveis  
- Interações específicas por ambiente  

---

### 📦 `servivo`
Representa os personagens:
- Jogador  
- NPCs (professores, colegas, Maeli)  
- Animais do campus

---

### 📦 `resources`
Arquivos auxiliares:
- Dados salvos  
- Configurações  
- Recursos gráficos 

---

## 🧪 Testes

O projeto possui testes automatizados utilizando **JUnit**, garantindo:

- Confiabilidade do sistema  
- Validação das funcionalidades principais  
- Correta persistência de dados  

---

## 🚀 Tecnologias Utilizadas

- Java  
- JUnit  
- JavaFX  

---

## 📌 Observações

Este projeto foi desenvolvido para fins acadêmicos e pode ser expandido com novas funcionalidades, melhorias gráficas e mecânicas adicionais.
