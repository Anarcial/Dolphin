import javafx.util.Pair;
import java.util.ArrayList;

/**
 * let's try something : I chose a pace. Large at first, to get
 * a local max with a strong asset.
 * Then once I get a result that seems cool, I reduce the pace
 * and start over from the previous cool-looking result.
 * Let's say I start with a 0.5 pace
 * then go to 0.1
 * then 0.05
 * might work to find a local max.
 * Problem is, it's only a local max, so we might evade the global one....
 */

public class Composition {
    private ArrayList<Asset> portfolio_;

    public Composition(ArrayList<Asset> portfolio) {
        portfolio_ = portfolio;
    }

    public ArrayList<Pair<Double, Asset>> compute() {
        ArrayList<Pair<Double, Asset>> portfolio = new ArrayList<>();
        for (Asset a : portfolio_)
            portfolio.add(new Pair<>(1./portfolio_.size(), a));
        return portfolio;
    }
}
