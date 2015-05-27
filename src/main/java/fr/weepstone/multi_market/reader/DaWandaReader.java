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
public class DaWandaReader implements MarketReader {

	public List<Product> readProducts(String pageHtml) {
		Document doc = Jsoup.parse(pageHtml);
		Element root = doc.getElementsByClass("product-list").first();
		Elements elements = root.getElementsByClass("product");
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i < elements.size(); i++) {
			Element element = elements.get(i);
			String id = element.attr("id");
			Element elementImage = element.getElementsByClass("picture").first();
			String url_image = elementImage.select("img[src$=.JPG]").first().attr("src");
			Element elementPrice = element.getElementsByClass("price").first();
			String priceStr = elementPrice.getElementsByClass("edit-product-link").first().text();
			double price = Double.parseDouble(priceStr.substring(0, priceStr.length() - " € EUR".length()));
			Element elementTitle = element.getElementsByClass("tooltip-trigger-hover").first();
			String url_product = "/product/" + id;
			String title = elementTitle.text().trim();
			Element categoryElement = element.getElementsByClass("category-data").first();
			String categorie = categoryElement.getElementsByClass("edit-product-link").first().text().trim();
			String[] categoriesSplit = categorie.split("»");
			List<String> categories = new ArrayList<String>();
			for (String cat : categoriesSplit) {
				categories.add(cat.trim());
			}
			// pas description ici
			Product product = Product.builder().id(id).categories(categories).url_image(url_image).price(price).title(title).url_product(url_product).build();
			products.add(product);
		}
		return products;
	}

}
