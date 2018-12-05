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
        for (int i = 0; i <= 8; i++) {
            System.out.print(board.outputRow(i));
            tracker.printTurn(i);
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