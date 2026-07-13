package localidades;
import servivo.Jogador;

public class PontoDeOnibus extends Local{
    private static final long serialVersionUID = 1L;
    private double valorPassagem;
    public PontoDeOnibus(String nome, String descricao){
        super(nome,descricao);
        valorPassagem = 2.70;
    }

    public double getValorPassagem() {
        return valorPassagem;
    }

    public void getInteracao(Jogador acao){
        acao.pegarOnibusCasa();
    }
}
