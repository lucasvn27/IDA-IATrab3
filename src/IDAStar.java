import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

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

        public double getH(){
            return h;
        }

        public double getF() {
            return g + h;
        }
    }

    private State actual;
    private Stack<State> path; 

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
        List<State> pathList = new java.util.ArrayList<>();
        while (end != null) {
            pathList.add(end);
            end = end.father;
        }
        java.util.Collections.reverse(pathList);
        return pathList.iterator();
    }

    private double search(State node, double bound, Ilayout goal) {
        Stack<State> stack = new Stack<>();
        double min = Double.POSITIVE_INFINITY;
        double f;
    
        stack.push(node);
    
        while (!stack.isEmpty()) {
            node = stack.pop();
            f = node.getF();
    
            if (f > bound) {
                min = Math.min(min, f);
                continue;
            }
    
            if (node.layout.isGoal(goal)) {
                actual = node;
                return -1;
            }
    
            List<State> sucs = sucessores(node, goal);
            for (State successor : sucs) {
                stack.push(successor);
            }
        }
    
        return min;
    }

    public Iterator<State> solve(Ilayout valorInicial, Ilayout goal) {
        State inicial = new State(valorInicial, null, goal);
        path = new Stack<>();
        path.push(inicial); 
        double bound = inicial.getF();
        while (true) {
            double t = search(inicial, bound, goal);
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
