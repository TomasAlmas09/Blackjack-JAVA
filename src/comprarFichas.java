import java.util.Scanner;

public class comprarFichas {

    public static void loja() {
        Scanner scanner = new Scanner(System.in);


        exibirSaldo();

        System.out.println("Deseja comprar fichas? (s/n)");
        String resposta = scanner.next().toLowerCase();

        if (resposta.equals("s")) {
            exibirFichas(); // Movido para fora do bloco if
            System.out.println("Escolha a ficha que deseja comprar:");

            int escolha = scanner.nextInt();

            if (escolha >= 1 && escolha <= Ficha.values().length) {
                System.out.println("Quantidade desejada:");
                int quantidade = scanner.nextInt();
                comprarFichas(Ficha.values()[escolha - 1], quantidade);
                exibirSaldo();
                Menus.jogos();
            } else {
                System.out.println("Escolha inválida.");
            }
        }
        else {
            Menus.jogos();
        }


    }

    public static void comprarFichas(Ficha ficha, int quantidade) {
        double custoTotal = quantidade * ficha.getValor();

        if (custoTotal > Menus.saldo_jogador) {
            System.out.println("\nVocê não tem saldo suficiente para comprar essa quantidade de fichas.");
        } else {
            Menus.saldo_jogador -= custoTotal;
            System.out.println("\nVocê comprou " + quantidade + " ficha(s) " + ficha + ", gastou " + custoTotal + "€.");
        }
    }

    private static void exibirSaldo() {
        System.out.println("O seu saldo atual é " + Menus.saldo_jogador + "€.");

    }

    private static void exibirFichas() {
        System.out.println("\n---------- ♠ ♥ ♦ ♣ FICHAS CASINO LISBOA ♣ ♦ ♥ ♠ ----------\n");
        for (int i = 0; i < Ficha.values().length; i++) {
            System.out.println((i + 1) + ". " + Ficha.values()[i] + " - Valor: " + Ficha.values()[i].getValor() + "€");
        }
    }
}
