package Search;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import chess.Move;
import chess.Board;
import util.LRUMap;


/**
 * A chess searcher that uses:
 * 
 * Negamax
 * Iterative Deepening 
 * Transposition Table
 * Quiescence Search
 * Move ordering (captures and promotions first)
 * Killer heuristic (best two moves seen are stored and tried first)
 * Avoid repetitions (to avoid draws)
 * Check extension (searches one ply deeper when board is in check)
 */

public class Negamax extends AbstractSearcher{

	// How deep our quiescence search goes
	private final int QUIESCENCE_MIN_DEPTH = 2;
	private final int QUIESCENCE_MID_DEPTH = 5;
	private final int QUIESCENCE_MAX_DEPTH = 100;

	// Create hash that contains previously processed nodes.
	private final int TRANSPOSITION_INIT_ELEMS = 2 << 20;
	private final int TRANSPOSITION_MAX_ELMS = TRANSPOSITION_INIT_ELEMS;
	private final float TRANSPOSITION_LOAD_FACTOR = 0.75f;
	private Map<String, BoardInfo<Move>> transpositionTable;

    // Store the information about repetitions
	private BoardCount boardCount = new BoardCount();

	// The depth the iterative deepening is currently at
	private int depthIteration = 0;

	private Board board;
    //private Book<Move, Board> book = new Book<Move, Board>();

	// Variables for performance analysis
	private ThreadMXBean bean;
	private long nodeCount, startTime, endTime;
	private int originalMinDepth = -1;

	public Move getBestMove(Board board, int myTime, int opTime) {
		nodeCount = 0;
		bean = ManagementFactory.getThreadMXBean();
		startTime = bean.getCurrentThreadCpuTime();

		this.board = board;		
		transpositionTable = new LRUMap<String, BoardInfo<Move>>(TRANSPOSITION_INIT_ELEMS,
				TRANSPOSITION_MAX_ELMS,
				TRANSPOSITION_LOAD_FACTOR);

		// Track current board to avoid repetitions
		boardCount.increment(board);

		// Start the time - logic that figures out how much time
		// we should have, is in the timer object. Also make sure
		// we don't timeup until we've processed the minDepth's needed
		//timer.start(myTime, opTime);
		//timer.notOkToTimeup();

		LinkedList<Move> moves = generateOrderedMoves();
		Collections.sort(moves, moveComparator);

		// Should never be called if we don't have any moves left
		if (moves.isEmpty()) {
			throw new IllegalStateException(); 
		}

		int infinity = evaluator.infty();
		Move bestMove = moves.getFirst();
		boolean timeout = false;

		// To do Iterative deepening, we have to repeat our alpha-beta search
		// froMove n = 1 ... depth. We keep track of the move ordering
		// so each future depth starts with the best possible moves	
		for(depthIteration = 1; depthIteration <= maxDepth; depthIteration++) {

			// We've hit the minimuMove depth, so timeup regularly
			

			// We may return froMove this call before we searched full depth, so verify result					    
			Move unverifiedBestMove = rootNegaMax(moves, depthIteration, -infinity, infinity);
			if (unverifiedBestMove != null) {
				bestMove = unverifiedBestMove;
				reportNewBestMove(bestMove);

				// Add the best move to the start of the list
				moves.remove(bestMove);
				moves.addFirst(bestMove);			
			}
			else {
				timeout = true;
			}
		}	
		board.applyMove(bestMove);
		//System.out.println(board.toString());
		boardCount.increment(board);
		board.undoMove(bestMove);
		return bestMove;
	}

	// negamax() at the root level, allowing us to keep track of the best move
	private Move rootNegaMax(LinkedList<Move> moves, int depth, int alpha, int beta) {
		nodeCount++;

		// Extend search when we find a player in check
		
		if(board.inCheck()) {
			depth++;
		}
	

		BoardInfo<Move> boardInfo = transpositionTable.get(board.signature());

		// See if we have a cache hit
		if((boardInfo != null) && (boardInfo.getDepth() >= depth)) {

			// We can just return, because our guess is as good or better in the hash
			if(boardInfo.getType() == BoardInfo.EXACT) {
				return boardInfo.getBestMove();
			}

			// Hash has a better lower bound, so set our current lower bound
			else if(boardInfo.getType() == BoardInfo.LOWER &&
					boardInfo.getValue() > alpha) {
				alpha = boardInfo.getValue();
			}

			// Hash has a better upper bound, so set our current upper bound
			else if(boardInfo.getType() == BoardInfo.UPPER &&
					boardInfo.getValue() < beta) {
				beta = boardInfo.getValue();
			}

			// If lower bound surpasses upper bound, return value (alpha/beta pruning)
			if(alpha >= beta) {
				return boardInfo.getBestMove();
			}
		}

		// Note - we don't have to order the moves here, since they are ordered
		// by our iterative deepening and passed as an argument here, in order

		// Best thing we've seen so far is -infinity
		int bestValue = -evaluator.infty();
		Move bestMove = moves.getFirst();
		int value;

		for (Move move : moves) {


			// Compute the new best Value
			board.applyMove(move);
			//System.out.println(board.toString());
			value = -negamax(depth-1, -beta, -alpha);
			board.undoMove(move);

			// We found a new max, also keep track of move
			if(value > bestValue) {
				bestValue = value;
				bestMove = move;
			}

			// If our max is greater than our lower bound, update our lower bound
			if(bestValue > alpha) {
				alpha = bestValue;
			}

			// Alpha-beta pruning
			if(bestValue >= beta) {
				break;
			}
		}

		updateTranspositionTable(alpha, beta, depth, bestValue, bestMove);
		return bestMove;
	}

