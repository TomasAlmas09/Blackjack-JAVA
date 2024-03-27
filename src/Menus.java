import java.util.Scanner;

public abstract class Menus {

    private static final int MIN_ENTRADA = 20;
    private static final int MIN_IDADE = 18;
    public static String nome_jogador;
    public static double saldo_jogador;
    public static String getNome() {
        return nome_jogador;
    }
    private static final Scanner scanner = new Scanner(System.in);
    public static void Entrada() {
        System.out.println("---------- ♠ ♥ ♦ ♣ CASINO LISBOA ♣ ♦ ♥ ♠ ----------\n");
        System.out.println("----- !!! ----- IDADE MÍNIMA: " + Menus.MIN_IDADE + "Anos ----- !!! -----");
        System.out.println("----- !!! ----- ENTRADA MÍNIMA: " + Menus.MIN_ENTRADA + "€ ----- !!! -----");

        }
    public static void aguardarEnter() {
        System.out.println("\nPressione ENTER para continuar...\n");
        scanner.nextLine();
    }

    public static void verificacao(){
        System.out.println("---------- ♠ ♥ ♦ ♣ CASINO LISBOA ♣ ♦ ♥ ♠ ----------\n");
        System.out.println("----- !!! ----- VERIFICAÇÃO DE ENTRADA ----- !!! -----\n");
        System.out.println("Insira o seu nome: ");
        nome_jogador=scanner.nextLine();
        System.out.println("Insira a sua idade: ");
        int idade= Integer.parseInt(scanner.nextLine());
        System.out.println("Com quanto dinheiro vai entrar: ");
        saldo_jogador= Integer.parseInt(scanner.nextLine());

        if (idade<18 || saldo_jogador<20){
            System.out.printf("\nA idade mínima são %d anos e o montante mínimo para entrar é %d€\n", Menus.MIN_IDADE, Menus.MIN_ENTRADA);
            System.exit(0);
        }


    }

    public static void jogos() {
        int verify = 0;
        System.out.println("\n---------- ♠ ♥ ♦ ♣ CASINO LISBOA ♣ ♦ ♥ ♠ ----------");
        System.out.println("\n---------- ♠ ♥ ♦ ♣ JOGOS DISPONÍVEIS ♣ ♦ ♥ ♠ ----------");
        System.out.println("\nSaldo disponivel:"+Menus.saldo_jogador+"€");

        for (JOGOS j : JOGOS.values()) {
            System.out.printf("\n%s", j);
        }

        while (verify == 0) {
            System.out.println("\n\nQue jogo deseja jogar?");
            String jogo_escolhido = scanner.nextLine().toLowerCase();

            if (jogo_escolhido.equals("blackjack")) {
                System.out.println("\nIniciando Blackjack...");
                System.out.println("\n---------- ♠ ♥ ♦ ♣ CASINO LISBOA ♣ ♦ ♥ ♠ ----------");
                System.out.println("\n---------- ♠ ♥ ♦ ♣ BLACKJACK ♣ ♦ ♥ ♠ ----------\n");
                Blackjack.JogarBJ();
                verify++;
            } else if (jogo_escolhido.equals("dados")) {
                System.out.println("\nIniciando Jogo de Dados...");
                System.out.println("\n---------- ♠ ♥ ♦ ♣ CASINO LISBOA ♣ ♦ ♥ ♠ ----------");
                System.out.println("\n---------- ♠ ♥ ♦ ♣ DADOS DA SORTE ♣ ♦ ♥ ♠ ----------\n");
                Dados.jogoDados();
                verify++;
            }
            else if (jogo_escolhido.equals("loja")) {
                System.out.println("\n---------- ♠ ♥ ♦ ♣ CASINO LISBOA ♣ ♦ ♥ ♠ ----------");
                System.out.println("\n---------- ♠ ♥ ♦ ♣ LOJA ♣ ♦ ♥ ♠ ----------\n");
                comprarFichas.loja();
                verify++;

            }
            else if (jogo_escolhido.equals("sair")) {
                System.out.println("\n---------- ♠ ♥ ♦ ♣ CASINO LISBOA ♣ ♦ ♥ ♠ ----------\n");
                System.out.println("\nObrigado por visitar o Casino Lisboa. Volte sempre "+nome_jogador+"!");
                verify++;
                scanner.close();
            }
            else {
                System.out.println("\nEscolha inválida. Por favor, escreva o nome corretamente.");

            }
        }
    }
}









