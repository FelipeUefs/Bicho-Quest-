package localidades;
import servivo.Jogador;

public class Lojinha extends Local{
    private static final long serialVersionUID = 1L;
    private double valorProduto;
    public Lojinha(String nome, String descricao){
        super(nome,descricao);
        valorProduto = 7;
    }

    public double getValorProduto() {
        return valorProduto;
    }

    public void comprarProduto(Jogador comprar, int quantidade){
        if (comprar.getLocalAtual() instanceof Lojinha) {
            Lojinha lojinha = (Lojinha) comprar.getLocalAtual();//como já foi verificado no instanceof, podemos afirmar que aqui é a catina
            if (comprar.getDinheiro() < 7) {
                System.out.println("Seu dinheiro é insuficiente para comprar produtos na lojinha!");
            } else {
                // Diminuir dinheiro e aumentar energia ou saude se estiverem baixos
                double valorLanche = quantidade * lojinha.getValorProduto();//Calcula o quanto será gasto com lanches;
                if (comprar.getDinheiro() >= valorLanche){
                    double dinheiro = comprar.getDinheiro();
                    dinheiro -= valorLanche; //Atualiza o valor do dinheiro do jogador
                    comprar.gastarDinheiro(dinheiro);//Volta como gasto para o Jogador
                    System.out.println("Lanches Comprados, foram gastos R$ " + valorLanche);
                }
            }
        }
        else {
            System.out.println("Você não está na Lojinha, então não pode comprar materiais");
        }
    }
}
