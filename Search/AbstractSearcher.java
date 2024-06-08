package Search;

import java.util.Observable;
import java.util.Observer;

import logic.BetterEvaluator;
import logic.Evaluator;
import logic.BetterEvaluator;


public abstract class AbstractSearcher
<
  M extends Move<M>,
  B extends Board1<M,B>
>
  implements Searcher<M,B> {
  protected Evaluator evaluator;
  protected int          minDepth;
  protected int          maxDepth;
  protected long         leafCount;
  protected long         nodeCount;

  
  private BestMovePublisher<M>
    bestMovePublisher = new BestMovePublisher<M>();
}

  public void setEvaluator(Evaluator<B> e)
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
  
  protected void reportNewBestMove(M move)
  {
    bestMovePublisher.updateBestMove(move);
    
	// Sleep so user can see moves visually as the searcher finds better move									
  }
  
  private static class BestMovePublisher
  <
    M extends Move<M>
  > extends Observable
  {
    public void updateBestMove( M move )
    {
      setChanged();
      notifyObservers(move);
    }
  }
}