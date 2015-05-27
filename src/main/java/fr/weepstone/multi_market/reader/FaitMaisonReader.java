package fr.weepstone.multi_market.reader;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import fr.weepstone.multi_market.model.Product;

@Service
public class FaitMaisonReader implements MarketReader {

	private static final String PATH = "http://www.fait-maison.com";


	public List<Product> readProducts(String pageHtml) {
		Document doc = Jsoup.parse(pageHtml);
		Elements elements = doc.getElementsByClass("product-presentation");
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i < elements.size(); i++) {

			Element element = elements.get(i);
			String id = element.attr("id");
			Element elementCategory = element.getElementsByClass("categories").first();
			List<String> categories = new ArrayList<String>();
			String categorieStr = elementCategory.text();
			categories.add(categorieStr.substring(categorieStr.indexOf(":") + 1, categorieStr.length()).trim());
			Element elementImage = element.select("img[src$=.JPG]").first();
			String url_image = elementImage.attr("src");
			Element elementPrice = element.getElementsByClass("price_with_euros").first();
			double price = Double.parseDouble(elementPrice.text().substring(0, elementPrice.text().length() - 1));
			Element elementDescription = element.getElementsByClass("description").first();
			String description = elementDescription.text().trim();
			Element elementTitle = element.getElementsByTag("dt").first();
			String title = elementTitle.childNode(0).outerHtml().trim();
			Element elementUrl = element.select("a[href$=.html]").first();
			String url_product = elementUrl.attr("href");
			Product product = Product.builder().id(id).categories(categories).url_image(url_image).price(price).description(description).title(title).url_product(url_product).build();
			products.add(product);
		}
		return products;
	}

	public String getUserUrl(String pageHtml) {
		Document doc = Jsoup.parse(pageHtml);
		Element element = doc.getElementById("bo_menu");
		Element boutique = element.getElementsByClass("fait-maison").first();
		return PATH + boutique.attr("href");
	}
}
