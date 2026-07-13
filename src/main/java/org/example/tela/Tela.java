package org.example.tela;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import servivo.*;
import localidades.*;
import controller.GerenciadorEventos;
import controller.Eventos;
import controller.Prova;
import controller.GerenciadorDeDados;

import java.util.ArrayList;
import java.util.Objects;

public class Tela extends Application {

    private Stage primeiraTela;
    private Jogador jogador;
    private GerenciadorEventos gerenciadorEventos;
    private GerenciadorDeDados gerenciadorDados;

    // Controle de Slots ativos (1, 2 ou 3)
    private int slotAtual = 1;
    private final String PREFIXO_SAVE = "save_bixo_quest_";

    // Sistema de Ciclos e Semestre para Formatura
    private int turnoAtual = 1;
    private final int MAX_TURNOS_POR_SEMESTRE = 8;
    private final int SEMESTRE_FORMATURA = 8;

    @Override
    public void start(Stage primaryStage) {
        this.primeiraTela = primaryStage;
        this.gerenciadorDados = new GerenciadorDeDados();

        ArrayList<Eventos> eventosDoSemestre = new ArrayList<>();
        Local salaExemplo = new SaladeAula("Sala de Aula", "Módulos Teóricos");
        eventosDoSemestre.add(new Eventos("Inscrição em Disciplinas", "Matrícula obrigatória no Colegiado para não perder o semestre!", 50, true, salaExemplo));
        eventosDoSemestre.add(new Eventos("Fila Absurda no RU", "A fila dobrou o bloco do módulo 3. Perdeu energia esperando.", 30, false, null));
        eventosDoSemestre.add(new Eventos("Dinheiro no Chão", "Você achou uma moeda de R$ 1,00 perdida perto do xérox!", 25, false, null));
        this.gerenciadorEventos = new GerenciadorEventos(eventosDoSemestre);

        exibirmenu();
    }

    private void exibirmenu() {
        StackPane menu = new StackPane();
        ImageView imageMenu = carregarImagemSprite("/Sprite/TelaFundomenu.png");

        // Botão Novo Jogo
        Button play = criarBotaoComSprite("/Sprite/btn_jogar.png", "/Sprite/btn_jogar_hover.png", "NOVO JOGO");
        play.setOnAction(actionEvent -> {
            telaSelecaoSlot(true); // Modo de Novo Jogo
        });

        // Botão Carregar Jogo (Substituindo visualmente e funcionalmente o antigo Opções)
        Button carregar = criarBotaoComSprite("/Sprite/btn_carregar.png", "/Sprite/btn_carregar_hover.png", "CARREGAR JOGO");
        carregar.setOnAction(actionEvent -> {
            telaSelecaoSlot(false); // Modo de Carregamento
        });

        Button sair = criarBotaoComSprite("/Sprite/btn_sair.png", "/Sprite/btn_sair_hover.png", "SAIR");
        sair.setOnAction(actionEvent -> {
            if (jogador != null) {
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
            }
            primeiraTela.close();
        });

        VBox painelBotoes = new VBox(15);
        painelBotoes.setAlignment(Pos.CENTER);
        painelBotoes.getChildren().addAll(play, carregar, sair);

        if (imageMenu != null) menu.getChildren().add(imageMenu);
        menu.getChildren().add(painelBotoes);

        Scene cenaMenu = new Scene(menu, 800, 600);
        primeiraTela.setTitle("Bixo Quest - Menu Inicial");
        primeiraTela.setScene(cenaMenu);
        primeiraTela.show();
    }

