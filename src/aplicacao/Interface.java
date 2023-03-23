package aplicacao;

import xadrez.Cor;
import xadrez.PecaXadrez;

public class Interface {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[1;90m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[94m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[97m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void printTabuleiro(PecaXadrez[][] pecas) {

        for (int i = 0; i < pecas.length; i++) {
            System.out.print(8 - i + " ");

            for(int j = 0; j < pecas.length; j++){

                if((j + i) % 2 == 0) {
                    System.out.print(ANSI_YELLOW_BACKGROUND);
                    printPeca(pecas[i][j]);
                    System.out.print(ANSI_RESET);
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
            if(peca.getCor() == Cor.BRANCO) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_BLACK + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }
}
