package aplicacao;

import xadrez.PartidaXadrez;

public class Main {
    public static void main(String[] args) {

        PartidaXadrez partida = new PartidaXadrez();

        Interface.printTabuleiro(partida.getPecas());
    }
}