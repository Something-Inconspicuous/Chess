package chess;
import pieces.*;


public class Move {
	
 
	// You guys are very stupid
	private int start_row;
	private int start_col;
 
	private int dest_row;
	private int dest_col;
	
	private Piece srcPiece;
	private Piece endPiece;
	private int flags;

	public static final int CAPTURE = 1;
	public static final int CASTLE = 2;
	public static final int PROMOTION = 4;
	public static final int CHECK = 8;
	public static final int EN_PASSANT = CAPTURE | 16;
	
	/**
	 * Constructs a move.
	 * 
	 * @param fromRank The starting rank/row
	 * @param fromFile The starting file/column
	 * @param toRank The target rank/row
	 * @param toFile The target file/column
	 * @param flags Any flags for this move
	 * @param srcPiece The piece moving
	 * @param endPiece The piece being moved upon (what?)
	 */
	public Move(int fromRank, int fromFile, int toRank, int toFile, int flags, Piece srcPiece, Piece endPiece) {
		start_col = fromFile;
		start_row = fromRank;
		
		dest_col = toFile;
		dest_row = toFile;
		
		this.flags = flags;
		
		this.srcPiece = srcPiece;
		this.endPiece = endPiece;
	}
	
	public Piece getSrcPiece() {
		return srcPiece;
	}
	
	public Piece getTargetPiece() {
		return endPiece;
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
		return flags == 0;
	}
	
	public boolean isCapture() {
		return (flags & CAPTURE) != 0;
	}
	
	public boolean isEnpassant() {
		return (flags ^ EN_PASSANT) == flags;
	}
	
	public boolean isCastle() {
		return (flags & CASTLE) != 0;
	}
	
	public boolean isPromotion() {
		return (flags & PROMOTION) != 0;
	}
	
	public boolean isCheck() {
		return (flags & CHECK) != 0;
	}

	@Override
	public String toString() {
		return "Move [start_row=" + start_row + ", start_col=" + start_col + ", dest_row=" + dest_row + ", dest_col="
				+ dest_col + ", srcPiece=" + srcPiece + ", endPiece=" + endPiece + ", flags=" + flags + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + start_row;
		result = prime * result + start_col;
		result = prime * result + dest_row;
		result = prime * result + dest_col;
		result = prime * result + ((srcPiece == null) ? 0 : srcPiece.hashCode());
		result = prime * result + ((endPiece == null) ? 0 : endPiece.hashCode());
		result = prime * result + flags;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (start_row != other.start_row)
			return false;
		if (start_col != other.start_col)
			return false;
		if (dest_row != other.dest_row)
			return false;
		if (dest_col != other.dest_col)
			return false;
		if (srcPiece == null) {
			if (other.srcPiece != null)
				return false;
		} else if (!srcPiece.equals(other.srcPiece))
			return false;
		if (endPiece == null) {
			if (other.endPiece != null)
				return false;
		} else if (!endPiece.equals(other.endPiece))
			return false;
		if (flags != other.flags)
			return false;
		return true;
	}
	

}