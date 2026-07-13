package controller;
import servivo.Jogador;
import localidades.*;
import java.io.*;


public class GerenciadorDeDados {
    public static class DadosJogo {
        public Jogador jogador;
        public int turnoAtual;
        public DadosJogo(Jogador jogador, int turnoAtual) {
            this.jogador = jogador;
            this.turnoAtual = turnoAtual;
        }
    }
    // Obtém a pasta de saves via Singleton SettingsManager
    private final String pastaSave = SettingsManager.getInstance().getPastaSave();
    //Salva estado atual do jogador.
    public void salvarJogo(Jogador jogador, String nomeArquivo, int turnoAtual){
        // Monta o caminho do arquivo de save dentro de resources/saves.
        String caminho = pastaSave + nomeArquivo + ".txt";
        // Garante que a pasta existe antes de salvar
        File dir = new File(pastaSave);
        if (!dir.exists()) dir.mkdirs();
        // try-with-resources garante fechamento automatico do writer.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            // Cada atributo eh salvo em uma linha para facilitar a leitura depois.
            writer.write(jogador.getNome() + "\n");
            writer.write(jogador.getEnergia() + "\n");
            writer.write(jogador.getDinheiro() + "\n");
            writer.write(jogador.getMotivacao() + "\n");
            writer.write(jogador.getDesempenhoAcademico() + "\n");
            writer.write(jogador.getSemestreAtual() + "\n");
            writer.write(jogador.getConhecimento() + "\n");
            writer.write(jogador.getLinha() + "\n");
            writer.write(jogador.getColuna() + "\n");
            writer.write(jogador.getInteragir() + "\n");
            writer.write(turnoAtual + "\n");

            // Se houver local atual, salva o nome; caso contrario, marca como "nenhum".
            if (jogador.getLocalAtual() != null){
                writer.write(jogador.getLocalAtual().getNome() + "\n");
            }
            else {
                writer.write("nenhum\n");
            }

            System.out.println("Jogo salvo com sucesso!");
        } catch (IOException e) {
            // Captura erros de IO, como pasta inexistente ou falta de permissao.
            System.out.println("Erro ao salvar o jogo: " + e.getMessage());
        }
    }

            // Método interno que lê o arquivo e retorna DadosJogo (usado por chamadas estáticas/instância)
            private static DadosJogo carregarDadosInterno(String nomeArquivo, String pastaSave) {
                String caminho = pastaSave + nomeArquivo + ".txt";
                Jogador jogador = new Jogador();
                try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
                    jogador.setNome(reader.readLine());
                    jogador.setEnergia(Integer.parseInt(reader.readLine()));
                    jogador.setDinheiro(Double.parseDouble(reader.readLine()));
                    jogador.setMotivacao(Integer.parseInt(reader.readLine()));
                    jogador.setDesempenhoAcademico(Double.parseDouble(reader.readLine()));
                    jogador.setSemestreAtual(Integer.parseInt(reader.readLine()));
                    jogador.setConhecimento(Integer.parseInt(reader.readLine()));
                    jogador.setPosicao(Integer.parseInt(reader.readLine()), Integer.parseInt(reader.readLine()));
                    jogador.setInteragir(Boolean.parseBoolean(reader.readLine()));
                    int turnoCarregado = Integer.parseInt(reader.readLine());
                    String nomeLocal = reader.readLine();
                    if (nomeLocal != null) {
                        jogador.setLocalAtual(localidades.LocalFactory.criarPorNome(nomeLocal));
                    }
                    return new DadosJogo(jogador, turnoCarregado);
                } catch (IOException e) {
                    System.out.println("Erro ao carregar o jogo: " + e.getMessage());
                    return null;
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Arquivo Corrompido: " + e.getMessage());
                    return null;
                }
            }

    // Instância: retorna DadosJogo (mantém compatibilidade com UI que precisa do turno)
    public DadosJogo carregarDadosJogo(String nomeArquivo){
        return carregarDadosInterno(nomeArquivo, this.pastaSave);
    }

    // Estático: conveniência usada em testes - retorna apenas o Jogador (ou null)
    public static Jogador carregarJogo(String nomeArquivo) {
        DadosJogo dados = carregarDadosInterno(nomeArquivo, SettingsManager.getInstance().getPastaSave());
        return dados != null ? dados.jogador : null;
    }

}
