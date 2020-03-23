package db.interfaces;

import db.pojos.Products;

import java.util.List;

public interface ProductManager {


	 public List<Products> searchByName(String name);
	 public List<Products> searchByType(String type);
	 public List<Products> searchByPrice(Float price);
	 public void add(Products product);
	 public void buy(Products product);
	
	 
}