	// Negamax with transposition tables and move ordering
	private int negamax(int depth, int alpha, int beta) {
		nodeCount++;

		// Extend search when we find a player in check
		/* 
		if(board.inCheck()) {
			depth++;
		}
		*/
		

		// See if we have a cache hit
		BoardInfo<Move> boardInfo = transpositionTable.get(board.signature());

		if((boardInfo != null) && (boardInfo.getDepth() >= depth)) {

			// We can just return, because our guess is as good or better in the hash
			if(boardInfo.getType() == BoardInfo.EXACT) {
				return boardInfo.getValue();
			}

			// Hash has a better lower bound, so set our current lower bound
			else if(boardInfo.getType() == BoardInfo.LOWER &&
					boardInfo.getValue() > alpha) {
				alpha = boardInfo.getValue();
			}

			// Hash has a better upper bound, so set our current upper bound
			else if(boardInfo.getType() == BoardInfo.UPPER &&
					boardInfo.getValue() < beta) {
				beta = boardInfo.getValue();
			}

			// If lower bound surpasses upper bound, return value (alpha/beta pruning)
			if(alpha >= beta) {
				return boardInfo.getValue();
			}
			
		}

		// Once we've seen a repetition already, consider that board a stalemate		
		if (boardCount.isRepetition(board)) {
			return -evaluator.stalemate();
		}

		// Base case
		if (depth == 0) {

			// Search more until we hit a quiet position. Limit search on this
			// so we don't time out on a super long leaf chain		
			if (depthIteration < minDepth) {
				return quiescenceSearch(QUIESCENCE_MIN_DEPTH, alpha, beta);				
			}
			else if (depthIteration == minDepth) {
				return quiescenceSearch(QUIESCENCE_MID_DEPTH, alpha, beta);							
			}
			return quiescenceSearch(QUIESCENCE_MAX_DEPTH, alpha, beta);
		}

		// Get the moves we can make
		LinkedList<Move> moves = generateOrderedMoves();

		// No moves to make
		if (moves.isEmpty()) {
			
			if (board.inCheck()) {
				return -evaluator.mate() - depth;
			}
			else {
				return -evaluator.stalemate();
			}
			
		}			
		else {

			// Add killer moves to front of list
			orderMoves(moves, boardInfo);

			// Best thing we've seen so far is -infinity
			int bestValue = -evaluator.infty();
			int value;

			// We know it's not empty so get the first move to start
			Move bestMove = moves.getFirst();
			value = -evaluator.infty();

			for (Move move : moves) {

				// Compute the new best Value
				board.applyMove(move);
				//System.out.println(board.toString());
				value = -negamax(depth-1, -beta, -alpha);
				board.undoMove(move);

				// We found a new max, also keep track of move
				if(value > bestValue) {
					bestValue = value;
					bestMove = move;
				}

				// If our max is greater than our lower bound, update our lower bound
				if(bestValue > alpha) {
					alpha = bestValue;
				}

				// Alpha-beta pruning
				if(bestValue >= beta) {
					break;
				}				
			}

			updateTranspositionTable(alpha, beta, depth, bestValue, bestMove);

			return bestValue;
		}
	}

