import javafx.util.Pair;
import java.util.ArrayList;


/**
 * Class AssetCategory
 * Represents a category of Assets.
 */

public class AssetCategory {

  public ArrayList<Asset> assets_;
  public ArrayList<Pair<Double, AssetCategory>> counterparts_;
  private Integer id_first_;

  public AssetCategory(Asset a) {
    assets_ = new ArrayList<>();
    assets_.add(a);
    id_first_ = a.id_;
  }

  public void add(Asset a) {
    assets_.add(a);
  }

  public Asset get(int i) {
    return assets_.get(i);
  }

  public void counterparts_add(double cov, AssetCategory ac) {
    counterparts_.add(new Pair<>(cov, ac));
  }

  public void counterparts_sort() {
    counterparts_ = Utils.insert_sort(counterparts_);
  }

  public Integer first_get() {
    return id_first_;
  }
}
