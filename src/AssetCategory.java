import java.util.Map;
import java.util.Pair;

//FIXME : need to class the other categories

public class AssetCategory {

  public Map<JumpValue,Asset> assets_;

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
}
