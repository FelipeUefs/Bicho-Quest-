package controller;

import localidades.Cantina;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servivo.Jogador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GerenciadorDeDadosTest {

	private static final Path SAVE_DIR = Paths.get("src", "main", "resources", "saves");

	private final GerenciadorDeDados gerenciador = new GerenciadorDeDados();
	private Path arquivoTeste;

	@BeforeEach
	void setUp() throws IOException {
		// Garante a pasta de save.
		Files.createDirectories(SAVE_DIR);
	}

	@AfterEach
	void tearDown() throws IOException {
		// Remove o arquivo do teste.
		if (arquivoTeste != null) {
			Files.deleteIfExists(arquivoTeste);
		}
	}

	@Test
	void salvarJogoDeveGravarCamposBasicosELocalNulo() throws IOException {
		// Salva sem local.
		arquivoTeste = caminhoDoSave("salvar_jogador_sem_local");

		Jogador jogador = novoJogadorBase();
		jogador.setNome("Ana");
		jogador.setEnergia(18);
		jogador.setMotivacao(7);
		jogador.setDesempenhoAcademico(12.5);
		jogador.setSemestreAtual(2);
		jogador.setConhecimento(15);
		jogador.setPosicao(1, 3);
		jogador.setInteragir(true);
		jogador.setLocalAtual(null);

		gerenciador.salvarJogo(jogador, "salvar_jogador_sem_local", 1);

		List<String> linhas = Files.readAllLines(arquivoTeste);

		assertEquals(List.of(
				"Ana", "18", "0.0", "7", "12.5", "2", "15", "1", "3", "true", "1", "nenhum"
		), linhas);
	}

	@Test
	void salvarJogoDeveGravarNomeDoLocalQuandoExiste() throws IOException {
		// Salva com local.
		arquivoTeste = caminhoDoSave("salvar_jogador_com_local");

		Jogador jogador = novoJogadorBase();
		jogador.setNome("Bruno");
		jogador.setEnergia(20);
		jogador.setMotivacao(9);
		jogador.setDesempenhoAcademico(10.0);
		jogador.setSemestreAtual(2);
		jogador.setConhecimento(8);
		jogador.setPosicao(4, 5);
		jogador.setInteragir(false);
		jogador.setLocalAtual(new Cantina("Cantina", "Onde se abastece as energias"));

		gerenciador.salvarJogo(jogador, "salvar_jogador_com_local", 1);

		List<String> linhas = Files.readAllLines(arquivoTeste);

		assertEquals("Cantina", linhas.get(11));
		assertEquals(12, linhas.size());
	}

	@Test
	void carregarJogoDeveReconstruirJogadorDeArquivoManual() throws IOException {
		// Lê arquivo valido.
		arquivoTeste = caminhoDoSave("carregar_jogo_valido");
		Files.write(arquivoTeste, List.of(
				"Carla",
				"18",
				"7",
				"12.5",
				"2",
				"15",
				"1",
				"3",
				"true",
				"1",
				"Cantina"
		));

		Jogador jogador = GerenciadorDeDados.carregarJogo("carregar_jogo_valido");

		assertNotNull(jogador);
		assertEquals("Carla", jogador.getNome());
		assertEquals(18, jogador.getEnergia());
		assertEquals(7, jogador.getMotivacao());
		assertEquals(12.5, jogador.getDesempenhoAcademico(), 0.001);
		assertEquals(2, jogador.getSemestreAtual());
		assertEquals(15, jogador.getConhecimento());
		assertEquals(1, jogador.getLinha());
		assertEquals(3, jogador.getColuna());
		assertTrue(jogador.getInteragir());
		assertInstanceOf(Cantina.class, jogador.getLocalAtual());
	}

	@Test
	void carregarJogoDeveRetornarNullQuandoArquivoNaoExiste() {
		// Arquivo nao existe.
		arquivoTeste = caminhoDoSave("arquivo_inexistente");

		Jogador jogador = gerenciador.carregarJogo("arquivo_inexistente");

		assertNull(jogador);
	}

	@Test
	void carregarJogoDeveRetornarNullQuandoArquivoCorrompido() throws IOException {
		// Arquivo corrompido.
		arquivoTeste = caminhoDoSave("arquivo_corrompido");
		Files.write(arquivoTeste, List.of(
				"Diego",
				"18",
				"20.0",
				"sete",
				"12.5",
				"2",
				"15",
				"1",
				"3",
				"true",
				"1",
				"Cantina"
		));

		Jogador jogador = gerenciador.carregarJogo("arquivo_corrompido");

		assertNull(jogador);
	}

	private static Jogador novoJogadorBase() {
		return new Jogador.Builder().build();
	}

	private static Path caminhoDoSave(String nomeArquivo) {
		return SAVE_DIR.resolve(nomeArquivo + ".txt");
	}
}