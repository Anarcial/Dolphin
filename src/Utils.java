import javafx.util.Pair;
import java.util.ArrayList;

/**
 * Class Utils
 * Regroups needed mathematical and algorithmic tools.
 */

public final class Utils {
    private Utils() {}

    public static double cov(ArrayList<Double> x, ArrayList<Double> y)
    {
        int size = java.lang.Math.min(x.size(), y.size());
        double sum = 0;
        for(int i = 0; i < size; ++i)
            sum += x.get(i);
        double xm = sum / (double) size;

        sum = 0;
        for (int i = 0; i < size; ++i)
            sum += y.get(i);
        double ym = sum / (double) size;

        sum = 0;

        for (int i = 0; i < size; ++i)
            sum += (x.get(i) - xm)*(y.get(i) - ym);

        return (sum / (double) x.size());
    }

    public static ArrayList<Pair<Double, AssetCategory>>
    insert_sort(ArrayList<Pair<Double, AssetCategory>> list) {
        for (int i = 1; i < list.size(); ++i) {
            Double cur = list.get(i).getKey();
            Pair<Double, AssetCategory> tmp = list.get(i);
            int j = i-1;
            for (;j >= 0 && list.get(j).getKey() > cur; --j)
                list.set(j+1, list.get(j));
            list.set(j+1, tmp);
        }
        return list;
    }

    public static double sharpe(Double ret, Double vol) {
        return ((ret - 0.0005) / vol);
    }
}
