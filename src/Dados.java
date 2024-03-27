import java.util.Random;
import java.util.Scanner;

// Implementação do jogo de dados
class JogoDeDados implements JogoDados {
    @Override
    public void jogar(int numeroEscolhido, double aposta) {
        int tentativasRestantes = 3;

        while (tentativasRestantes > 0) {
            System.out.println("\nTentativa restante: " + tentativasRestantes);
            System.out.print("Qual é o resultado da soma dos dados (2 a 12)? ");

            Scanner scanner = new Scanner(System.in);
            int respostaUsuario = scanner.nextInt();

            if (respostaUsuario < 2 || respostaUsuario > 12) {
                System.out.println("Número inválido. Tente novamente.");
                continue;
            }

            int somaDados = rolarDado() + rolarDado();
            System.out.println("Soma dos dados: " + somaDados);

            if (respostaUsuario == somaDados) {
                double ganho = aposta;
                System.out.println("\nParabéns! Você ganhou " + ganho*2 + "€! A soma dos dados foi exatamente igual ao número escolhido.");
                Menus.saldo_jogador += ganho;
                break;
            } else {
                System.out.println("\nVocê errou! A soma dos dados não foi igual ao número escolhido.");

                if (somaDados > respostaUsuario) {
                    System.out.println("A soma dos dados é maior do que o número escolhido.");
                } else {
                    System.out.println("A soma dos dados é menor do que o número escolhido.");
                }

                tentativasRestantes--;
            }
        }

        if (tentativasRestantes == 0) {
            System.out.println("Suas tentativas acabaram. Você perdeu a aposta de " + aposta + "€.");
            Menus.saldo_jogador -= aposta;
        }

        System.out.println("Saldo atual: " + Menus.saldo_jogador + "€");
    }

    private int rolarDado() {
        Random random = new Random();
        return random.nextInt(6) + 1; // Números entre 1 e 6
    }
}

// Classe principal que interage com o usuário
public class Dados {

    public static void jogoDados() {
        while (true) {
            jogarJogoDeDados();

            System.out.println("\nQuer jogar novamente? (s/n)");
            Scanner scanner = new Scanner(System.in);
            String resposta = scanner.next().toLowerCase();
            if (!resposta.equals("s")) {
                break;
            }
        }

        Menus.jogos();
    }

    private static void jogarJogoDeDados() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Saldo: " + Menus.saldo_jogador + "€");

        System.out.print("\nInsira a sua aposta: ");
        double aposta = scanner.nextDouble();

        if (aposta <= 0 || aposta > Menus.saldo_jogador) {
            System.out.println("\nAposta inválida. Tente novamente.");
            return;
        }

        JogoDeDados jogoDeDados = new JogoDeDados();
        jogoDeDados.jogar(0, aposta); // O número escolhido é irrelevante, pois não será usado
    }
}
