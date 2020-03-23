package db.sqlite;

import java.sql.Connection;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.ProductManager;
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
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Products> searchByPrice(Float price) {
		// TODO Auto-generated method stub
		return null;
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

}
