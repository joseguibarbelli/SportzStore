public class Roupa extends Produto {
    private static final long serialVersionUID = 1L;
    private String tamanho;

    public Roupa(String nome, double preco, String tamanho) {
        super(nome, preco);

        if (tamanho == null || tamanho.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o tamanho.");
        }

        this.tamanho = tamanho.trim();
    }

    @Override
    public String getDescricao() {
        return getNome() + " - tamanho " + tamanho;
    }
}