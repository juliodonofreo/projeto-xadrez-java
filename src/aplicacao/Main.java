package aplicacao;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PartidaXadrez partida = new PartidaXadrez();

        while (true) {
            Interface.printTabuleiro(partida.getPecas());
            System.out.println();
            System.out.print("Posicao de origem: ");
            PosicaoXadrez origem = Interface.lerPosicaoXadrez(sc);

            System.out.println();
            System.out.print("Posicao de destino: ");
            PosicaoXadrez destino = Interface.lerPosicaoXadrez(sc);

            PecaXadrez pecaCapturada = partida.fazerMovimentoXadrez(origem, destino);
        }
    }
}