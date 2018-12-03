public class Driver {
	public static void main(String[] args) {
  	 Game game = new Game(true);
  	 boolean isX = true;
  	 char move = 'h';
  	 for(int i = 1; i <= 4; ++i) {
  		 game.submitMove(""  + move-- + i, isX);
  		 game.printState();
		 System.out.println(game.getBoard().checkWin("h1", isX));
  	 }  	 
  }
}