package logic;

import java.util.ArrayList;

import chess.Board;
import pieces.Piece;

public final class BetterEvaluator{
	private static final int WHITE = 0;
	private static final int BLACK = 1;

	private static final int STALEMATE = 0;
	private static final int MATE      = 300000;	
	private static final int INFINITY  = 1000000;

	private static final int PHASE_CONSTANT = 256;

	// Material value of a piece 
	private static final int pawnValue   = 100;
	private static final int knightValue = 320;
	private static final int bishopValue = 330;
	private static final int rookValue   = 500;
	private static final int queenValue  = 900;
	private static final int kingValue   = MATE;

	// Penalties for having 2 knights, rooks, or no pawns
	private static final int knightPenalty = -10;
	private static final int rookPenalty = -20;
	private static final int noPawnsPenalty = -20;

	// Bonuses depending on how many pawns are left
	private static final int[] knightPawnAdjustment =
		{-30, -20, -15, -10, -5, 0, 5, 10, 15}; 

	private static final int[] rookPawnAdjustment =
		{25, 20, 15, 10, 5, 0, -5, -10, -15}; 

	private static final int[] dualBishopPawnAdjustment =
		{40, 40, 35, 30, 25, 20, 20, 15, 15};

	private static final int tempoBonus = 10;


	private static final int pawnPosWhite[][] =
		{
		{0,   0,   0,   0,   0,   0,  0,  0},
		{5,  10,  10, -20, -20,  10, 10,  5},
		{5,  -5, -10,   0,   0, -10, -5,  5},
		{0,   0,   0,  20,  20,   0,  0,  0},
		{5,   5,  10,  25,  25,  10,  5,  5},		
		{10, 10,  20,  30,  30,  20, 10, 10},
		{50, 50,  50,  50,  50,  50, 50, 50},
		{0,   0,   0,   0,   0,   0,  0,  0}
		};

	private static final int pawnPosBlack[][] =
		{
		{0,   0,   0,   0,   0,   0,  0,  0},
		{50, 50,  50,  50,  50,  50, 50, 50},
		{10, 10,  20,  30,  30,  20, 10, 10},
		{5,   5,  10,  25,  25,  10,  5,  5},		
		{0,   0,   0,  20,  20,   0,  0,  0},
		{5,  -5, -10,   0,   0, -10, -5,  5},
		{5,  10,  10, -20, -20,  10, 10,  5},
		{0,   0,   0,   0,   0,   0,  0,  0}
		};

	private static final int knightPosWhite[][] =
		{
		{-50, -40, -30, -30, -30, -30, -40, -50},
		{-40, -20,   0,   5,   5,   0, -20, -40},
		{-30,   5,  10,  15,  15,  10,   5, -30},
		{-30,   0,  15,  20,  20,  15,   0, -30},
		{-30,   5,  15,  20,  20,  15,   5, -30},	 
		{-30,   0,  10,  15,  15,  10,   0, -30},	 
		{-40, -20,   0,   0,   0,   0, -20, -40},
		{-50, -40, -30, -30, -30, -30, -40, -50}
		};

	private static final int knightPosBlack[][] =
		{
		{-50, -40, -30, -30, -30, -30, -40, -50},
		{-40, -20,   0,   0,   0,   0, -20, -40},
		{-30,   0,  10,  15,  15,  10,   0, -30},	 
		{-30,   5,  15,  20,  20,  15,   5, -30},
		{-30,   0,  15,  20,  20,  15,   0, -30},
		{-30,   5,  10,  15,  15,  10,   5, -30},
		{-40, -20,   0,   5,   5,   0, -20, -40},
		{-50, -40, -30, -30, -30, -30, -40, -50}
		};

	private static final int bishopPosWhite[][] =
		{
		{-20, -10, -10, -10, -10, -10, -10, -20},
		{-10,   5,   0,   0,   0,   0,   5, -10},
		{-10,  10,  10,  10,  10,  10,  10, -10},     
		{-10,   0,  10,  10,  10,  10,   0, -10},     
		{-10,   5,   5,  10,  10,   5,   5, -10},     
		{-10,   0,   5,  10,  10,   5,   0, -10},     
		{-10,   0,   0,   0,   0,   0,   0, -10},     
		{-20, -10, -10, -10, -10, -10, -10, -20}
		};

	private static final int bishopPosBlack[][] =
		{
		{-20, -10, -10, -10, -10, -10, -10, -20},
		{-10,   0,   0,   0,   0,   0,   0, -10},
		{-10,   0,   5,  10,  10,   5,   0, -10},
		{-10,   5,   5,  10,  10,   5,   5, -10},     
		{-10,   0,  10,  10,  10,  10,   0, -10},     
		{-10,  10,  10,  10,  10,  10,  10, -10},     
		{-10,   5,   0,   0,   0,   0,   5, -10},
		{-20, -10, -10, -10, -10, -10, -10, -20}
		};

