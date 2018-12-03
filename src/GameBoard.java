public class GameBoard {
	private char[][] board = new char[8][8];
	private byte[][] positions = new byte[8][8];
	private int[][] weights;
  
  public GameBoard() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j< board.length; j++) {
        board[i][j] = '-';
        positions[i][j] = 0;
      }
    }
  }
  
  public void move(String pos, boolean isX) {
    char val;
    byte check;
    
    if (isX) {
      val = 'X';
      check = 1;
    } else {
      val = 'O';
      check = -1;
    }
    
    int row = pos.charAt(0);
    if(row > 96) {
    	row -= 32;
    }
    row -= 65;
    int col = pos.charAt(1);
    col -= 49;
    
    board[row][col] = val;
    positions[row][col] = check;
  }
  
  public boolean checkWin(String pos, boolean isX) {
      byte check;
      int counter = 0;

      if (isX) {
          check = 1;
      } else {
          check = -1;
      }

      int row = pos.charAt(0);
      if(row > 96) {
          row -= 32;
      }
      row -= 65;
      int col = pos.charAt(1);
      col -= 49;

      for (int i = 0; i < positions.length; i++) {
          if (positions[row][i] == check) {
              counter++;
          } else {
              counter = 0;
          }

          if (counter == 4) {
              return true;
          }
      }

      for (int i = 0; i < positions.length; i++) {
          if (positions[i][col] == check) {
              counter++;
          } else {
              counter = 0;
          }

          if (counter == 4) {
              return true;
          }
      }
      // Are you ready for the magic?
      int diagDiff = col - row;
      for (int i = 0; i < positions.length - Math.abs(diagDiff); i++) {
          if (diagDiff > 4) break;

          if (diagDiff < 0) {
              diagDiff = Math.abs(diagDiff);
              if (positions[i][diagDiff] == check) {
                  counter++;
              } else {
                  counter = 0;
              }

              if (counter == 4) {
                  return true;
              }

              diagDiff++;
          } else if (diagDiff > 0) {
              diagDiff = Math.abs(diagDiff);
              if (positions[diagDiff][i] == check) {
                  counter++;
              } else {
                  counter = 0;
              }

              if (counter == 4) {
                  return true;
              }

              diagDiff++;
          } else {
              if (positions[i][i] == check) {
                  counter++;
              } else {
                  counter = 0;
              }

              if (counter == 4) {
                  return true;
              }
          }
      }

      int diagSum = col + row;
      int start = 0;
      int end = diagSum + 1;
      if (diagSum > 11 || diagSum < 3) {
          return false;
      }

      if (diagSum > 7) {
          start = diagSum - 7;
          end = positions.length;
      }
      int j = end - 1;

      for (int i = start; i < end; i++) {
          if (positions[i][j] == check) {
              counter++;
          } else {
              counter = 0;
          }
          
          if (counter == 4) {
              return true;
          }
          
          j--;
     }
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
