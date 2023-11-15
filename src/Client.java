import java.util.*;

public class Client {
	
	public static void main (String [] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		IDAStar s = new IDAStar();
		
		int valorInicial = sc.nextInt();
		int goalTemp = valorInicial * 3;
		
		Iterator<IDAStar.State> it = s.solve(new Board(valorInicial, "nada"), new Board(goalTemp,"nada"));
		while (it.hasNext()) {
			IDAStar.State i = it.next();
			System.out.println(i);
			if (!it.hasNext()) {
				System.out.println();
				System.out.println((int) i.getG());
			}
		}
		
		sc.close();
	}

}