    // --- Seleção de 3 Slots de Salvamento/Carregamento ---
    public void telaSelecaoSlot(boolean novoJogo) {
        StackPane painelSlots = new StackPane();
        ImageView imageCarregar = carregarImagemSprite("/Sprite/TelaFundomenu.png");
        painelSlots.setStyle("-fx-background-color: #0f172a;");

        VBox layoutGeral = new VBox(25);
        layoutGeral.setStyle ("-fx-background-color: rgba(15, 23, 42, 0.85)");
        layoutGeral.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label(novoJogo ? "🎮 SELECIONE UM SLOT PARA NOVO JOGO" : "📂 SELECIONE UM SLOT PARA CARREGAR");
        lblTitulo.setStyle("-fx-text-fill: #00ffcc; -fx-font-size: 24px; -fx-font-weight: bold;");

        VBox boxSlots = new VBox(15);
        boxSlots.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 3; i++) {
            final int numeroSlot = i;
            GerenciadorDeDados.DadosJogo dadosSlot = gerenciadorDados.carregarDadosJogo(PREFIXO_SAVE + numeroSlot);
            Jogador jogSlot = dadosSlot != null ? dadosSlot.jogador : null;
            String infoTexto = "Slot " + numeroSlot + " - [ Vazio ]";
            if (jogSlot != null) {
                infoTexto = "Slot " + numeroSlot + " - " + jogSlot.getNome() + " (Semestre: " + jogSlot.getSemestreAtual() + "º)";
            }

            Label lblInfoSlot = new Label(infoTexto);
            lblInfoSlot.setStyle("-fx-text-fill: #f4f4f4; -fx-font-size: 14px; -fx-pref-width: 300px;");

            Button btnAcaoSlot = new Button(novoJogo ? "Gravar Aqui" : "Carregar");
            btnAcaoSlot.setStyle(novoJogo ? "-fx-background-color: #5783ff; -fx-text-fill: #f4f4f4; -fx-font-weight: bold; -fx-cursor: hand;"
                    : "-fx-background-color: #3b82f6; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

            if (!novoJogo && jogSlot == null) {
                btnAcaoSlot.setDisable(true);
                btnAcaoSlot.setStyle("-fx-background-color: #475569; -fx-text-fill: #94a3b8;");
            }

            btnAcaoSlot.setOnAction(e -> {
                this.slotAtual = numeroSlot;
                if (novoJogo) {
                    telaCriacaoPersonagem();
                } else {
                    if (dadosSlot != null) {
                        this.jogador = dadosSlot.jogador;
                        this.turnoAtual = dadosSlot.turnoAtual;
                    }
                    if (jogador.getEnergia() <= 0) {
                        moverJogador(new PontoDeOnibus("Ponto de Onibus", "Volte para casa de buzu por R$ 2.70"), 3, 3);
                    } else {
                        telaSelecaoLugar();
                    }
                }
            });

            HBox rowSlot = new HBox(20);
            rowSlot.setAlignment(Pos.CENTER);
            rowSlot.getChildren().addAll(lblInfoSlot, btnAcaoSlot);
            boxSlots.getChildren().add(rowSlot);
        }

        Button btnVoltar = new Button("Voltar ao Menu");
        btnVoltar.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnVoltar.setOnAction(e -> exibirmenu());

        layoutGeral.getChildren().addAll(lblTitulo, boxSlots, btnVoltar);
        painelSlots.getChildren().add(imageCarregar);
        painelSlots.getChildren().add(layoutGeral);

        Scene cenaSlots = new Scene(painelSlots, 800, 600);
        primeiraTela.setScene(cenaSlots);
    }

    public void telaCriacaoPersonagem() {
        StackPane painelCriacao = new StackPane();
        painelCriacao.setStyle("-fx-background-color: #0f172a;");

        VBox boxFormulario = new VBox(20);
        boxFormulario.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label("🎓 MATRÍCULA DE CALOURO (SLOT " + slotAtual + ") 🎓");
        lblTitulo.setStyle("-fx-text-fill: #00ffcc; -fx-font-size: 24px; -fx-font-weight: bold;");

        Label lblInstrucao = new Label("Digite o seu nome para iniciar a jornada:");
        lblInstrucao.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        TextField campoNome = new TextField();
        campoNome.setPromptText("Ex: Calouro das Galáxias");
        campoNome.setMaxWidth(300);
        campoNome.setStyle("-fx-font-size: 14px; -fx-padding: 8px;");

        Label lblErro = new Label("");
        lblErro.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");

        Button btnMatricular = new Button("Confirmar Matrícula e Jogar");
        btnMatricular.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20; -fx-cursor: hand;");

        btnMatricular.setOnAction(e -> {
            String nomeDigitado = campoNome.getText().trim();

            if (nomeDigitado.isEmpty()) {
                lblErro.setText("🚨 O nome do estudante não pode ficar em branco!");
            } else {
                this.jogador = new Jogador.Builder()
                    .setNome(nomeDigitado)
                    .setEnergia(25)
                    .setDinheiro(20.00)
                    .setSemestreAtual(1)
                    .setInteragir(true)
                    .setLocalAtual(new PontoDeOnibus("Ponto de Onibus", "Volte para casa de buzu por R$ 2.70"))
                    .setPosicao(3, 3)
                    .build();

                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                telaAcoesLocal();
            }
        });

        boxFormulario.getChildren().addAll(lblTitulo, lblInstrucao, campoNome, lblErro, btnMatricular);
        painelCriacao.getChildren().add(boxFormulario);

        Scene cenaCriacao = new Scene(painelCriacao, 800, 600);
        primeiraTela.setScene(cenaCriacao);
    }

