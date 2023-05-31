import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORTA);
            System.out.println("Servidor iniciado na porta " + PORTA);

            while (true) {
                Socket jogador1 = serverSocket.accept();
                System.out.println("Jogador conectado: " + jogador1);

                Socket jogador2 = serverSocket.accept();
                System.out.println("Jogador conectado: " + jogador2);

                ThreadGame threadGame = new ThreadGame(jogador1, jogador2);
                threadGame.start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


