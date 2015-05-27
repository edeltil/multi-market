package fr.weepstone.multi_market.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@Getter
public final class Product {
	private String id;
	private String title;
	private String description;
	private String url_product;
	private String url_image;
	private double price;
	private List<String> categories;
}
