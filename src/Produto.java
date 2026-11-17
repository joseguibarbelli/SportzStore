import java.io.Serializable;

public abstract class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        if (nome == null || nome.trim().isEmpty() || !Double.isFinite(preco) || preco < 0.01) {
            throw new IllegalArgumentException("Nome ou preco invalido.");
        }

        this.nome = nome.trim();
        this.preco = Math.round(preco * 100) / 100.0;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public abstract String getDescricao();
}