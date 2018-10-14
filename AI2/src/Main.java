import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        int n = 1000;
        int sol = 0;
        double solved;
        double time = 0;
        int iterations = 0;
        ArrayList<State> exampleSolutions = new ArrayList<>();
        FileWriter w = new FileWriter("analytics.txt", true);

        for (int i = 0; i < n; i++) {

            State x = nQueen.simulatedAnnealing(nQueen.generate());

            if (x != null && x.getFitness() == 0) {
                sol++;
                time += nQueen.getTime();
                iterations += nQueen.getIterations();
                System.out.println(i + ": time: " + nQueen.getTime() + " ms");
                System.out.println(i + ": iterations: " + nQueen.getIterations());
            } else {
                System.out.println(i + ": time out, no solution");
            }

            if (i >= n - 3) {
                exampleSolutions.add(x);
            }
        }

        solved = sol / ((double) n);
        time = time / ((float) n);
        iterations = iterations / n;
        w.write("\nSimulated Annealing -----------------------------------------------------\n");
        w.write("% solved:        " + solved + "\n");
        w.write("avg time:        " + time + " ms\n");
        w.write("avg iter:        " + iterations + "\n");
        w.write("Example States:\n");
        for (State s : exampleSolutions) {
            if (s != null)
                w.write(s.toString() + '\n');
        }

        System.out.println("Solutions: " + sol/((double) n));

        sol = 0;
        time = 0;
        iterations = 0;
        int generations = 0;
        exampleSolutions.clear();
        for (int i = 0; i < n; i++) {

            State x = nQueen.geneticAlgorithm(100, 0.25);

            if (x != null && x.getFitness() == 0) {
                sol++;
                time += nQueen.getTime();
                iterations += nQueen.getGenerated();
                generations += nQueen.getGeneration();
                System.out.println(i + ": time: " + nQueen.getTime() + " ms");
                System.out.println(i + ": generated: " + nQueen.getGenerated());
                System.out.println(i + ": generations: " + nQueen.getGeneration());
            } else {
                System.out.println(i + ": time out, no solution");
            }

            if (i >= n - 3) {
                exampleSolutions.add(x);
            }
        }

        solved = sol / ((double) n);
        time = time / ((float) n);
        iterations = iterations / n;
        generations = generations / n;
        w.write("\nGenetic Algorithm -------------------------------------------------------\n");
        w.write("% solved:        " + solved + "\n");
        w.write("avg time:        " + time + " ms\n");
        w.write("avg children:    " + iterations + "\n");
        w.write("avg generations: " + generations + "\n");
        w.write("Example States:\n");
        for (State s : exampleSolutions) {
            if (s != null)
                w.write(s.toString() + '\n');
        }
        w.close();

        System.out.println("Solutions: " + sol/((double) n));
    }
}
