import java.util.ArrayList;

public class MoveTracker {
    private ArrayList<String> oMoves;
    private ArrayList<String> xMoves;
    private int movesMade;

    public MoveTracker() {
        xMoves = new ArrayList<>();
        oMoves = new ArrayList<>();
        movesMade = 0;
    }

    public boolean submitMove(boolean xMove, String move) {
        if (xMove) {
            return submitMove(move, xMoves);
        } else {
            return submitMove(move, oMoves);
        }
    }

    private boolean submitMove(String move, ArrayList<String> list) {
        if (list.contains(move)) {
            System.out.println("Error: A piece is already placed there.");
            return false;
        } else {
            movesMade++;
            list.add(move);
            return true;
        }
    }

    //step: row that you are trying to print
    public void printTurn(int step) {
        if (step == 0) {
            if (Game.isX) System.out.println("Player vs. Opponent");
            else System.out.println("Opponent vs. Player");
        } else if (step > 0 && step <= 8) {
 
        	for(int i = 0; step + i <= xMoves.size(); i+= 8) {
                System.out.print("   " + (step + i) + ". ");
                if( (step + i) == 9) System.out.print(" ");
                if ( (step + i) - 1 < xMoves.size()) {
                    System.out.print(xMoves.get( (step + i) - 1) + " ");
                }
                if ( (step + i) - 1 < oMoves.size()) {
                    System.out.print(oMoves.get( (step + i) - 1));
                    System.out.print(" | ");
                }
                else {
                	System.out.print("~");
                }
        	}
            System.out.println();
        }
    }

    public int getTurnsPlayed() {
        return movesMade / 2;
    }

    public String getLastMove(boolean xMove) {
        if (xMove) {
            return xMoves.get(xMoves.size() - 1);
        } else {
            return oMoves.get(oMoves.size() - 1);
        }
    }
}

