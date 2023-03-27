package aplicacao;

import xadrez.ChessException;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidaXadrez partida = new PartidaXadrez();

        while (true) {
            try {
                Interface.limparTela();
                Interface.printPartida(partida);
                System.out.println();
                System.out.print("Posicao de origem: ");
                PosicaoXadrez origem = Interface.lerPosicaoXadrez(sc);

                boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
                Interface.limparTela();
                Interface.printTabuleiro(partida.getPecas(), movimentosPossiveis);

                System.out.println();
                System.out.print("Posicao de destino: ");
                PosicaoXadrez destino = Interface.lerPosicaoXadrez(sc);

                PecaXadrez pecaCapturada = partida.fazerMovimentoXadrez(origem, destino);
            }
            catch (ChessException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }
}