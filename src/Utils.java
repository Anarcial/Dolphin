import java.lang.Math;
import java.util.List;
import java.util.Iterator;

public final class Utils {
  public Utils() {}

  public int cov(List<Price> x, List<Price> y)
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
}
