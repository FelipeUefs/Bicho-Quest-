package localidades;
import controller.Prova;
import servivo.Jogador;

public class SaladeAula extends Local {
    private static final long serialVersionUID = 1L;

    public SaladeAula(String nome, String descricao) {
        super(nome, descricao);
    }

    public void aulaTeorica(Jogador acao) {
       assistirAula(acao, "Assistindo aula");
    }

    public void fazerProva(Jogador provaTeorica) {
        Prova prova = new Prova();
        prova.realizarProva(provaTeorica);
    }
    public void cursarDisciplina(Jogador jogador) {
        //Executa  lógica se o "getInteragir" for true
        if (jogador.getInteragir()) {
            if (jogador.getEnergia() >= 5) {
                System.out.println("Estudando: " + this.getNome());

                // Lógica de atributos
                int vigor = jogador.getEnergia() - 5;
                jogador.setEnergia(vigor);
                jogador.aumentarConhecimento(10);

                System.out.println("Conhecimento +10 | Energia atual: " + jogador.getEnergia());
            } else {
                System.out.println("Você está exausto demais para estudar!");
            }
        }
        //Se for false, o metodo termina sem fazer ou mostrar nada.
    }
}
