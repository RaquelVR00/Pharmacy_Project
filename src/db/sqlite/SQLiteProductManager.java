package db.sqlite;

import java.sql.Connection;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.ProductManager;
import db.pojos.Component;
import db.pojos.Product;
import db.pojos.Worker;

public class SQLiteProductManager implements ProductManager {
	private Connection c;
	public SQLiteProductManager(Connection c) {
		this.c=c;
	}
	
	@Override
	public List<Product> searchByName(String name) {
		Product newProduct = null;
		List<Product> productList = new ArrayList<Product>();
		try {
			String sql = "SELECT * FROM product " + " WHERE name LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, "%"+name+"%");
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
					int id = rs.getInt(1);
					String productsName = rs.getString(2);
					String productsType = rs.getString(3);
					Float productsPrice = rs.getFloat(4);
					int numberProducts = rs.getInt(5);
					newProduct = new Product(id,productsName,productsType,productsPrice,numberProducts);
				productList.add(newProduct);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	@Override
	public List<Product> searchByType(String type) {
		Product newProduct = null;
		List<Product> productList = new ArrayList<Product>();
		try {
			String sql = "SELECT * FROM product WHERE type LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, "%"+type+"%");
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
					int id = rs.getInt(1);
					String productsName = rs.getString(2);
					String productsType = rs.getString(3);
					Float productsPrice = rs.getFloat(4);
					int numberProducts = rs.getInt(5);
					newProduct = new Product(id,productsName,productsType,productsPrice,numberProducts);
				productList.add(newProduct);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	@Override
	public List<Product> searchByPrice(Float price) {
		Product newProduct = null;
		List<Product> productList = new ArrayList<Product>();
		try {
			String sql = "SELECT * FROM product AS p "
					+ " JOIN productComponents AS pc ON p.id=pc.productId "
					+ " JOIN component AS c ON pc.componentId=c.id"+ " WHERE p.price LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setFloat(1, price);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
					int id = rs.getInt(1);
					String productsName = rs.getString(2);
					String productsType = rs.getString(3);
					Float productsPrice = rs.getFloat(4);
					int numberProducts = rs.getInt(5);
					newProduct = new Product(id,productsName,productsType,productsPrice,numberProducts);
				productList.add(newProduct);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	@Override
	public void add(Product product) {
		try {
			String sql = "INSERT INTO product (name, type, price, numberProducts) "
					+ "VALUES (?,?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1,product.getName());
			prep.setString(2, product.getType());
			prep.setFloat(3, product.getPrice());
			prep.setInt(4, product.getNumberProducts());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Product getProduct(int productId) {
		//Get product and components
		Product newProduct = null;
		try {
			String sql="SELECT * FROM product AS p JOIN productComponents AS pc ON p.id = pc.productId "
					+"JOIN component AS c ON pc.componentId=c.id "
					+"WHERE p.id = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, productId);
			ResultSet rs= p.executeQuery();
			List<Component> componentsList = new ArrayList<Component>();
			boolean productCreated = false;
			while(rs.next()) {
				if(!productCreated) {
			   int newProductId = rs.getInt(1);
			   String productName = rs.getString(2);
			   String productType = rs.getString(3);
			   float productPrice = rs.getFloat(4);
			   int numberProducts = rs.getInt(5);
			   newProduct = new Product(newProductId,productName,productType,productPrice,numberProducts);
			   productCreated = true;
				}
			   int componentId = rs.getInt(8);
			   String componentName = rs.getString(9);
			   Float price = rs.getFloat(10);
			   String supplier = rs.getString(11);
			   int numberComponents = rs.getInt(12);
			   Component newComponent = new Component(componentId, componentName, price, supplier, numberComponents);
			   componentsList.add(newComponent);
			}
			newProduct.setComponents(componentsList);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return newProduct;
	}

	public void update(Product product) {
		try {
			// Update the number of products
			String sql = "UPDATE product SET numberProducts=? WHERE id=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(2, product.getId());
			s.setInt(1, product.getNumberProducts());
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void delete(int product_id) {
		try {
			String sql = "DELETE FROM product WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, product_id);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public List<Product> showProducts() {
		Product newProduct = null;
		List<Product> productsList = new ArrayList<Product>();
		try {
			String sql = "SELECT * FROM product ";
			PreparedStatement prep = c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String productsName = rs.getString(2);
				String productsType = rs.getString(3);
				Float productsPrice = rs.getFloat(4);
				int numberProducts = rs.getInt(5);
				newProduct = new Product(id,productsName,productsType,productsPrice,numberProducts);
				productsList.add(newProduct);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return productsList;
	}

}
