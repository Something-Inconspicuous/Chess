package chess;

import java.util.ArrayList;

import pieces.*;

public class Board {
	Piece[][] board = new Piece[8][8];

	private static final int WHITE = 0;
	private static final int BLACK = 1;

	public Board() {
		for (int i = 0; i < 2; i++) {
			board[i * 7][0] = new Rook("lichess", i != 0, i * 7, 0);
			board[i * 7][1] = new Knight("lichess", i != 0, i * 7, 1);
			board[i * 7][2] = new Bishop("lichess", i != 0, i * 7, 2);
			board[i * 7][3] = new Queen("lichess", i != 0, i * 7, 3);
			board[i * 7][4] = new King("lichess", i != 0, i * 7, 4);
			board[i * 7][5] = new Bishop("lichess", i != 0, i * 7, 5);
			board[i * 7][6] = new Knight("lichess", i != 0, i * 7, 6);
			board[i * 7][7] = new Rook("lichess", i != 0, i * 7, 7);
		}

		for (int j = 0; j < 8; j++) {
		 // board[1][j] = new Pawn("lichess", false, 1, j);
		}

		for (int j = 0; j < 8; j++) {
			board[6][j] = new Pawn("lichess", true, 6, j);
		}

	}

	/**
	 * @return the board as a 2D piece array
	 */
	public Piece[][] getBoard() {
		return board;
	}

	/**
	 * 
	 * @param rank
	 * @param column
	 * @param toRank
	 * @param toColumn
	 * @throws Exception
	 */
	public void move(int rank, int column, int toRank, int toColumn) throws Exception {
		if (board[toRank][toColumn] != null) {
			throw new Exception();
		}

		board[toRank][toColumn] = board[rank][column];
		board[rank][column] = null;
	}

	/**
	 * 
	 * @param rank
	 * @param column
	 * @param fromRank
	 * @param fromColumn
	 */
	public void takes(int rank, int column, int fromRank, int fromColumn) {
		board[rank][column] = board[fromRank][fromColumn];

		board[fromRank][fromColumn] = null;
	}

	/**
	 * 
	 * @return
	 */
	public int toPlay() {
		// return 0 if white and 1 if black
		return 0;
	}

	/**
	 * 
	 * @param player
	 * @return
	 */
	public ArrayList<Piece> allPiecesOfColor(int player) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();

		for (Piece[] x : board) {
			for (Piece piece : x) {
				if (piece.getColor() == player) {
					pieces.add(piece);
				}
			}
		}
		return pieces;
	}

	/**
	 * keeps track of castling availability
	 * 
	 * @return true if castling is still valid; false otherwise
	 */
	public boolean canCastle() {
		return false;
	}

	@Override
	/**
	 * returns the board as a string representation
	 */
	public String toString() {
		String returnStr = "";
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col] != null) {
					returnStr += "[" + board[row][col].getNameChar() + "]";
				} else {
					returnStr += "[ ]";
				}
				
				returnStr += ((col+1)%8 == 0) ? "\n" : "";
			}
		}

		return returnStr;
	}

}