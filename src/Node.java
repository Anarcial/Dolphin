import java.util.ArrayList;

public final class Node {
  
  private ArrayList<Node> sons_;
  private Asset ass_;
  private double sharpe_;

  public Node(Asset ass, double sharpe) {
    ass_ = ass;
    sharpe_ = sharpe;
    sons_ = ArrayList();
  }

  public ArrayList<Node> sons_get() { return sons_; }
  public Node son_get(int i) { return sons_.get(i); }
  public Asset ass_get() { return ass_: }
  public double sharpe_get() { return sharpe_; }


  public void sons_add(Node n) { sons_.add(n); } 
}
