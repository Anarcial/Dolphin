import java.util.ArrayList;

public final class Node {
  
  public Node next_;
  public Asset ass_;
  public int nb_;


  public Node(Asset ass, int nb) {
    ass_ = ass;
    nb_ = nb;
    next_ = null;
  }
}
