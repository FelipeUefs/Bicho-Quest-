package servivo;
import localidades.Local;

public class Personagem {
    private String nome;
    private String descricao;
    protected Local localizacao;

    public Personagem(String nome, String descricao, Local localizacao){
        this.nome = nome;
        this.descricao = descricao;
        this.localizacao = localizacao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Local getLocalizacao() {
        return localizacao;
    }

}

