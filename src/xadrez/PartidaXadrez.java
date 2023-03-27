package xadrez;

import jogoTabuleiro.Peca;
import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

import java.util.ArrayList;
import java.util.List;

public class PartidaXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
        iniciarPartida();
    }

    public int getTurno(){
        return turno;
    }

    public Cor getJogadorAtual(){
        return jogadorAtual;

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

    public boolean[][] movimentosPossiveis (PosicaoXadrez posicaoOrigem){
        Posicao posicao = posicaoOrigem.paraPosicao();
        validarPosicaoOrigem(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();
    }

    public PecaXadrez fazerMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
        Posicao origem = posicaoOrigem.paraPosicao();
        Posicao destino = posicaoDestino.paraPosicao();
        validarPosicaoOrigem(origem);
        validarPosicaoDestino(origem, destino);
        Peca pecaCapturada = fazerMovimento(origem, destino);
        proximoTurno();
        return (PecaXadrez)pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino) {
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.posicionarPeca(p, destino);

        if(pecaCapturada != null) {
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        return pecaCapturada;
    }

    private void validarPosicaoOrigem(Posicao posicao) {
        if(!tabuleiro.haPeca(posicao)){
            throw new ChessException("Nao ha uma peca na posicao de origem");
        }
        if(jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()){
            throw new ChessException("A peca escolhida nao e sua. ");
        }
        if(!tabuleiro.peca(posicao).haMovimentoPossivel()){
            throw new ChessException("Nao ha movimentos possiveis para a peca escolhida");
        }
    }

    private void validarPosicaoDestino(Posicao origem, Posicao destino) {
        if (!tabuleiro.peca(origem).movimentoPossivel(destino)){
            throw new ChessException("A peca escolhida nao pode se mover para a posicao de destino");
        }
    }

    private void proximoTurno(){
        turno++;
        jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private void posicionarNovaPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.posicionarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
        pecasNoTabuleiro.add(peca);
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
