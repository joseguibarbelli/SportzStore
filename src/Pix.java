public class Pix implements FormaPagamento {
    @Override
    public double calcularTotal(double valor) {
        return Math.round(valor * 0.95 * 100) / 100.0;
    }

    @Override
    public String getNome() {
        return "Pix (5% de desconto)";
    }
}