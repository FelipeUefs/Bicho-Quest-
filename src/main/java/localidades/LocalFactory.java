package localidades;

/**
 * Factory para criar instâncias de Local a partir de um nome.
 * Padrão: Factory Method
 * Utilidade: centraliza a lógica de criação de objetos Local, evitando switch/case espalhados
 * e facilitando adição de novos tipos no futuro.
 */
public class LocalFactory {

    /**
     * Cria um Local apropriado com base no nome salvo.
     * Retorna null se o nome não corresponder a nenhum Local conhecido.
     */
    public static Local criarPorNome(String nomeLocal) {
        if (nomeLocal == null) return null;

        switch (nomeLocal) {
            case "Cantina":
                return new Cantina("Cantina", "Onde se Abastece as Energias");
            case "Ponto de Onibus":
                return new PontoDeOnibus("Ponto de Onibus", "Sair ou Voltar pro Campus. Passagem R$ 2.70");
            case "Colegiado":
                return new Colegiado("Colegiado", "Onde está a salvadora Maeli");
            case "Sala de Aula":
                return new SaladeAula("Sala de Aula", "Onde as aulas acontecem");
            case "Laboratorio":
                return new Laboratorio("Laboratório", "Onde os experimentos acontecem");
            case "Lojinha":
                return new Lojinha("Lojinha", "Onde se compra materiais para os estudos");
            default:
                return null;
        }
    }
}

