package fr.weepstone.multi_market.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.weepstone.multi_market.model.NameProvider;
import fr.weepstone.multi_market.model.ParamProvider;
import fr.weepstone.multi_market.model.Product;
import fr.weepstone.multi_market.model.Provider;
import fr.weepstone.multi_market.model.UserMarket;
import fr.weepstone.multi_market.net.Connection;
import fr.weepstone.multi_market.reader.EtsyReader;
import fr.weepstone.multi_market.repository.UserRepository;

@Service
public class EtsyService {

	@Autowired
	private Connection connection;

	@Autowired
	private EtsyReader reader;

	@Autowired
	private UserRepository uRepository;

	public List<Product> getProducts() throws KeyManagementException, NoSuchAlgorithmException, IOException, SQLException {
		HashMap<String, String> params = new HashMap<String, String>();
		UserMarket user = uRepository.findByUsername("akemi");
		Provider etsyProvider = null;
		for (Provider provider : user.getProviders()) {
			if (provider.getName().equals(NameProvider.ETSY)) {
				etsyProvider = provider;
			}
		}
		ParamProvider username = etsyProvider.getUserName();
		ParamProvider password = etsyProvider.getPassword();
		params.put(username.getKey(), username.getValue());
		params.put(password.getKey(), password.getValue());
		List<String> cookieValues = new ArrayList<String>();
		for (ParamProvider param : etsyProvider.getParams()) {
			params.put(param.getKey(), param.getValue());
		}
		List<String> cookies = new ArrayList<String>();
		cookies.add("uaid=uaid%3DATviGfeT_LoSQbqAat9nd8nEDby0%26_now%3D1427282308%26_slt%3Do_jLuCJc%26_kid%3D1%26_ver%3D1%26_mac%3Dqt-pvCuVEsh7_Yxxe_y0Q5CSJvZ-8Y5Pyo_6bCQtdRQ.; st-v1-1-1-_etsy_com=2%3Ae937fa59b0c05da291d463767583c375f5c2fccd%3A1427282332%3A1427979309%3Adc22adab2239bf2d23eccd4c75343d05419c342e2835717340c31835361ad1bdc26585f15c0fda4c;kt-v1-0-1-_etsy_com=2%3A632e189c014b4cba98237a927dce007925ecc4d7%3A1427282332%3A1427979309%3Adc22adab2239bf2d23eccd4c75343d05419c342e2835717340c31835361ad1bdc26585f15c0fda4c;");
		connection.connection("https://www.etsy.com/fr/signin", params, cookieValues);
		String pageProducts = connection
				.getPageHtml(
						"https://www.etsy.com/api/v3/ajax/shop/10772234//listings/elasticsearch?limit=40&offset=0&sort_field=ending_date&sort_order=descending&state=active&language_id=0&query=&shop_section_id=&linesheet_collection_id=&listing_tag=&is_featured=&shipping_profile_id=&is_retail=true&is_wholesale=&is_retail_only=&is_wholesale_only=",
						cookies);
		System.out.println(pageProducts);
		return reader.readProducts(pageProducts);
	}

}
