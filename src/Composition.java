import javafx.util.Pair;
import java.util.ArrayList;

/**
 * let's try something : I chose a pace. Large at first, to get
 * a local max with a strong asset.
 * Then once I get a result that seems cool, I reduce the pace
 * and start over from the previous cool-looking result.
 * Let's say I start with a 0.25 pace
 * then go to 0.1
 * then 0.05
 * might work to find a local max.
 * Problem is, it's only a local max, so we might evade the global one...
 */

public class Composition {
    private ArrayList<Asset> portfolio_;
    private double[][] covs_;

    public Composition(ArrayList<Asset> portfolio) {
        portfolio_ = portfolio;
        covs_ = new double[20][20];
        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 20; j++)
                covs_[i][j] = -2.;
    }

    public ArrayList<Pair<Double, Asset>> compute() {
        double PACE = 0.025;
        ArrayList<Pair<Double, Asset>> res_portfolio = new ArrayList<>();
        for (Asset a : portfolio_)
            res_portfolio.add(new Pair<>(1./portfolio_.size(), a));
        double best_sharpe = port_sharpe(res_portfolio);
        int strongest = 0;
        while(PACE >= 0.01) {
            ArrayList<Pair<Double, Asset>> new_port = new ArrayList<>();
            new_port.add(new Pair<>(res_portfolio.get(strongest).getKey() + PACE,
                            res_portfolio.get(strongest).getValue()));
            double dif = PACE / 19.;
            for (int i = 0; i < 20; ++i) {
                if (strongest != i) {
                    new_port.add(new Pair<>(res_portfolio.get(i).getKey() - dif,
                                    res_portfolio.get(i).getValue()));
                }
            }
            double new_sharpe = port_sharpe(new_port);

            if (new_sharpe > best_sharpe) {
                res_portfolio = new_port;
                best_sharpe = new_sharpe;
                PACE /= 2.;
            }
            else
                ++strongest;

            if (strongest >= res_portfolio.size())
                strongest = 0;
        }

        return res_portfolio;
    }

    private double port_sharpe(ArrayList<Pair<Double, Asset>> portfolio) {
        double ret = 0;
        double var = 0;
        for (int i = 0; i < 20; i++) {
            ret += portfolio.get(i).getValue().return_/20;
            for (int j = 0; j < 20; j++) {
                if (covs_[i][j] == -2.) {
                    double tmp = Utils.cov(/* FIXME */);
                    covs_[i][j] = tmp;
                    covs_[j][i] = tmp;
                }

                var +=  portfolio.get(i).getKey() *
                        portfolio.get(i).getValue().vol_ *
                        portfolio.get(j).getKey() *
                        portfolio.get(j).getValue().vol_ *
                        covs_[i][j];
            }
        }
        return ((ret - 0.05) / Math.sqrt(var));
    }
}
