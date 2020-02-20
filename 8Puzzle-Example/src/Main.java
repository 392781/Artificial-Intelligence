import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Problem p = new Problem();
        for (Function<byte[], Integer> heuristic : getHeuristicList()) {
            p.initialState.state = new byte[9];
        }
    }

    private static boolean isValidState(State state) {
        boolean isValid = true;

        for (int i = 0; i < state.state.length - 1; i++)
            for (int j = i + 1; j < state.state.length; j++)
                if (state.state[i] != 0 && state.state[j] != 0 && state.state[i] > state.state[j])
                    isValid = !isValid;

        return isValid;
    }

    private static Collection<Function<byte[], Integer>> getHeuristicList() {
        Collection<Function<byte[], Integer>> list = new ArrayList<>();
        list.add((hamming) -> {
            int counter = 0;
            for (int i = 0; i < hamming.length; ++i)
                if (hamming[i] != 0 && hamming[i] != i)
                    ++counter;

            return counter;
        });

        list.add((manhattan) -> {
            int counter = 0;
            for (int i = 0; i < manhattan.length; ++i) {
                if (manhattan[i] != 0 && manhattan[i] != i) {
                    int delta = Math.abs(manhattan[i] - i);
                    counter += delta / 3 + delta % 3;
                }
            }
            return counter;
        });
        return list;
    }
}
