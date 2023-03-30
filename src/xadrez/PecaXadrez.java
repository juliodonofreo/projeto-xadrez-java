package xadrez;

import jogoTabuleiro.Peca;
import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

    private Cor cor;
    private int contagemMovimentos;

    public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }

    public int getContagemMovimentos(){
        return contagemMovimentos;
    }

    public void aumentarContagemMovimentos(){
        contagemMovimentos++;
    }

    public void diminuirContagemMovimentos(){
        contagemMovimentos--;
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
