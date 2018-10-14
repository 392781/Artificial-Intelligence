import java.util.Random;

public class State {
    private int[] state;
    private int fitness;
    private final float goal = 0;

    public State() {
        state = new int[21];
    }

    public State(int[] state) {
        this.state = state.clone();
        fitness = calcFitness();
    }

    public int[] getState() {
        return state;
    }

    public int getFitness() {
        return fitness;
    }

    public boolean checkGoal() {
        return fitness == goal;
    }


    public int calcFitness() {
        int fitness = 0;

        for (int i = 0; i < state.length - 1; i++) {
            for (int j = i + 1; j < state.length; j++) {

                if (state[i] == state[j]) {
                    fitness++;
                } else if ((j - i) == Math.abs(state[j] - state[i])) {
                    fitness++;
                }
            }
        }

        return fitness;
    }


    public void mutate() {
        Random rand = new Random();
        int index = rand.nextInt(state.length);
        int mutation = rand.nextInt(state.length);
        state[index] = mutation;
        fitness = this.calcFitness();
    }


    @Override
    public String toString() {
        StringBuffer build = new StringBuffer();
        build.append("{");
        for (int i = 0; i < state.length; i++) {
            if (i < state.length - 1)
                build.append(state[i] + ", ");
            else
                build.append(state[i]);
        }
        build.append("};");

        return build.toString();
    }
}
