import java.util.Scanner;

public class PlayableGame {
    public static void run() {
        Scanner kb = new Scanner(System.in);
        boolean flag = true;
        boolean isX;
        String input;

        do {
            System.out.print("Who goes first, C for computer, O for opponent: ");
            input = kb.next();

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

        String move;
        boolean exit = true;
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

        }
        // Else do this
        do {
            //something something generated player move
            move = "a1";
            game.submitMove(move, isX);
            game.printState();
            System.out.println("  Player's move is: " + move);
            exit = game.getBoard().checkWin(move, isX);
            if (exit) {
                System.out.println("  Player wins!");
                break;
            }
            isX = !isX;

            //opponent move
            do {
                System.out.print("  Choose Opponent's next move: ");
                move = kb.next().toLowerCase();
                goodMove = game.submitMove(move, isX);
            } while (!goodMove);
            System.out.println();
            exit = game.getBoard().checkWin(move, isX);
            isX = !isX;
            game.printState();
            if (exit) {
                System.out.println("   Opponent wins!");
            }
        } while (!exit);
    }

    public static void main(String[] args) {
        run();
    }
}
