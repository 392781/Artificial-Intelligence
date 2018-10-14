import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.function.Function;

public class AStar implements LocalSearchInterface {
    private Function<State, Integer> heuristic;
    private PriorityQueue<Node> frontier;

    public Collection<byte[]> search(Problem problem, Function<State, Integer> heuristic)
            throws UnsolvableProblemException {
        frontier = new PriorityQueue<>((a, b) -> a.estTotalCost - b.estTotalCost);
        this.heuristic = heuristic;

        frontier.add(new Node(null, problem.initialState));

        while (true) {
            if (frontier.isEmpty())
                throw new UnsolvableProblemException();

            Node currentNode = frontier.poll();

            if (problem.goalTest(currentNode.state))
                return getStateList(currentNode);

            int[] moves = problem.moves(currentNode.state);
            for (int move : moves) {
                Node node = new Node(currentNode, problem.getState(currentNode.state, move));
                frontier.add(node);
            }
        }
    }

    private Collection<byte[]> getStateList(Node goalNode) {
        Collection<byte[]> solutionSeq = new ArrayList<>();
        loadStateList(solutionSeq, goalNode);
        return solutionSeq;
    }

    private void loadStateList(Collection<byte[]> solutionSeq, Node node) {
        if (node.parent == null) return;
        loadStateList(solutionSeq, node.parent);
        solutionSeq.add(node.state.state);
    }

    private class Node {
        Node parent;
        State state;
        int estTotalCost;

        private Node(Node parent, State state) {
            this.parent = parent;
            this.state = state;
            estTotalCost = state.pathCost + heuristic.apply(state);
        }
    }
}