    public void telaSelecaoLugar() {
        if (jogador.getEnergia() <= 0) {
            moverJogador(new PontoDeOnibus("Ponto de Onibus", "Volte para casa de buzu por R$ 2.70"), 3, 3);
            return;
        }

        StackPane raizMapa = new StackPane();
        ImageView viewMapa = carregarImagemSprite("/Sprite/MapaGeral.png");

        GridPane gradeLocais = new GridPane();
        gradeLocais.setAlignment(Pos.CENTER);
        gradeLocais.setHgap(40); gradeLocais.setVgap(40);

        Label informacao = new Label("Clique no bloco para se movimentar (Turno " + turnoAtual + "/" + MAX_TURNOS_POR_SEMESTRE + "):");
        informacao.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-background-color: rgba(0,0,0,0.85); -fx-padding: 10px; -fx-font-weight: bold;");
        informacao.setTranslateY(-240);

        Button btnCantina = criarBotaoMapa("CANTINA", "#d97706");
        Button btnLaboratorio = criarBotaoMapa("LABORATÓRIO", "#4f46e5");
        Button btnColegiado = criarBotaoMapa("COLEGIADO", "#059669");
        Button btnPontoOnibus = criarBotaoMapa("PONTO ÔNIBUS", "#2563eb");
        Button btnSalaAula = criarBotaoMapa("SALA DE AULA", "#7c3aed");

        Button btnVoltarMenu = new Button("Salvar e Ir pro Menu");
        btnVoltarMenu.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 10 20;");
        btnVoltarMenu.setTranslateY(240);
        btnVoltarMenu.setOnAction(e -> {
            gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
            exibirmenu();
        });

        btnCantina.setOnAction(e -> moverJogador(new Cantina("Cantina", "Recupere suas energias gastando pouco"), 1, 2));
        btnLaboratorio.setOnAction(e -> moverJogador(new Laboratorio("Laboratorio", "Estudos práticos de computação"), 0, 1));
        btnColegiado.setOnAction(e -> moverJogador(new Colegiado("Colegiado", "Bloco administrativo"), 2, 0));
        btnPontoOnibus.setOnAction(e -> moverJogador(new PontoDeOnibus("Ponto de Onibus", "Volte para casa de buzu por R$ 2.70"), 3, 3));
        btnSalaAula.setOnAction(e -> moverJogador(new SaladeAula("Sala de Aula", "Módulos de aulas teóricas"), 1, 1));

        gradeLocais.add(btnLaboratorio, 1, 0);
        gradeLocais.add(btnSalaAula, 0, 1);
        gradeLocais.add(btnCantina, 2, 1);
        gradeLocais.add(btnColegiado, 0, 2);
        gradeLocais.add(btnPontoOnibus, 2, 2);

        if (viewMapa != null) raizMapa.getChildren().add(viewMapa);
        raizMapa.getChildren().addAll(informacao, gradeLocais, btnVoltarMenu);
        Scene cenaMapa = new Scene(raizMapa, 800, 600);
        primeiraTela.setScene(cenaMapa);
    }

    private void moverJogador(Local local, int l, int c) {
        this.jogador.setLocalAtual(local);
        this.jogador.setPosicao(l, c);
        gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
        telaAcoesLocal();
    }

