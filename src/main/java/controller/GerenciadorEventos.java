package controller;

import java.util.List;
import java.util.Random;

public class GerenciadorEventos {

    private List<Eventos> eventos;
    private Random random;

    public GerenciadorEventos(List<Eventos> eventos) {
        this.eventos = eventos;
        this.random = new Random();
    }

    public Eventos gerarEvento() {
        //Percorre os eventos dentro da lista, pegando suas probabilidades
        for (Eventos e : eventos) {
            //Cria Sorte para cada evento, e se o sorte for menor que a probabilidade do evento, ele retorna o evento
            int sorte = random.nextInt(100);

            if (sorte < e.getProbabilidade()) {
                return e;
            }
        }

        return null;
    }
}
