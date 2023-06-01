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

                BufferedReader entradaJogador1 = new BufferedReader(new InputStreamReader(jogador1.getInputStream()));
                String modalidadeJogador1 = entradaJogador1.readLine();

                BufferedReader entradaJogador2 = new BufferedReader(new InputStreamReader(jogador2.getInputStream()));
                String modalidadeJogador2 = entradaJogador2.readLine();

                if (modalidadeJogador1 != null && modalidadeJogador1.equals("1") && modalidadeJogador2 != null && modalidadeJogador2.equals("1")) {
                    ThreadGame threadGame = new ThreadGame(jogador1, jogador2);
                    threadGame.start();
                } else if (modalidadeJogador1 != null && modalidadeJogador1.equals("2")) {
                    ThreadComputador threadComputador = new ThreadComputador(jogador1);
                    threadComputador.start();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}