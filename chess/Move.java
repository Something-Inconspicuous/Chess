package chess;
import pieces.*;


public class Move {
	
 
	int start_row;
	int start_col;
		
	//u need a desitonation Row and jgb3j 
	int dest_row;
	int dest_col;
	String move;
	
	Piece srcPiece;
	Piece endPiece;
	int type;
	
	public Move(String move, int type, Piece srcPiece, Piece endPiece) {
		this.move = move;
		start_col = move.charAt(0) - 'A';
		start_row = move.charAt(1) - '0';
		
		dest_col = move.charAt(3) - 'A';
		dest_row = move.charAt(4) - '0';
		
		this.type = type;
		
		
		this.srcPiece = srcPiece;
		this.endPiece = endPiece;
	}
	
	public Piece getSrcPiece() {
		return srcPiece;
	}
	
	public Piece getTargetPiece() {
		return endPiece;
	}
	
	public String getMove() {
		return move;
	}
	public int getStartRow() {
		return start_row;
	}
	
	public int getStartCol() {
		return start_col;
	}
	
	public int getDestRow() {
		return dest_row;
	}
	
	public int getDestCol() {
		return dest_col;
	}
	
	public boolean isNormal() {
		return type == 0;
	}
	
	public boolean isCapture() {
		return type == 1;
	}
	
	public boolean isEnpassant() {
		return type == 5;
	}
	
	public boolean isCastle() {
		return type == 2;
	}
	
	public boolean isPromotion() {
		return type == 3;
	}
	
	public boolean isCheck() {
		return type == 4;
	}
	

}