	// Quiescence Search With Transposition Table - Searches to make sure we aren't giving an
	// artificial advantage to a move because of the limit in our depth tree
	private int quiescenceSearch(int depth, int alpha, int beta) {
		nodeCount++;

		// We've already gone through depthIteration iterations of negamax
		// so our real depth is depthIteration + the depth value in this call
		int quiescenceDepth = depth + depthIteration;

		// See if we have a cache hit
		BoardInfo<Move> boardInfo = transpositionTable.get(board.signature());

		if((boardInfo != null) && (boardInfo.getDepth() >= quiescenceDepth)) {

			// We can just return, because our guess is as good or better in the hash
			if(boardInfo.getType() == BoardInfo.EXACT) {
				return boardInfo.getValue();
			}

			// Hash has a better lower bound, so set our current lower bound
			else if(boardInfo.getType() == BoardInfo.LOWER &&
					boardInfo.getValue() > alpha) {
				alpha = boardInfo.getValue();
			}

			// Hash has a better upper bound, so set our current upper bound
			else if(boardInfo.getType() == BoardInfo.UPPER &&
					boardInfo.getValue() < beta) {
				beta = boardInfo.getValue();
			}

			// If lower bound surpasses upper bound, return value (alpha/beta pruning)
			if(alpha >= beta) {
				return boardInfo.getValue();
			}
		}

		int value = evaluator.eval(board);		

		// Base case
		if(depth == 0) {
			return value;
		}

		// Standing pat causes a beta cutoff
		if(value >= beta) {
			return value;
		}

		LinkedList<Move> moves = generateNonQuietMoves();					

		// We are in a "quiet" position, so finish
		if(moves.isEmpty()) {
			return value;
		}

		// Standing pat score can become new alpha
		if(value > alpha) {
			alpha = value;
		}		

		// Ordering is important for quiescence search since we're comparing captures
		// so we need to distinguish the best moves. Empirically, sorting here is faster
		Collections.sort(moves, moveComparator);
		orderMoves(moves, boardInfo);

		// Our best value up to this point is our lower bound
		int bestValue = -evaluator.infty();

		// We know it's not empty...so just get any move to start
		Move bestMove = moves.getFirst();
		for (Move move : moves) {

			// If we're out of time, get out of the loop and ignore these results
			//if(timer.timeup()) {
			//	return -evaluator.infty();
			//}

			// Compute the new best value
			board.applyMove(move);
			//System.out.println(board.toString());
			value = -quiescenceSearch(depth-1, -beta, -alpha);
			board.undoMove(move);

			// We found a new max, also keep track of move
			if(value > bestValue) {
				bestValue = value;
				bestMove = move;
			}

			// If our max is greater than our lower bound,
			// update our lower bound
			if(bestValue > alpha) {
				alpha = bestValue;
			}

			// Alpha-beta pruning
			if(bestValue >= beta) {
				break;
			}
		}

		updateTranspositionTable(alpha, beta, quiescenceDepth, bestValue, bestMove);
		return bestValue;
	}
	// Store results in the transposition table as a lower bound, upper bound, or exact value	
	private void updateTranspositionTable(int alpha, int beta, int depth, int bestValue, Move bestMove) {
		if(bestValue <= alpha) {
			updateTranspositionTable(bestValue, bestMove, BoardInfo.LOWER, depth);
		}
		else if(bestValue >= beta) {
			updateTranspositionTable(bestValue, bestMove, BoardInfo.UPPER, depth);				
		}
		else {
			updateTranspositionTable(bestValue, bestMove, BoardInfo.EXACT, depth);
		}				
	}

	// Either add or update entry in transposition table
	private void updateTranspositionTable(int value, Move move, int type, int depth) {
		BoardInfo<Move> boardInfo = transpositionTable.get(board.signature());
		if(boardInfo != null) {
			boardInfo.updateInfo(value, move, type, depth);
		}
		else {
			transpositionTable.put(board.signature(), new BoardInfo<Move>(value, move, type, depth));
		}
	}

	// Adds the killer moves to the top of the list
	private void orderMoves(LinkedList<Move> moves, BoardInfo<Move> boardInfo) {
		if(boardInfo != null) {
			if(boardInfo.getSecondBestMove() != null) {
				moves.remove(boardInfo.getSecondBestMove());
				moves.addFirst(boardInfo.getSecondBestMove());					
			}
			moves.remove(boardInfo.getBestMove());
			moves.addFirst(boardInfo.getBestMove());				
		}		
	}

	// Order the moves (captures/promotions first)
	// Doing a full sort in this method was not cost effective
	private LinkedList<Move> generateOrderedMoves() {
		List<Move> psmoves = board.calculateAllTheMoves();
		LinkedList<Move> moves = new LinkedList<Move>();
		Set<Move> setmoves = new HashSet<Move>(256);

		for(Move m : psmoves) {
			if(setmoves.add(m)) {			
				if(m.isCapture() || m.isEnpassant() || m.isPromotion()) {
					moves.addFirst(m);	
				}
				else {
					moves.add(m);
				}
			}
		}
		return moves;
	}

	// Generates a list of moves that are only captures and promotions
	public LinkedList<Move> generateNonQuietMoves() {
		List<Move> psmoves = board.calculateAllTheMoves();
		LinkedList<Move> moves = new LinkedList<Move>();
		Set<Move> setmoves = new HashSet<Move>(256);

		// Check all plausible moves
		for(Move m : psmoves) {

			// Has to be legal and unique
			if(setmoves.add(m)) {

				// Only place captures and promotions in this list
				if(m.isCapture() || m.isEnpassant() || m.isPromotion()) {
					moves.add(m);	
				}
			}
		}
		return moves;
	}

	private Comparator<Move> moveComparator = new Comparator<Move>() {
		public int compare (Move move1, Move move2) {
			int score1, score2;

			board.applyMove(move1);	
			//System.out.println(board.toString());
			score1 = evaluator.eval(board);
			board.undoMove(move1);

			board.applyMove(move2);
			//System.out.println(board.toString());
			score2 = evaluator.eval(board);
			board.undoMove(move2);

			return score1 - score2;
		}
	};

	@Override
	public void setTimer(Timer t) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setTimer'");
	}
}