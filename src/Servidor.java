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

            while(true) {
                Socket jogador = serverSocket.accept();
                System.out.println("Jogador 1 conectado: " + jogador);
                ThreadGame threadGame = new ThreadGame(jogador);
                threadGame.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


