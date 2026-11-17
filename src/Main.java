import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("pt-BR"));

        ArrayList<Produto> catalogo = new ArrayList<Produto>();
        catalogo.add(new Roupa("Camiseta", 100, "M"));
        catalogo.add(new Equipamento("Bola", 80, "Futebol"));

        ArrayList<Pedido> pedidos;

        try {
            pedidos = carregar();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.out.println(
                    "Nao foi possivel ler pedidos.dat. Verifique o arquivo.");
            return;
        }

        while (true) {
            System.out.println(
                    "\n1 - Novo pedido\n2 - Listar pedidos\n0 - Salvar e sair");

            try {
                int opcao = Integer.parseInt(ler("Opcao: "));

                if (opcao == 1) {
                    Pedido pedido = criarPedido(catalogo);
                    pedidos.add(pedido);
                    salvar(pedidos);
                    System.out.println("Pedido salvo.\n" + pedido.getResumo());

                } else if (opcao == 2) {
                    if (pedidos.isEmpty()) {
                        System.out.println("Nenhum pedido cadastrado.");
                    }

                    for (int i = 0; i < pedidos.size(); i++) {
                        System.out.println("\nPedido " + (i + 1));
                        System.out.println(pedidos.get(i).getResumo());
                    }

                } else if (opcao == 0) {
                    salvar(pedidos);
                    break;

                } else {
                    System.out.println("Opcao inexistente.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Digite um numero nas opcoes.");

            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());

            } catch (IOException e) {
                System.out.println(
                        "Falha no arquivo ou na consulta de CEP: "
                                + e.getMessage());

                System.out.println(
                        "Se o pedido ja foi concluido, ele continua na memoria.");

            } catch (NoSuchElementException e) {
                break;
            }
        }

        teclado.close();
    }

    private static String ler(String mensagem) {
        System.out.print(mensagem);
        return teclado.nextLine().trim();
    }

    private static Pedido criarPedido(ArrayList<Produto> catalogo)
            throws IOException {

        String cliente = ler("Nome do cliente: ");
        String cep = ler("CEP para entrega (Enter para retirar na loja): ");
        String endereco = "Retirada na loja";

        if (!cep.isEmpty()) {
            endereco = ConsultaCep.buscar(cep);
            System.out.println(endereco);

            String detalhe = ler(
                    "Numero/complemento (inclua a rua se necessario): ");

            if (detalhe.isEmpty()) {
                throw new IllegalArgumentException("Informe o numero ou S/N.");
            }

            endereco += " | " + detalhe;
        }

        Pedido pedido = new Pedido(cliente, endereco);

        while (true) {
            for (int i = 0; i < catalogo.size(); i++) {
                Produto produto = catalogo.get(i);

                System.out.printf(
                        "%d - %s  - R$ %.2f%n",
                        i + 1, produto.getDescricao(), produto.getPreco());
            }

            int codigo = Integer.parseInt(
                    ler("Produto (0 para terminar): "));

            if (codigo == 0) {
                break;
            }

            if (codigo < 1 || codigo > catalogo.size()) {
                System.out.println("Produto inexistente.");

            } else {
                pedido.adicionarProduto(catalogo.get(codigo - 1));
                System.out.println("Uma unidade adicionada.");
            }
        }

        int pagamento = Integer.parseInt(
                ler("Pagamento: 1 - Pix | 2 - Cartao: "));

        FormaPagamento forma;

        if (pagamento == 1) {
            forma = new Pix();

        } else if (pagamento == 2) {
            forma = new Cartao();

        } else {
            throw new IllegalArgumentException("Pagamento inexistente.");
        }

        pedido.finalizar(forma);
        return pedido;
    }

    private static void salvar(ArrayList<Pedido> pedidos) throws IOException {
        try (ObjectOutputStream arquivo = new ObjectOutputStream(
                new FileOutputStream("pedidos.tmp"))) {

            arquivo.writeInt(pedidos.size());

            for (Pedido pedido : pedidos) {
                arquivo.writeObject(pedido);
            }
        }

        Files.move(
                Paths.get("pedidos.tmp"),
                Paths.get("pedidos.dat"),
                StandardCopyOption.REPLACE_EXISTING);
    }

    private static ArrayList<Pedido> carregar()
            throws IOException, ClassNotFoundException {

        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

        if (new File("pedidos.dat").exists()) {
            try (ObjectInputStream arquivo = new ObjectInputStream(
                    new FileInputStream("pedidos.dat"))) {

                int quantidade = arquivo.readInt();

                if (quantidade < 0) {
                    throw new IOException("Quantidade de pedidos invalida.");
                }

                for (int i = 0; i < quantidade; i++) {
                    Object objeto = arquivo.readObject();

                    if (!(objeto instanceof Pedido)) {
                        throw new IOException("O arquivo contem dados invalidos.");
                    }

                    pedidos.add((Pedido) objeto);
                }
            }
        }

        return pedidos;
    }
}