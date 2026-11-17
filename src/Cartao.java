public class Cartao implements FormaPagamento {
    @Override
    public double calcularTotal(double valor) {
        return valor;
    }

    @Override
    public String getNome() {
        return "Cartao";
    }
}