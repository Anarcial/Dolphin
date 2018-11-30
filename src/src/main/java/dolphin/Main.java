package dolphin;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class Main {
    public static void main(String[] args) {
		JsonReader reader = null;
		InputStream istream = null;
		try {
			istream = new FileInputStream("43Mo-assets-list.json");
			reader = new JsonReader(new InputStreamReader(istream, "UTF-8"));
			Gson gson = new Gson();
			JsonObject[] assets = gson.fromJson(reader, JsonObject[].class);
			System.out.println(assets.length);
			List<JsonObject> assetsList = Arrays.asList(assets);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
