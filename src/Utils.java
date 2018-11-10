import java.lang.Math;
import java.util.List;
import java.util.Iterator;

public final class Utils {
  public Utils() {}

  public static int cov(List<Price> x, List<Price> y)
  {
    if (x.size() != y.size())
      return -2;

    double sum = 0;
    for(Iterator<Price> i = x.iterator(); i.hasNext();) {
      Price p = i.next();
      sum += p.nav_;
    }
    double xm = sum / (double) x.size();
    sum = 0;
    
    for (Iterator<Price> i = y.iterator(); i.hasNext();) {
      Price p = i.next();
      sum += p.nav_;
    }
    double ym = sum / (double) y.size();

    sum = 0;
    for (int i = 0; i < x.size(); ++i)
      sum += (x.get(i).nav_ - xm)*(y.get(i).nav_ - ym);

    return (sum / (double) x.size());
  }

  public static ArrayList<Pair<double, AssetCategory>>
    insert_sort(ArrayList<Pair<double, AssetCategory>> list) {
      while (int i = 1; i < list.size(); ++i) {
        double cur = list.get(i);
        int j = i-1;
        while (j >= 0 && list.get(j) > cur; --j)
          list.set(j+1, list.get(j));
        list.set(j+1, cur);
      }
    }
}
