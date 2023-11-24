import java.util.*;

public class Client {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        IDAStar s = new IDAStar();

        int valorInicial = sc.nextInt();
        int goalTemp = valorInicial * 3;

        long startTime = System.currentTimeMillis(); // Record start time

        Iterator<IDAStar.State> it = s.solve(new Board(valorInicial, "nada"), new Board(goalTemp, "nada"));

        long endTime = System.currentTimeMillis(); // Record end time
        long executionTime = endTime - startTime;

        int nosGerados = s.getGeneratedNodesCount(); // Get the maximum depth
		int nosExpandidos = s.getExpandedNodesCount();
		int maxG = s.getMaxG();

        while (it.hasNext()) {
            IDAStar.State i = it.next();
            System.out.println(i);
            if (!it.hasNext()) {
                System.out.println();
                System.out.println("Resultado Final: " + (int) i.getG());
                System.out.println("Tempo de execucao: " + executionTime + " milissegundos");
                System.out.println("Memoria: " + getMemoryUsage() + " bytes");
                System.out.println("Nos gerados: " + nosGerados);
				System.out.println("Nos expandidos: " + nosExpandidos);
				System.out.println("Penetrance: " + maxG);
            }
        }

        sc.close();
    }

    private static long getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}