    public void telaAcoesLocal() {
        StackPane painelCenario = new StackPane();
        Local local = (Local) jogador.getLocalAtual();

        String nomeArquivoImagem = local.getNome().toLowerCase()
                .replace("ó", "o").replace("â", "a").replace("ú", "u").replace(" ", "_") + ".png";
        ImageView viewBg = carregarImagemSprite("/Sprite/" + nomeArquivoImagem);//Mapa Geral

        Label barraStatus = new Label();
        atualizarLabelsDeStatus(barraStatus);
        barraStatus.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-background-color: rgba(0,0,0,0.85); -fx-padding: 10px;");
        barraStatus.setTranslateY(-240);

        String textoInicialConsole = "Prédio Atual: " + local.getNome();
        if (jogador.getEnergia() <= 0) {
            textoInicialConsole += " | 🚨 EXAUSTÃO TOTAL! Vá para o Ponto de Ônibus para poder voltar para casa.";
        }

        Label consoleLogs = new Label(textoInicialConsole);
        consoleLogs.setStyle("-fx-text-fill: #00ffcc; -fx-font-size: 14px; -fx-background-color: rgba(0,0,0,0.9); -fx-padding: 12px; -fx-max-width: 650px;");
        consoleLogs.setTranslateY(170);

        VBox layoutBotoesAcao = new VBox(10);
        layoutBotoesAcao.setAlignment(Pos.CENTER);
        ImageView spriteDoLocal = null;

        if (local instanceof Cantina) {
            spriteDoLocal = carregarImagemSprite("/Sprite/Cantina.png");
            if (spriteDoLocal != null) {
                spriteDoLocal.setFitHeight(800);
                spriteDoLocal.setFitWidth(600);
                // Ajuste o tamanho
                spriteDoLocal.setPreserveRatio(true);
            }
            Button btnComprar = new Button("Comprar Lanche Completo");
            btnComprar.setStyle("-fx-background-color: #f59e0b; -fx-text-fill: black; -fx-font-weight: bold; -fx-cursor: hand;");
            btnComprar.setPrefSize(280, 38);

            Button btnCachorro = new Button("Fazer carinho no Doguinho");
            btnCachorro.setStyle("-fx-background-color: #d97706; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");
            btnCachorro.setPrefSize(280, 35);

            btnComprar.setOnAction(e -> {
                double granaAntes = jogador.getDinheiro();
                int energiaAntes = jogador.getEnergia();

                jogador.adquirirLanches(1);

                if (jogador.getEnergia() > energiaAntes) {
                    consoleLogs.setText("🍔 Lanche consumido! Vigor reabastecido.\nEnergia atual: " + jogador.getEnergia());
                    gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                    computarTurno(consoleLogs);
                } else if (energiaAntes == 25) {
                    consoleLogs.setText("Sua energia já está no limite máximo (25)!");
                } else if (jogador.getDinheiro() == granaAntes) {
                    consoleLogs.setText("Saldo insuficiente na carteira para comprar lanches!");
                }
                atualizarLabelsDeStatus(barraStatus);
                atualizarEstadoBotoes(layoutBotoesAcao, consoleLogs);
            });
            btnCachorro.setOnAction(e -> {
                Cachorro dog = new Cachorro("Doguinho", "Cachorro Caramelo do Campus", local);
                dog.Interagir(jogador); // Aumenta a motivação internamente
                consoleLogs.setText("🐶 Doguinho: 'AU AU!'\nSua motivação aumentou fazendo carinho no cachorro!");
                atualizarLabelsDeStatus(barraStatus);
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                computarTurno(consoleLogs);
                atualizarEstadoBotoes(layoutBotoesAcao, consoleLogs);
            });
            layoutBotoesAcao.getChildren().addAll(btnComprar, btnCachorro);

        } else if (local instanceof Laboratorio) {
            spriteDoLocal = carregarImagemSprite("/Sprite/Laboratorio.png");
            if (spriteDoLocal != null) {
                spriteDoLocal.setFitHeight(800);
                spriteDoLocal.setFitWidth(600);
                // Ajuste o tamanho
                spriteDoLocal.setPreserveRatio(true);
            }
            Button btnEstudar = new Button("Fazer Prática de Hardware (-5 Energia)");
            Button btnConversarColega = new Button("Falar com Colega de Classe");
            Button btnHardware = new Button("Aprender Hardware (-5 Energia)");

            btnEstudar.setStyle("-fx-background-color: #6366f1; -fx-text-fill: white; -fx-cursor: hand;");
            btnConversarColega.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-cursor: hand;");
            btnHardware.setStyle("-fx-background-color: #0d9488; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");

            btnEstudar.setPrefSize(280, 35); btnConversarColega.setPrefSize(280, 35); btnHardware.setPrefSize(280, 35);

            btnEstudar.setOnAction(e -> {
                if (jogador.getEnergia() < 5) {
                    consoleLogs.setText("🚨 Você não tem energia suficiente para programar!");
                    return;
                }
                Laboratorio lab = (Laboratorio) local;
                lab.cursarDisciplina(jogador);
                consoleLogs.setText("💻Toca o Barco! Conhecimento Prático aumentado!");
                atualizarLabelsDeStatus(barraStatus);
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                computarTurno(consoleLogs);
                atualizarEstadoBotoes(layoutBotoesAcao, consoleLogs);
            });


            btnConversarColega.setOnAction(e -> {
                Colegas colega = new Colegas("Colega", "Estudante veterano", local);
                colega.Interagir(jogador);
                consoleLogs.setText("👥 Colega: 'E aí bixo! Foca em entender Bosco");
                atualizarLabelsDeStatus(barraStatus);
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                computarTurno(consoleLogs);
                atualizarEstadoBotoes(layoutBotoesAcao, consoleLogs);
            });

            btnHardware.setOnAction(e -> {
                Prova p = new Prova();
                double rendimentoAntes = jogador.getDesempenhoAcademico();
                p.realizarProva(jogador);
                if (jogador.getDesempenhoAcademico() > rendimentoAntes) {
                    consoleLogs.setText("📝 Resultado da Avaliação: Você foi APROVADO! Seu rendimento subiu.");
                } else {
                    consoleLogs.setText("📝 Resultado da Avaliação: REPROVADO! Nota insuficiente. Precisa estudar mais.");
                }
                atualizarLabelsDeStatus(barraStatus);
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                computarTurno(consoleLogs);
                atualizarEstadoBotoes(layoutBotoesAcao, consoleLogs);
            });
            layoutBotoesAcao.getChildren().addAll(btnEstudar, btnConversarColega, btnHardware);

        } else if (local instanceof Colegiado) {
            spriteDoLocal = carregarImagemSprite("/Sprite/Colegiado.png");
            if (spriteDoLocal != null) {
                spriteDoLocal.setFitHeight(800);
                spriteDoLocal.setFitWidth(600);
                // Ajuste o tamanho
                spriteDoLocal.setPreserveRatio(true);
            }
            Button btnMaeli = new Button("Falar com Maeli (Colegiado)");
            Button btnSofas = new Button("Cochilar nos Sofás (+25 Energia)");
            Button btnGato = new Button("Brincar com Gatinho");

            btnGato.setStyle("-fx-background-color: #14b8a6; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");
            btnMaeli.setStyle("-fx-background-color: #059669; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");
            btnSofas.setStyle("-fx-background-color: #06b6d4; -fx-text-fill: white; -fx-cursor: hand;");
            btnMaeli.setPrefSize(280, 35); btnSofas.setPrefSize(280, 35);btnGato.setPrefSize(280, 35);

            btnMaeli.setOnAction(e -> {
                Maeli npcMaeli = new Maeli("Maeli", "Secretária", local);
                npcMaeli.Interagir(jogador);
                consoleLogs.setText("👩‍💼 Maeli: 'Olá! Lembre de ficar atento aos prazos de ajuste de matrícula no portal institucional.'");
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                computarTurno(consoleLogs);
                atualizarEstadoBotoes(layoutBotoesAcao, consoleLogs);
            });

            btnGato.setOnAction(e -> {
                Gato cat = new Gato("Gatinho", "Gato folgado do Colegiado", local);
                cat.Interagir(jogador); // Aumenta a motivação internamente
                consoleLogs.setText("🐱 Gatinho: 'Miau!'\nSua motivação aumentou brincando com o gato!");
                atualizarLabelsDeStatus(barraStatus);
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                computarTurno(consoleLogs);
                atualizarEstadoBotoes(layoutBotoesAcao, consoleLogs);
            });

            btnSofas.setOnAction(e -> {
                Colegiado col = (Colegiado) local;
                consoleLogs.setText(col.descansar(jogador.getNome(), jogador));
                atualizarLabelsDeStatus(barraStatus);
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                computarTurno(consoleLogs);
                atualizarEstadoBotoes(layoutBotoesAcao, consoleLogs);
            });
            layoutBotoesAcao.getChildren().addAll(btnMaeli, btnGato, btnSofas);

        } else if (local instanceof SaladeAula) {
            spriteDoLocal = carregarImagemSprite("/Sprite/SalaAula.png");
            if (spriteDoLocal != null) {
                spriteDoLocal.setFitHeight(600);
                spriteDoLocal.setFitWidth(800);
                // Ajuste o tamanho
                spriteDoLocal.setPreserveRatio(false);
            }
            Button btnAula = new Button("Assistir Aula Teórica (-5 Energia)");
            Button btnFalarProfessor = new Button("Tirar Dúvida com o Professor");
            Button btnExame = new Button("Prestar Prova de Programação");

            btnAula.setStyle("-fx-background-color: #8b5cf6; -fx-text-fill: white; -fx-cursor: hand;");
            btnFalarProfessor.setStyle("-fx-background-color: #ec4899; -fx-text-fill: white; -fx-cursor: hand;");
            btnExame.setStyle("-fx-background-color: #ea580c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-weight: bold;");

            btnAula.setPrefSize(280, 35); btnFalarProfessor.setPrefSize(280, 35); btnExame.setPrefSize(280, 35);

            btnAula.setOnAction(e -> {
                if (jogador.getEnergia() < 5) {
                    consoleLogs.setText("🚨 Você está exausto demais para prestar atenção na aula!");
                    return;
                }
                jogador.setEnergia(jogador.getEnergia() - 5);
                consoleLogs.setText("📚 Aula assistida com sucesso!\nSeu Conhecimento Teórico e índices acadêmicos aumentaram.");
                atualizarLabelsDeStatus(barraStatus);
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                computarTurno(consoleLogs);
                atualizarEstadoBotoes(layoutBotoesAcao, consoleLogs);
            });

            btnFalarProfessor.setOnAction(e -> {
                Professor prof = new Professor("Professor", "Docente", local);
                prof.Interagir(jogador);
                consoleLogs.setText("👨🏫 Professor: 'Excelente dúvida! Lembre-se que variáveis locais só existem dentro do escopo do método em que foram declaradas, enquanto os atributos pertencem ao objeto.'");
                atualizarLabelsDeStatus(barraStatus);
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                computarTurno(consoleLogs);
                atualizarEstadoBotoes(layoutBotoesAcao, consoleLogs);
            });

            btnExame.setOnAction(e -> {
                Prova p = new Prova();
                double rendimentoAntes = jogador.getDesempenhoAcademico();
                p.realizarProva(jogador);
                if (jogador.getDesempenhoAcademico() > rendimentoAntes) {
                    consoleLogs.setText("📝 Resultado da Avaliação: Você foi APROVADO! Seu rendimento subiu.");
                } else {
                    consoleLogs.setText("📝 Resultado da Avaliação: REPROVADO! Nota insuficiente. Precisa estudar mais.");
                }
                atualizarLabelsDeStatus(barraStatus);
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                computarTurno(consoleLogs);
                atualizarEstadoBotoes(layoutBotoesAcao, consoleLogs);
            });
            layoutBotoesAcao.getChildren().addAll(btnAula, btnFalarProfessor, btnExame);

        } else if (local instanceof PontoDeOnibus) {
            spriteDoLocal = carregarImagemSprite("/Sprite/PontoDeOnibus.png");
            if (spriteDoLocal != null) {
                // Ajuste o tamanho
                spriteDoLocal.setPreserveRatio(false);
            }
            Button btnOnibus = new Button("Pegar Ônibus / Encerrar Turno");
            btnOnibus.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
            btnOnibus.setPrefSize(280, 40);

            btnOnibus.setOnAction(e -> {
                double carteiraAntes = jogador.getDinheiro();
                jogador.pegarOnibusCasa();

                if (jogador.getDinheiro() < carteiraAntes || jogador.getEnergia() <= 0) {
                    if (jogador.getEnergia() <= 0 && jogador.getDinheiro() == carteiraAntes) {
                        jogador.setEnergia(10);
                    }
                    consoleLogs.setText("🚌 Você pegou o busu de volta para casa. Turno encerrado e energias renovadas!");
                    atualizarLabelsDeStatus(barraStatus);
                    gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
                    forcarFimDeTurno(consoleLogs);
                } else {
                    consoleLogs.setText("🚨 Você não tem R$ 2,70 para pagar a passagem do ônibus!");
                }
            });
            layoutBotoesAcao.getChildren().add(btnOnibus);
        }
        if (spriteDoLocal != null) {
            painelCenario.getChildren().add(spriteDoLocal);
        }
        Button btnVoltarCampus = new Button("Voltar para o Campus");
        btnVoltarCampus.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnVoltarCampus.setPrefSize(280, 35);

        btnVoltarCampus.setOnAction(e -> {
            gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
            telaSelecaoLugar();
        });
        layoutBotoesAcao.getChildren().add(btnVoltarCampus);

        // Força a desativação de botões na inicialização caso a energia já venha zerada de salvamento
        atualizarEstadoBotoes(layoutBotoesAcao, consoleLogs);

        if (viewBg != null) painelCenario.getChildren().add(viewBg);
        painelCenario.getChildren().addAll(barraStatus, layoutBotoesAcao, consoleLogs);

        Scene cenaCenario = new Scene(painelCenario, 800, 600);
        primeiraTela.setScene(cenaCenario);
    }

