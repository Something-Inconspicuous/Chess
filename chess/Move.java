package chess;



public class Move {
	
 
	int start_row;
	int start_col;
		
	//u need a desitonation Row and jgb3j 
	int dest_row;
	int dest_col;
	String move;
	
	int type;
	
	public Move(String move, int type) {
		this.move = move;
		start_col = move.charAt(0) - 65;
		start_row = move.charAt(1) - 65;
		
		dest_row = move.charAt(3) - 65;
		dest_col = move.charAt(4) - 65;
		
		this.type = type;
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