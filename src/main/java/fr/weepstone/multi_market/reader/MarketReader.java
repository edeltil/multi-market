package fr.weepstone.multi_market.reader;

import java.util.List;

import fr.weepstone.multi_market.model.Product;

public interface MarketReader {

	List<Product> readProducts(String pageHtml);

	// String getUserUrl(String pageHtml);

}