    private void atualizarEstadoBotoes(VBox layoutBotoes, Label console) {
        boolean exausto = jogador.getEnergia() <= 0;

        if (exausto) {
            console.setText(console.getText() + "\n🚨 [EXAUSTÃO] Você desmaiou de sono! Vá IMEDIATAMENTE para o Ponto de Ônibus.");

            for (javafx.scene.Node no : layoutBotoes.getChildren()) {
                if (no instanceof Button) {
                    Button btn = (Button) no;
                    if (!btn.getText().contains("Voltar") && !btn.getText().contains("Ônibus")) {
                        btn.setDisable(true);
                        btn.setStyle("-fx-background-color: #475569; -fx-text-fill: #94a3b8;");
                    }
                }
            }
        }
    }

    private void computarTurno(Label console) {
        turnoAtual++;
        checarMudancaCiclo(console);
    }

    private void forcarFimDeTurno(Label console) {
        turnoAtual = MAX_TURNOS_POR_SEMESTRE + 1;
        checarMudancaCiclo(console);
    }

    private void checarMudancaCiclo(Label console) {
        // Instancia a prova para verificar o status das matérias
        Prova provaController = new Prova();

        if (turnoAtual > MAX_TURNOS_POR_SEMESTRE) {
            turnoAtual = 1; // Reseta o turno do ciclo atual

            // SÓ PASSA SE PASSAR NAS DUAS MATÉRIAS
            if (provaController.passouNasDuasMaterias()) {
                int proximoSemestre = jogador.getSemestreAtual() + 1;
                jogador.setSemestreAtual(proximoSemestre);

                // PERDE 20% DE DESEMPENHO ACADÊMICO: Mantém 80% como bagagem de conhecimento
                double desempenhoAtual = jogador.getDesempenhoAcademico();
                jogador.setDesempenhoAcademico(desempenhoAtual * 0.8);

                // Reseta o status das provas para o novo semestre
                provaController.resetarMaterias();

                if (proximoSemestre > SEMESTRE_FORMATURA) {
                    telaVitoria();
                    return;
                }

                console.setText("🎓 MUDANÇA DE CICLO! Você passou para o " + proximoSemestre + "º Semestre da UEFS!\nEnergias renovadas e parte do seu conhecimento foi levada para o novo período.");
                jogador.setEnergia(25);
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
            } else {
                // SE NÃO PASSOU NAS DUAS: Repete o semestre atual (perde o semestre)
                // O desempenho NÃO é alterado aqui para ajudá-lo a passar na nova tentativa
                console.setText("🚨 FIM DO PERÍODO! Você NÃO passou nas duas matérias obrigatórias.\nTerá que repetir o " + jogador.getSemestreAtual() + "º Semestre! Aproveite o conhecimento acumulado.");
                jogador.setEnergia(25); // Dá energia nova para tentar novamente
                gerenciadorDados.salvarJogo(jogador, PREFIXO_SAVE + slotAtual, turnoAtual);
            }
        } else {
            Eventos ev = gerenciadorEventos.gerarEvento();
            if (ev != null) {
                console.setText(console.getText() + "\n🚨 [EVENTO]: " + ev.getNome() + " -> " + ev.getDescricao());
            }
        }
    }

