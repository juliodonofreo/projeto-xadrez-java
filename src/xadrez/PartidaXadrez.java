package xadrez;

import jogoTabuleiro.Peca;
import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

    private Tabuleiro tabuleiro;

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        iniciarPartida();
    }

    public PecaXadrez[][] getPecas() {
        PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];

        for (int i = 0; i < tabuleiro.getLinhas(); i++) {

            for(int j = 0; j < tabuleiro.getColunas(); j++) {
                mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
            }
        }

        return mat;
    }

    public PecaXadrez fazerMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
        Posicao origem = posicaoOrigem.paraPosicao();
        Posicao destino = posicaoDestino.paraPosicao();
        validarPosicaoOrigem(origem);
        Peca pecaCapturada = fazerMovimento(origem, destino);
        return (PecaXadrez)pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino) {
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(origem);
        tabuleiro.posicionarPeca(p, destino);
        return pecaCapturada;
    }

    private void validarPosicaoOrigem(Posicao posicao) {
        if(!tabuleiro.haPeca(posicao)){
            throw new ChessException("Não há uma peça na posição de origem");
        }
    }

    private void posicionarNovaPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.posicionarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
    }

    private void iniciarPartida(){
        posicionarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

        posicionarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));

    }
}
