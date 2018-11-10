import java.util.Map;
import java.util.Pair;


public class AssetCategory {

  public Map<JumpValue,Asset> assets_;

  public ArrayList<Pair<double, AssetCategory>> counterparts_;

  public AssetCategory(Asset a) {
    assets_ = new HashMap<>();
    assets_.put(a.id_, a);
  }

  public add(Asset a) {
    assets_.put(a.id_, a);
  }

  public Asset get(JumpValue id) {
    return assets_.get(id);
  }

  public void counterparts_add(double cov, AssetCategory ac) {
    counterparts_.add(new Pair<cov, ac>);
  }
}
