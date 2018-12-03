import java.util.List;
import java.util.Iterator;
import java.util.HashSet;
import java.lang.Math;
import java.util.ArrayList;

/**
 * Categorizer class.
 * Creates all Assets categories.
 * Assets are categorized by closest covariance.
 * In order to categorize an asset :
 * - Compute cov with first category
 * - If cov in CAT_SCOPE, then the asset belongs to that category
 * - Otherwise, search for an other category that has the closest cov
 *   to the one found
 * - Repeat the test with the new category
 * - If no category is found, create a new one with the asset
 */

/*

 *
 */

public final class Categorizer {

    private final static double CAT_SCOPE = 0.1;
    private final static double COV_SCOPE = 0.2;
    private List<Asset> assets_;
    public ArrayList<AssetCategory> categories_;

    public Categorizer(List<Asset> assets) {
        assets_ = assets;
        categories_ = new ArrayList<>();
    }

    public void create_categories() {
        for (Iterator<Asset> it = assets_.iterator(); it.hasNext();) {
            Asset a = it.next();
            AssetCategory cat = categorization(a);
            if (cat != null) {
                categories_.add(cat);/* Had to create a new category */
                a.cat_set(cat);
                for (int j = 0; j < categories_.size(); ++j) {
                    AssetCategory ac = categories_.get(j);
                    if (!ac.first_get().equals(a.id_)) {
                        double cov = Utils.cov(cat.get(0).cots_, ac.get(0).cots_) / 265;
                        ac.counterparts_add(cov, cat);
                        cat.counterparts_add(cov, ac);
                    }
                }

            }
        }
        for (Iterator<AssetCategory> it = categories_.iterator(); it.hasNext();) {
            AssetCategory cat = it.next();
            cat.counterparts_sort();
        }
    }

    /**
     * Determines whether an asset belongs to a category, or needs a new one
     * @param to_cat
     * @return the new AssetCategory if to_cat doesn't belong to an existing one
     * null otherwise
     */

    private AssetCategory categorization(Asset to_cat) {
        HashSet<Integer> tested_cat = new HashSet<>();

        ArrayList<Double> to_cat_p = to_cat.cots_;
        if (categories_.size() == 0) {
            return (new AssetCategory(to_cat));
        }
        AssetCategory cat = categories_.get(0);

        while (cat != null) {
            tested_cat.add(cat.first_get());
            ArrayList<Double> cat_p = cat.get(0).cots_;
            double cov = Utils.cov(cat_p, to_cat_p) / 265;
            if (cov >= 1 - CAT_SCOPE) {
                cat.add(to_cat);
                to_cat.cat_set(cat);
                return null;
            }
            else {
                int i = 0;

                for (; i < cat.counterparts_.size(); ++i)
                    if (!tested_cat.contains(cat.counterparts_.get(i).getValue().first_get())
                            && Math.abs(cat.counterparts_.get(i).getKey() - cov) <= COV_SCOPE) {
                        cat = cat.counterparts_.get(i).getValue();
                        break;
                    }

                if (i == cat.counterparts_.size())
                    cat = null;
            }
        }
        return (new AssetCategory(to_cat));
    }
}