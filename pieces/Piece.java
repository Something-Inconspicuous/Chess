package pieces;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import chess.*;
import chessgui.Runner;


public abstract class Piece implements MouseListener, ActionListener {

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

	protected boolean alrCalculated;

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
	 * @return the position of the piece by {rank, column}
	 */
	public int[] getPosition() {
		return new int[] { rank, column };
	}

	public int getRank() {
		return rank;
	}

	public int getColumn() {
		return column;
	}

	protected void revalidateMoves() {
		if (alrCalculated) {
			return;
		}

		HashMap<String, JPanel> tempMap = Runner.boardGUI.getPositionMap();
		validPanels.clear();

		LinkedList<Move> temp = getAllMoves();

		for (Move m : temp) {
			System.out.println(m.getMove());
			System.out.println(m.getDestCol() + " " + m.getDestRow());
			validPanels.add(tempMap.get((char) (65 + m.getDestCol()) + "" + (8 - m.getDestRow())));
		}

		alrCalculated = true;

	}

	public abstract void changePieceType(boolean isW);

	// this should honestly bee in the board class but its a bit too late to rewrite
	// code isnt it.
	public boolean validMove(int r, int c) {

		HashMap<String, JPanel> tempMap = Runner.boardGUI.getPositionMap();
		Piece[][] board = Runner.board.getBoard();
		Piece prevPiece = board[r][c];
		board[rank][column] = null;
		board[r][c] = this;

		int prevRank = rank;
		int prevCol = column;

		rank = r;
		column = c;

		King myKing = Runner.board.kings[getColor()];
		int kingCol = myKing.getColumn();
		int kingRank = myKing.getRank();

		// temporaily make the move

		// see if there still exists any checks on the king
		// make liek a function interface that applies a function on a certain loop if I
		// were to ooptimize this in the future

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (!tempMap
						.containsKey((char) (65 + kingCol + 2 * (2 * i - 1)) + "" + (8 - (kingRank + (2 * j - 1))))) {
					continue;
				}

				if (board[kingRank + (2 * j - 1)][kingCol + 2 * (2 * i - 1)] != null
						&& board[kingRank + (2 * j - 1)][kingCol + 2 * (2 * i - 1)].getColor() != getColor()
						&& board[kingRank + (2 * j - 1)][kingCol + 2 * (2 * i - 1)].getNameChar() == 'N') {

					board[r][c] = prevPiece;
					board[prevRank][prevCol] = this;

					rank = prevRank;
					column = prevCol;

					return false;
				}
			}

		}

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (!tempMap
						.containsKey((char) (65 + kingCol + (2 * j - 1)) + "" + (8 - (kingRank + 2 * (2 * i - 1))))) {
					continue;
				}

				if (board[kingRank + 2 * (2 * i - 1)][kingCol + (2 * j - 1)] != null
						&& board[kingRank + 2 * (2 * i - 1)][kingCol + (2 * j - 1)].getColor() != getColor()
						&& board[kingRank + 2 * (2 * i - 1)][kingCol + (2 * j - 1)].getNameChar() == 'N') {

					board[r][c] = prevPiece;
					board[prevRank][prevCol] = this;

					rank = prevRank;
					column = prevCol;
					return false;
				}

			}
		}

		for (int j = 0; j < 2; j++) {
			for (int i = 1; i <= Math.abs(j * 7 - kingCol); i++) {

				if (board[kingRank][(2 * j - 1) * i + kingCol] != null) {
					if (board[kingRank][(2 * j - 1) * i + kingCol].getColor() != getColor()
							&& board[kingRank][(2 * j - 1) * i + kingCol].getValue() >= 5) {
						board[r][c] = prevPiece;
						board[prevRank][prevCol] = this;

						rank = prevRank;
						column = prevCol;
						return false;
					}
					break;
				}

			}
		}

		for (int j = 0; j < 2; j++) {
			for (int i = 1; i <= Math.abs(j * 7 - kingRank); i++) {
				if (board[kingRank + (2 * j - 1) * i][kingCol] != null) {
					if (board[kingRank + (2 * j - 1) * i][kingCol].getColor() != getColor()
							&& board[kingRank + (2 * j - 1) * i][kingCol].getValue() >= 5) {
						board[r][c] = prevPiece;
						board[prevRank][prevCol] = this;

						rank = prevRank;
						column = prevCol;
						return false;
					}
					break;
				}

			}
		}

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				for (int row = kingRank + (2 * j - 1), col = kingCol + (2 * i - 1); row >= 0 && row < 8 && col >= 0
						&& col < 8; row += (2 * j - 1), col += (2 * i - 1)) {

					if (board[row][col] != null) {
						if (board[row][col].getColor() != getColor() && (board[row][col].getName().equals("Queen")
								|| board[row][col].getName().equals("Bishop"))) {
							board[r][c] = prevPiece;
							board[prevRank][prevCol] = this;

							rank = prevRank;
							column = prevCol;
							return false;
						}

						break;
					}

				}
			}
		}

		if (kingCol != 0 && board[kingRank + ((isWhite) ? -1 : 1)][kingCol - 1] != null
				&& board[kingRank + ((isWhite) ? -1 : 1)][kingCol - 1].getColor() != getColor()
				&& board[kingRank + ((isWhite) ? -1 : 1)][kingCol - 1].getNameChar() == 'P') {
			board[r][c] = prevPiece;
			board[prevRank][prevCol] = this;

			rank = prevRank;
			column = prevCol;
			return false;
		}

		if (kingCol != 7 && board[kingRank + ((isWhite) ? -1 : 1)][kingCol + 1] != null
				&& board[kingRank + ((isWhite) ? -1 : 1)][kingCol + 1].getColor() != getColor()
				&& board[kingRank + ((isWhite) ? -1 : 1)][kingCol + 1].getNameChar() == 'P') {
			board[r][c] = prevPiece;
			board[prevRank][prevCol] = this;

			rank = prevRank;
			column = prevCol;
			return false;
		}

		board[r][c] = prevPiece;
		board[prevRank][prevCol] = this;

		rank = prevRank;
		column = prevCol;
		return true;

	}

	public abstract LinkedList<Move> getAllMoves();

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

	public void actionPerformed(ActionEvent e) {
		alrCalculated = false;

	}

}