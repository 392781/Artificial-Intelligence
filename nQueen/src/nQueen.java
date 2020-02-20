import java.util.*;

public class nQueen {
    private static int generation;
    private static int generated;
    private static int iterations;
    private static long start;
    private static long end;

    public static int getGeneration() {
        return generation;
    }

    public static int getGenerated() {
        return generated;
    }

    public static int getIterations() {
        return iterations;
    }

    public static double getTime() {
        return (end - start) / Math.pow(10, 6);
    }

    /*
        ALGORITHMS
     */

    public static State simulatedAnnealing(State initState) {
        State current = initState;
        State next;
        iterations = 0;
        double eDelta;
        double temperature = 1;
        boolean found = false;
        Random rand = new Random();

        start = System.nanoTime();
        while (!found && temperature > 0.00003) {
            next = new State(current.getState());
            temperature = 1/(Math.log10(iterations + 10));
            iterations++;

            if (temperature == 0)
                return current;

            next.mutate();

            eDelta = current.getFitness() - next.getFitness();
            double chance = rand.nextDouble();

            if (eDelta > 0)
                current = next;
            else if (chance <= Math.exp(eDelta/temperature))
                current = next;

            if (current.checkGoal()) {
                found = true;
                end = System.nanoTime();
            }
        }
        end = System.nanoTime();

        return current;
    }


    @SuppressWarnings("unchecked")
    public static State geneticAlgorithm(int populationSize, double mutationChance) {
        ArrayList<State> population = generate(populationSize);
        generated = population.size();
        ArrayList<State> newPopulation = new ArrayList<>();

        Random rand = new Random();
        State solution = null;
        boolean found = false;
        generation = 0;

        start = System.nanoTime();
        while(!found && generation < 10000) {

            Collections.sort(population, (a, b) -> (a.getFitness() - b.getFitness()));

            if (generation == 0 && population.get(0).getFitness() == 0) {
                solution = population.get(0);
                end = System.nanoTime();
                break;
            }

            for (int i = 0; i < population.size(); i++) {
                int xIndex;
                int yIndex;

                do {
                    xIndex = rand.nextInt((int) (population.size() * 0.25));
                    yIndex = rand.nextInt((int) (population.size() * 0.25));
                } while (xIndex == yIndex);

                State x = population.get(xIndex);
                State y = population.get(yIndex);

                State child = reproduce(x, y);
                generated++;

                double chance = rand.nextDouble();

                if (chance <= mutationChance) {
                    child.mutate();
                }

                if (child.checkGoal()) {
                    solution = child;
                    end = System.nanoTime();
                    found = true;
                    break;
                }

                newPopulation.add(child);
            }

            population = (ArrayList<State>) newPopulation.clone();
            newPopulation.clear();
            generation++;
        }

        return solution;
    }

    /*
        HELPER METHODS
     */

    private static State reproduce(State x, State y) {
        Random rand = new Random();
        int c = rand.nextInt(21);

        int[] xArr = x.getState();
        int[] yArr = y.getState();
        int[] iArr = new int[21];

        for (int i = 0; i < xArr.length; i++) {
            if (i < c) {
                iArr[i] = xArr[i];
            } else {
                iArr[i] = yArr[i];
            }
        }

        return new State(iArr);
    }


    private static ArrayList<State> generate(int k) {
        ArrayList<State> population = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            population.add(generate());
        }

        return population;
    }

    public static State generate() {
        Random rand = new Random();
        int[] arr = new int[21];

        for (int i = 0; i < 21; i++) {
            arr[i] = rand.nextInt(21);
        }

        return new State(arr);
    }
}
