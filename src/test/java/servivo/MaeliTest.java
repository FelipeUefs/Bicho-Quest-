package servivo;

import localidades.Local;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaeliTest {

    @Test
    void interagirComJogadorSemInteracaoNaoDeveImprimirNada() {
        // Cenário: jogador nao interage, entao Maeli nao responde.
        Maeli maeli = new Maeli("Maeli", "NPC", new Local("Colegiado", "Atendimento"));
        Jogador jogador = new Jogador.Builder()
            .setInteragir(false)
            .build();

        String saida = capturarSaida(() -> maeli.Interagir(jogador));

        assertTrue(saida.trim().isEmpty());
    }

    @Test
    void segundaInteracaoNoMesmoSemestreDeveBloquearNovaDica() {
        // Cenário: primeira interacao da dica, segunda bloqueada no mesmo semestre.
        Maeli maeli = new Maeli("Maeli", "NPC", new Local("Colegiado", "Atendimento"));
        Jogador jogador = new Jogador.Builder()
            .setInteragir(true)
            .build();

        String primeiraSaida = capturarSaida(() -> maeli.Interagir(jogador));
        String segundaSaida = capturarSaida(() -> maeli.Interagir(jogador));

        assertTrue(primeiraSaida.contains("Maeli: "));
        assertTrue(segundaSaida.contains("te dei uma dica"));
    }

    @Test
    void novoSemestreDevePermitirNovaDicaAposBloqueio() {
        // Cenário: apos reset do semestre, Maeli pode dar dica novamente.
        Maeli maeli = new Maeli("Maeli", "NPC", new Local("Colegiado", "Atendimento"));
        Jogador jogador = new Jogador.Builder()
            .setInteragir(true)
            .build();

        maeli.Interagir(jogador);
        maeli.Interagir(jogador);
        maeli.novoSemestre();

        String saidaAposReset = capturarSaida(() -> maeli.Interagir(jogador));

        assertTrue(saidaAposReset.contains("Maeli: "));
        assertFalse(saidaAposReset.contains("te dei uma dica"));
    }

    private String capturarSaida(Runnable acao) {
        // Utilitario para validar mensagens impressas no console.
        PrintStream saidaOriginal = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream captura = new PrintStream(buffer);
        try {
            System.setOut(captura);
            acao.run();
            captura.flush();
            return buffer.toString();
        } finally {
            System.setOut(saidaOriginal);
            captura.close();
        }
    }
}

