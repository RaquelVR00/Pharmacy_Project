package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.ComponentManager;
import db.pojos.Component;
import db.pojos.Product;

public class SQLiteComponentManager implements ComponentManager {

	private Connection c;
	
	public SQLiteComponentManager(Connection c) {
		this.c=c;
	}
	
	public void give(int productId, int componentId) {
	//Link Product and Component
			try {
				String sql = "INSERT INTO productComponents (productId,componentId) "
						+ "VALUES (?,?)";
				PreparedStatement prep = c.prepareStatement(sql);
				prep.setInt(1,productId);
				prep.setInt(2,componentId);
				prep.executeUpdate();
				prep.close();
			} catch (SQLException e) {
				e.printStackTrace();	
		}
	}
	
	public void add(Component component) {
		try {
			String sql = "INSERT INTO component (name,price,supplier,numberComponents) "
					+ "VALUES (?,?,?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, component.getName());
			prep.setFloat(2, component.getPrice());
			prep.setString(3,component.getSupplier());
			prep.setInt(4, component.getNumberComponents());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Component> showComponents(){
		List<Component> componentsList = new ArrayList<Component>();
		try {
			String sql = "SELECT * FROM component";
			PreparedStatement prep = c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
			int id = rs.getInt("id");
			String componentName = rs.getString("name");
			String supplier = rs.getString("supplier");
			Float componentPrice = rs.getFloat("price");
			int numberComponents = rs.getInt("numberComponents");
			Component newComponent = new Component(id,componentName,componentPrice,supplier,numberComponents);
			//Add to component list
			componentsList.add(newComponent);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return componentsList;
	}

	

	@Override
	public List<Component> searchByName(String name) {
		List<Component> componentList=new ArrayList<Component>();
		try {
			String sql = "SELECT * FROM component WHERE name LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, name);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String componentName=rs.getString("name");
				float price=rs.getFloat("price");
				String supplier=rs.getString("supplier");
				int numberComponents = rs.getInt("numberComponents");
				Component newcomponent=new Component(id,componentName,price,supplier,numberComponents);
				componentList.add(newcomponent);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return componentList;
		
	}

	@Override
	public List<Component> searchBySupplier(String supplier) {
		List<Component> supplierList=new ArrayList<Component>();
		try {
			String sql = "SELECT * FROM component WHERE supplier LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, supplier);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String componentName=rs.getString("name");
				float price=rs.getFloat("price");
				String supplierName=rs.getString("supplier");
				int numberComponents = rs.getInt("numberComponents");
				Component newcomponent=new Component(id,componentName,price,supplierName,numberComponents);
				supplierList.add(newcomponent);
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return supplierList;
	}
	
	public Component getComponent(int componentId) {
		//Get product and components
		Component newComponent = null;
		try {
			String sql="SELECT * FROM component"
					+" WHERE id = ?";
			PreparedStatement p= c.prepareStatement(sql);
			p.setInt(1, componentId);
			ResultSet rs= p.executeQuery();
			boolean componentCreated = false;
			while(rs.next()) {
				if(!componentCreated) {
			   int newComponentId = rs.getInt(1);
			   String componentName = rs.getString(2);
			   float componentPrice = rs.getFloat(3);
			   String componentSupplier = rs.getString(4);
			   int numberComponent = rs.getInt(5);
			   newComponent = new Component(newComponentId,componentName,componentPrice,componentSupplier, numberComponent);
			   componentCreated = true;
				}
			  }
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return newComponent;
	}
	
	public void update(Component component ) {
		try {
			String sql = "UPDATE component SET numberComponents=? WHERE id=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(2, component.getId());
			s.setInt(1, component.getNumberComponents());
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

