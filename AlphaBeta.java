public class AlphaBeta {
	Game g;
	Heuristic h;
	public int bestX;
	public int bestY;
	private int depthLimit;
	
    public AlphaBeta(Game game) {
    	g = game;
    	h = new Heuristic();
    }
    
    public int DFS(int depthLimit, boolean isX) {
    	boolean[] plays = g.getPlays();
    	byte[][] positions = g.getBoard().getPositions();
    	this.depthLimit = depthLimit;
    	return DFS(depthLimit, isX, plays, positions);
    }
    
    private int DFS(int depthLimit, boolean isX, boolean[] plays, byte[][] positions) {
    	if(depthLimit > 1) {
    		int highScore = (isX) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        	int highX = -1;
        	int highY = -1;
        	for(int i = 0; i < plays.length; ++i) {
        		int cur = 0;
        		if(plays[i]) {
        			int x = i%8;
        			int y = i/8;
        			
        			if(isX) {
        				cur += h.scorePosition(y, x, positions, isX);
        			}
        			else {
        				cur -= h.scorePosition(y, x, positions, isX);
        			}
        			
        			plays[8*y+x] = false;
        			boolean setUp = false;
        			boolean setDown = false;
        			boolean setLeft = false;
        			boolean setRight = false;
        	    	
        	    	if(y - 1 >= 0 && positions[y - 1][x] == 0) {
        	    		plays[8*(y - 1) + x] = true;
        	    		setUp = true;
        	    	}
        	    	if(y + 1 < 8 && positions[y + 1][x] == 0) {
        	    		plays[8*(y + 1) + x] = true;
        	    		setDown = true;
        	    	}
        	    	if(x - 1 >= 0 && positions[y][x - 1] == 0) {
        	    		plays[8*y + (x - 1)] = true;
        	    		setLeft = true;
        	    	}
        	    	if(x + 1 < 8 && positions[y][x + 1] == 0) {
        	    		plays[8*y + (x + 1)] = true;
        	    		setRight = true;
        	    	}
        			
        			positions[x][y] = (byte)((isX) ? 1 : -1);
        			cur += DFS(depthLimit - 1, !isX, plays, positions);
        			positions[x][y] = 0;
        			
        			plays[8*y+x] = true;
        			if(setUp) {
        	    		plays[8*(y - 1) + x] = false;
        	    	}
        	    	if(setDown) {
        	    		plays[8*(y + 1) + x] = false;
        	    	}
        	    	if(setLeft) {
        	    		plays[8*y + (x - 1)] = false;
        	    	}
        	    	if(setRight) {
        	    		plays[8*y + (x + 1)] = false;
        	    	}
        			
        			if(isX && cur > highScore) {
        				highScore = cur;
        				highX = x;
        				highY = y;
        			}
        			else if(!isX && cur < highScore) {
        				highScore = cur;
        				highX = x;
        				highY = y;
        			}
        			
        			this.bestX = highX;
                	this.bestY = highY;
        		}
        		if(depthLimit == this.depthLimit) {
        			System.out.print("[" + cur + "]");
        			if((i + 1) % 8 == 0) {
        				System.out.println();
        			}
        		}
        	}
        	return highScore;
    	}
    	else {
    		return 0;
    	}
    }
    
}
