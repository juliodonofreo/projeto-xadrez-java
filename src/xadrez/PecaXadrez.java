package xadrez;

import jogoTabuleiro.Peca;
import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

    private final Cor cor;

    public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public PosicaoXadrez getPosicaoXadrez(){
        return PosicaoXadrez.dePosicao(posicao);
    }

    public abstract boolean[][] movimentosPossiveis();

    protected boolean haUmaPecaAdversaria(Posicao posicao) {
        PecaXadrez peca = (PecaXadrez)getTabuleiro().peca(posicao);
        return peca != null && peca.getCor() != cor;
    }


}
