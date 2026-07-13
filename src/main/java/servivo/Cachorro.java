package servivo;
import localidades.Local;

public class Cachorro extends Personagem {
    public Cachorro(String nome, String descricao, Local localizacao){
        super(nome, descricao, localizacao);
    }

    public void latir(){
        System.out.println("AU AU!");
    }

    public void Interagir(Jogador acao) {
        if (acao.getInteragir()){
            System.out.println("AU AU!");
            acao.aumentarMotivacao(5);
        }
    }

}
