package servivo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import localidades.Local;


public class Maeli extends Personagem {
    private List<String> dicas;
    private int dicasUsadas;
        public Maeli(String nome, String descricao, Local localizacao){
            super(nome, descricao, localizacao);
            dicas = new ArrayList<>();
            dicasUsadas = 0;
            //Dicas Gerais
            dicas.add("Não tente fazer tudo de uma vez. Universidade é maratona, não corrida de 100 metros.");
            dicas.add("Sempre acompanhe seus atributos. Ignorar energia e saúde pode custar caro depois.");
            dicas.add("Planejamento de turno é tudo. Cada semana mal usada vira dor de cabeça na prova.");

            //Energia e saúde
            dicas.add("Não deixe para estudar só na semana de prova… você já sabe o que acontece.");
            dicas.add("Conhecimento acumulado aumenta suas chances em eventos importantes.");
            dicas.add("Disciplinas difíceis exigem preparo antecipado. Não subestime programação e hardware.");

            //Conhecimento e desempenho
            dicas.add("Ficar sem dinheiro pode te impedir de comer ou comprar materiais importantes.");
            dicas.add("Sempre guarde um pouco, eventos inesperados acontecem o tempo todo.");
            dicas.add("A cantina é tentadora… mas cuidado para não falir no meio do semestre.");

            //Dinheiro
            dicas.add("Ficar sem dinheiro pode te impedir de comer ou comprar materiais importantes.");
            dicas.add("Sempre guarde um pouco. Eventos inesperados acontecem o tempo todo.");
            dicas.add("A cantina é tentadora… mas cuidado para não falir no meio do semestre.");

            //Eventos aleatórios
            dicas.add("Provas surpresa acontecem. Esteja minimamente preparado sempre.");
            dicas.add("Nem todo evento ruim pode ser evitado… mas você pode reduzir o impacto.");
            dicas.add("Às vezes a sorte ajuda — mas não confie só nela.");

            //Progressão no curso
            dicas.add("Reprovar uma disciplina atrasa sua formatura. Escolha bem suas ações.");
            dicas.add("Equilibrar estudo e setEnergia é a chave para passar em todas as matérias.");
            dicas.add("Formar-se não é só passar — é sobreviver ao processo.");
        }

    public void Interagir(Jogador acao) {
        if (!acao.getInteragir()){
            return;
        }
        if (dicasUsadas  >= 1){
            System.out.println("Maeli: Já te dei uma dica!");
            return;
        }
        Random aleatorio = new Random();
        int indice = aleatorio.nextInt(dicas.size()); //Pega o tamanho da lista e define um indice
        System.out.println("Maeli: " + dicas.get(indice)); //Imprime a dica
        dicasUsadas++;
    }
    public void novoSemestre(){
            dicasUsadas = 0;
    }

}
