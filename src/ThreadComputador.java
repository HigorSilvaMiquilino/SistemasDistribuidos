import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ThreadComputador extends Thread {

    Socket jogador;
    BufferedReader entradaJogador;
    PrintWriter saidaJogador;
    Random random;
    private int pontuacaoJogador = 0;
    private int pontuacaoComputador = 0;
    private int  pontuacaoMaxima = 3;

    public ThreadComputador(Socket jogador) {
        this.jogador = jogador;
    }

    @Override
    public void run() {
        try {
            entradaJogador = new BufferedReader(new InputStreamReader(jogador.getInputStream()));
            saidaJogador = new PrintWriter(jogador.getOutputStream(), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String palpiteJogador = null;
        String numeroJogador = null;
        random = new Random();

        while (true) {
            try {
                palpiteJogador = entradaJogador.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Jogador palpite: " + palpiteJogador);

            try {
                numeroJogador = entradaJogador.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Jogador número: " + numeroJogador);


            String palpiteComputador = (random.nextBoolean()) ? "par" : "impar";
            System.out.println("Palpite do computador: " +palpiteComputador);

            int numeroComputadorAleatorio =  random.nextInt(10) + 1;
            System.out.println("Computador numero: " + numeroComputadorAleatorio);


            String resultado = verificarResultado(palpiteJogador, palpiteComputador, numeroJogador,  numeroComputadorAleatorio);
            System.out.println("Resultado: " + resultado);

            if (resultado.contains("Jogador")) {
                pontuacaoJogador++;
            } else if (resultado.contains("Computador")) {
                pontuacaoComputador++;
            }

            System.out.println("Placar:");
            System.out.println("Jogador: " + pontuacaoJogador + " ponto(s)");
            System.out.println("Computador: " + pontuacaoComputador + " ponto(s)");

            String contexto = "Jogador " + palpiteJogador +"," + numeroJogador +" -- "+ " Computador " + palpiteComputador +","+numeroComputadorAleatorio ;

            if (pontuacaoJogador >= pontuacaoMaxima) {
                saidaJogador.println("Fim - Jogador venceu!"+" "+ resultado + " " + contexto);
                break;
            } else if (pontuacaoComputador >= pontuacaoMaxima) {
                saidaJogador.println("Fim - Computador venceu!"+" "+ resultado + " " + contexto);
                break;
            }


            saidaJogador.println(resultado + "Placar : "+"Jogador " + pontuacaoJogador + " ponto(s)"+ "," +" Computador " + pontuacaoComputador + " ponto(s) -- " + contexto);

            if (resultado.equals("Fim"))
                break;

        }

        try {
            entradaJogador.close();
            saidaJogador.close();
            jogador.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String verificarResultado(String palpiteJogador, String palpiteComputador,
                                             String numeroJogador, int numeroComputador) {

        int numJogador = Integer.parseInt(numeroJogador);

        System.out.println("Número do Jogador : " + numJogador);
        System.out.println("Número do Computador : " + numeroComputador);


        int soma = (numJogador + numeroComputador);

        String resultado = soma % 2 == 0 ? "par" : "impar";

        String vencedor;

        if (palpiteJogador.equals(palpiteComputador)) {
            vencedor = "Empate";
        } else if (palpiteJogador.equals(resultado)) {
            vencedor = "Jogador";
        } else {
            vencedor = "Computador";
        }
        return "Resultado: " + soma +", que é "+ resultado + ", " + vencedor + " venceu. ";

    }
}




