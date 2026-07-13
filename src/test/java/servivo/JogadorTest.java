package servivo;

import org.junit.jupiter.api.Test;

import localidades.Cantina;
import localidades.Local;
import localidades.PontoDeOnibus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JogadorTest {

    @Test
    void testAdquirirLanchesInCantinaComInteracao() {
        // Cenário: jogador na cantina com interação ativa.
        Jogador jogador = new Jogador.Builder()
            .setEnergia(10)
            .setDinheiro(0.0)
            .setInteragir(true)
            .setLocalAtual(new Cantina("CantinaTest", "Teste"))
            .build();
        // Simula gasto anterior
        jogador.gastarDinheiro(20.0);

        // Acao: compra 2 lanches.
        jogador.adquirirLanches(2); // 2 lanches x R$5 => -10 dinheiro, +10 energia

        // Validacao: dinheiro diminui e energia aumenta.
        assertEquals(10.0, jogador.getDinheiro(), 0.0001);
        assertEquals(20, jogador.getEnergia());
    }

    @Test
    void testAdquirirLanchesForaDaCantinaNaoAlteraStatus() {
        // Cenário: local atual nao e cantina.
        Jogador jogador = new Jogador.Builder()
            .setEnergia(10)
            .setDinheiro(0.0)
            .setInteragir(true)
            .setLocalAtual(new Local("Rua", "Nao cantina"))
            .build();
        jogador.gastarDinheiro(20.0);

        // Acao: tenta comprar lanche fora da cantina.
        jogador.adquirirLanches(1);

        // Validacao: status permanece igual.
        assertEquals(20.0, jogador.getDinheiro(), 0.0001);
        assertEquals(10, jogador.getEnergia());
    }

    @Test
    void testPegarOnibusCasaComInteracaoEDinheiroSuficiente() {
        // Cenário: jogador no ponto, com dinheiro e interação ativa.
        Jogador jogador = new Jogador.Builder()
            .setDinheiro(0.0)
            .setInteragir(true)
            .setLocalAtual(new PontoDeOnibus("Ponto", "Teste"))
            .build();
        jogador.gastarDinheiro(5.0);

        // Acao: pega onibus.
        jogador.pegarOnibusCasa();

        // Validacao: valor da passagem e descontado.
        assertEquals(2.30, jogador.getDinheiro(), 0.001);
    }

    @Test
    void testPegarOnibusCasaSemInteracaoNaoCobraPassagem() {
        // Cenário: jogador no ponto, mas sem interagir.
        Jogador jogador = new Jogador.Builder()
            .setDinheiro(5.0)
            .setInteragir(false)
            .setLocalAtual(new PontoDeOnibus("Ponto", "Teste"))
            .build();

        // Acao: tenta pegar onibus sem interagir.
        jogador.pegarOnibusCasa();

        // Validacao: nao deve haver desconto.
        assertEquals(5.0, jogador.getDinheiro(), 0.001);
    }
}
