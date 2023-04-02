package aplicacao;

import xadrez.ChessException;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidaXadrez partida = new PartidaXadrez();
        List<PecaXadrez> capturadas = new ArrayList<>();

        while (!partida.isCheckMate()) {
            try {
                Interface.limparTela();
                Interface.printPartida(partida, capturadas);
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

                if(pecaCapturada != null) {
                    capturadas.add(pecaCapturada);
                }

                if(partida.getPromovida() != null) {
                    System.out.print("Digite peça para promoção (BP/CA/RA/TR): ");
                    String tipo = sc.nextLine().toUpperCase();
                    while (!tipo.equals("RA") && !tipo.equals("CA") && !tipo.equals("TR") && !tipo.equals("BP")) {
                        System.out.print("Valor inválido! Digite peça para promoção (BP/CA/RA/TR): ");
                        tipo = sc.nextLine().toUpperCase();
                    }
                    partida.substituirPecaPromovida(tipo);
                }
            }
            catch (ChessException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch (IllegalStateException e) {
                partida.setCheckMate(true);
            }
        }

        Interface.limparTela();
        Interface.printPartida(partida, capturadas);

    }
}