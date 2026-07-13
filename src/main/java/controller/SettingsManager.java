package controller;

/**
 * SettingsManager implementa o padrão Singleton para centralizar configurações
 * do jogo (ex: pasta de saves). Isso evita constantes espalhadas e facilita
 * futuras mudanças de configuração em único ponto.
 */
public class SettingsManager {
    private static SettingsManager instance;

    // Exemplo de configuração centralizada
    // Valor padrão atualizado para a pasta dentro de resources, compatível com os testes
    private String pastaSave = "saves/";

    private SettingsManager() {
        // construtor privado para impedir instanciação externa
    }

    public static synchronized SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }

    public String getPastaSave() {
        return pastaSave;
    }

    public void setPastaSave(String pastaSave) {
        this.pastaSave = pastaSave;
    }
}

