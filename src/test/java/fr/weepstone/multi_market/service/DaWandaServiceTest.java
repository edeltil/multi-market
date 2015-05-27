package fr.weepstone.multi_market.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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
public class DaWandaServiceTest {

	@Autowired
	DaWandaService dawandaService;

	@Test
	public void pageParsingTest() throws KeyManagementException, NoSuchAlgorithmException, IOException, SQLException {
		List<Product> products = dawandaService.getProducts();
		Assert.assertTrue(products.size() == 23);
	}

}
