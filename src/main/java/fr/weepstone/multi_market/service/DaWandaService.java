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
import fr.weepstone.multi_market.reader.DaWandaReader;
import fr.weepstone.multi_market.repository.UserRepository;

@Service
public class DaWandaService {

	private static String URL_SESSION = "https://fr.dawanda.com/core/sessions?__AJAX__";

	private static String URL_PRODUCT_MANAGER = "http://fr.dawanda.com/product_manager";

	@Autowired
	private Connection connection;

	@Autowired
	private DaWandaReader reader;

	@Autowired
	private UserRepository uRepository;

	public List<Product> getProducts() throws KeyManagementException, NoSuchAlgorithmException, IOException, SQLException {
		HashMap<String, String> params = new HashMap<String, String>();
		UserMarket user = uRepository.findByUsername("akemi");
		Provider daWandaProvider = null;
		for (Provider provider : user.getProviders()) {
			if (provider.getName().equals(NameProvider.DAWANDA)) {
				daWandaProvider = provider;
			}
		}
		ParamProvider username = daWandaProvider.getUserName();
		ParamProvider password = daWandaProvider.getPassword();
		params.put(username.getKey(), username.getValue());
		params.put(password.getKey(), password.getValue());
		List<String> cookieValues = new ArrayList<String>();
		cookieValues.add("remember_me_token");
		cookieValues.add("_dawanda_session");
		for (ParamProvider param : daWandaProvider.getParams()) {
			params.put(param.getKey(), param.getValue());
		}
		List<String> cookies = connection.connection(URL_SESSION, params, cookieValues);
		String pageProducts = connection.getPageHtml(URL_PRODUCT_MANAGER, cookies);
		System.out.println(pageProducts);
		return reader.readProducts(pageProducts);
	}

}
