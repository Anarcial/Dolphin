import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
		JsonReader reader = null;
		InputStream istream = null;
		try {
			istream = new FileInputStream("43Mo-assets-list.json");
			reader = new JsonReader(new InputStreamReader(istream, StandardCharsets.UTF_8));
			Gson gson = new Gson();
			JsonObject[] j_assets = gson.fromJson(reader, JsonObject[].class);
			Translator trans = new Translator();
			ArrayList<Asset> assets = trans.translate(j_assets);
			// FIXME : retrieve volatilities and update assets

			Categorizer categorizer = new Categorizer(assets);
			categorizer.create_categories();

			PortfoliosGenerator ptfg = new PortfoliosGenerator(categorizer.categories_);
			ArrayList<ArrayList<Asset>> ptfs = ptfg.generate(assets);

			Evaluator evlt = new Evaluator(ptfs);
			ArrayList<Asset> best_ptf = evlt.evaluate();

			Composition cmp = new Composition(best_ptf);
			ArrayList<Pair<Double, Asset>> result = cmp.compute();

			// FIXME : sends result

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
