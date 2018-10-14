public class Problem {
    public State initialState;
    private static int[][] movemap =
            {{1, 3},
            {0, 2, 4},
            {1, 5},
            {0, 4, 6},
            {1, 3, 5, 7},
            {2, 4, 8},
            {3, 7},
            {4, 6, 8},
            {5, 7}};

    public boolean goalTest(State state) {
        for (int i = 0; i < state.state[i]; i++) {
            if (state.state[i] != i)
                return false;
        }

        return true;
    }

    public int[] moves(State state) {
        return movemap[state.zeroPos];
    }

    public State getState(State state, int move) {
        State returnState = new State();
        for (int i = 0; i < state.state.length; i++)
            returnState.state[i] = state.state[i];

        swap(returnState.state, state.blankPosition, move);
        returnState.blankPosition = (byte) move;
        returnState.pathCost = state.pathCost + 1;

        return returnState;
    }

    private void swap(byte[] state, byte index1, int index2) {
        state[index1] ^= state[index2];
        state[index2] ^= state[index1];
        state[index1] ^= state[index2];

        /*
            a[i] = 5    0101    1. 0001     2. 0001
            a[j] = 4    0100    1. 0100     2. 0101
         */
    }
}
