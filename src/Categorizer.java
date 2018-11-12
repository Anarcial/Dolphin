import java.util.List;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Pair;
import java.lang.Math;

public final class Categorizer {
  
  private final static double CAT_SCOPE = 0.2;
  private final static double COV_SCOPE = 0.2;
  private List<Asset> assets_;
  private ArrayList<AssetCategory> categories_;

  public Categorizer(List<Asset> assets) {
    assets_ = assets;
    categories_ = ArrayList();
  }

  public void create_categories() {
    for (Iterator<Asset> it = assets_.iterator(); it.hasNext();) {
      Asset a = it.next();
      AssetCategory cat = categorization(a);
      if (cat != null) {
        categoies_.add(cat);
        for (Iterator<AssetCategory> it = categories_.iterator(); it.hasNext();) {
          AssetCategory ac = it.next();
          ac.counterparts_add(Utils.cov(/*FIXME : get cat & ac prices*/), ac);
        }
      }
    }
    for (Iterator<AssetCategory> it = categories_.iterator(); it.hasNext();) {
      AssetCategory cat = it.next();
      cat.counterparts_ = Utils.insert_sort(cat.counterparts_);
    }
  }

  private AssetCategory categorization(Asset to_cat) {
    HashSet<JumpValue> tested_cat = HashSet<>();

    List<Price> to_cat_p = /* FIXME : retrieve prices */;
    AssetCategory cat = categories_.get(0);
    
    while (cat != null) {
      tested_cat.add(cat.first_get());
      List<Price> cat_p = /* FIXME : retrieve prices */;
      double cov = Utils.cov(cat_p, to_cat_p);
      if (cov >= 1 - CAT_SCOPE) {
        cat.assets_.add(to_cat.label_, to_cat);
        return null;
      }
      else {
        int i = 0;
        
        for (; i < cat.counterparts_.size(); ++i)
          if (!tested_cat.contains(cat.counterparts_.get(i).getValue().first_get())
              && Math.abs(cat.counterparts_.get(i).getKey() - cov) <= COV_SCOPE)
            cat = cat.counterparts_.get(i).getValue();

        if (i == cat.counterparts_.size())
          cat = null;
      }
    }
    return (new AssetCategory(to_cat));
  }
}
