package db.sqlite;

import java.sql.Connection;
import java.sql.Date;

import java.sql.PreparedStatement;
import java.util.List;

import db.interfaces.ProductManager;
import db.pojos.Products;

public class SQLProduct implements ProductManager {
	private Connection c;
	public SQLProduct(Connection c) {
		this.c=c;
	}

	@Override
	public List<Products> searchByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> searchByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Products> searchByPrice(Float price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Products product) {
			String sql = "INSERT INTO products (name, type, price) "
					+ "VALUES (?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1,products.);
			prep.setDate(2, Date.valueOf(dobDate));
			prep.setString(3,  address);
			prep.setDouble(4, salary);
			prep.setInt(5, dep_id);
			prep.executeUpdate();
			prep.close();

	}

	@Override
	public void buy(Products product) {
		// TODO Auto-generated method stub

	}

}
