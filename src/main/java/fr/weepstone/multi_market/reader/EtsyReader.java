package fr.weepstone.multi_market.reader;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import fr.weepstone.multi_market.model.Product;

@Service
public class EtsyReader implements MarketReader {

	public List<Product> readProducts(String pageHtml) {

		List<Product> products = new ArrayList<Product>();
		try {
			JSONParser parser = new JSONParser();
			JSONObject reader;
			reader = (JSONObject) parser.parse(pageHtml);
			JSONArray jsonArray = (JSONArray) reader.get("listings");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = (JSONObject) jsonArray.get(i);
				String id = Long.toString((Long) json.get("listing_id"));
				String description = (String) json.get("description");
				String title = (String) json.get("title");
				String url_product = (String) json.get("url");
				double price = Double.parseDouble((String) json.get("price"));
				JSONArray images = (JSONArray) json.get("images");
				String url_image = (String) images.get(0);
				List<String> categories = new ArrayList<String>();
				categories.add((String) json.get("section_name"));

				Product product = Product.builder().id(id).categories(categories).url_image(url_image).price(price).description(description).title(title).url_product(url_product).build();
				products.add(product);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}
}
