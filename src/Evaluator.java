import java.util.ArrayList;
import java.lang.Math;

/**
 * Evaluator class
 * Evaluates a pool of portfolios, choosing the best one according to its sharpe
 * In order not to test the whole pool :
 *  -> Limits testing same portfolios
 *  -> Tries to keep covariances in order not to compute them each time
 */

public class Evaluator {
    private ArrayList<ArrayList<Asset>> portfolios_;
    private double[][] covs_;
    private double SCOPE = 2.;

    public Evaluator(ArrayList<ArrayList<Asset>> portfolios) {
        portfolios_ = portfolios;
        covs_ = new double[20][20];
    }

    /**
     *
     * @return best portfolio of the pool
     */
    public ArrayList<Asset> evaluate() {
        clear();

        int num_best_port = 0;
        double best_sharpe =  port_sharpe(0, 20);
        boolean new_best = false;
        for (int i = 1; i < portfolios_.size(); ++i) {
            if (new_best) {
                clear();
                new_best = false;
            }
            else if (update(i-1, i))
                continue;
            for (int depth = 0; depth < 20; ++depth) {
                double cur_sharpe = port_sharpe(i, depth);
                if (cur_sharpe < best_sharpe - SCOPE)
                    break;
                else {
                    if (depth == 19 && cur_sharpe > best_sharpe) {
                        num_best_port = i;
                        best_sharpe = cur_sharpe;
                        new_best = true;
                    }
                }
            }
        }
        return portfolios_.get(num_best_port);
    }

    /**
     *
     * @param num_port
     * @param nb_asset
     * @return sharpe value of nb_asset combination of one portfolio
     */
    private double port_sharpe(int num_port, int nb_asset) {
        ArrayList<Asset> port = portfolios_.get(num_port);
        double ret = 0;
        double var = 0;
        double weight = 100./nb_asset;
        for (int i = 0; i < nb_asset; i++) {
            ret += port.get(i).return_/nb_asset;
            for (int j = 0; j < nb_asset; j++) {
                if (covs_[i][j] == -2.) {
                    double tmp = Utils.cov(/* FIXME */);
                    covs_[i][j] = tmp;
                    covs_[j][i] = tmp;
                }

                var += weight * port.get(i).vol_ *
                        weight * port.get(j).vol_ *
                        covs_[i][j];
            }
        }
        return ((ret - 0.05) / Math.sqrt(var));
    }


    /**
     * clears the covariance array for next evaluation
     */
    private void clear() {
        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 20; j++)
                covs_[i][j] = -2.;
    }


    /**
     *
     * @param num_old_port
     * @param num_new_port
     * @return true if both portfolios are the same, false otherwise
     */
    private boolean update(int num_old_port, int num_new_port) {
        boolean same = true;
        for (int i = 0; i < 20; ++i) {
            if (!portfolios_.get(num_new_port).contains(portfolios_.get(num_old_port).get(i))) {
                same = false;
                for (int j = 0; j < 20; ++j)
                    covs_[i][j] = -2.;
            }
        }
        return same;
    }
}
