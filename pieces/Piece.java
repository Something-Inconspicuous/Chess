package pieces;

abstract class Piece {
	private int value;
	private String name;
	private char nameChar;
	private int rank;
	private int column;
	
	/**
	 * 
	 * @return the value of the piece
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * 
	 * @return the name of the piece
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return the character that represents the piece
	 */
	public char getNameChar() {
		return nameChar;
	}
	
	/**
	 * 
	 * @return the position of the piece by {column, rank}
	 */
	public int[] getPosition() {
		return new int[] {column, rank};
	}
	
	/**
	 * 
	 * @param r
	 * @param c
	 */
	public abstract void move(int r, int c);
	
	/**
	 * 
	 */
	public abstract void seeable();
}
