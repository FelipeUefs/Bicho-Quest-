package controller;

import servivo.Jogador;

public class Prova {
    // Variáveis para controlar as duas matérias do semestre
    private static boolean passouMateria1 = false;
    private static boolean passouMateria2 = false;

    public void realizarProva(Jogador jogador) {
        // Exemplo de lógica: se o desempenho for maior que um limite, aprova
        // Aqui simula que ele tenta passar primeiro na matéria 1, depois na 2
        if (!passouMateria1) {
            passouMateria1 = true; // Simulação de aprovação
            jogador.setDesempenhoAcademico(jogador.getDesempenhoAcademico() + 1.0);
        } else if (!passouMateria2) {
            passouMateria2 = true; // Simulação de aprovação na segunda
            jogador.setDesempenhoAcademico(jogador.getDesempenhoAcademico() + 1.0);
        }
    }

    // Verifica se passou nas duas disciplinas
    public boolean passouNasDuasMaterias() {
        return passouMateria1 && passouMateria2;
    }

    // Reseta o estado para o próximo semestre
    public void resetarMaterias() {
        passouMateria1 = false;
        passouMateria2 = false;
    }
}