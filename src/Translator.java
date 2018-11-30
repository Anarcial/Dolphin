import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.util.Pair;

import java.util.ArrayList;


public class Translator {
    public Translator() { }

    public ArrayList<Asset> translate(JsonObject[] j_assets) {
        ArrayList<Asset> assets = new ArrayList<>();
        for (int i = 0; i < j_assets.length; ++i) {
            Asset a = new Asset(j_assets[i].get("id").getAsInt());
            a.cots_ = cots_get(j_assets[i]);
            a.return_ = j_assets[i].get("quotes").getAsJsonArray().get(
                    j_assets[i].get("quotes").getAsJsonArray().size() - 1 ).
                        getAsJsonObject().get("return").getAsJsonObject().get("value").getAsDouble();
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
        ArrayList<Double> cots = new ArrayList<>();
        JsonArray quotes = j_asset.get("quotes").getAsJsonArray();
        for (int i = 0; i < quotes.size(); ++i)
            cots.add(quotes.get(i).getAsJsonObject().get("nav").getAsJsonObject().get("value").getAsDouble());
        return cots;
    }
}
