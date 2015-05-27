package fr.weepstone.multi_market.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.weepstone.multi_market.model.Product;
import fr.weepstone.multi_market.net.Connection;
import fr.weepstone.multi_market.service.DaWandaService;
import fr.weepstone.multi_market.service.EtsyService;
import fr.weepstone.multi_market.service.FaitMaisonService;

@Component
@RestController
public class MultiMarketController {

	@Autowired
	private Connection connection;

	@Autowired
	private FaitMaisonService faitMaisonService;

	@Autowired
	private DaWandaService dawandaService;

	@Autowired
	private EtsyService etsyService;

	@RequestMapping(value = "/fait-maison/products", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getFaitMaisonProducts() throws KeyManagementException, NoSuchAlgorithmException, IOException, SQLException {
		return faitMaisonService.getProducts();
	}

	@RequestMapping(value = "/fait-maison/product", method = RequestMethod.POST)
	public Product addFaitMaisonProduct(@RequestBody Product product) {
		return null;
	}

	@RequestMapping(value = "/dawanda/products", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getDawandaProducts() throws KeyManagementException, NoSuchAlgorithmException, IOException, SQLException {
		return dawandaService.getProducts();
	}

	@RequestMapping(value = "/etsy/products", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getEtsyProducts() throws KeyManagementException, NoSuchAlgorithmException, IOException, SQLException {
		return etsyService.getProducts();
	}

}
