
enum Ficha {
    BRANCA(1),
    ROSA(2.5),
    VERMELHA(5),
    AZUL(10),
    VERDE(25),
    PRETA(100),
    ROXA(500),
    LARANJA(1000);

    private double valor;

    Ficha(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }
}