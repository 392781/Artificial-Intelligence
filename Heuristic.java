
public class Heuristic {
    //scoring vars for conditions
    private final int TWO = 10;
    private final int TWOPLUS = 50;
    private final int THREE = 100;
    private final int THREEPLUS = 44444;
    private final int FOUR = 1000000;
    private final int bTHREE = 120;
    private final int bFOUR = 50000;
    private final int BLOCKBONUS = 1;
    private final int WALLPENALTY = -1;

    public Heuristic() {

    }

    //this algorithm is extremely complicated
    //but, lucky for you, you don't need to know how it works
    //basically, it operates on each of the 4 positions around the x,y coordinate given
    //for each of those positions, if the piece there is an allied piece, we enter attacking mode
    //if it's an enemy piece, we enter blocking mode
    //if it's an empty square, we just stop looking entirely.
    //We go up to 4 squares both in that direction and in the opposite direction.
    //During this, we keep track of the chain in a row of the piece we found initially
    //We get bigger bonuses for blocking a lot of pieces, and bigger bonuses for attacking a lot of pieces
    //During this process, we also check whether the attack we are trying to make is possibly winnable at all
    //If it's not possibly winnable, we stop entirely, and do not add any points. This can happen if we're blocked
    //on both ends by enemy pieces, or a wall.