	private static final int rookPosWhite[][] =
		{
		{0,  0,  0,  5,  5,  0,  0,  0},
		{-5, 0,  0,  0,  0,  0,  0, -5},	 
		{-5, 0,  0,  0,  0,  0,  0, -5},	 
		{-5, 0,  0,  0,  0,  0,  0, -5},	 
		{-5, 0,  0,  0,  0,  0,  0, -5},	 
		{-5, 0,  0,  0,  0,  0,  0, -5},	 
		{5, 10, 10, 10, 10, 10, 10,  5},
		{0,  5,  5,  5,  5,  5,  5,  0}
		};

	private static final int rookPosBlack[][] =
		{
		{0,  5,  5,  5,  5,  5,  5,  0},
		{5, 10, 10, 10, 10, 10, 10,  5},
		{-5, 0,  0,  0,  0,  0,  0, -5},	 
		{-5, 0,  0,  0,  0,  0,  0, -5},	 
		{-5, 0,  0,  0,  0,  0,  0, -5},	 
		{-5, 0,  0,  0,  0,  0,  0, -5},	 
		{-5, 0,  0,  0,  0,  0,  0, -5},	 
		{0,  0,  0,  5,  5,  0,  0,  0}
		};

	private static final int queenPosWhite[][] =
		{
		{-20, -10, -10, -5, -5, -10, -10, -20},
		{-10,   0,   5,  0,  0,   0,   0, -10},	 
		{-10,   5,   5,  5,  5,   5,   0, -10},	 
		{0,     0,   5,  5,  5,   5,   0, -5},	 
		{-5,    0,   5,  5,  5,   5,   0, -5},	 
		{-10,   0,   5,  5,  5,   5,   0, -10},	 		
		{-10,   0,   0,  0,  0,   0,   0, -10},
		{-20, -10, -10, -5, -5, -10, -10, -20}
		};

	private static final int queenPosBlack[][] =
		{
		{-20, -10, -10, -5, -5, -10, -10, -20},
		{-10,   0,   0,  0,  0,   0,   0, -10},
		{-10,   0,   5,  5,  5,   5,   0, -10},	 
		{-5,    0,   5,  5,  5,   5,   0, -5},	 
		{0,     0,   5,  5,  5,   5,   0, -5},	 
		{-10,   5,   5,  5,  5,   5,   0, -10},	 
		{-10,   0,   5,  0,  0,   0,   0, -10},	 
		{-20, -10, -10, -5, -5, -10, -10, -20}
		};

	private static final int kingPosWhite[][] =
		{
		{20,   30,  10,   0,   0,  10,  30,  20},
		{20,   20,   0,   0,   0,   0,  20,  20},	 
		{-10, -20, -20, -20, -20, -20, -20, -10},	 		
		{-20, -30, -30, -40, -40, -30, -30, -20},	 
		{-30, -40, -40, -50, -50, -40, -40, -30},		
		{-30, -40, -40, -50, -50, -40, -40, -30},
		{-30, -40, -40, -50, -50, -40, -40, -30},
		{-30, -40, -40, -50, -50, -40, -40, -30}
		};

	private static final int kingPosBlack[][] =
		{
		{-30, -40, -40, -50, -50, -40, -40, -30},
		{-30, -40, -40, -50, -50, -40, -40, -30},
		{-30, -40, -40, -50, -50, -40, -40, -30},
		{-30, -40, -40, -50, -50, -40, -40, -30},
		{-20, -30, -30, -40, -40, -30, -30, -20},	 
		{-10, -20, -20, -20, -20, -20, -20, -10},	 
		{20,   20,   0,   0,   0,   0,  20,  20},	 
		{20,   30,  10,   0,   0,  10,  30,  20}
		};

	private static final int kingPosWhiteEnd[][] =
		{
		{-50, -30, -30, -30, -30, -30, -30, -50},
		{-30, -30,   0,   0,   0,   0, -30, -30},	 
		{-30, -10,  20,  30,  30,  20, -10, -30},					
		{-30, -10,  30,  40,  40,  30, -10, -30},	 		
		{-30, -10,  30,  40,  40,  30, -10, -30},				
		{-30, -10,  20,  30,  30,  20, -10, -30},		
		{-30, -20, -10,   0,   0, -10, -20, -30},
		{-50, -40, -30, -20, -20, -30, -40, -50}
		};

