package servivo;
import java.util.Random;
import localidades.Local;

public class Colegas extends Personagem{

    public Colegas(String nome, String descricao, Local localizacao) {
        super(nome, descricao, localizacao);
    }

    //Colocar dialogos em gerenciador de conversas
    public void Interagir(Jogador acao) {
        if (acao.getInteragir()) {
            Random r = new Random();
            int ganho = r.nextInt(5) + 1;
            acao.aumentarConhecimento(ganho);
            System.out.println("Colega: Ei, aqui está uma dica de estudo que pode te ajudar! Você ganhou pontos de conhecimento!");
            }
        }
}

