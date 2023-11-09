import java.util.*;

public class Client {
	
	public static void main (String [] args) throws Exception { //nao muda
		Scanner sc = new Scanner(System.in);
		AStar s = new AStar();
		
		int valorInicial = sc.nextInt();
		int goalTemp = valorInicial * 3;
		
		Iterator<AStar.State> it = s.solve(new Board(valorInicial, "nada"), new Board(goalTemp,"nada"));
		while (it.hasNext()) {
			AStar.State i = it.next();
			System.out.println(i);
			if (!it.hasNext()) {
				System.out.println();
				System.out.println((int) i.getG());
			}
		}
		
		sc.close();
	}
}

