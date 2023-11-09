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
    public int hashCode() { //nao muda
        final int prime = 31;
        int result = 1;
        result = prime * result + board;
        return result;
    }

    @Override
    public boolean equals(Object obj) { //nao muda
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Board other = (Board) obj;
        return this.board == other.board;
    }
	
	public String toString() { //nao muda
		return Integer.toString(board);
	}

	@Override
	public List<Ilayout> children() { //nao muda
		List<Ilayout> children = new ArrayList<Ilayout>(3);
        children.add(new Board(this.board + 1,"increment"));
        children.add(new Board(this.board - 1, "decrement"));
        children.add(new Board(this.board * 2, "double"));
		return children;
	}

    @Override
    public double estimateCost(Ilayout goal) { //a fazer bem as contas
        int goalValue = ((Board)goal).board;
        int custo = 0;
        if(this.board >= 0){
            custo = Math.abs((goalValue/2) - board);
        }else{
            custo = Math.abs((goalValue/4) - board);
        }
        return custo;
    }
	
	@Override
	public boolean isGoal(Ilayout l) { //nao muda
		return l.equals(this);
	}

    @Override
    public int getBoard() {
        return board;
    }

    @Override
    public double getG() { //nao muda
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