    private void telaVitoria() {
        StackPane painelVitoria = new StackPane();
        painelVitoria.setStyle("-fx-background-color: #0f172a;");

        VBox layoutVitoria = new VBox(20);
        layoutVitoria.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label("🎉 PARABÉNS! JOGO ZERADO! 🎉");
        lblTitulo.setStyle("-fx-text-fill: #10b981; -fx-font-size: 28px; -fx-font-weight: bold;");

        Label lblDesc = new Label("Você concluiu os 8 semestres com sucesso!\nO diploma de Ciência da Computação na UEFS é seu!");
        lblDesc.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-text-alignment: center;");

        Button btnSair = new Button("Fechar Jogo");
        btnSair.setStyle("-fx-background-color: #4f46e5; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 30;");
        btnSair.setOnAction(e -> primeiraTela.close());

        layoutVitoria.getChildren().addAll(lblTitulo, lblDesc, btnSair);
        painelVitoria.getChildren().add(layoutVitoria);

        Scene cenaVitoria = new Scene(painelVitoria, 800, 600);
        primeiraTela.setScene(cenaVitoria);
    }

    private void atualizarLabelsDeStatus(Label label) {
        label.setText("Estudante: " + jogador.getNome() +
                " | ⚡ Vigor: " + jogador.getEnergia() + "/25" +
                " | 🪙 Carteira: R$ " + String.format("%.2f", jogador.getDinheiro()) +
                " | 📅 Turno: " + Math.min(turnoAtual, MAX_TURNOS_POR_SEMESTRE) + "/" + MAX_TURNOS_POR_SEMESTRE +
                " | 🎓 Semestre: " + jogador.getSemestreAtual() + "/" + SEMESTRE_FORMATURA);
    }

