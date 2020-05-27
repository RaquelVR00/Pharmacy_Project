package db.interfaces;

import db.pojos.Product;

import java.util.List;

public interface ProductManager {


	 public List<Product> searchByName(String name);
	 public List<Product> searchByType(String type);
	 public List<Product> searchByPrice(Float price);
	 public void add(Product product);
	 public Product getProduct(int productId);
	 public void update(Product product);
	 public List<Product> showProducts();
	 public void delete(int product_id);
}
