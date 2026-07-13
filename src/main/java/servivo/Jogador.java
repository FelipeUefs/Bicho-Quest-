package servivo;

import localidades.Local;
import localidades.Cantina;
import localidades.PontoDeOnibus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Observers: adiciona suporte ao padrão Observer para permitir que
 * outras partes do sistema (ex: UI) sejam notificadas quando o estado
 * do jogador mudar.
 * Padrão: Observer
 */

public class Jogador implements Serializable {

    //Identificador Invisivel da versão da Classe
    private static final long serialVersionUID = 1L;

    private int energia;
    private int nivelDeConhecimento;
    private int saude;
    private double dinheiro;
    private int motivacao;
    private double desempenhoAcademico;
    private int semestreAtual;
    private boolean interagir;
    private String nome;
    private Local localAtual; //Ideia de armazenar o local onde o jogador está.
    private int linha; //Linha do mapa onde o jogador está
    private int coluna; //Coluna do mapa onde o jogador está

    // Lista de observadores que acompanham mudanças neste jogador.
    // Marcado como transient para não ser serializado junto com o jogador.
    private transient List<JogadorObserver> observers = new ArrayList<>();

    public void addObserver(JogadorObserver obs) {
        if (obs == null) return;
        if (observers == null) observers = new ArrayList<>();
        observers.add(obs);
    }

    public void removeObserver(JogadorObserver obs) {
        if (observers != null) observers.remove(obs);
    }