    private Button criarBotaoMapa(String texto, String corHex) {
        Button btn = new Button(texto);
        btn.setPrefSize(160, 50);
        btn.setStyle("-fx-background-color: " + corHex + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 6;");
        return btn;
    }

    private Button criarBotaoComSprite(String caminhoNormal, String caminhoHover, String textoAlternativo) {
        Button botao = new Button();
        botao.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        try {
            Image imgNormal = new Image(Objects.requireNonNull(getClass().getResourceAsStream(caminhoNormal)));
            Image imgHover = new Image(Objects.requireNonNull(getClass().getResourceAsStream(caminhoHover)));
            ImageView viewIcone = new ImageView(imgNormal);
            viewIcone.setFitWidth(210); viewIcone.setFitHeight(60);
            botao.setGraphic(viewIcone);

            ScaleTransition aumenta = new ScaleTransition(Duration.millis(120), botao);
            aumenta.setToX(1.08); aumenta.setToY(1.08);
            ScaleTransition diminui = new ScaleTransition(Duration.millis(120), botao);
            diminui.setToX(1.0); diminui.setToY(1.0);

            botao.setOnMouseEntered(m -> { diminui.stop(); viewIcone.setImage(imgHover); aumenta.play(); });
            botao.setOnMouseExited(m -> { aumenta.stop(); viewIcone.setImage(imgNormal); diminui.play(); });
        } catch (Exception e) {
            botao.setText(textoAlternativo);
            botao.setPrefSize(210, 60);
            botao.setStyle("-fx-background-color: #4a5568; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6;");
        }
        return botao;
    }

    private ImageView carregarImagemSprite(String caminho) {
        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(caminho)));
            if (img.isError()) return null;
            ImageView view = new ImageView(img);
            view.setFitWidth(800); view.setFitHeight(600);
            return view;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}