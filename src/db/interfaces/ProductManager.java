package db.interfaces;

import db.pojos.Products;

import java.util.List;

public interface ProductManager {


	 public Products searchByName(String name);
	 public Products searchByType(String type);
	 public Products searchByPrice(Float price);
	 public void add(Products product);
	 public void buy(Products product);
	 public Products getProduct(int productId);
	 public void update(Products product);
}
