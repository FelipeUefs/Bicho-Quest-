package servivo;

/**
 * Interface Observer para receber notificações quando o estado do Jogador muda.
 * Padrão: Observer
 */
public interface JogadorObserver {
    /**
     * Chamado quando o estado do jogador é atualizado.
     * @param jogador jogador que mudou
     * @param evento descrição curta do que mudou (ex: "energia", "dinheiro")
     */
    void onAtualizacao(Jogador jogador, String evento);
}

