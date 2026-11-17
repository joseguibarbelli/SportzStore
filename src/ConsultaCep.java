import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConsultaCep {
    public static String buscar(String cep) throws IOException {
        cep = cep.trim().replace("-", "");

        if (!cep.matches("[0-9]{8}")) {
            throw new IllegalArgumentException("O CEP deve ter 8 numeros.");
        }

        URL url = new URL("https://viacep.com.br/ws/" + cep + "/xml/");
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

        conexao.setConnectTimeout(5000);
        conexao.setReadTimeout(5000);

        try (InputStream resposta = conexao.getInputStream()) {
            DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();

            fabrica.setFeature(
                    "http://apache.org/xml/features/disallow-doctype-decl", true);

            Document xml = fabrica.newDocumentBuilder().parse(resposta);

            if (xml.getElementsByTagName("erro").getLength() > 0) {
                throw new IllegalArgumentException("CEP nao encontrado.");
            }

            return campo(xml, "logradouro") + " - " + campo(xml, "bairro")
                    + " - " + campo(xml, "localidade") + "/" + campo(xml, "uf")
                    + " - CEP " + campo(xml, "cep");

        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException("Resposta invalida do servico de CEP.", e);

        } finally {
            conexao.disconnect();
        }
    }

    private static String campo(Document xml, String nome) throws IOException {
        NodeList campos = xml.getElementsByTagName(nome);

        if (campos.getLength() == 0) {
            throw new IOException("Resposta incompleta do servico de CEP.");
        }

        return campos.item(0).getTextContent();
    }
}