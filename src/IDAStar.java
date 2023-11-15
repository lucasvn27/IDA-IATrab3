import java.util.*;

public class IDAStar {
    static class State {
        private Ilayout layout;
        private State father;
        private double g;
        private double h;

        public State(Ilayout l, State n, Ilayout goal) {
            layout = l;
            father = n;
            if (father != null) {
                g = father.g + l.getG();
            } else {
                g = 0.0;
            }
            h = l.estimateCost(goal);
        }

        public double getF() {
            return g + h;
        }
    }

    private State actual;
    private HashMap<Integer, Double> fechados;

    public Iterator<State> idaStarSearch(Ilayout valorInicial, Ilayout goal) {
        State inicial = new State(valorInicial, null, goal);
        fechados = new HashMap<>();
        double limite = inicial.getF();  // Inicializa o limite com o valor da heurística inicial

        while (true) {
            double[] result = depthLimitedSearch(inicial, limite);

            if (result[0] == 0) {
                return buildPath(result[1]);
            } else if (result[0] == Double.POSITIVE_INFINITY) {
                return null;  // Nenhuma solução encontrada
            } else {
                limite = result[0];  // Atualiza o limite para o próximo valor
            }
        }
    }

    private double[] depthLimitedSearch(State state, double limit) {
        if (state.layout.isGoal(state.layout)) {
            return new double[]{0, state.getF()};  // Solução encontrada
        } else if (state.getF() > limit) {
            return new double[]{state.getF(), 0};  // Corte
        } else {
            double minCost = Double.POSITIVE_INFINITY;

            List<State> sucs = sucessores(state, state.layout);
            fechados.put(state.layout.getBoard(), state.getF());

            for (State sucessor : sucs) {
                if (!fechados.containsKey(sucessor.layout.getBoard()) || sucessor.getF() < fechados.get(sucessor.layout.getBoard())) {
                    double[] result = depthLimitedSearch(sucessor, limit);
                    if (result[0] == 0) {
                        return result;
                    } else if (result[0] < minCost) {
                        minCost = result[0];
                    }
                }
            }

            return new double[]{minCost, 0};  // Corte
        }
    }

    private List<State> sucessores(State n, Ilayout goal) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n, goal);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    private Iterator<State> buildPath(double endF) {
        // Encontre o estado final correspondente ao valor F
        State endState = null;
        for (Map.Entry<Integer, Double> entry : fechados.entrySet()) {
            if (entry.getValue() == endF) {
                endState = new State(new Board(entry.getKey(), ""), null, null);
                break;
            }
        }

        if (endState == null) {
            throw new RuntimeException("Estado final não encontrado para o valor F fornecido.");
        }

        List<State> path = new ArrayList<>();
        State current = endState;
        while (current != null) {
            path.add(current);
            current = current.father;
        }
        Collections.reverse(path);
        return path.iterator();
    }
}
