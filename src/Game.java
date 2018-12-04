public class Game {
    public static boolean isX;
    private MoveTracker tracker;
    private GameBoard board;

    public Game(boolean _isX) {
        isX = _isX;
        tracker = new MoveTracker();
        board = new GameBoard();
    }

    public void printState() {
        int turnsPlayed = tracker.getTurnsPlayed();
        for (int i = 0; i <= 8 + turnsPlayed; i++) {
            if (i <= 8) {
                System.out.print(board.outputRow(i));
            }
            if (i <= turnsPlayed) {
                tracker.printTurn(i);
            } else {
                System.out.println();
            }
        }
        System.out.println();
    }

    public boolean submitMove(String move, boolean isX) {
        if (tracker.submitMove(isX, move)) {
            board.move(move, isX);
            return true;
        } else {
            return false;
        }
    }

    public GameBoard getBoard() {
        return board;
    }
}