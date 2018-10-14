import java.util.ArrayList;
import java.util.Arrays;

public class Puzzle {
    private int[] shape;
    private final int[] goal = {0, 1, 2, 3, 4, 6, 7, 8};

    public Puzzle() {
        shape = new int[9];
    }

    public Puzzle(int[] shape) {
        this.shape = shape.clone();
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
        ArrayList<Puzzle_OLD> successors = new ArrayList<Puzzle_OLD>();
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


    public boolean isGoal() {
        return Arrays.equals(shape, goal);
    }


    public boolean isSolvable() {
        int inversions = 0;

        for (int i = 0; i < shape.length - 1; i++) {
            for (int j = i + 1; j < shape.length; j++) {
                if (shape[i] > shape[j] && shape[i] != 0 && shape[j] != 0)
                    inversions++;
            }
        }

        return (inversions % 2 == 0);
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
