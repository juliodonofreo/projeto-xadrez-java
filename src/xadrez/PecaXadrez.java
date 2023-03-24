package xadrez;

import jogoTabuleiro.Peca;
import jogoTabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

    protected Cor cor;

    public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }


}
