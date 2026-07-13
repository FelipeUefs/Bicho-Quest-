package localidades;
import controller.Prova;
import servivo.Jogador;

public class Laboratorio extends Local {
    private static final long serialVersionUID = 1L;

    public Laboratorio(String nome, String descricao) {
        super(nome, descricao);
    }
    public void aulaPratica(Jogador praticar) {
        assistirAula(praticar, "Praticando no laboratório");
    }

    public void fazerProva(Jogador provaPratica){
        Prova prova = new Prova();
        prova.realizarProva(provaPratica);
    }

    public void cursarDisciplina(Jogador jogador) {
        // Só executa qualquer lógica ou print se o "getInteragir" for true
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
        // Se for false, o metodo termina sem fazer ou mostrar nada.
    }

}