	private static final int kingPosBlackEnd[][] =
		{
		{-50, -40, -30, -20, -20, -30, -40, -50},
		{-30, -20, -10,   0,   0, -10, -20, -30},
		{-30, -10,  20,  30,  30,  20, -10, -30},		
		{-30, -10,  30,  40,  40,  30, -10, -30},		
		{-30, -10,  30,  40,  40,  30, -10, -30},	 		
		{-30, -10,  20,  30,  30,  20, -10, -30},			
		{-30, -30,   0,   0,   0,   0, -30, -30},	 
		{-50, -30, -30, -30, -30, -30, -30, -50}
		};
	
	
	
	
	
	
	public static int eval(Board board) {

		// Get current player
		int player = board.toPlay();
		int opponent = (player == WHITE) ? BLACK : WHITE;		
		//System.out.println("eval toplay" + player);
		int openingPlayerValue = getValueOfPieces(board, player, false);
		int endPlayerValue = getValueOfPieces(board, player, true);

		int openingOpponentValue = getValueOfPieces(board, opponent, false);
		int endOpponentValue = getValueOfPieces(board, opponent, true);

		// Weigh the two evaluation functions based on the current phase
		int phase = currentPhase(board);
		int playerValue = ((openingPlayerValue * (PHASE_CONSTANT - phase)) +
				(endPlayerValue * phase)) / PHASE_CONSTANT;
		int opponentValue = ((openingOpponentValue * (PHASE_CONSTANT - phase)) +
				(endOpponentValue * phase)) / PHASE_CONSTANT;

		// Return the difference between our current score and opponents
		return playerValue - opponentValue;
	}
	
	
	private static int getValueOfPieces(Board board, int player, boolean isEndGame) {

		ArrayList<Piece> pieces = board.allPiecesOfColor(player);

		// Determine which arrays to use
		int[][] pawnPos, knightPos, bishopPos, rookPos, queenPos, kingPos;
		int pawnCount, opponentPawnCount, bishopCount,
		knightCount, rookCount, queenCount;
		if (player == WHITE) {
			pawnPos = pawnPosWhite;
			knightPos = knightPosWhite;
			bishopPos = bishopPosWhite;
			rookPos = rookPosWhite;
			queenPos = queenPosWhite;
			if (isEndGame) {
				kingPos = kingPosWhiteEnd;
			}
			else {			
				kingPos = kingPosWhite;
			}			
			pawnCount = board.countOfType("Pawn", 0);
			opponentPawnCount = board.countOfType("Pawn", 1);
			bishopCount = board.countOfType("Bishop", 0);
			knightCount = board.countOfType("Knight", 0);
			rookCount = board.countOfType("Rook", 0);
			queenCount = board.countOfType("Queen", 0);
		}
		else {
			pawnPos = pawnPosBlack;
			knightPos = knightPosBlack;
			bishopPos = bishopPosBlack;
			rookPos = rookPosBlack;
			queenPos = queenPosBlack;
			if (isEndGame) {
				kingPos = kingPosBlackEnd;
			}
			else {			
				kingPos = kingPosBlack;
			}
			pawnCount = board.countOfType("Pawn", 1);
			opponentPawnCount = board.countOfType("Pawn", 0);
			bishopCount = board.countOfType("Bishop", 1);
			knightCount = board.countOfType("Knight", 1);
			rookCount = board.countOfType("Rook", 1);
			queenCount = board.countOfType("Queen", 1);		
		}
		//System.out.println(pawnCount);
		int value = 0;		
		for(Piece piece : pieces) {
			switch(piece.getName()) {
			case "Pawn":
				value += pawnValue + pawnPos[piece.getPosition()[0]][piece.getPosition()[1]];
				break;				
			case "Knight":
				value += knightValue + knightPos[piece.getPosition()[0]][piece.getPosition()[1]] +
				knightPawnAdjustment[pawnCount];
				break;
			case "Bishop":
				value += bishopValue + bishopPos[piece.getPosition()[0]][piece.getPosition()[1]];
				break;				
			case "Rook":
				value += rookValue + rookPos[piece.getPosition()[0]][piece.getPosition()[1]] +
				rookPawnAdjustment[pawnCount];
				break;
			case "Queen":
				value += queenValue + queenPos[piece.getPosition()[0]][piece.getPosition()[1]];
				break;
			case "King":
				value += kingValue + kingPos[piece.getPosition()[0]][piece.getPosition()[1]];
				break;
			}
		}

		// Give two bishops a bonus depending on pawns
		if (bishopCount > 1) {
			value += dualBishopPawnAdjustment[pawnCount];
		}
		if (knightCount > 1) {
			value += knightPenalty;
		}
		if (rookCount > 1) {
			value += rookPenalty;
		}
		if (pawnCount == 0) {
			value += noPawnsPenalty;
		}

		if (player == board.toPlay()) {
			value += tempoBonus;
		}


		if ((pawnCount == 0) && (value < bishopValue) && (value > 0)) {
			return 0;
		}


		if (value > 0 && pawnCount == 0 && opponentPawnCount == 0 && knightCount == 2 &&
				bishopCount == 0 && rookCount == 0 && queenCount == 0) {
			return 0;
		}

		return value;
	}
	
	private static int currentPhase(Board board) {		
		int knightPhase = 1;
		int bishopPhase = 1;
		int rookPhase = 2;
		int queenPhase = 4;
		int totalPhase = knightPhase*4 + bishopPhase*4 + rookPhase*4 + queenPhase*2;
		int phase = totalPhase;

		phase -= board.countOfType("Knight") * knightPhase;
		phase -= board.countOfType("Bishop") * bishopPhase;
		phase -= board.countOfType("Rook")   * rookPhase;
		phase -= board.countOfType("Queen")  * queenPhase;
		return (phase * PHASE_CONSTANT + (totalPhase / 2)) / totalPhase;
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

	public int weightOfPawn() {
		return pawnValue;
	}
	
}
