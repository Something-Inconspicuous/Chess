package logic;

import java.util.ArrayList;

import chess.Board;
import pieces.Piece;

public class Evaluator{
	
	
	//teama
	private static final int WHITE = 0;
	private static final int BLACK = 1;
	
	
	//values
	private static final int INFINITY  = 1000000;
	private static final int MATE      = 300000;
	private static final int STALEMATE = 0;
	
	/* Material value of a piece */
	private static final int kingval      = 350;
	private static final int queenval     = 900;
	private static final int rookval      = 500;
	private static final int bishopval    = 300;
	private static final int knightval    = 300;
	private static final int pawnval      = 100;
	
	/* The bonus for castling */
	private static final int CASTLE_BONUS = 10;
	
	
	//evaluation tables
	private static int bishoppos[][] =
		{
		 {-5, -5, -5, -5, -5, -5, -5, -5},
	     {-5, 10,  5,  8,  8,  5, 10, -5},
	     {-5,  5,  3,  8,  8,  3,  5, -5},
	     {-5,  3, 10,  3,  3, 10,  3, -5},
	     {-5,  3, 10,  3,  3, 10,  3, -5},
	     {-5,  5,  3,  8,  8,  3,  5, -5},
	     {-5, 10,  5,  8,  8,  5, 10, -5},
	     {-5, -5, -5, -5, -5, -5, -5, -5}
		};
		private static int knightpos[][] =
		{
		 {-10, -5, -5, -5, -5, -5, -5,-10},
		 { -8,  0,  0,  3,  3,  0,  0, -8},
		 { -8,  0, 10,  8,  8, 10,  0, -8},
		 { -8,  0,  8, 10, 10,  8,  0, -8},
		 { -8,  0,  8, 10, 10,  8,  0, -8},
		 { -8,  0, 10,  8,  8, 10,  0, -8},
		 { -8,  0,  0,  3,  3,  0,  0, -8},
		 {-10, -5, -5, -5, -5, -5, -5,-10}
		};
		
		private static int pawnposWhite[][] =
		{
		 {0,  0,  0,  0,  0,  0,  0,  0},
	     {0,  0,  0, -5, -5,  0,  0,  0},
	     {0,  2,  3,  4,  4,  3,  2,  0},
	     {0,  4,  6, 10, 10,  6,  4,  0},
	     {0,  6,  9, 10, 10,  9,  6,  0},
	     {4,  8, 12, 16, 16, 12,  8,  4},
	     {5, 10, 15, 20, 20, 15, 10,  5},
	     {0,  0,  0,  0,  0,  0,  0,  0}
		};
		
		// I created this, should be just a flipped
		// version of the white array
		private static int pawnposBlack[][] =
		{
		 {0,  0,  0,  0,  0,  0,  0,  0},
		 {5, 10, 15, 20, 20, 15, 10,  5},
		 {4,  8, 12, 16, 16, 12,  8,  4},
		 {0,  6,  9, 10, 10,  9,  6,  0},
		 {0,  4,  6, 10, 10,  6,  4,  0},
		 {0,  2,  3,  4,  4,  3,  2,  0},
		 {0,  0,  0, -5, -5,  0,  0,  0},
		 {0,  0,  0,  0,  0,  0,  0,  0}
		 };
	
	
		
		
		
		public int eval(Board board) {
			// Get current player
			int player = board.toPlay();
			int playerValue = 0;
			
			// Opponent is opposite of player
			int opponent = (player == WHITE) ? BLACK : WHITE;		
			int opponentValue = 0;
		
			ArrayList<Piece> pieces = board.allPiecesOfColor(player);
			playerValue = getValueOfPieces(pieces, player);
			
			pieces = board.allPiecesOfColor(opponent);
			opponentValue = getValueOfPieces(pieces, opponent);

			// Updates score based on castling
			if(board.hasCastled(player)) {
				playerValue += CASTLE_BONUS;
			}
			if(board.hasCastled(opponent)) {
				opponentValue += CASTLE_BONUS;
			}
					
			// Return the difference between our current score and opponents
			return playerValue - opponentValue;
		}
	
	
		private int getValueOfPieces(ArrayList<Piece> pieces, int player) {
		
			// Determine pawn array to use
			int[][] pawnpos = (player == WHITE) ? pawnposWhite : pawnposBlack;
			
			int value = 0;
			for(Piece piece : pieces) {
				switch(piece.getName()) {
					case "Pawn":
						value += pawnval + pawnpos[piece.getPosition()[0]][piece.getPosition()[1]];
						break;				
					case "Bishop":
						value += bishopval + bishoppos[piece.getPosition()[0]][piece.getPosition()[1]];
						break;				
					case "Knight":
						value += knightval + knightpos[piece.getPosition()[0]][piece.getPosition()[1]];
						break;
					case "Rook":
						value += rookval;                             
						break;
					case "Queen":
						value += queenval;
						break;
					case "King":
						value += kingval;	
						break;
				}
			}
			return value;
		}
		public int infty() {
			return INFINITY;
		}

		public int mate() {
			return MATE;
		}

		public int stalemate() {
			return STALEMATE;
		}
}