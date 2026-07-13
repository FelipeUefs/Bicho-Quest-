package localidades;

import org.junit.jupiter.api.Test;
import servivo.Jogador;
import servivo.Maeli;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ColegiadoTest {

    @Test
    void getMaeliDeveRetornarNpcInicializadaNoConstrutor() {
        // Cenário: Maeli deve ser criada junto com o Colegiado.
        Colegiado colegiado = new Colegiado("Colegiado", "Atendimento academico");

        Maeli maeli = colegiado.getMaeli();

        assertNotNull(maeli);
        assertEquals("Maeli", maeli.getNome());
        assertSame(colegiado, maeli.getLocalizacao());
    }

    @Test
    void interagirComMaeliNaoDeveLancarExcecaoQuandoInteragirFalse() {
        // Cenário: sem interagir, chamada nao deve quebrar.
        Colegiado colegiado = new Colegiado("Colegiado", "Atendimento academico");
        Jogador jogador = new Jogador.Builder()
            .setInteragir(false)
            .build();

        assertDoesNotThrow(() -> colegiado.interagirComMaeli(jogador));
    }

    @Test
    void descansarNoColegiadoDeveRecuperarEnergiaParaVinteECinco() {
        // Cenário: jogador esta no Colegiado e descansa.
        Colegiado colegiado = new Colegiado("Colegiado", "Atendimento academico");
        Jogador jogador = new Jogador.Builder()
            .setEnergia(8)
            .setLocalAtual(colegiado)
            .build();

        String retorno = colegiado.descansar("Felipe", jogador);

        assertEquals("Felipe descansou no Colegiado.", retorno);
        assertEquals(25, jogador.getEnergia());
    }

    @Test
    void descansarForaDoColegiadoNaoDeveAlterarEnergia() {
        // Cenário: jogador fora do Colegiado nao pode descansar.
        Colegiado colegiado = new Colegiado("Colegiado", "Atendimento academico");
        Jogador jogador = new Jogador.Builder()
            .setEnergia(8)
            .setLocalAtual(new Local("Cantina", "Lanche"))
            .build();

        String retorno = colegiado.descansar("Felipe", jogador);

        assertEquals("Você não está no Colegiado, então não pode descansar aqui.", retorno);
        assertEquals(8, jogador.getEnergia());
    }
}

