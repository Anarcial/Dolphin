import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.util.Pair;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class Translator {
    public Translator() { }

    public ArrayList<Asset> translate(JsonObject[] j_assets) {
        ArrayList<Asset> assets = new ArrayList<>();
        for (int i = 0; i < j_assets.length; ++i) {
            if (j_assets[i].get("quotes").getAsJsonArray().size() == 0)
                continue;
            Asset a = new Asset(j_assets[i].get("id").getAsInt());
            a.cots_ = cots_get(j_assets[i]);
            NumberFormat f = NumberFormat.getInstance(Locale.FRANCE);

            try {
                a.return_ = f.parse(j_assets[i].get("quotes").getAsJsonArray().get(
                        j_assets[i].get("quotes").getAsJsonArray().size() - 1).
                        getAsJsonObject().get("return").getAsJsonObject().get("value").getAsString()).doubleValue();
            }
            catch (java.text.ParseException e) {
                System.err.println(e.getMessage());
            }
            assets.add(a);
        }
        return assets;
    }

    public void vol_add(ArrayList<Asset> assets_, ArrayList<Pair<Integer, Double>> vols_) {
        for (Asset a : assets_) {
            for (Pair<Integer, Double> p : vols_)
                if (a.id_ == p.getKey()) {
                    a.vol_ = p.getValue();
                    break;
                }
        }
    }

    private ArrayList<Double> cots_get(JsonObject j_asset) {
        try {
            NumberFormat f = NumberFormat.getInstance(Locale.FRANCE);
            ArrayList<Double> cots = new ArrayList<>();
            JsonArray quotes = j_asset.get("quotes").getAsJsonArray();
            for (int i = 0; i < quotes.size(); ++i) {
                JsonObject nav = quotes.get(i).getAsJsonObject().get("nav").getAsJsonObject();
                double val = f.parse(nav.get("value").getAsString()).doubleValue();
                //double d = quotes.get(i).getAsJsonObject().get("nav").getAsJsonObject().get("value").getAsDouble();
                cots.add(val);
            }
            return cots;
        }
        catch (java.text.ParseException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
