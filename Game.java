import java.util.HashSet;

public class Game {
    public static boolean isX;
    private MoveTracker tracker;
    private GameBoard board;
    private boolean[] plays;
    private static int[] coords;
    HashSet<String> validMoves = new HashSet<String>();

    public Game(boolean _isX) {
    	plays = new boolean[64];
        isX = _isX;
        tracker = new MoveTracker();
        board = new GameBoard();
        coords = new int[2];

        char x = 'a';
        char y = '1';

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                validMoves.add("" + x + y);
                y++;
            }
            x++;
            y = '1';
        }

    }

    public void printState() {
        for (int i = 0; i <= 8; i++) {
            System.out.print(board.outputRow(i));
            tracker.printTurn(i);
        }
        System.out.println();
    }

    public boolean submitMove(String move, boolean isX) {
        if (!validMoves.contains(move)) {
            return false;
        } else if (tracker.submitMove(isX, move)) {
            board.move(move, isX);
            return true;
        } else {
            return false;
        }
    }
    
    public void printValidMoves() {
    	for(int i = 0; i < 8; ++i) {
    		for(int j = 0; j < 8; ++j) {
    			System.out.print("[" + ((plays[i*8 + j])? "O" : " ") + "]");
    		}
    		System.out.println();
    	}
    	System.out.println();
    }
    
    private void updateMoves(int[] move) {
    	updateMoves(move[0], move[1], true);
    }
    
    public void updateMoves(int x, int y, boolean set) {
    	plays[8*y+x] = !set;
    	byte[][] positions = board.getPositions();
    	
    	if(y - 1 >= 0 && positions[y - 1][x] == 0) {
    		plays[8*(y - 1) + x] = set;
    	}
    	if(y + 1 < 8 && positions[y + 1][x] == 0) {
    		plays[8*(y + 1) + x] = set;
    	}
    	if(x - 1 >= 0 && positions[y][x - 1] == 0) {
    		plays[8*y + (x - 1)] = set;
    	}
    	if(x + 1 < 8 && positions[y][x + 1] == 0) {
    		plays[8*y + (x + 1)] = set;
    	}
    }
    
    public static int squareToLinear(int row, int col) {
    	return 8*row + col;
    }
    
    public static int[] linearToSquare(int n) {
    	//col
    	coords[0] = n%8;
    	//row
    	coords[1] = n/8;
    	return coords;
    	
    }

    public GameBoard getBoard() {
        return board;
    }
    
    public boolean[] getPlays() {
    	return plays;
    }
}