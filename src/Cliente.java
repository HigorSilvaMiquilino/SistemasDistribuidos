import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    private static final String HOST = "localhost";
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(HOST, PORTA);

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            String mensagemRecebida;
            while ((mensagemRecebida = entrada.readLine()) != null) {
                System.out.println("Servidor: " + mensagemRecebida);

                if (mensagemRecebida.equalsIgnoreCase("Fim do jogo"))
                    break;

                System.out.print("Digite seu palpite, o número aleatória é: (par ou impar): ");
                String palpite = teclado.readLine();

                saida.println(palpite);
            }

            entrada.close();
            saida.close();
            teclado.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

