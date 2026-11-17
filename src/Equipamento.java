public class Equipamento extends Produto {
    private static final long serialVersionUID = 1L;
    private String esporte;

    public Equipamento(String nome, double preco, String esporte) {
        super(nome, preco);

        if (esporte == null || esporte.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o esporte.");
        }

        this.esporte = esporte.trim();
    }

    @Override
    public String getDescricao() {
        return getNome() + " - " + esporte;
    }
}