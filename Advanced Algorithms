package AlgProjectCascade;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Cascade final project
 * @author Jessica Lilland
 * @author Bryce Kunkle
 * @version Spring 2021
 */
public class CascadeModel {
	
	/** 2D array of Space objects of the gameboard **/
	private Space[][] Board;
	
	/** enum for who owns a space on the board **/
	public enum SpaceOwner {
		red, blue, empty
		}
	
	/** the player whose turn it currently is **/
	private SpaceOwner curPlayer;
	
	/** score of how many red tiles are on the board **/
	private int numRed; // for keeping score
	
	/** score of how many blue tiles are on the board **/
	private int numBlue; // for keeping score
	/** tile that will be displayed to player as next tile to be placed **/
	private Space previewTile;
	
	/** A helper object to handle observer pattern behavior */
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	
	/**
	 * constructor to make an object of the model
	 * @param size the width that the player chooses to set the board to
	 */
	public CascadeModel(int size) {
		Board = new Space[size][size];
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				Board[row][col] = null; // set all spaces to nothing
			}
		}
		curPlayer = SpaceOwner.red; // red goes first
		numRed = 0; //start score at 0
		numBlue = 0;
		//create new tile to show as the next move
		previewTile = new Space(curPlayer, randomArrows(), randomWeight(), -1, -1); // did -1 to make compiler happy 
	}
	
	/**
	 * reset the game board
	 */
	public void reset() {
		for (int row = 0; row < Board.length; row++) {
			for (int col = 0; col < Board[0].length; col++) {
				Board[row][col] = null; // set all spaces to nothing
			}
		}
		curPlayer = SpaceOwner.red; // red goes first
		numRed = 0; //start score at 0
		numBlue = 0;
	}
	
	/**
	 * Set the size of the game board
	 * @param size
	 * @throws IllegalArgumentException
	 */
	public void setSize(int size) throws IllegalArgumentException {
		if (size < 0) {
			throw new IllegalArgumentException("Size cannot be a non-positive value");
		}
		Board = new Space[size][size];
		this.pcs.firePropertyChange("size", null, null);
	}
	
	/**
	 * get the size of the game board
	 * @return Board.length
	 */
	public int getSize() {
		return Board.length;
	}
	
	/**
	 * Generate an array of four random T/F values indicating whether there is an arrow there or not
	 * @return arrows
	 */
	public boolean[] randomArrows() {
		Random rand = new Random();
		boolean[] arrows = new boolean[4];
		for(int i = 0; i < 4; i++) {
			arrows[i] = rand.nextBoolean();
		}
		return arrows;
	}
	
	/**
	 * Rotates the previewTile arrows clockwise
	 */
	public void rotateArrowsCW() {
		previewTile.rotateCW();
	}
	
	/**
	 * Rotates the previewTile arrows counter-clockwise
	 */
	public void rotateArrowsCCW() {
		previewTile.rotateCCW();
	}
	
	/**
	 * Generate a random integer
	 * @return randomInt
	 */
	public int randomWeight() {
		Random rand = new Random();
		int upperbound = 10;
		int randomInt = rand.nextInt(upperbound);
		return randomInt;
	}
	
	/**
	 * Call made in addTile when the tile placed makes the attack on surrounding tiles
	 * @param row of attacker
	 * @param col of attacker
	 */
	public void attack(int row, int col) {

		// intitialize a Queue q
		Queue<Space> q = new LinkedList<>();
		
		q.add(Board[row][col]); // add the attacking tile to the queue
		
		while(!q.isEmpty()) {
			Space queueFront = q.remove(); // remove what's at the front of the queue, assign it to
			
			for(int i = 0; i < 4; i++) {
				int r = queueFront.getRow(); // for assigning a value for row
				int c = queueFront.getCol(); // for assigning value for column
				
				if(queueFront.getArrows()[i] && i == 0) c = queueFront.getCol()-1; // pointing left, change column to look at left space
				else if(queueFront.getArrows()[i] && i == 1) r = queueFront.getRow()-1; // pointing up, change row to one above
				else if(queueFront.getArrows()[i] && i == 2) c = queueFront.getCol()+1; // pointing right
				else if(queueFront.getArrows()[i] && i == 3) r = queueFront.getRow()+1; // pointing down
										
				// check if the space is off the board or null:
				if(r < 0 || r > Board.length-1 || c < 0 || c > Board.length-1 || Board[r][c] == null) { // nothing there so there's nothing to attack
				}
				else if(Board[r][c].getPlayer() != curPlayer) {// make sure tile being attacked isn't color of attacker
					if((Board[r][c].getArrows()[(i+2)%4]) && (queueFront.getWeight() > Board[r][c].getWeight())) { 
						 // arrow pointing back and attacking tile weight wins
						attackerWins(r, c);
						q.add(Board[r][c]);  
					} 
					else if(!Board[r][c].getArrows()[(i+2)%4]){ // no arrows pointing back, attacker wins
						attackerWins(r, c);
						q.add(Board[r][c]);
					} // else defender has won--do nothing
				} //else // same player, no attack
			} // end of for loop
					
		}
	}
	
	/**
	 * When an attacking tile wins against another, this method does all the necessary tasks
	 * @param row of losing tile
	 * @param col of losing tile
	 */
	public void attackerWins(int row, int col) {
		SpaceOwner oldPlayer = Board[row][col].getPlayer();
		switchOwner(row, col);
		this.pcs.firePropertyChange("space owner", oldPlayer, Board[row][col].getPlayer());
		if (curPlayer == SpaceOwner.blue) {
			numBlue++; // incrementing and decrementing scores here
			numRed--;
		}
		else {
			numBlue--;
			numRed++;
		}
	}
	
	/**
	 * Called by Visual when a tile location gets chosen
	 * @param row of location to be placed
	 * @param col of location to be placed
	 */
	public void addTile(int row, int col) throws IllegalArgumentException{
		//TODO add illegal moves here
		if(isGameOver()) {
			System.err.println("Game is over, cannot place tile");
			throw new IllegalArgumentException("Game is over, cannot place tile");
		}
		if(Board[row][col] != null) { // there has already been a tile placed here, can't over-write it
			System.err.println("A tile was already here, cannot place tile");
			throw new IllegalArgumentException("Tile was already here, cannot place tile");
		}
		
		previewTile.setRow(row); 
		previewTile.setCol(col);
		Board[row][col] = previewTile;
		this.pcs.firePropertyChange("add tile", null, Board[row][col]);

		if(curPlayer == SpaceOwner.red) numRed++; // update the score here
		else numBlue++;
		
		attack(row, col);
		
		if(!isGameOver()) {
			//switch curPlayer
			if (curPlayer == SpaceOwner.blue) curPlayer = SpaceOwner.red;
			else curPlayer = SpaceOwner.blue;
			
			//create new tile to show as the next move
			previewTile = new Space(curPlayer, randomArrows(), randomWeight(), -1, -1); // did -1 to make compiler happy
			this.pcs.firePropertyChange("new preview tile", Board[row][col], previewTile);
		}

	}
	
	/**
	 * The Visual side can call this to get the values of a tile
	 * @param row of tile
	 * @param col of tile
	 * @return text string of values
	 */
	public String getTile(int row, int col) {
		Space tile;// = new Space();
		
		if(row == -1) tile = previewTile;
		else tile = Board[row][col];
		if(tile==null) return "";
		String text = String.valueOf(tile.getWeight()) + " ";
		if(tile.getArrows()[0]) text += "< ";
		if(tile.getArrows()[1]) text += "^ ";
		if(tile.getArrows()[2]) text += "> ";
		if(tile.getArrows()[3]) text += "v ";
		
		return text; 
	}
	
	/**
	 * For Visual side to call to get whose turn it currently is
	 * @return curPlayer
	 */
	public SpaceOwner getCurPlayer() {
		return curPlayer;
	}
	
	/**
	 * For Visual to call to get the player that owns a tile
	 * @param row
	 * @param col
	 * @return player
	 */
	public SpaceOwner getSpaceOwner(int row, int col) {
		if(Board[row][col] == null) return SpaceOwner.empty;
		return Board[row][col].getPlayer();
	}
	
	/**
	 * Checks to see if the game is over
	 * @return true if it is over
	 */
	public boolean isGameOver() {
		for(int i = 0; i < Board.length; i++) {
			for(int j = 0; j < Board[0].length; j++) {
				// if there is an empty space, then the game is not over
				if (Board[i][j] == null || Board[i][j].getPlayer() == SpaceOwner.empty) return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns feedback on the state of the game
	 * who's turn it is, what the score is, that the game is over, who the winner was, etc
	 * @return feedback
	 */
	public String getPlayerFeedback() {
		String feedback;
		if(!isGameOver()) {
			feedback = "It is " + curPlayer + "'s turn. ";
			feedback += "Red's score is " + numRed + " and blue's score is " + numBlue;
		}else {
			feedback = "The game is over! ";
			if(numRed > numBlue) feedback += "Red wins!";
			else if(numBlue > numRed) feedback += "Blue wins! ";
		}
		return feedback;
	}
	
	/**
	 * Switches owner of a space to the opposite player
	 * @param row
	 * @param col
	 */
	public void switchOwner(int row, int col) {
		if (Board[row][col].getPlayer() == SpaceOwner.blue) Board[row][col].setPlayer(SpaceOwner.red);
		else if (Board[row][col].getPlayer() == SpaceOwner.red) Board[row][col].setPlayer(SpaceOwner.blue);
		else System.err.println("No space owner of this tile to switch");
	}

	/**
	 * Create a way for Observers to subscribe to this
	 * @param listener
	 */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    /**
     * Allow observers to be able to unsubscribe as well
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }


}
