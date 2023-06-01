import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadComputador extends Thread {

    Socket jogador;
    BufferedReader entradaJogador;
    PrintWriter saidaJogador;

    public ThreadComputador(Socket jogador) {
        this.jogador = jogador;

    }

    @Override
    public void run() {
        System.out.println("Só testando mesmo");
    }


}
