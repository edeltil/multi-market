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
public class EtsyReaderTest {

	@Autowired
	EtsyReader reader;

	@Test
	public void pageParsingTest() throws IOException {

		File file = new File(getClass().getClassLoader().getResource("etsy_products.html").getFile());
		BufferedReader buffReader = new BufferedReader(new FileReader(file));
		StringBuilder result = new StringBuilder("");

		while (buffReader.ready()) {
			String read = buffReader.readLine();
			result.append(read).append("\n");
		}
		buffReader.close();
		List<Product> products = reader.readProducts(result.toString());
		Assert.assertTrue(products.size() == 2);
		Product product1 = products.get(0);
		Assert.assertEquals(product1.getTitle(), "Bracelet noir cuir avec une perle carrée");
		Assert.assertEquals(product1.getDescription(),
				"Ce bracelet est réalisé à partir de 2 brins de cuir de buffle de 3mm. Il est orné d&#39;une perle carrée en métal.\n\nLe fermoir est rond et aimanté.\n\nLe bracelet peut être ajusté à votre poignée au moment de la commande.");
		Assert.assertTrue(product1.getPrice() == 15);
		Assert.assertEquals(product1.getId(), "227526332");
		Assert.assertEquals(product1.getUrl_image(), "https://img1.etsystatic.com/057/0/10772234/il_fullxfull.747490121_8r85.jpg");
		Assert.assertTrue(product1.getUrl_product().equals("https://www.etsy.com/fr/listing/227526332/bracelet-noir-cuir-avec-une-perle-carree"));
		Assert.assertTrue(product1.getCategories().size() == 1);
		Assert.assertEquals(product1.getCategories().get(0), "Bracelet");
	}
}
