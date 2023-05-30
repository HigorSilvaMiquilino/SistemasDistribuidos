import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadGame extends Thread{

    Socket jogador;
    BufferedReader entradaJogador1, entradaJogador2;
    PrintWriter saidaJogador1, saidaJogador2;

    public ThreadGame(Socket jogador) {
        this.jogador = jogador;
    }


    @Override
    public void run() {
        try {
            entradaJogador1 = new BufferedReader(new InputStreamReader(jogador.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            saidaJogador1 = new PrintWriter(jogador.getOutputStream(), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            entradaJogador2 = new BufferedReader(new InputStreamReader(jogador.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            saidaJogador2 = new PrintWriter(jogador.getOutputStream(), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }



        saidaJogador1.println("Você é o Jogador 1");
        saidaJogador2.println("Você é o Jogador 2");

        String palpiteJogador1 = null, palpiteJogador2 = null;
        while (true) {
            try {
                palpiteJogador1 = entradaJogador1.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Jogador 1: " + palpiteJogador1);

            try {
                palpiteJogador2 = entradaJogador2.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Jogador 2: " + palpiteJogador2);

            String resultado = verificarResultado(palpiteJogador1, palpiteJogador2);
            saidaJogador1.println(resultado);
            saidaJogador2.println(resultado);

            if (resultado.equals("Fim do jogo"))
                break;
        }

        try {
            entradaJogador1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saidaJogador1.close();
        try {
            entradaJogador2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saidaJogador2.close();
    }

    private static String verificarResultado(String palpiteJogador1, String palpiteJogador2) {
        int numeroJogador1 = (int) (Math.random() * 10) + 1;
        int numeroJogador2 = (int) (Math.random() * 10) + 1;

        System.out.println("Número do Jogador 1: " + numeroJogador1);
        System.out.println("Número do Jogador 2: " + numeroJogador2);

        int soma = numeroJogador1 + numeroJogador2;

        String resultado = soma % 2 == 0 ? "par" : "impar";

        if (palpiteJogador1.equals(resultado) && palpiteJogador2.equals(resultado)) {
            return "Empate. O número é " + soma + ", que é " + resultado + ".";
        } else if (palpiteJogador1.equals(resultado)) {
            return "Jogador 1 venceu. O número é " + soma + ", que é " + resultado + ".";
        } else if (palpiteJogador2.equals(resultado)) {
            return "Jogador 2 venceu. O número é " + soma + ", que é " + resultado + ".";
        } else {
            return "Nenhum jogador venceu. O número é " + soma + ", que é " + resultado + ".";
        }
    }
}

