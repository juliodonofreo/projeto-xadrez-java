package jogoTabuleiro;

public abstract class Peca {

    protected Posicao posicao;
    private final Tabuleiro tabuleiro;

    public Peca(Tabuleiro tabuleiro) {
        posicao = null;
        this.tabuleiro = tabuleiro;
    }

    protected Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public abstract boolean[][] movimentosPossiveis();

    public boolean movimentoPossivel(Posicao posicao) {
        return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
    }

    public boolean haMovimentoPossivel() {
        boolean[][] mat = movimentosPossiveis();

        for (boolean[] colunas : mat) {
            for (int j = 0; j < mat.length; j++) {
                if (colunas[j]) {
                    return true;
                }
            }
        }

        return false;
    }

}
