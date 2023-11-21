import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

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

        public String toString() {
            return layout.toString();
        }

        public double getG() {
            return g;
        }

        public double getF() {
            return g + h;
        }
    }

    private State actual;
    private Stack<State> path; 

    private double search(State node, double g, double bound, Ilayout goal) {
        double f = g + node.h;
        if (f > bound) {
            return f;
        }
        if (node.layout.isGoal(goal)) {
            actual = node;
            return -1; 
        }

        double min = Double.POSITIVE_INFINITY;
        List<State> sucs = sucessores(node, goal);
        for (State successor : sucs) {
            path.push(successor);
            double t = search(successor, g + successor.layout.getG(), bound, goal);
            path.pop();
            if (t == -1) {
                return -1;
            }
            if (t < min) {
                min = t;
            }
        }
        return min;
    }

    private List<State> sucessores(State n, Ilayout goal) { //ordenar pelo valor de f(n)
        List<State> sucs = n.layout.children().stream()
                .filter(e -> n.father == null || !e.equals(n.father.layout))
                .map(e -> new State(e, n, goal))
                .sorted((s1, s2) -> Double.compare(s1.getF(), s2.getF()))
                .collect(Collectors.toList());
        return sucs;
    }
    


    private Iterator<State> buildPath(State end) {
        List<State> pathList = new java.util.ArrayList<>();
        State current = end;
        while (current != null) {
            pathList.add(current);
            current = current.father;
        }
        java.util.Collections.reverse(pathList);
        return pathList.iterator();
    }

    public Iterator<State> solve(Ilayout valorInicial, Ilayout goal) {
        State inicial = new State(valorInicial, null, goal);
        path = new Stack<>();
        path.push(inicial); 
        double bound = inicial.getF();
        while (true) {
            double t = search(inicial, 0, bound, goal);
            if (t == -1) {
                return buildPath(actual);
            }
            if (t == Double.POSITIVE_INFINITY) {
                return null;
            }
            bound = t;
            path.clear();
            path.push(inicial);
        }
    }
}
