import java.util.*;

public class Client {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        IDAStar idaStar = new IDAStar();

        int valorInicial = sc.nextInt();
        int goalTemp = valorInicial * 3;

        Iterator<IDAStar.State> it = idaStar.idaStarSearch(new Board(valorInicial, ""), new Board(goalTemp, ""));

        if (it != null && it.hasNext()) {
            while (it.hasNext()) {
                IDAStar.State state = it.next();
                System.out.println(state);
            }

            IDAStar.State finalState = it.next();
            System.out.println("\nCusto total: " + (int) finalState.getG());
        } else {
            System.out.println("Não foi encontrada uma solução.");
        }

        sc.close();
    }
}
