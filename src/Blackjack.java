import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Classe Carta representa uma carta do baralho
class Carta {
    private String valor;
    private NAIPES naipe;

    public Carta(String valor, NAIPES naipe) {
        this.valor = valor;
        this.naipe = naipe;
    }

    public String getDescricao() {
        return valor + " de " + naipe;
    }

    @Override
    public String toString() {
        return getDescricao();
    }

    public int getValorNumerico() {
        if (valor.equals("Ás")) {
            return 11;
        } else if (valor.equals("Valete") || valor.equals("Dama") || valor.equals("Rei")) {
            return 10;
        } else {
            return Integer.parseInt(valor);
        }
    }
}

// Classe Baralho representa um baralho de cartas
class Baralho {
    private ArrayList<Carta> cartas;

    public Baralho() {
        cartas = new ArrayList<>();
        inicializarBaralho();
        embaralhar();
    }

    private void inicializarBaralho() {
        String[] valores = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Valete", "Dama", "Rei", "Ás"};
        for (String valor : valores) {
            for (NAIPES naipe : NAIPES.values()) {
                cartas.add(new Carta(valor, naipe));
            }
        }
    }

    private void embaralhar() {
        Collections.shuffle(cartas);
    }

    public Carta pegarCarta() {
        if (cartas.isEmpty()) {
            System.out.println("Baralho vazio. Embaralhar novamente.");
            inicializarBaralho();
            embaralhar();
        }
        return cartas.remove(0);
    }
}

// Classe Jogador representa um jogador no jogo
class Jogador implements ParticipanteInterface {
    private Scanner scanner;
    private ArrayList<Carta> mao;
    private String nome_jogador;
    private double saldo;
    private double aposta;

    private double naposta;

    public Jogador(Scanner scanner, String nome_jogador, double saldo) {
        this.scanner = scanner;
        this.mao = new ArrayList<>();
        this.nome_jogador = nome_jogador;
        this.saldo = saldo;
        this.aposta = 0;
        this.naposta = 0;

        // Atualizar Menus.saldo_jogador
        Menus.saldo_jogador = this.saldo;
    }

    public String getNome() {
        return nome_jogador;
    }

    public void realizarAposta(double valor) {
        if (valor > 0 && valor <= saldo) {
            aposta = valor;
            saldo -= aposta;

            // Atualizar Menus.saldo_jogador após cada alteração no saldo
            Menus.saldo_jogador = this.saldo;
        } else {
            System.out.println("\nAposta inválida. Certifique-se de ter saldo suficiente.");
        }
    }

    public void ganharAposta(double ganho) {
        saldo +=  ganho;  // Adiciona tanto a aposta original quanto o ganho
        System.out.println(Menus.nome_jogador+" ganhou " + (ganho) + "€");

        // Atualizar Menus.saldo_jogador após cada alteração no saldo
        Menus.saldo_jogador = this.saldo;
    }


    public double getAposta() {
        return aposta;
    }

    public void exibirSaldo() {
        System.out.println(nome_jogador + " - Saldo: " + saldo + "€");
    }

    public void empate() {
        saldo += aposta;
        aposta = 0;
        System.out.println("Empate! O dinheiro voltou para o jogador.");
    }

    @Override
    public boolean desejaPedir() {
        System.out.println("Deseja 'pedir', 'passar' ou 'dobrar'? ");
        String escolha = scanner.next().toLowerCase();
        if (escolha.equals("dobrar") && saldo >= aposta * 2) {
            System.out.println("Aumentou o valor da aposta para " + aposta * 2 + "€");
            saldo+=aposta;
            // Correção: Passar o dobro do valor da aposta
            realizarAposta(aposta * 2);

            return false;
        } else if (escolha.equals("dobrar") && saldo <= aposta * 2) {
            System.out.println("Saldo insuficiente");
            return escolha.equals("pedir");
        } else {
            return escolha.equals("pedir");
        }
    }

    @Override
    public void receberCarta(Carta carta) {
        mao.add(carta);
    }

    @Override
    public void exibirMao(String nome) {
        System.out.println(nome_jogador + " Mão: " + mao + " (Total: " + calcularTotal() + ")");
    }

    public void exibirSaldoAposta() {
        System.out.println(Menus.nome_jogador + " - Saldo: " + saldo + "€ | Aposta Atual: " + aposta + "€");
    }


    @Override
    public int calcularTotal() {
        int total = 0;
        int ases = 0;

        for (Carta carta : mao) {
            total += carta.getValorNumerico();
            if (carta.getValorNumerico() == 11) {
                ases++;
            }
        }

        while (total > 21 && ases > 0) {
            total -= 10;
            ases--;
        }

        return total;
    }
}

// Classe Dealer representa o dealer no jogo
class Dealer implements ParticipanteInterface {
    private ArrayList<Carta> mao;
    private boolean todasCartasReveladas;

    public Dealer() {
        this.mao = new ArrayList<>();
        this.todasCartasReveladas = false;
    }

    @Override
    public boolean desejaPedir() {
        return calcularTotal() < 17;
    }

    @Override
    public void receberCarta(Carta carta) {
        mao.add(carta);
    }

