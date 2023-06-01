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

            System.out.println("Escolha a modalidade:");
            System.out.println("1. Jogador vs Jogador");
            System.out.println("2. Jogador vs Computador");
            System.out.print("Opção: ");
            String modalidade = teclado.readLine();

            saida.println(modalidade);


            String mensagemRecebida;
            while ((mensagemRecebida = entrada.readLine()) != null) {
                System.out.println("Servidor: " + mensagemRecebida);

                if (mensagemRecebida.equalsIgnoreCase("Fim"))
                    break;

                System.out.print("Digite seu palpite (par ou impar): ");
                String palpite = teclado.readLine();
                System.out.print("Digite um número: ");
                String numero = teclado.readLine();

                saida.println(palpite);
                saida.println(numero);
            }

            String resultado = entrada.readLine();
            System.out.println("Resultado: " + resultado);

            entrada.close();
            saida.close();
            teclado.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}