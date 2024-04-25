package chess;

import pieces.*;

public class Board {
	private Piece[][] board = new Piece[8][8];

	public Board() {
		for (int i = 0; i < 2; i++) {
			board[i * 7][0] = new Rook("club", i != 0, i * 7, 0);
			board[i * 7][1] = new Knight("club", i != 0, i * 7, 1);
			board[i * 7][2] = new Bishop("club", i != 0, i * 7, 2);
			board[i * 7][3] = new Queen("club", i != 0, i * 7, 3);
			board[i * 7][4] = new King("club", i != 0, i * 7, 4);
			board[i * 7][5] = new Bishop("club", i != 0, i * 7, 5);
			board[i * 7][6] = new Knight("club", i != 0, i * 7, 6);
			board[i * 7][7] = new Rook("club", i != 0, i * 7, 0);
		}

		for (int j = 0; j < 8; j++) {
			board[1][j] = new Pawn("club", false, 1, j);
		}

		for (int j = 0; j < 8; j++) {
			board[6][j] = new Pawn("club", true, 6, j);
		}

	}
	
	public Piece[][] getBoard() {
		return board;
	}

	public void move(int rank, int column, int toRank, int toColumn) throws Exception {
		if (board[toRank][toColumn] != null) {
			throw new Exception();
		}

		board[toRank][toColumn] = board[rank][column];
		board[rank][column] = null;
	}

	public void takes(int rank, int column, int fromRank, int fromColumn) {
		board[rank][column] = board[fromRank][fromColumn];

		board[fromRank][fromColumn] = null;
	}

}