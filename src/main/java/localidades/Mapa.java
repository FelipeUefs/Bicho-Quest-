package localidades;

public class Mapa {

    private Local[][] mapa;
    private int linhas;
    private int colunas;

    public Mapa(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        mapa = new Local[linhas][colunas];
    }

    public void setLocal(int i, int j, Local local) {
        mapa[i][j] = local;
    }

    public Local getLocal(int i, int j) {
        return mapa[i][j];
    }

    public void mostrarMapa(int jogadorLinha, int jogadorColuna) {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {

                if (i == jogadorLinha && j == jogadorColuna) {
                    System.out.print(" [J] ");
                } else if (mapa[i][j] instanceof SaladeAula) {
                    System.out.print(" [S] ");
                } else if (mapa[i][j] instanceof Cantina) {
                    System.out.print(" [C] ");
                } else {
                    System.out.print(" [ ] ");
                }
            }
            System.out.println();
        }
    }
}