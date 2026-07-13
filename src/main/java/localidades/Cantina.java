package localidades;
import servivo.Jogador;

public class Cantina extends Local{
    private static final long serialVersionUID = 1L;
    private int valorPeca;

    public Cantina(String nome, String descricao) {
        super(nome, descricao);
        valorPeca = 5;

    }
    public int getPrecoLanche() {
        return valorPeca;
    }

    public void getInteracao(Jogador acao){
        acao.adquirirLanches(1);

    }
}
