package xadrez;

import jogoTabuleiro.Peca;
import jogoTabuleiro.Tabuleiro;

public class PecaXadrez extends Peca {

    protected Cor cor;

    public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    protected String pintarPeca(String emoji){
        if(cor == Cor.PRETO) {
            return "\033[30m" + emoji + "\033[m";
        }
        else {
            return emoji;
        }
    }
}
