package pieces;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import chess.*;

public abstract class Piece implements MouseListener {

	protected int value;
	protected String name;
	protected char nameChar;

	protected int rank;
	protected int column;

	protected String setType;
	protected boolean isWhite;

	protected ImageIcon img;
	protected JButton pieceSprite;

	protected Board parent;
	protected Point prevPoint;

	protected JPanel parentSquare;
	protected Border originalBorder;

	protected abstract void revalidateMoves();

	protected HashSet<JPanel> validPanels;

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
		return new int[] { rank, column };
	}

	/**
	 * 
	 * @param r
	 * @param c
	 */
	protected abstract void move(int r, int c);

	/**
	 * Allow the player to view the possible locations this piece can move to
	 */
	protected abstract void seeable();

	public int getColor() {
		return (isWhite) ? 0 : 1;
	}

	public JButton getPieceSprite() {
		return pieceSprite;
	}
}