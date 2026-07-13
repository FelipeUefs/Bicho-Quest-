package controller;

import localidades.Local;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventosTest {

    @Test
    void construtorDeveInicializarTodosOsCampos() {
        // Cenário: validar todos os campos inicializados pelo construtor.
        Local localFixo = new Local("Cantina", "Local para lanche");

        Eventos evento = new Eventos(
                "Greve",
                "Aula cancelada",
                35,
                true,
                localFixo
        );

        assertEquals("Greve", evento.getNome());
        assertEquals("Aula cancelada", evento.getDescricao());
        assertEquals(35, evento.getProbabilidade());
        assertTrue(evento.isObrigatorio());
        assertSame(localFixo, evento.getLocalFixo());
    }

    @Test
    void deveManterProbabilidadeNosExtremosValidos() {
        // Cenário: limites comuns de probabilidade (0 e 100).
        Local localFixo = new Local("Ponto", "Saida");

        Eventos eventoProbZero = new Eventos("E1", "Desc", 0, false, localFixo);
        Eventos eventoProbCem = new Eventos("E2", "Desc", 100, false, localFixo);

        assertEquals(0, eventoProbZero.getProbabilidade());
        assertEquals(100, eventoProbCem.getProbabilidade());
        assertFalse(eventoProbZero.isObrigatorio());
        assertFalse(eventoProbCem.isObrigatorio());
    }
}

