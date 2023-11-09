import java.util.*;

public class AStar {
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

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return g;
        }

        public double getF() {
            double valueFinal = g+h;
            return valueFinal;
        }
    }

    protected PriorityQueue<State> abertos;
    private HashMap<Integer, Double> fechados;

    private State actual;

    final private List<State> sucessores(State n, Ilayout goal) {
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

    private Iterator<State> buildPath(State end) {
        List<State> path = new ArrayList<>();
        State current = end;
        while (current != null) {
            path.add(current);
            current = current.father;
        }
        Collections.reverse(path);
        return path.iterator();
    }

    final public Iterator<State> solve(Ilayout valorInicial, Ilayout goal) {
        State inicial = new State(valorInicial, null, goal);
        abertos = new PriorityQueue<>(10, (s1, s2) -> (int) Math.signum(s1.getF() - s2.getF()));
        fechados = new HashMap<>();
        abertos.add(inicial);
        List<State> sucs;
        while (!valorInicial.isGoal(goal)) {
            if (abertos.isEmpty()) {
                return null;
            }
            actual = abertos.poll();
            if (actual.layout.isGoal(goal)) {
                return buildPath(actual);
            } else {
                sucs = sucessores(actual, goal);
                fechados.put(actual.layout.getBoard(), actual.getF());
                for (State sucessor : sucs) {
                    if (!fechados.containsKey(sucessor.layout.getBoard())) {
                        abertos.add(sucessor);
                    } else {
                        double existingF = fechados.get(sucessor.layout.getBoard());
                        if (sucessor.getF() < existingF) {
                            fechados.put(sucessor.layout.getBoard(), sucessor.getF());
                            abertos.add(sucessor);
                        }
                    }
                }
            }
        }
        return abertos.iterator();
    }
}
