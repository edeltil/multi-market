package fr.weepstone.multi_market.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import fr.weepstone.multi_market.MultiMarket;
import fr.weepstone.multi_market.model.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MultiMarket.class)
@WebAppConfiguration
public class DaWandaReaderTest {

	@Autowired
	DaWandaReader reader;

	@Test
	public void pageParsingTest() throws IOException {
		File file = new File(getClass().getClassLoader().getResource("dawanda_products.html").getFile());
		BufferedReader buffReader = new BufferedReader(new FileReader(file));
		StringBuilder result = new StringBuilder("");

		while (buffReader.ready()) {
			String read = buffReader.readLine();
			result.append(read).append("\n");
		}
		buffReader.close();
		List<Product> products = reader.readProducts(result.toString());
		Assert.assertTrue(products.size() == 23);
		Product product1 = products.get(0);
		Assert.assertTrue(product1.getTitle().equals("Porte feuille artisanale en cuir"));
		Assert.assertTrue(product1.getPrice() == 20);
		Assert.assertTrue(product1.getId().equals("37820469"));
		Assert.assertTrue(product1.getUrl_image().equals("//s33.dawandastatic.com/Product2/37820/37820469/listview_m/1352822809-266.jpg"));
		Assert.assertTrue(product1.getUrl_product().equals("/product/37820469"));
		Assert.assertTrue(product1.getCategories().size() == 3);
		Assert.assertTrue(product1.getCategories().get(0).equals("Sacs"));
		Assert.assertTrue(product1.getCategories().get(1).equals("Petite maroquinerie"));
		Assert.assertTrue(product1.getCategories().get(2).equals("Portefeuille"));
	}
}
