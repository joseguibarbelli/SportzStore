import java.io.Serializable;
import java.util.ArrayList;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cliente;
    private String endereco;
    private ArrayList<Produto> produtos = new ArrayList<Produto>();
    private String pagamento;
    private double total;
    private boolean finalizado;

    public Pedido(String cliente, String endereco) {
        if (cliente == null || cliente.trim().isEmpty()
                || endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe cliente e endereco.");
        }

        this.cliente = cliente.trim();
        this.endereco = endereco.trim();
    }

    public void adicionarProduto(Produto produto) {
        if (finalizado) {
            throw new IllegalStateException("O pedido ja foi finalizado.");
        }

        if (produto == null) {
            throw new IllegalArgumentException("Escolha um produto.");
        }

        produtos.add(produto);
    }

    public void finalizar(FormaPagamento forma) {
        if (finalizado || produtos.isEmpty()) {
            throw new IllegalStateException("Pedido vazio ou ja finalizado.");
        }

        if (forma == null) {
            throw new IllegalArgumentException("Escolha o pagamento.");
        }

        double subtotal = 0;

        for (Produto produto : produtos) {
            subtotal += produto.getPreco();
        }

        subtotal = Math.round(subtotal * 100) / 100.0;
        total = forma.calcularTotal(subtotal);
        pagamento = forma.getNome();
        finalizado = true;
    }

    public String getResumo() {
        String texto = "Cliente: " + cliente
                + "\nEntrega: " + endereco + "\n";

        for (Produto produto : produtos) {
            texto += produto.getDescricao()
                    + String.format(" - R$ %.2f%n", produto.getPreco());
        }

        return texto + "Pagamento: " + pagamento
                + String.format("%nTotal: R$ %.2f%n", total);
    }
}