    @Override
    public int calcularTotal() {
        int total = 0;
        int ases = 0;

        for (Carta carta : mao) {
            total += carta.getValorNumerico();
            if (carta.getValorNumerico() == 11) {
                ases++;
            }
        }

        while (total > 21 && ases > 0) {
            total -= 10;
            ases--;
        }

        return total;
    }

    public boolean todasCartasReveladas() {
        return todasCartasReveladas;
    }

    @Override
    public void exibirMao(String jogador) {
        if (!todasCartasReveladas) {
            if (mao.size() == 2) {
                System.out.println(jogador + " Mão: [" + mao.get(0) + ", ?] ");

            }  else {
                System.out.println(jogador + " Mão: " + mao);
                System.out.println("Total do Dealer: " + calcularTotal());
                todasCartasReveladas = true;
            }
        } else {
            System.out.println(jogador + " Mão: " + mao + " (Total: " + calcularTotal() + ")");
        }
    }
}

// Classe Blackjack representa o jogo de Blackjack
public class Blackjack {
    public static void JogarBJ() {
        Scanner scanner = new Scanner(System.in);
        boolean continuarJogando = true;

        while (continuarJogando) {
            jogarBlackjack(scanner);
            System.out.println("\nQuer jogar novamente? (s/n)");
            String resposta = scanner.next().toLowerCase();
            continuarJogando = resposta.equals("s");
        }

        Menus.jogos();
    }

    private static void jogarBlackjack(Scanner scanner) {
        // Inicialização do baralho
        Baralho baralho = new Baralho();

        // Inicialização das mãos do jogador e do dealer
        ParticipanteInterface jogador = new Jogador(scanner, Menus.nome_jogador, Menus.saldo_jogador);
        ParticipanteInterface dealer = new Dealer();
        System.out.println("Saldo: " + Menus.saldo_jogador + "€\n");
        // Realizar aposta inicial
        System.out.println("Escreva o valor da aposta:");
        double apostaInicial = scanner.nextDouble();
        ((Jogador) jogador).realizarAposta(apostaInicial);

        // Distribuir cartas iniciais
        jogador.receberCarta(baralho.pegarCarta());
        dealer.receberCarta(baralho.pegarCarta());
        jogador.receberCarta(baralho.pegarCarta());
        dealer.receberCarta(baralho.pegarCarta());

        // Exibir saldo, aposta e cartas iniciais
        ((Jogador) jogador).exibirSaldoAposta();
        jogador.exibirMao(Menus.nome_jogador);
        dealer.exibirMao("Dealer");

        // Jogador faz suas jogadas
        jogarRodada(jogador, dealer, baralho, Menus.nome_jogador);

        // Determinar vencedor
        determinarVencedor(jogador, dealer);

        // Exibir saldo final após a rodada
        ((Jogador) jogador).exibirSaldo();
    }

    private static void jogarRodada(ParticipanteInterface jogador, ParticipanteInterface dealer, Baralho baralho, String nome) {
        while (true) {
            // Jogador faz suas jogadas
            if (jogador.desejaPedir()) {
                Carta cartaJogador = baralho.pegarCarta();
                jogador.receberCarta(cartaJogador);
                jogador.exibirMao(nome);

                int totalJogador = jogador.calcularTotal();
                if (totalJogador >= 21) {
                    break;
                }
            } else {
                break;  // Se o jogador escolhe "passar", encerra a vez do jogador.
            }
        }

        // Dealer faz suas jogadas
        while (dealer.desejaPedir()) {
            Carta cartaDealer = baralho.pegarCarta();
            dealer.receberCarta(cartaDealer);

            int totalDealer = dealer.calcularTotal();
            if (totalDealer >= 17) {
                break;
            }
        }
    }

    private static void determinarVencedor(ParticipanteInterface jogador, ParticipanteInterface dealer) {
        System.out.println("\n---------- ♠ ♥ ♦ ♣ CASINO LISBOA ♣ ♦ ♥ ♠ ----------");
        System.out.println("\n---------- ♠ ♥ ♦ ♣ BLACKJACK RESULTADOS ♣ ♦ ♥ ♠ ----------\n");

        jogador.exibirMao(((Jogador) jogador).getNome());  // Use o método getNome para obter o nome do jogador
        dealer.exibirMao("Dealer");

        int totalJogador = jogador.calcularTotal();
        int totalDealer = dealer.calcularTotal();

        if (totalJogador > 21) {
            System.out.println("\n" + ((Jogador) jogador).getNome() + " ultrapassou 21. Dealer vence!\n");
            System.out.println(((Jogador) jogador).getNome() + " perdeu " + ((Jogador) jogador).getAposta() + "€");
        } else if (totalDealer > totalJogador && totalDealer <= 21) {
            System.out.println("\nDealer vence!\n");
            System.out.println(((Jogador) jogador).getNome() + " perdeu " + ((Jogador) jogador).getAposta() + "€");
        } else if (totalJogador == totalDealer) {
            System.out.println("\nEmpate!\n");
            ((Jogador) jogador).empate();
        } else {
            System.out.println(((Jogador) jogador).getNome() + " vence!\n");
            double ganho = jogador instanceof Jogador ? ((Jogador) jogador).getAposta() * 2 : 0;
            ((Jogador) jogador).ganharAposta(ganho);
        }
    }
}
