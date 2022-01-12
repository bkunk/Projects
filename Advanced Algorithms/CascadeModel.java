package AlgProjectCascade;

import AlgProjectCascade.CascadeModel.SpaceOwner;

/**
 * Cascade final project
 * @author Bryce Kunkle
 * @version Spring 2021
 */
public class Space {

	/** the player who owns the board square **/
	private SpaceOwner player; 
	
	/** array of T/F for where arrows are on tile **/
	private boolean [] arrows; // (left, top, right, bottom)
	
	/** the weight 0-9 that a board square holds **/
	private int tileWeight; 
	
	/** the row location of the tile **/
	private int row;
	
	/** the columnlocation of the tile **/
	private int col; 
	
	/**
	 * Constructor for creating a Space object
	 * @param player
	 * @param arrows
	 * @param tileWeight
	 * @param row
	 * @param col
	 */
	public Space(SpaceOwner player, boolean[] arrows, int tileWeight, int row, int col) {
		this.player = player;
		this.arrows = arrows;
		this.tileWeight = tileWeight;
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Rotate the arrows clockwise
	 */
	public void rotateCW() {
		boolean [] temp = arrows.clone();
		for(int i = 0; i < 4; i++){
				arrows[i] = temp[(i+1)%4];
		}
	}

	/**
	 * Rotate the arrows counterclockwise
	 */
	public void rotateCCW() {
		boolean[] temp = arrows.clone();
		for(int i = 0; i < 4; i++){
				if(i == 0) arrows[i] = temp[3];
				else arrows[i] = temp[(i-1)];
		}
	}
	
	/**
	 * getter for player
	 * @return player
	 */
	public SpaceOwner getPlayer() {
		return this.player;
	}
	
	/**
	 * setter for player
	 * @param player
	 */
	public void setPlayer(SpaceOwner player) {
		this.player = player;
	}
	
	/**
	 * getter for arrows
	 * @return arrows
	 */
	public boolean[] getArrows() {
		return this.arrows;
	}
	
	/**
	 * setter for arrows
	 * @param arrows
	 */
	public void setArrows(boolean[] arrows) {
		this.arrows = arrows;
	}
	
	/**
	 * getter for weight
	 * @return tileWeight
	 */
	public int getWeight() {
		return this.tileWeight;
	}
	
	/**
	 * setter for tileWeight
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.tileWeight = weight;
	}
	
	/**
	 * getter for row
	 * @return row
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * setter for row
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * getter for column
	 * @return col
	 */
	public int getCol() {
		return this.col;
	}
	
	/**
	 * setter for column
	 * @param col
	 */
	public void setCol(int col) {
		this.col = col;
	}


}
