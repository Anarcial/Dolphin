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
	private void getVolatility(List<Integer> ratios, List<Integer> assetsIds, String startDate = "", String endDate = "") {
		final RatioMultiAssetParamModel locParam = new RatioMultiAssetParamModel(ratios, assetsIds, null, startDate, endDate, null);
		final String locURI = URL + "ratio/invoke";

		URIBuilder locURIBuilder;
		HttpPost locHttpPost;
		String locResponse;
		ClosableHttpClient client;
		HttpClientContext clientContext;

		try {
			locURIBuilder = new URIBuilder(locURI);
			locHttpPost = RequestUtil.createPost(locURIBuilder, locParam);
			AuthCache authCache = new BasicAuthCache();
			BasicScheme basicAuth = new BasicScheme();
			final HttpHost locLocalhost = new HttpHost("dolphin.jump-technology.com", 3472, "https");
			authCache.put(locLocalhost, )
			clientContext = HttpClientContext.create();
			clientContext.setAuthCache(authCache);
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(
					new AuthScope(locLocalhost),
					new UsernamePasswordCredentials("epita_user_3", "dkw3JReNdZmZ6WV4"));
			SSLContextBuilder builder = new SSLContextBuilder();
			SSLConnectionSocketFactory sslsf = null;
			try {
				builder.loadTrustMaterial(null, new TrustSelfSignedStrategy(){
					@Override
					public boolean isTrusted(X509Certificate[] chain,
											 String authType) throws CertificateException {
						return true;//FIXME : A MODIFIER QUAND HTTPS OK
					}
				});
				sslsf = new SSLConnectionSocketFactory(
						builder.build(), NoopHostnameVerifier.INSTANCE);//FIXME : A MODIFIER QUAND HTTPS OK
				client = HttpClientBuilder.create()
						.setDefaultCredentialsProvider(credsProvider)
						.setSSLSocketFactory(sslsf)
						.build();
				locResponse = executeRequest(client, clientContext, locHttpPost);
				System.out.println(locResponse);
			} catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException parE) {
				parE.printStackTrace();
			}
		} catch (URISyntaxException locE) {
			System.out.println("Vérifier la syntaxe de la requete "+locURI);
			return null;
		}
		if (locResponse == null) {
			return null;
		}
	}
	public static void main(String[] args) {
		List<Integer> ratios = new ArrayList<>();
		ratios.add(18);
		List<Integer> assetsIds = new ArrayList<>();
		assetsIds.add(599);
		getVolatility(ratios, assetsIds);
		/*
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
		}*/
	}
}
