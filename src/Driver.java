public class Driver {
    public static void main(String[] args) {

        Game game = new Game(true);
        Heuristic h = new Heuristic();
        boolean isX = true;
        char move = 'g';
        char[] moves = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        for (int i = 0; i < 2; ++i) {
            boolean cont = true;
            while (cont) {
                char randRow = moves[(int) (Math.random() * moves.length)];
                int randCol = 1 + (int) (Math.random() * 8);
                cont = !game.submitMove("" + randRow + randCol, isX);
            }
            isX = !isX;
            game.printState();
        }
        for (int i = 0; i < 30; ++i) {
            int bestX = 4;
            int bestY = 4;
            int bestH = 0;
            for (int x = 0; x < 8; ++x) {
                for (int y = 0; y < 8; ++y) {
                    int curH = h.scorePosition(x, y, game.getBoard().getPositions(), isX);
                    if (curH > bestH) {
                        bestH = curH;
                        bestX = x;
                        bestY = y;
                    }
                }
            }
            String bestMove = "" + moves[bestX] + (bestY + 1);
            System.out.println("Playing in: " + bestMove);

            game.submitMove(bestMove, isX);
            game.printState();
            isX = !isX;

        }

    }

    private static void testHeuristic() {
        Game game = new Game(true);
        Heuristic h = new Heuristic();
        boolean isX = true;
        char move = 'g';
        char[] moves = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        for (int i = 0; i <= 32; ++i) {
            boolean cont = true;
            while (cont) {
                char randRow = moves[(int) (Math.random() * moves.length)];
                int randCol = 1 + (int) (Math.random() * 8);
                cont = !game.submitMove("" + randRow + randCol, isX);
            }
            isX = !isX;
            game.printState();
            if (i % 8 == 0) {
                byte[][] positions = game.getBoard().getPositions();
                for (int x = 0; x < 8; ++x) {
                    for (int y = 0; y < 8; ++y) {
                        System.out.print("[" + positions[x][y] + "]");
                    }
                    System.out.println();
                }
                System.out.println();

                int[][] scores = new int[8][8];
                for (int x = 0; x < 8; ++x) {
                    for (int y = 0; y < 8; ++y) {
                        scores[x][y] = h.scorePosition(x, y, positions, true);
                        System.out.print("[" + scores[x][y] + "]");
                    }
                    System.out.println();
                }
                System.out.println();

            }
        }
    }
}