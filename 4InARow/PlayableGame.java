import java.util.Scanner;
import java.util.Random;
public class PlayableGame {
    public static void run(int depth) {
        Scanner kb = new Scanner(System.in);
        boolean flag = true;
        boolean isX;
        char[] moves = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        String input;

        do {
            System.out.print("Who goes first, C for computer, O for opponent: ");
            input = kb.next().toUpperCase();

            if (input.equals("C") || input.equals("O")) {
                flag = false;
            } else{
                System.out.println("Incorrect, try again");
            }

        } while(flag);
        System.out.println();

        if (input.equals("C"))
            isX = true;
        else
            isX = false;


        Game game = new Game(isX);
        AlphaBeta ab = new AlphaBeta(game);

        String move;
        boolean exit = false;
        boolean goodMove;

        // If opponent goes first, do this
        if (!isX) {
            do {
                System.out.print("\n  Choose Opponent's next move: ");
                move = kb.next().toLowerCase();
                goodMove = game.submitMove(move, isX);
            } while (!goodMove);
            game.printState();
            isX = !isX;
        // If player goes first, do this
        } else {
            Random rand = new Random();
            char x = (char) (rand.nextInt(2) + 100);
            int y = rand.nextInt(2) + 4;

            move = "" + x + y;
            game.submitMove(move, isX);
            game.printState();
            isX = !isX;
            System.out.println("  Player's move is: " + move);
        }

        // Else do this
        while (!exit) {
            if (!isX) {
                do {
                    System.out.print("  Choose Opponent's next move: ");
                    move = kb.next().toLowerCase();
                    goodMove = game.submitMove(move, isX);
                } while (!goodMove);
                System.out.println();
                exit = game.getBoard().checkWin(move, isX);
                game.printState();
                isX = !isX;

                if (exit) {
                    System.out.println("  Opponent wins!");
                }
            } else {
                ab.DFS(depth, isX);
                move = "" + moves[ab.bestY] + (ab.bestX + 1);
                game.submitMove(move, isX);
                game.printState();
                System.out.println("  Player's move is: " + move);
                exit = game.getBoard().checkWin(move, isX);
                isX = !isX;
                if (exit) {
                    System.out.println("  Player wins!");
                }
            }
        }
    }
}
