package servivo;
import localidades.Local;

public class Gato extends Personagem {
    public Gato(String nome, String descricao, Local localizacao){
        super(nome, descricao, localizacao);
    }

    public void miar(){
        System.out.println("Miau!");
    }

    public void Interagir(Jogador acao) {
        if (acao.getInteragir()){
            System.out.println("Miau!");
            acao.aumentarMotivacao(5);
        }
    }

    @Override
    public Local getLocalizacao() {
        return super.getLocalizacao();
    }
}
