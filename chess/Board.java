package chess;
import pieces.*;

public class Board {
	Piece[][] board = new Piece[8][8];
	
	
	public Board() {
		for(int i = 0; i < 2; i++) {
			board[i*7][0] = new Rook();
			board[i*7][1] = new Knight();
			board[i*7][2] = new Bishop();
			board[i*7][3] = new Queen();
			board[i*7][4] = new King();
			board[i*7][5] = new Bishop();
			board[i*7][6] = new Knight();
			board[i*7][7] = new Rook();
		}
		
		for(int j = 0; j < 8; j++) {
			board[1][j] = new Pawn();
		}
		
		for(int j = 0; j < 8; j++) {
			board[6][j] = new Pawn();
		}
		
	}
	
	public void move(int rank, int column, int toRank, int toColumn) throws Exception{
		if(board[toRank][toColumn] != null) {
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
