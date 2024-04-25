package pieces;

import java.awt.event.MouseListener;

import javax.swing.JButton;
import chess.*;

public abstract class Piece implements MouseListener {

	protected int value;
	protected String name;
	protected char nameChar;

	protected int rank;
	protected int column;

	protected String setType;
	protected boolean isWhite;

	protected JButton pieceSprite;

	protected Board parent;

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
		return new int[] { column, rank };
	}

	/**
	 * 
	 * @param r
	 * @param c
	 */
	protected abstract void move(int r, int c);

	/**
	 * 
	 */
	protected abstract void seeable();

	public JButton getPieceSprite() {
		return pieceSprite;
	}
}