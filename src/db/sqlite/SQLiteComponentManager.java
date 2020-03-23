package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.ComponentManager;
import db.pojos.Component;

public class SQLiteComponentManager implements ComponentManager {

	private Connection c;
	public SQLiteComponentManager(Connection c) {
		this.c=c;
	}
	public void add(Component component) {
		try {
			String sql = "INSERT INTO components (name,price,supplier) "
					+ "VALUES (?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, component.getName());
			prep.setFloat(2, component.getPrice());
			prep.setString(3,component.getSupplier());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Component> searchByName(String name) {
		List<Component> componentList=new ArrayList<Component>();
		try {
			String sql = "SELECT * FROM components WHERE name LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, name);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String componentName=rs.getString("name");
				float price=rs.getFloat("price");
				String supplier=rs.getString("supplier");
				Component newcomponent=new Component(componentName,price,supplier);
				componentList.add(newcomponent);
			} 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return componentList;
		
	}

	@Override
	public List<Component> searchBySupplier(String supplier) {
		List<Component> supplierList=new ArrayList<Component>();
		try {
			String sql = "SELECT * FROM components WHERE supplier LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, supplier);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String componentName=rs.getString("name");
				float price=rs.getFloat("price");
				String supplierName=rs.getString("supplier");
				Component newcomponent=new Component(componentName,price,supplierName);
				supplierList.add(newcomponent);
			} 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return supplierList;
	}

}
