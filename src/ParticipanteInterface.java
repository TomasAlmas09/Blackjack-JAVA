interface ParticipanteInterface {
    boolean desejaPedir();

    void receberCarta(Carta carta);

    void exibirMao(String jogador);

    int calcularTotal();

}