    private void notifyObservers(String evento) {
        if (observers == null) return;
        for (JogadorObserver obs : new ArrayList<>(observers)) {
            try { obs.onAtualizacao(this, evento); } catch (Exception ignored) {}
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nick) {
        this.nome = nick;
    }

    public boolean getInteragir() {
        return interagir;
    }

    public void setDinheiro(double dinheiro) {
        this.dinheiro = dinheiro;
        notifyObservers("dinheiro");
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setPosicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        notifyObservers("posicao");
    }

    public void setNivelDeConhecimento(int nivelDeConhecimento) {
        this.nivelDeConhecimento = nivelDeConhecimento;
        notifyObservers("conhecimento");
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
        notifyObservers("energia");
    }

    public int getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(int motivacao) {
        this.motivacao = motivacao;
        notifyObservers("motivacao");
    }

    public void aumentarMotivacao(int valor) {
        this.motivacao += valor;
        notifyObservers("motivacao");
    }

    public int getNivelDeConhecimento() {
        return nivelDeConhecimento;
    }

    public int getConhecimento() {
        return nivelDeConhecimento;
    }

    public void setConhecimento(int conhecimento) {
        this.nivelDeConhecimento = conhecimento;
        notifyObservers("conhecimento");
    }

    public void aumentarConhecimento(int conhecimento) {
        this.nivelDeConhecimento += conhecimento;
        notifyObservers("conhecimento");
    }

    public double getDesempenhoAcademico() {
        return desempenhoAcademico;
    }

    public void setSemestreAtual(int semestre) {
        this.semestreAtual = semestre;
        notifyObservers("semestre");
    }

    public int getSemestreAtual() {
        return semestreAtual;
    }

    public void setDesempenhoAcademico(double desempenho) {
        this.desempenhoAcademico = desempenho;
        notifyObservers("desempenho");
    }

    public void aumentarDesempenhoacd(int desempAcademico) {
        this.desempenhoAcademico += desempAcademico;
        notifyObservers("desempenho");
    }

    public Local getLocalAtual() {
        return localAtual;
    }

    public void setLocalAtual(Local localAtual) {
        this.localAtual = localAtual;
        notifyObservers("local");
    }

    public double getDinheiro() {
        return dinheiro;
    }

    public boolean gastarDinheiro(double dinheiro) {
        if (this.dinheiro >= dinheiro) {
            this.dinheiro -= dinheiro;
            notifyObservers("dinheiro");
            return true;
        } else {
            System.out.println("Saldo insuficiente!");
            return false;
        }
    }

    public void setInteragir(boolean interagir) {
        this.interagir = interagir;
        notifyObservers("interagir");
    }

    public void adquirirLanches(int quantidade) {
        if (this.localAtual instanceof Cantina) {
            Cantina cantina = (Cantina) this.localAtual;//como já foi verificado no instanceof, podemos afirmar que aqui é a catina
            if (this.interagir) {
                if (this.energia == 25) {
                    System.out.println("Energia Cheia, não é preciso comprar Lanches!");
                } else {
                    // Diminuir dinheiro e aumentar energia ou saude se estiverem baixos
                    double valorLanche = quantidade * cantina.getPrecoLanche();//Calcula o quanto será gasto com lanches;
                    if (this.dinheiro >= valorLanche) {
                        this.dinheiro -= valorLanche; //Atualiza o valor do dinheiro do jogador
                        int recuEnergy = 5 * quantidade;
                        this.energia += recuEnergy;//Sobe valores de energia
                        if (this.energia > 25) {
                            this.energia = 25;//25 é o limite de energia
                        }
                        System.out.println("Lanches Comprados, foram gastos R$ " + valorLanche);
                        System.out.println("Energia Recuperada +" + recuEnergy);
                        // Notifica observers sobre as mudanças de dinheiro e energia
                        notifyObservers("dinheiro");
                        notifyObservers("energia");
                    }
                }
            }
        } else {
            System.out.println("Você não está na cantina, então não pode comprar lanches");
        }
    }
    public boolean AprovadoNoSemestre() {
        int META_APROVACAO = 60;
        return this.getDesempenhoAcademico() > META_APROVACAO;
    }

    public void zerarDesempenhoAcademico() {
        this.desempenhoAcademico = 0;
    }

    public void pegarOnibusCasa() {
        if (this.localAtual instanceof PontoDeOnibus) { //Verifica Local atual do Jogador
            PontoDeOnibus pontobusu = (PontoDeOnibus) this.localAtual;
            if (this.interagir) {
                double passagem = pontobusu.getValorPassagem();
                gastarDinheiro(passagem);
            }
        }
    }

    /**
     * Builder para Jogador.
     * Padrão: Builder
     * Utilidade: facilita a criação de Jogador com muitos campos opcionais de forma legível.
     */
    public static class Builder {
        private int energia = 25;
        private int nivelDeConhecimento = 0;
        private int saude = 100;
        private double dinheiro = 0.0;
        private int motivacao = 0;
        private double desempenhoAcademico = 0.0;
        private int semestreAtual = 1;
        private boolean interagir = true;
        private String nome = "";
        private Local localAtual = null;
        private int linha = 0;
        private int coluna = 0;

        public Builder setEnergia(int energia) { this.energia = energia; return this; }
        public Builder setNivelDeConhecimento(int conhecimento) { this.nivelDeConhecimento = conhecimento; return this; }
        public Builder setSaude(int saude) { this.saude = saude; return this; }
        public Builder setDinheiro(double dinheiro) { this.dinheiro = dinheiro; return this; }
        public Builder setMotivacao(int motivacao) { this.motivacao = motivacao; return this; }
        public Builder setDesempenhoAcademico(double desempenho) { this.desempenhoAcademico = desempenho; return this; }
        public Builder setSemestreAtual(int semestre) { this.semestreAtual = semestre; return this; }
        public Builder setInteragir(boolean interagir) { this.interagir = interagir; return this; }
        public Builder setNome(String nome) { this.nome = nome; return this; }
        public Builder setLocalAtual(Local local) { this.localAtual = local; return this; }
        public Builder setPosicao(int linha, int coluna) { this.linha = linha; this.coluna = coluna; return this; }

        public Jogador build() {
            Jogador j = new Jogador();
            j.energia = this.energia;
            j.nivelDeConhecimento = this.nivelDeConhecimento;
            j.saude = this.saude;
            j.dinheiro = this.dinheiro;
            j.motivacao = this.motivacao;
            j.desempenhoAcademico = this.desempenhoAcademico;
            j.semestreAtual = this.semestreAtual;
            j.interagir = this.interagir;
            j.nome = this.nome;
            j.localAtual = this.localAtual;
            j.linha = this.linha;
            j.coluna = this.coluna;
            return j;
        }
    }
}


