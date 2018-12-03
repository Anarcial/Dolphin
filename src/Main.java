import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import javafx.util.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        JsonReader reader = null;
		InputStream istream = null;
		try {
			istream = new FileInputStream("last-past-year-quotes.json");
			reader = new JsonReader(new InputStreamReader(istream, StandardCharsets.UTF_8));
			Gson gson = new Gson();
			JsonObject[] j_assets = gson.fromJson(reader, JsonObject[].class);
			System.out.println("Translator");
			Translator trans = new Translator();
			ArrayList<Asset> assets = trans.translate(j_assets);
			istream.close();
            reader.close();

			istream = new FileInputStream("ratios.json");
			reader = new JsonReader(new InputStreamReader(istream, StandardCharsets.UTF_8));
			gson = new Gson();
			j_assets = gson.fromJson(reader, JsonObject[].class);
			System.out.println("Vols");
			for (Asset a : assets) {
			    for (int i = 0; i < j_assets.length; ++i) {
			        JsonElement j = j_assets[i].get(a.id_.toString());
			        if (j != null) {
						NumberFormat f = NumberFormat.getInstance(Locale.FRANCE);
						try {
							a.vol_ = f.parse(j.getAsJsonObject().get("18").getAsJsonObject().
									get("value").getAsString()).doubleValue();
							a.sharpe_ = Utils.sharpe(a.return_, a.vol_);
						}
						catch (java.text.ParseException e) {
							System.err.println(e.getMessage());
						}
                    }
                }
            }

			for (int i = 0; i < assets.size(); ++i)
				if (assets.get(i).vol_ == null)
					assets.remove(i);
			System.out.println("Categorizer");
			Categorizer categorizer = new Categorizer(assets);
			categorizer.create_categories();

			System.out.println("Prtf Gen");
			PortfoliosGenerator ptfg = new PortfoliosGenerator(categorizer.categories_);
			ArrayList<ArrayList<Asset>> ptfs = ptfg.generate(assets);

			System.out.println("Evaluator");
			Evaluator evlt = new Evaluator(ptfs);
			ArrayList<Asset> best_ptf = evlt.evaluate();

			System.out.println("Compodition");
			Composition cmp = new Composition(best_ptf);
			ArrayList<Pair<Double, Asset>> result = cmp.compute();

			for (Pair<Double, Asset> p : result)
			    System.out.println(p.getKey() + " " + p.getValue().id_);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
        }
    }
}
