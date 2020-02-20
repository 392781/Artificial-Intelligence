public class Node implements Comparable<Node> {
    private Puzzle current;
    private Node parent;
    private int depth;
    private int heuristic;
    private int totalCost;

    public Puzzle getCurrent() {
        return current;
    }

    public Node getParent() {
        return parent;
    }

    public int getDepth() {
        return depth;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }



    public Node(Puzzle puzzle) {
        this.current = puzzle;
        parent = null;
        depth = 0;
        heuristic = 0;
        totalCost = depth + heuristic;
    }



    public Node(Node prev, Puzzle puzzle, int depth, int heuristic) {
        parent = prev;
        this.current = puzzle;
        this.depth = depth;
        this.heuristic = heuristic;
        totalCost = depth + heuristic;
    }


    
    @Override
    public int compareTo(Node node) {
        if (this.getTotalCost() > node.getTotalCost())
            return 1;
        else if (this.getTotalCost() < node.getTotalCost())
            return -1;
        else
            return 0;
    }
}
