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
public class FaitMaisonReaderTest {

	@Autowired
	FaitMaisonReader reader;

	@Test
	public void pageParsingTest() throws IOException {
		File file = new File(getClass().getClassLoader().getResource("fait_maison_products.html").getFile());
		BufferedReader buffReader = new BufferedReader(new FileReader(file));
		StringBuilder result = new StringBuilder("");

		while (buffReader.ready()) {
			String read = buffReader.readLine();
			result.append(read).append("\n");
		}
		buffReader.close();
		List<Product> products = reader.readProducts(result.toString());
		Assert.assertTrue(products.size() == 32);
		Product product1 = products.get(0);
		Assert.assertTrue(product1.getTitle().equals("Tapis de souris bleu pigeon en cuir"));
		Assert.assertTrue(product1.getPrice() == 20);
		Assert.assertTrue(product1.getDescription().equals("Ce tapis de souris est réalisé à partir de cuir de couleur bleu pigeon. Il a une taille de 15*20cm. Ce cuir est d'une épaisseur de 1.1mm.Sur commande la couleur peut etre disponible."));
		Assert.assertTrue(product1.getId().equals("product-161602"));
		Assert.assertTrue(product1.getUrl_image().equals("/system/uploads/product_photo/files/000/000/322/410/thumb145q70_P9021070B.JPG"));
		Assert.assertTrue(product1.getUrl_product().equals("http://www.fait-maison.com/accesoires/accessoires-maison/akemi_tapis-de-souris-bleu-pigeon-en-cuir.html"));
		Assert.assertTrue(product1.getCategories().size() == 1);
		Assert.assertTrue(product1.getCategories().get(0).equals("maison"));
	}

	@Test
	public void userUrlTest() throws IOException {
		File file = new File(getClass().getClassLoader().getResource("fait_maison_url.html").getFile());
		BufferedReader buffReader = new BufferedReader(new FileReader(file));
		StringBuilder result = new StringBuilder("");

		while (buffReader.ready()) {
			String read = buffReader.readLine();
			result.append(read).append("\n");
		}
		buffReader.close();
		String url = reader.getUserUrl(result.toString());
		Assert.assertTrue(url.equals("http://www.fait-maison.com/member/shops/8282"));
	}

}
