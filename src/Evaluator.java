import java.util.ArrayList;
import java.lang.Math;

public class Evaluator {
    private ArrayList<ArrayList<Asset>> portfolios_;
    private double[][] covs_;
    private double SCOPE = 2.;

    public Evaluator(ArrayList<ArrayList<Asset>> portfolios) {
        portfolios_ = portfolios;
        covs_ = new double[20][20];
    }

    public ArrayList<Asset> evaluate() {
        for (int i = 0; i < 20; i++)
            for (int j = 0; j < 20; j++)
                covs_[i][j] = -2.;

        int num_best_port = 0;
        double best_sharpe =  port_sharpe(0, 20);

        for (int i = 1; i < portfolios_.size(); ++i) {
            for (int depth = 0; depth < 20; ++depth) {
                double cur_sharpe = port_sharpe(i, depth);
                if (cur_sharpe < best_sharpe - SCOPE)
                    break;
                else {
                    if (depth == 19) {
                        num_best_port = i;
                        best_sharpe = cur_sharpe;
                    }
                }
            }
        }
        return portfolios_.get(num_best_port);
    }

    private double port_sharpe(int num_port, int nb_asset) {
        ArrayList<Asset> port = portfolios_.get(num_port);
        double ret = 0;
        double var = 0;
        double weight = 100/nb_asset;
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
}
