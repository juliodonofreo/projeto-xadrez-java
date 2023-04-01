package xadrez.pecas;

import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

    private PartidaXadrez partidaXadrez;

    public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
        super(tabuleiro, cor);
        this.partidaXadrez = partidaXadrez;
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        if(getCor() == Cor.BRANCO){
            p.setValores(posicao.getLinha() - 1, posicao.getColuna());

            if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() - 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());

            if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().haPeca(p2) && getContagemMovimentos() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if(getTabuleiro().posicaoExiste(p) && haUmaPecaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if(getTabuleiro().posicaoExiste(p) && haUmaPecaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Movimento especial en passant branco
            if (posicao.getLinha() == 3) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if(getTabuleiro().posicaoExiste(esquerda) && haUmaPecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getVulneravelEnPassant()){
                    mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
                }

                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if(getTabuleiro().posicaoExiste(direita) && haUmaPecaAdversaria(direita) && getTabuleiro().peca(direita) == partidaXadrez.getVulneravelEnPassant()){
                    mat[direita.getLinha() - 1][direita.getColuna()] = true;
                }
            }

        }
        else{
            p.setValores(posicao.getLinha() + 1, posicao.getColuna());

            if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() + 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());

            if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().haPeca(p2) && getContagemMovimentos() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if(getTabuleiro().posicaoExiste(p) && haUmaPecaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if(getTabuleiro().posicaoExiste(p) && haUmaPecaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Movimento especial en passant preto
            if (posicao.getLinha() == 4) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if(getTabuleiro().posicaoExiste(esquerda) && haUmaPecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getVulneravelEnPassant()){
                    mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }

                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if(getTabuleiro().posicaoExiste(direita) && haUmaPecaAdversaria(direita) && getTabuleiro().peca(direita) == partidaXadrez.getVulneravelEnPassant()){
                    mat[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }

        return mat;
    }

    @Override
    public String toString() {
        return "PE";
    }
}
