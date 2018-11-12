import java.util.Map;
import java.util.Pair;


public class AssetCategory {

  public Map<JumpValue,Asset> assets_;
  public ArrayList<Pair<double, AssetCategory>> counterparts_;
  private JumpValue id_first_;

  public AssetCategory(Asset a) {
    assets_ = new HashMap<>();
    assets_.put(a.id_, a);
    id_first_ = a.id_;
  }

  public add(Asset a) {
    assets_.put(a.id_, a);
  }

  public Asset get(JumpValue id) {
    return assets_.get(id);
  }

  public void counterparts_add(double cov, AssetCategory ac) {
    counterparts_.add(new Pair<double, AssetCategory>(cov, ac));
  }

  public void counterparts_sort() {
    counterparts_ = Utils.insert_sort(counterparts_);
  }

  public JumpValue first_get() {
    return id_first_;
  }
}
