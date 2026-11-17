public interface FormaPagamento {
    double calcularTotal(double valor);
    String getNome();
}