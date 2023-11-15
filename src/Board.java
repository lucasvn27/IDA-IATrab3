import java.util.ArrayList;
import java.util.List;

public class Board implements Ilayout, Cloneable  {
	
	private int board;
    private String operacao;
	
	public Board(int str, String operacao) throws IllegalStateException {
		board = str;
        this.operacao = operacao;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + board;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Board other = (Board) obj;
        return this.board == other.board;
    }
	
	public String toString() {
		return Integer.toString(board);
	}

	@Override
	public List<Ilayout> children() {
		List<Ilayout> children = new ArrayList<Ilayout>(3);
        Board irmao1 = new Board(this.board + 1,"increment");
        Board irmao2 = new Board(this.board - 1, "decrement");
        Board irmao3 = new Board(this.board * 2, "double");
        children.add(irmao1);
        children.add(irmao2);
        children.add(irmao3);
		return children;
	}
	
	@Override
	public boolean isGoal(Ilayout l) {
		return l.equals(this);
	}

    @Override
    public double getG() {
        switch (operacao) {
            case "increment":
                return 1;
            case "decrement":
                return 2;
            case "double":
                return 3;
            default:
                return 0;
        }
    }
	
}
