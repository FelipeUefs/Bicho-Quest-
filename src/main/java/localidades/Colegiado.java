package localidades;
import servivo.Maeli;
import servivo.Jogador;

public class Colegiado extends Local {
    private static final long serialVersionUID = 1L;
    private transient Maeli maeli; //Ajusta Maeli para não ser Salva no arquivo, pois ela é "Temporária" e pode ser recriada quando o jogo for carregado.
    public Colegiado(String nome, String descricao) {
        super(nome, descricao);
        this.maeli = new Maeli("Maeli", "NPC do Colegiado", this);
        //Cria a NPC Maeli e a coloca dentro do Colegiado
    }

        public Maeli getMaeli() {
            return maeli;
        }

        public void interagirComMaeli(Jogador jogador) {
            maeli.Interagir(jogador);
            //Faz as interações de Maeli com o Jogador.

        }
        //Metodo para recuperar energia do Jogador ao descansar no Colegiado
        public String descansar(String nomeJogador, Jogador acao) {
            if (acao.getLocalAtual() instanceof Colegiado) {
                acao.setEnergia(25);
                return nomeJogador + " descansou no Colegiado.";
            } else {
                return "Você não está no Colegiado, então não pode descansar aqui.";
            }
        }
}