    public int scorePosition(int x, int y, byte[][] positions, boolean isX) {
        int bound = positions.length - 1;
        if (!checkBounds(x, y, bound)) {
            System.out.println("Inputted index was out of bounds.");
            return -1;
        }

        if (positions[x][y] != 0) {
            return 0;
        }

        int score = 0;

        //assigning whether we are a 1  or -1 in the board
        byte us = (byte) ((isX) ? 1 : -1);
        byte them = (byte) (us * -1);

        //double for-loop for each of the 4 directional pairs
        for (int n = 0; n < 4; n++) {
        	
        	int moveX = 0;
        	int moveY = 0;
        	
        	switch(n) {
        	case 0:
        		moveX = -1;
        		moveY = 0;
        		break;
        	case 1:
        		moveX = 1;
        		moveY = 0;
        		break;
        	case 2:
        		moveY = -1;
        		moveX = 0;
        		break;
        	case 3:
        		moveY = 1;
        		moveX = 0;
        		break;
        	}
            
            //how many in a row we have found
            int inARow = 0;
            int emptySpace = 0;
            //the current x and y position we are checking
            int curX = x;
            int curY = y;
            //whether we are attacking or blocking
            boolean attacking = false;
            boolean blocking = false;
            //whether our attack is finishable
            boolean finishable = true;
            boolean breakChain = false;
            
            boolean unblocked = true;

            for (int i = 0; i < 4; ++i) {
                //move our current position
                curX += moveX;
                curY += moveY;

                //if we passed the edge of the board, stop looking
                if (!checkBounds(curX, curY, bound)) {
                	if(i == 0) {
                		score += WALLPENALTY;
                	}
                	if(!breakChain) {
                		unblocked = false;
                	}
                    break;
                }

                byte cur = positions[curX][curY];

                //if we havent decided whether we're attacking or blocking yet
                if (!attacking && !blocking && cur != 0) {
                    inARow++;
                    //if we found a piece of ours, set us to attacking mode
                    if (cur == us) {
                        attacking = true;
                    }
                    //if we found a piece of theirs, set us to blocking mode
                    else {
                        blocking = true;
                    }
                }
                //if we're attacking and found another of our pieces
                else if (attacking) {
                    if (!breakChain) {
                    	if(cur == us) {
                            inARow++;                    		
                    	}
                    	else if(cur == them) {
                    		unblocked = false;
                    		break;
                    	}
                    	else {
                    		breakChain = true;
                    		emptySpace++;
                    	}
                    } else if(cur == 0) {
                        emptySpace++;
                    } else {
                    	break;
                    }
                }
                //if we're blocking and found another of their pieces
                else if (blocking) {
                	if (!breakChain) {
                		if(cur == them) {
                			inARow++;
                		}
                		else if(cur == us) {
                			unblocked = false;
                			break;
                		}
                		else {
                			breakChain = true;
                			emptySpace++;
                		}
                    } else if(cur == 0){
                        emptySpace++;
                    } else {
                    	break;
                    }
                }
                //if none of those things were true, we stop looking
                else {
                    break;
                }
            }

            //start looking in the other direction for the same pattern
            //we found in the original direction
            curX = x;
            curY = y;
            breakChain = false;
            boolean skip = false;

            for (int i = 0; i < 4; i++) {
                //move our cursor in the opposite direction
                curX -= moveX;
                curY -= moveY;

                //if we passed the edge of the board, stop looking
                if (!checkBounds(curX, curY, bound)) {
                    //if we hit the edge and we were attacking, check how many pieces
                    //we've found in a row. if it's less than 3, this is an unwinnable attack.
                    if ( (attacking || blocking) && inARow + emptySpace < 3) {
                        finishable = false;
                    } else if(!breakChain) {
                    	unblocked = false;
                    }
                    break;
                }

                byte cur = positions[curX][curY];
                if (attacking) {
                    //if we found a piece of theirs blocking us, check how many pieces
                    //we've found in a row. if it's less than 3, this is an unwinnable attack
                    if (!breakChain) {
                        if (cur == them) {
                        	unblocked = false;
                        	if(inARow + emptySpace < 3) {
                                finishable = false;
                        	}
                            break;
                        }
                        //otherwise, if we found another of our pieces, increment counter
                        else if (cur == us) {
                            inARow++;
                            if(i == 0) {
                            	skip = true;
                            }
                        }
                        //otherwise, we found an empty space 
                        else {
                            breakChain = true;
                            emptySpace++;
                        }
                    } else {
                        if (cur == them && inARow + emptySpace < 3) {
                            finishable = false;
                            break;
                        } else {
                            emptySpace++;
                        }
                    }
                }
                if (blocking) {
                    //if we found a piece of theirs blocking us, check how many pieces
                    //we've found in a row. if it's less than 3, this is an unwinnable attack
                    if (!breakChain) {
                        if (cur == us) {
                        	unblocked = false;
                        	if(inARow + emptySpace < 3) {
                                finishable = false;
                        	}
                            break;
                        }
                        //otherwise, if we found another of our pieces, increment counter
                        else if (cur == them) {
                            inARow++;
                            if(i == 0) {
                            	skip = true;
                            }
                        }
                        //otherwise, we found an empty space or can win, so stop looking.
                        else {
                            breakChain = true;
                            emptySpace++;
                        }
                    } else {
                        if (cur == us && inARow + emptySpace < 3) {
                            finishable = false;
                            break;
                        } else {
                            emptySpace++;
                        }
                    }
                }
                //otherwise, we stop looking
                else {
                    break;
                }
            }

            if (attacking && finishable) {
                if (inARow == 1) {
                	if(unblocked) {
                		score += TWOPLUS;
                	} else {
                        score += TWO;                		
                	}
                } else if (inARow == 2) {
                	if(unblocked) {
                		score += THREEPLUS;
                	} else {
                        score += THREE;
                	}
                } else if (inARow >= 3) {
                    score += FOUR;
                }
            } else if (blocking && finishable) {
                if (inARow == 1) {
                    score += BLOCKBONUS;
                } else if (inARow == 2) {
                	if(unblocked) {
                		score += bFOUR;
                	}
                	else {
                        score += bTHREE;
                	}
                } else if (inARow >= 3) {
                    score += bFOUR;
                }
            }
            
            if(skip) {
            	n++;
            }
        }

        return score;
    }

    private boolean checkBounds(int n, int bound) {
        return (n < 0 || n > bound);
    }

    private boolean checkBounds(int x, int y, int bound) {
        return !(x < 0 || x > bound || y < 0 || y > bound);
    }
}
