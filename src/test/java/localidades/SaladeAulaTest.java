package localidades;

import org.junit.jupiter.api.Test;
import servivo.Jogador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaladeAulaTest {

    @Test
    void aulaTeoricaComInteracaoAumentaConhecimentoEDesempenho() {
        // Cenário: aluno interage com a aula teorica.
        SaladeAula sala = new SaladeAula("Sala 1", "Aula de POO");
        Jogador jogador = new Jogador.Builder()
            .setInteragir(true)
            .build();

        int conhecimentoInicial = jogador.getNivelDeConhecimento();
        double desempenhoInicial = jogador.getDesempenhoAcademico();

        // Acao: assistir aula.
        sala.aulaTeorica(jogador);

        // Validacao: conhecimento sobe (1 a 10) e desempenho sobe 10.
        int ganhoConhecimento = jogador.getNivelDeConhecimento() - conhecimentoInicial;
        assertTrue(ganhoConhecimento >= 1 && ganhoConhecimento <= 10);
        assertEquals(desempenhoInicial + 10, jogador.getDesempenhoAcademico(), 0.001);
    }

    @Test
    void aulaTeoricaSemInteracaoNaoAlteraAtributos() {
        // Cenário: sem interacao, nao deve mudar atributos.
        SaladeAula sala = new SaladeAula("Sala 1", "Aula de POO");
        Jogador jogador = new Jogador.Builder()
            .setInteragir(false)
            .build();

        int conhecimentoInicial = jogador.getNivelDeConhecimento();
        double desempenhoInicial = jogador.getDesempenhoAcademico();

        sala.aulaTeorica(jogador);

        assertEquals(conhecimentoInicial, jogador.getNivelDeConhecimento());
        assertEquals(desempenhoInicial, jogador.getDesempenhoAcademico(), 0.001);
    }

    @Test
    void cursarDisciplinaComEnergiaSuficienteConsomeEnergiaEAumentaConhecimento() {
        // Cenário: energia suficiente para estudar.
        SaladeAula sala = new SaladeAula("Sala 1", "Aula de POO");
        Jogador jogador = new Jogador.Builder()
            .setInteragir(true)
            .setEnergia(10)
            .build();

        int conhecimentoInicial = jogador.getNivelDeConhecimento();

        sala.cursarDisciplina(jogador);

        assertEquals(5, jogador.getEnergia());
        assertEquals(conhecimentoInicial + 10, jogador.getNivelDeConhecimento());
    }

    @Test
    void cursarDisciplinaComEnergiaInsuficienteNaoAlteraStatus() {
        // Cenário: energia baixa, estudo deve ser bloqueado.
        SaladeAula sala = new SaladeAula("Sala 1", "Aula de POO");
        Jogador jogador = new Jogador.Builder()
            .setInteragir(true)
            .setEnergia(4)
            .build();

        int conhecimentoInicial = jogador.getNivelDeConhecimento();

        sala.cursarDisciplina(jogador);

        assertEquals(4, jogador.getEnergia());
        assertEquals(conhecimentoInicial, jogador.getNivelDeConhecimento());
    }

    @Test
    void cursarDisciplinaSemInteracaoNaoAlteraStatus() {
        // Cenário: sem interagir, nao deve consumir energia nem dar conhecimento.
        SaladeAula sala = new SaladeAula("Sala 1", "Aula de POO");
        Jogador jogador = new Jogador.Builder()
            .setInteragir(false)
            .setEnergia(10)
            .build();

        int conhecimentoInicial = jogador.getNivelDeConhecimento();

        sala.cursarDisciplina(jogador);

        assertEquals(10, jogador.getEnergia());
        assertEquals(conhecimentoInicial, jogador.getNivelDeConhecimento());
    }

    @Test
    void fazerProvaMantemDesempenhoEmFaixaValida() {
        // Cenário: prova tem resultado aleatorio, mas desempenho final e previsivel.
        SaladeAula sala = new SaladeAula("Sala 1", "Aula de POO");
        Jogador jogador = new Jogador.Builder()
            .setNivelDeConhecimento(5)
            .build();

        sala.fazerProva(jogador);

        double desempenho = jogador.getDesempenhoAcademico();
        assertTrue(desempenho == 0.0 || desempenho == 15.0);
    }
}

