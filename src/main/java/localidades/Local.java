package localidades;
import servivo.Jogador;

import java.io.Serializable;
import java.util.Random;

public class Local implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nome;
    private String descricao;


    public Local(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
    //Metodo assistir aula, utilizado em Sala de Aula e Laboratório
    protected void assistirAula(Jogador jogador, String acaoTexto) {
        if (!jogador.getInteragir()) return;

        Random r = new Random();
        int ganho = r.nextInt(10) + 1;

        jogador.aumentarConhecimento(ganho);
        jogador.aumentarDesempenhoacd(10);

        System.out.println(acaoTexto + " na " + getNome() + ": " + getDescricao());
        System.out.println("Professor: Parabéns, você ganhou " + ganho + " pontos de conhecimento e 10 pontos de desempenho acadêmico!");
    }

}