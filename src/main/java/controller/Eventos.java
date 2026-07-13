package controller;
import localidades.Local;

public class Eventos {
    private String nome;
    private String descricao;
    private int probabilidade;
    private boolean obrigatorio;
    private Local LocalFixo;
    public Eventos(String nome, String descricao, int probabilidade, boolean obrigatorio, Local LocalFixo) {
            this.nome = nome;
            this.descricao = descricao;
            this.probabilidade = probabilidade;
            this.obrigatorio = obrigatorio;
            this.LocalFixo = LocalFixo;
        }

        public String getNome() {
            return nome;
        }

        public String getDescricao() {
            return descricao;
        }

        public int getProbabilidade() {
            return probabilidade;
        }

        public boolean isObrigatorio() {
            return obrigatorio;
        }

        public Local getLocalFixo() {
            return LocalFixo;
        }
    }
