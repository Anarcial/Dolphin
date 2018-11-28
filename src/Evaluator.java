import java.util.ArrayList;

public class Evaluator {
    private ArrayList<ArrayList<Asset>> portfolios_;

    public Evaluator(ArrayList<ArrayList<Asset>> portfolios) {
        portfolios_ = portfolios;
    }

    public ArrayList<Asset> evaluate() {
        ArrayList<Asset> best_portfolio = portfolios_.get(0);

        return best_portfolio;
    }

    private Double port_sharpe(int num, int nb_ass) {}
}
