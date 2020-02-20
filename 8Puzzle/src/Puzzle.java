import java.util.*;

public class Puzzle {
    private int[] shape;
    private final int[] goal = {0,1,2,3,4,5,6,7,8};

    public Puzzle() {
        shape = new int[9];
    }

    public Puzzle(int[] shape) {
        this.shape = shape;
    }

    public int[] getShape() {
        return shape;
    }



    public int hamming() {
        int position = 0;
        int misplaced = 0;

        for (int val : shape){
            if (val != position && val != 0) {
                misplaced++;
            }
            position++;
        }

        return misplaced;
    }



    public int manhattan() {
        int position = 0;
        int distance = 0;

        for (int i = 0; i < shape.length; i++) {
            if (shape[i] != position && shape[i] != 0) {
                int x = shape[i] % 3;
                int y = shape[i] / 3;
                distance += Math.abs(x - (i % 3)) + Math.abs(y - (i / 3));
            }
            position++;
        }

        return distance;
    }



    public ArrayList<Puzzle> successors() {
        ArrayList<Puzzle> successors = new ArrayList<Puzzle>();
        int hole = -1;

        for (int i = 0; i < shape.length; i++) {
            if (shape[i] == 0)
                hole = i;
        }

        if (hole != 0 && hole != 1 && hole != 2) {
            swap(hole, hole - 3, successors);
        }

        if (hole != 6 && hole != 7 && hole != 8) {
            swap(hole, hole + 3, successors);
        }

        if (hole != 0 && hole != 3 && hole != 6) {
            swap(hole, hole - 1, successors);
        }

        if (hole != 2 && hole != 5 && hole != 8) {
            swap(hole, hole + 1, successors);
        }

        return successors;
    }



    public boolean isSolvable() {
        int inversions = 0;

        for (int i = 0; i < shape.length - 1; i++) {
            for (int j = i + 1; j < shape.length; j++) {
                if (shape[i] > shape[j] && shape[i] != 0 && shape[j] != 0) {
                    inversions++;
                }
            }
        }
        return (inversions % 2 == 0);
    }



    public static Stack<Puzzle> generate(int numberOfPuzzles) {
        Stack<Puzzle> generatedPuzzles = new Stack<>();
        HashSet<Integer> testSet = new HashSet<>();
        Puzzle generated;
        int val;

        for (int i = 0; i < numberOfPuzzles; i++) {
            Random rand = new Random();
            int[] possible = new int[9];

            do {
                for (int j = 0; j < 9; j++) {
                    testSet.add(j);
                }

                int index = 0;
                while (!testSet.isEmpty()) {
                    val = rand.nextInt(9);
                    if (testSet.contains(val)) {
                        testSet.remove(val);
                        possible[index++] = val;
                    }
                }

                generated = new Puzzle(possible);

            } while(!generated.isSolvable());
            generatedPuzzles.push(generated);
        }

        return generatedPuzzles;
    }



    public boolean isGoal() {
        return Arrays.equals(shape, goal);
    }



    public String toString() {
        StringBuilder build = new StringBuilder();

        for (int i = 0; i < shape.length; i++) {
            if (i % 3 == 2) {
                build.append(shape[i] + " ");
                build.append("\n");

            } else {
                build.append(shape[i] + " ");
            }
        }
        build.append("\n");

        return build.toString();
    }

    

    private void swap(int hole, int adjacent, ArrayList<Puzzle> successors) {
        int[] temp = shape.clone();
        int savedVal    = temp[hole];
        temp[hole]      = temp[adjacent];
        temp[adjacent]  = savedVal;
        successors.add(new Puzzle(temp));
    }
}
