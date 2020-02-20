import java.util.*;

public class Solver_OLD {
    private static int nodeCost;
    private static int depth;

    public static Stack<Puzzle_OLD> solve(Puzzle_OLD root, String heuristic) {
        nodeCost = 0;
        PriorityQueue<Node> pqueue = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.getTotalCost() > o2.getTotalCost())
                    return 1;
                else if (o1.getTotalCost() < o2.getTotalCost())
                    return -1;
                else
                    return 0;
            }
        });
        Stack<Puzzle_OLD> solutionSequence = new Stack<>();
        Node node = new Node(root);
        pqueue.add(node);

        while (!pqueue.isEmpty()) {
            Node temp = pqueue.poll();
            if (!temp.getCurrent().isGoal()) {
                ArrayList<Puzzle_OLD> successors = temp.getCurrent().successors();
                Node check = new Node(null,null,0,0);
                for (Puzzle_OLD puzzle : successors) {
                    if (heuristic.toLowerCase().equals("hamming")) {
                        check = new Node(temp, puzzle, temp.getDepth(),
                            puzzle.hamming());
                    } else if (heuristic.toLowerCase().equals("manhattan")) {
                        check = new Node(temp, puzzle, temp.getDepth(),
                            puzzle.manhattan());
                    }

                    if (!pqueue.contains(check)) {
                        check.setDepth(temp.getDepth() + 1);
                        pqueue.add(check);
                        nodeCost++;
                    }
                }
            } else {
                pqueue.clear();
                depth = temp.getDepth();
                solutionSequence.push(temp.getCurrent());
                while (temp.getParent() != null) {
                    temp = temp.getParent();
                    solutionSequence.push(temp.getCurrent());
                }
                break;
            }
        }
        return solutionSequence;
    }

    public static int getDepth() {
        return depth;
    }

    public static int getNodeCost() {
        return nodeCost;
    }


    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        int input;

        System.out.println("8-Puzzle_OLD Solver_OLD");

        do {
            System.out.print("\n   1 - Generate \n" +
                    "   2 - Input \n" +
                    "   0 - Exit \n" +
                    "   Enter option: ");

            input = kb.nextInt();

            switch (input) {
                case 0:
                    System.exit(8);
                case 1:
                    Puzzle_OLD generated = Puzzle_OLD.generate(1).pop();
                    Solver_OLD.solve(generated, "hamming");

                    int depth = Solver_OLD.getDepth();
                    int nodes = Solver_OLD.getNodeCost();

                    System.out.println("\n\n\n GENERATED SOLUTION\n");
                    System.out.println("Hamming ------- Depth: " + depth +
                            "\n                Nodes: " + nodes);

                    Solver_OLD.solve(generated, "manhattan");

                    depth = Solver_OLD.getDepth();
                    nodes = Solver_OLD.getNodeCost();

                    System.out.println("\nManhattan ----- Depth: " + depth +
                            "\n                Nodes: " + nodes);
                    break;
                case 2:
                    System.out.print("Please input desired puzzle separated by spaces: ");

                    try {
                        int[] puzzle = new int[9];
                        kb.nextLine();
                        String option = kb.nextLine();
                        String[] temp = option.split(" ");

                        for (int i = 0; i < puzzle.length; i++) {
                            puzzle[i] = Integer.parseInt(temp[i]);
                        }

                        Puzzle_OLD customPuzzle = new Puzzle_OLD(puzzle);
                        if (customPuzzle.isSolvable()) {
                            System.out.println("\n\n\nHAMMING SOLUTION\n");
                            Stack<Puzzle_OLD> customPuzzleSolution = Solver_OLD.solve(customPuzzle, "hamming");

                            while (!customPuzzleSolution.empty()) {
                                System.out.println(customPuzzleSolution.pop());
                            }

                            int hammingDepth = Solver_OLD.getDepth();
                            int hammingNodeCost = Solver_OLD.getNodeCost();



                            System.out.println("\n\n\nMANHATTAN SOLUTION\n");
                            customPuzzleSolution = Solver_OLD.solve(customPuzzle, "manhattan");

                            while (!customPuzzleSolution.empty()) {
                                System.out.println(customPuzzleSolution.pop());
                            }

                            int manhattanDepth = Solver_OLD.getDepth();
                            int manhattanNodeCost = Solver_OLD.getNodeCost();

                            System.out.println("Hamming ------- Depth: " + hammingDepth +
                                                "\n                Nodes: " + hammingNodeCost);
                            System.out.println("\nManhattan ----- Depth: " + manhattanDepth +
                                                "\n                Nodes: " + manhattanNodeCost);
                            System.out.println();
                        } else {
                            System.out.println(customPuzzle.isSolvable());
                            System.out.println("\nUnsolveable!!! Try again!");
                        }

                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
                        System.out.println("\nInvalid input!!! Try again!");
                        break;
                    }
                    break;
            }
        } while (!exit);
    }
}
