public class GameBoard {
	private char[][] board = new char[8][8];
  
  public GameBoard() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j< board.length; j++) {
        board[i][j] = '-';
      }
    }
  }
  
  public void move(String pos, boolean isX) {
    char val;
    
    if (isX == true) {
      val = 'X';
    } else {
      val = 'O';
    }
    
    int posX = pos.charAt(0);
    if(posX > 96) {
    	posX -= 32;
    }
    posX -= 65;
    int posY = pos.charAt(1);
    posY -= 49;
    
    board[posX][posY] = val;
  }
  
  public boolean checkWin() {
    //to-do
	  return false;
  }
  
  public String outputRow(int row) {
    String out = "";
	char yVal = (char)((int)'A' + row - 1);
    
    if (row == 0) {
      out = "  1 2 3 4 5 6 7 8     ";
    } else {
			out += yVal + " ";
      
      for (int i = 0; i < board.length; i++) {
        out += board[row - 1][i] + " ";
      }
      
      out += "    ";
    }
    
    return out;
  }
}
