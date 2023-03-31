package xadrez;

import jogoTabuleiro.Peca;
import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;
import xadrez.pecas.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PartidaXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;

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

    public boolean isCheck(){
        return check;
    }

    public boolean isCheckMate() {
        return checkMate;
    }

    public void setCheckMate(boolean checkMate) {
        this.checkMate = checkMate;
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

        if(testarCheck(jogadorAtual)){
            desfazerMovimento(origem, destino, pecaCapturada);
            throw new ChessException("Você não pode se colocar em check. ");
        }

        check = testarCheck(oponente(jogadorAtual));
        if(testarCheckMate(oponente(jogadorAtual))) {
            checkMate = true;
        }
        else{
            proximoTurno();
        }
        return (PecaXadrez)pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino) {
        PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(origem);
        p.aumentarContagemMovimentos();
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.posicionarPeca(p, destino);

        if(pecaCapturada != null) {
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
        PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(destino);
        p.diminuirContagemMovimentos();
        tabuleiro.posicionarPeca(p, origem);

        if (pecaCapturada != null) {
            tabuleiro.posicionarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
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

    private Cor oponente(Cor cor) {
        return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private PecaXadrez rei(Cor cor) {
        List<Peca> lista = pecasNoTabuleiro.stream().filter(Objects::nonNull).toList();
        lista = lista.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).toList();

        for(Peca p: lista) {
            if(p instanceof Rei) {
                return (PecaXadrez)p;
            }
        }
        throw new IllegalStateException("Não existe um rei " + cor + " no tabuleiro. ");
    }

    private boolean testarCheck(Cor cor) {
        Posicao posicaoRei = rei(cor).getPosicaoXadrez().paraPosicao();
        List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(Objects::nonNull).toList();
        pecasOponente = pecasOponente.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).toList();

        for(Peca p: pecasOponente) {
            boolean[][] mat = p.movimentosPossiveis();
            if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]){
                return true;
            }
        }
        return false;
    }

    private boolean testarCheckMate(Cor cor) {
        if(!testarCheck(cor)) {
            return false;
        }

        List<Peca> lista = pecasNoTabuleiro.stream()
                .filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).toList();
        for (Peca p: lista){
            boolean[][] mat = p.movimentosPossiveis();

            for(int i = 0; i < tabuleiro.getLinhas(); i++){
                for(int j = 0; j < tabuleiro.getColunas(); j++){
                    if(mat[i][j]) {
                        Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().paraPosicao();
                        Posicao destino = new Posicao(i, j);
                        Peca pecaCapturada = fazerMovimento(origem, destino);
                        try{
                            boolean testCheck = testarCheck(cor);

                            if (!testCheck) {
                                return false;
                            }
                        }
                        catch (IllegalStateException e) {
                            break;
                        }
                        finally {
                            desfazerMovimento(origem, destino, pecaCapturada);
                        }
                    }
                }
            }
        }
        return true;
    }

    private void posicionarNovaPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.posicionarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
        pecasNoTabuleiro.add(peca);
    }

    private void iniciarPartida(){

        posicionarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO));
        posicionarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO));

        posicionarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO));
        posicionarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO));
    }
}