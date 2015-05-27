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
import fr.weepstone.multi_market.reader.FaitMaisonReader;
import fr.weepstone.multi_market.repository.UserRepository;

@Service
public class FaitMaisonService {

	private static final String URL_CONNECTION = "http://www.fait-maison.com/session";

	private static final String URL_USER = "http://www.fait-maison.com/member/user";

	private static List<String> COOKIE_VALUES = new ArrayList<String>();
	{
		COOKIE_VALUES.add("remember_me_token");
		COOKIE_VALUES.add("_fait_maison0552014");
	}

	@Autowired
	private Connection connection;

	@Autowired
	private FaitMaisonReader reader;

	@Autowired
	private UserRepository uRepository;

	public List<Product> getProducts() throws KeyManagementException, NoSuchAlgorithmException, IOException, SQLException {
		HashMap<String, String> params = new HashMap<String, String>();
		UserMarket user = uRepository.findByUsername("akemi");
		Provider faitMaisonProvider = null;
		for (Provider provider : user.getProviders()) {
			if (provider.getName().equals(NameProvider.FAIT_MAISON)) {
				faitMaisonProvider = provider;
			}
		}
		ParamProvider username = faitMaisonProvider.getUserName();
		ParamProvider password = faitMaisonProvider.getPassword();
		params.put(username.getKey(), username.getValue());
		params.put(password.getKey(), password.getValue());
		for (ParamProvider param : faitMaisonProvider.getParams()) {
			params.put(param.getKey(), param.getValue());
		}
		List<String> cookies = connection.connection(URL_CONNECTION, params, COOKIE_VALUES);
		String pageUser = connection.getPageHtml(URL_USER, cookies);
		String urlProducts = reader.getUserUrl(pageUser);
		String pageProducts = connection.getPageHtml(urlProducts, cookies);
		return reader.readProducts(pageProducts);
	}

	void addProduct(Product product) {
		String url_shop = "http://www.fait-maison.com/member/shops/8282/products";

	}

}
