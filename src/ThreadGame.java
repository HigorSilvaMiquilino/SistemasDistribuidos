import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadGame extends Thread{

    Socket jogador1, jogador2;
    BufferedReader entradaJogador1, entradaJogador2;
    PrintWriter saidaJogador1, saidaJogador2;
    private int pontuacaoJogador1 = 0;
    private int pontuacaoJogador2 = 0;
    private int  pontuacaoMaxima = 3;

    public ThreadGame(Socket jogador1, Socket jogador2) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
    }

    @Override
    public void run() {
        try {
            entradaJogador1 = new BufferedReader(new InputStreamReader(jogador1.getInputStream()));
            saidaJogador1 = new PrintWriter(jogador1.getOutputStream(), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            entradaJogador2 = new BufferedReader(new InputStreamReader(jogador2.getInputStream()));
            saidaJogador2 = new PrintWriter(jogador2.getOutputStream(), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        saidaJogador1.println("Você é o Jogador 1");
        saidaJogador2.println("Você é o Jogador 2");

        String palpiteJogador1 = null, palpiteJogador2 = null;
        String numeroJogador1 = null, numeroJogador2 = null;
        while (true) {
            try {
                palpiteJogador1 = entradaJogador1.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Jogador 1 palpite: " + palpiteJogador1);

            try {
                numeroJogador1 = entradaJogador1.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Jogador 1 número: " + numeroJogador1);

            try {
                palpiteJogador2 = entradaJogador2.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Jogador 2 palpite: " + palpiteJogador2);

            try {
                numeroJogador2 = entradaJogador2.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Jogador 2 número: " + numeroJogador2);

            String resultado = verificarResultado(palpiteJogador1, palpiteJogador2, numeroJogador1, numeroJogador2);
            System.out.println("Resultado: " + resultado);

            if (resultado.contains("Jogador 1")) {
                pontuacaoJogador1++;
            } else if (resultado.contains("Jogador 2")) {
                pontuacaoJogador2++;
            }

            System.out.println("Placar:");
            System.out.println("Jogador 1: " + pontuacaoJogador1 + " ponto(s)");
            System.out.println("Jogador 2: " + pontuacaoJogador2 + " ponto(s)");

            String contexto = "Jogordor 1: " + palpiteJogador1 + "," + numeroJogador1 + " -- "+ " Jogador 2: " + palpiteJogador2 +","+numeroJogador2;

            if (pontuacaoJogador1 >= pontuacaoMaxima) {
                saidaJogador1.println("Fim - Jogador 1 venceu!"+" "+resultado+" "+contexto);
                saidaJogador2.println("Fim - Jogador 1 venceu!"+" "+resultado+" "+contexto);
                break;
            } else if (pontuacaoJogador2 >= pontuacaoMaxima) {
                saidaJogador1.println("Fim - Jogador 2 venceu!"+" "+resultado+" "+contexto);
                saidaJogador2.println("Fim - Jogador 2 venceu!"+" "+resultado+" "+contexto);
                break;
            }



            saidaJogador1.println(resultado + "Placar : "+"Jogador 1: " + pontuacaoJogador1 + " ponto(s)"+ " , " +" Jogador 2: " + pontuacaoJogador2 + " ponto(s) -- "+contexto);
            saidaJogador2.println(resultado + "Placar : "+"Jogador 1: " + pontuacaoJogador1 + " ponto(s)"+ " , " +" Jogador 2: " + pontuacaoJogador2 + " ponto(s) -- "+contexto);

            if (resultado.equals("Fim"))
                break;

        }

        try {
            entradaJogador1.close();
            saidaJogador1.close();
            entradaJogador2.close();
            saidaJogador2.close();
            jogador1.close();
            jogador2.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String verificarResultado(String palpiteJogador1, String palpiteJogador2,
                                             String numeroJogador1, String numeroJogador2) {

        int numJogador1 = Integer.parseInt(numeroJogador1);
        int numJogador2 = Integer.parseInt(numeroJogador2);

        System.out.println("Número do Jogador 1: " + numJogador1);
        System.out.println("Número do Jogador 2: " + numJogador2);

        int soma = numJogador1 + numJogador2;

        String resultado = soma % 2 == 0 ? "par" : "impar";

        String vencedor;

        if (palpiteJogador1.equals(palpiteJogador2)) {
            vencedor = "Empate";
        } else if (palpiteJogador1.equals(resultado)) {
            vencedor = "Jogador 1";
        } else {
            vencedor = "Jogador 2";
        }
        return "Resultado: " + (numJogador1 + numJogador2) +", que é "+ resultado + ", " + vencedor + " venceu. ";

    }
}