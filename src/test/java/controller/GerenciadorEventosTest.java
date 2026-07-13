package controller;

import localidades.Local;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GerenciadorEventosTest {

    @Test
    void gerarEventoRetornaPrimeiroQuandoSorteEhMenorQueProbabilidade() throws Exception {
        // Cenário: o primeiro evento ja atende a probabilidade.
        Eventos e1 = novoEvento("Evento 1", 50);
        Eventos e2 = novoEvento("Evento 2", 90);
        GerenciadorEventos gerenciador = new GerenciadorEventos(Arrays.asList(e1, e2));

        definirRandom(gerenciador, new RandomFixo(10)); // 10 < 50

        Eventos sorteado = gerenciador.gerarEvento();

        assertEquals(e1.getProbabilidade(), sorteado.getProbabilidade());
    }

    @org.testng.annotations.Test
    void gerarEventoPulaPrimeiroQuandoNaoPassaESelecionaSegundo() throws Exception {
        // Cenário: o primeiro falha e o segundo passa.
        Eventos e1 = novoEvento("Evento 1", 30);
        Eventos e2 = novoEvento("Evento 2", 80);
        GerenciadorEventos gerenciador = new GerenciadorEventos(Arrays.asList(e1, e2));

        definirRandom(gerenciador, new RandomFixo(30, 10)); // 30 !< 30, depois 10 < 80

        Eventos sorteado = gerenciador.gerarEvento();

        assertEquals(e2.getProbabilidade(), sorteado.getProbabilidade());
    }

    @Test
    void gerarEventoRetornaNullQuandoNenhumEventoPassa() throws Exception {
        // Cenário: nenhum evento atende a regra sorte < probabilidade.
        Eventos e1 = novoEvento("Evento 1", 20);
        Eventos e2 = novoEvento("Evento 2", 40);
        GerenciadorEventos gerenciador = new GerenciadorEventos(Arrays.asList(e1, e2));

        definirRandom(gerenciador, new RandomFixo(90, 90));

        Eventos sorteado = gerenciador.gerarEvento();

        assertNull(sorteado);
    }

    @Test
    void gerarEventoRetornaNullQuandoListaEstaVazia() {
        // Cenário: sem eventos cadastrados, retorno deve ser null.
        GerenciadorEventos gerenciador = new GerenciadorEventos(Collections.emptyList());

        Eventos sorteado = gerenciador.gerarEvento();

        assertNull(sorteado);
    }

    private static Eventos novoEvento(String nome, int probabilidade) {
        return new Eventos(nome, "desc", probabilidade, false, new Local("L", "D"));
    }

    private static void definirRandom(GerenciadorEventos gerenciador, Random random) throws Exception {
        // Injeta random fixo para deixar o teste deterministico.
        Field campoRandom = GerenciadorEventos.class.getDeclaredField("random");
        campoRandom.setAccessible(true);
        campoRandom.set(gerenciador, random);
    }

    private static class RandomFixo extends Random {
        // Random controlado para reproduzir exatamente os sorteios.
        private final int[] valores;
        private int indice;

        private RandomFixo(int... valores) {
            this.valores = valores;
        }

        @Override
        public int nextInt(int bound) {
            if (indice >= valores.length) {
                throw new IllegalStateException("Sem valores fixos suficientes para o teste");
            }
            int valor = valores[indice++];
            if (valor < 0 || valor >= bound) {
                throw new IllegalArgumentException("Valor fixo fora do intervalo esperado");
            }
            return valor;
        }
    }
}

