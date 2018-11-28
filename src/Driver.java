public class Driver {
	public static void main(String[] args) {
  	 Game game = new Game(true);
  	 boolean isX = true;
  	 char move = 'a';
  	 for(int i = 1; i <= 8; ++i) {
  		 game.submitMove(""  + move++ + i, isX);
  		 isX = !isX;
  		 game.printState();
  	 }  	 
  }
}