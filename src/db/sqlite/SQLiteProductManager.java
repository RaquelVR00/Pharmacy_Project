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
import db.pojos.Products;

public class SQLiteProductManager implements ProductManager {
	private Connection c;
	public SQLiteProductManager(Connection c) {
		this.c=c;
	}
	
	List <Products> productsList = new ArrayList <Products> ();
	
	@Override
	public List<Products> searchByName(String name) {
		try {
			String sql = "SELECT * FROM products WHERE name LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, "%"+name+"%");
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
			int id = rs.getInt("id");
			String productsName = rs.getString("name");
			String productsType = rs.getString("type");
			Float productsPrice = rs.getFloat("price");
			Products newproducts = new Products(productsName,productsType,productsPrice);
			productsList.add(newproducts);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return productsList;
	}

	@Override
	public List<Products> searchByType(String type) {
		try {
			String sql =  "SELECT * FROM produts WHERE type LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, "%" + type + "%");
			ResultSet rs  = prep.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String productsName = rs.getString("name");
				String productsType = rs.getString("type");
				Float productsPrice = rs.getFloat("price");
				Products newproducts = new Products(productsName,productsType,productsPrice);
				productsList.add(newproducts);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return productsList;
	}

	@Override
	public List<Products> searchByPrice(Float price) {
		try {
			String sql = "SELECT * FROM products WHERE price LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, "%"+price+"%");
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
			int id = rs.getInt("id");
			String productsName = rs.getString("name");
			String productsType = rs.getString("type");
			Float productsPrice = rs.getFloat("price");
			Products newproducts = new Products(productsName,productsType,productsPrice);
			productsList.add(newproducts);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return productsList;
	}

	@Override
	public void add(Products product) {
		try {
			String sql = "INSERT INTO products (name, type, price) "
					+ "VALUES (?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1,product.getName());
			prep.setString(2, product.getType());
			prep.setFloat(3, product.getPrice());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void buy(Products product) {
		// TODO Auto-generated method stub

	}
	
	public Products getProduct(int productId) {
		//Get product and components
		Products newProduct = null;
		try {
			String sql="SELECT * FROM products AS p JOIN productComponents AS pc ON p.id = pc.productId"
					+"JOIN component AS c ON pc.componentId=c.id"
					+"WHERE p.id = ?";
			PreparedStatement p= c.prepareStatement(sql);
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
			   newProduct = new Products(newProductId,productName,productType,productPrice);
			   productCreated = true;
				}
			   int componentId = rs.getInt("7");
			   String componentName = rs.getString("8");
			   Component newComponent = new Component(componentId, componentName);
			   componentsList.add(newComponent);
			}
				newProduct.setComponents(componentsList);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return newProduct;
	}

	public void update(Products product) {
		try {
			// Update every aspect of a particular dog
			String sql = "UPDATE product SET numberProducts=? WHERE id=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, product.getId());
			s.setInt(2, product.getNumberProducts());
			s.executeUpdate();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
