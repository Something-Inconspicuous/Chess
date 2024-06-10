package chess;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;

import pieces.*;

public class Board {
	Piece[][] board = new Piece[8][8];
	public King[] kings = new King[2];

	private static final int WHITE = 0;
	private static final int BLACK = 1;
	private Piece prevPiece;
	private int currentTurn = 0;
	
	private JButton forceRecalibrate;
	private LinkedList<Move> moveHistory;
	public Board() {
		this("default");
	}

	public Board(String type) {
		forceRecalibrate = new JButton();
		
		for (int i = 0; i < 2; i++) {
			board[i * 7][0] = new Rook(type, i != 0, i * 7, 0);
			board[i * 7][1] = new Knight(type, i != 0, i * 7, 1);
			board[i * 7][2] = new Bishop(type, i != 0, i * 7, 2);
			board[i * 7][3] = new Queen(type, i != 0, i * 7, 3);
			board[i * 7][4] = new King(type, i != 0, i * 7, 4);
			board[i * 7][5] = new Bishop(type, i != 0, i * 7, 5);
			board[i * 7][6] = new Knight(type, i != 0, i * 7, 6);
			board[i * 7][7] = new Rook(type, i != 0, i * 7, 7);
			
			kings[1-i] = (King)board[i * 7][4];
		}

		for (int j = 0; j < 8; j++) {
			board[1][j] = new Pawn(type, false, 1, j);
		}

		for (int j = 0; j < 8; j++) {
			board[6][j] = new Pawn(type, true, 6, j);
		}
		
		for(int i = 6; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				forceRecalibrate.addActionListener(board[i][j]);
			} 
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
		return currentTurn;
	}
	
	public int getCurrentTurn() {
		return currentTurn;
	}

	public void toggleTurn() {
		currentTurn = (currentTurn == WHITE) ? BLACK : WHITE;
		forceRecalibrate.doClick();
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
				if (piece != null && piece.getColor() == player) {
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

	public int countOfType(String type) {
		int count = 0;
		for (Piece[] x : board) {
			for (Piece piece : x) {
				if (piece != null && piece.getName().equals(type)) {
					count++;
				}
			}
		}
		return count;
	}

	public int countOfType(String type, int color) {
		int count = 0;
		for (Piece[] x : board) {
			for (Piece piece : x) {
				if (piece != null && piece.getName().equals(type) && piece.getColor() == color) {
					count++;
				}
			}
		}
		return count;
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

				returnStr += ((col + 1) % 8 == 0) ? "\n" : "";
			}
		}

		return returnStr;
	}
	
	public LinkedList<Move> calculateAllTheMoves(){
		LinkedList<Move> list = new LinkedList<Move>();
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] == null)
					continue;
			
				list.addAll(board[i][j].getAllMoves());
			}
		}
		
		return list;
		
	}

	public String signature(){
		String out = "";
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				if(board[i][j] == null){
					out += " ";
				} else {
					out+= board[i][j].getNameChar();
				}
			}
		}
		return out;
	}
	
	public void applyMove(Move move) {
		String src = move.getMove().substring(0, 2);
		String to = move.getMove().substring(3);
		
		Piece prevPiece = board[to.charAt(0)-65][to.charAt(1)-48];
		
		board[to.charAt(0)-65][to.charAt(1)-48] = board[src.charAt(0)-65][src.charAt(1)-48];
		
		board[src.charAt(0)-65][src.charAt(1)-48] = null;

	}
	
	public void undoMove(Move move) {
		
		String src = move.getMove().substring(0, 2);
		String to = move.getMove().substring(3);
		
		
		board[src.charAt(0)-65][src.charAt(1)-48] = board[to.charAt(0)-65][to.charAt(1)-48];
		
		board[to.charAt(0)-65][to.charAt(1)-48] = prevPiece;
	}
	
	
	public boolean inCheck() {
		return !kings[currentTurn].validMove(kings[currentTurn].getRank(), kings[currentTurn].getColumn());
	}
	

}
