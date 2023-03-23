package aplicacao;

import xadrez.PecaXadrez;

public class Interface {

    public static void printTabuleiro(PecaXadrez[][] pecas) {

        for (int i = 0; i < pecas.length; i++) {
            System.out.print(8 - i + " ");

            for(int j = 0; j < pecas.length; j++){

                if((j + i) % 2 == 0) {
                    System.out.print("\033[47m");
                    printPeca(pecas[i][j]);
                    System.out.print("\033[m");
                }

                else{
                    printPeca(pecas[i][j]);
                }
            }

            System.out.println();
        }

        System.out.println("  a b c d e f g h");
    }

    public static void printPeca(PecaXadrez peca) {
        if(peca == null){
            System.out.print("-");
        }
        else {
            System.out.print(peca);
        }
        System.out.print(" ");
    }
}
