package servivo;
import java.util.Random;
import localidades.Local;

public class Professor extends Personagem {
    public Professor(String nome, String descricao, Local localizacao){
        super(nome, descricao, localizacao);
    }

    //Colocar dialogos em gerenciador de conversas
    public void Interagir(Jogador acao) {
        if (acao.getInteragir()) {
            Random r = new Random();
            int ganho = r.nextInt(10) + 1;
            acao.aumentarConhecimento(ganho);
            acao.aumentarDesempenhoacd(10);
            System.out.println("Professor: Parabéns, você ganhou " + ganho + " pontos de conhecimento e 10 pontos de desempenho acadêmico!");
        }
    }
}