import java.util.*;

public class IDAStar {
    static class State {
        private Ilayout layout;
        private State father;
        private double g;

        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father != null)
                g = father.g + l.getG();
            else
                g = 0.0;
        }

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return g;
        }
    }

    protected PriorityQueue<State> abertos;
    private Set<Ilayout> fechados;

    private State actual;

    final private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
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
        State inicial = new State(valorInicial, null);
        abertos = new PriorityQueue<>(10, (s1, s2) -> (int) Math.signum(s1.getG() - s2.getG()));
        fechados = new HashSet<>();
        abertos.add(inicial);
        List<State> sucs;
        while (!valorInicial.isGoal(goal)) {
            if (abertos.isEmpty()) {
                return null; // No solution
            }
            actual = abertos.poll();
            if (actual.layout.isGoal(goal)) {
                return buildPath(actual);
            } else {
                sucs = sucessores(actual);
                fechados.add(actual.layout);
                for (State sucessor : sucs) {
                    if (!fechados.contains(sucessor.layout)) {
                        abertos.add(sucessor);
                    }
                }
            }
        }
        return abertos.iterator();
    }
}
