package Search;

import java.util.Observable;
import java.util.Observer;

import logic.BetterEvaluator;
import logic.BetterEvaluator;


public abstract class AbstractSearcher
<
   Move,
  Board
>
  implements Searcher<Move,Board> {
  protected BetterEvaluator evaluator;
  protected int          minDepth;
  protected int          maxDepth;
  protected long         leafCount;
  protected long         nodeCount;

  
  private BestMovePublisher<Move>
    bestMovePublisher = new BestMovePublisher<Move>();

  public void setEvaluator(BetterEvaluator e)
  {
    evaluator =  e;
  }

  public void setFixedDepth(int depth)
  {
    setMaxDepth(depth);
    setMinDepth(depth);
  }

  public void setMaxDepth(int depth)
  {
    maxDepth = depth;
  }

  public void setMinDepth(int depth)
  {
    minDepth = depth;
  }


  public long leafCount()
  {
    return leafCount;
  }

  public long nodeCount()
  {
    return nodeCount;
  }
  
  public void addBestMoveObserver(Observer o)
  {
	  
	// Make sure we only have one observer at a time
	bestMovePublisher.deleteObservers();
    bestMovePublisher.addObserver(o);
  }
  
  protected void reportNewBestMove(Move move)
  {
    bestMovePublisher.updateBestMove(move);
    
	// Sleep so user can see moves visually as the searcher finds better move									
  }
  
  private static class BestMovePublisher
  <
    Move
  > extends Observable
  {
    public void updateBestMove( Move move )
    {
      setChanged();
      notifyObservers(move);
    }
  }
}