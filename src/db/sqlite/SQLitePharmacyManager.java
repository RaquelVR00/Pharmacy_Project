package db.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import db.interfaces.PharmacyManager;
import db.pojos.Component;
import db.pojos.Pharmacy;
import db.pojos.Product;
public class SQLitePharmacyManager implements PharmacyManager {
	private Connection c;
	public SQLitePharmacyManager(Connection c) {
		this.c=c;
	}


	@Override
	public List<Pharmacy> searchByName(String name) {
		List <Pharmacy> pharmacyList = new ArrayList <Pharmacy> ();
		try {
			String sql = "SELECT * FROM pharmacy WHERE name LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, "%"+name+"%");
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
			int id = rs.getInt("id");
			String pharmacyName = rs.getString("name");
			int pharmacyContract_pid = rs.getInt("contract_pid");
			String pharmacyLocation = rs.getString("location");
			Pharmacy newpharmacy = new Pharmacy(id,pharmacyName,pharmacyContract_pid,pharmacyLocation);
			pharmacyList.add(newpharmacy);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return pharmacyList;
	}


	@Override
	public void add(Pharmacy pharmacy) {
		// TODO Auto-generated method stub
		try {
			String sql = "INSERT INTO pharmacy (name, location, contract_pid) "
					+ "VALUES (?,?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, pharmacy.getName());
			prep.setString(2, pharmacy.getLocation());
			prep.setInt(3, pharmacy.getContract_pid() );
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void give(int pharmacyId, int productId) {
		//Link Product and Pharmacy
				try {
					String sql = "INSERT INTO pharmacyProducts (pharmacyId,productId) "
							+ "VALUES (?,?)";
					PreparedStatement prep = c.prepareStatement(sql);
					prep.setInt(1,pharmacyId);
					prep.setInt(2,productId);
					prep.executeUpdate();
					prep.close();
				} catch (SQLException e) {
					e.printStackTrace();	
			}
	}
	
	public Pharmacy getPharmacy(int pharmacyId) {
		//Get product and components
		Pharmacy newPharmacy = null;
		try {
			String sql="SELECT * FROM pharmacy AS p JOIN pharmacyProducts AS pp ON p.id = pp.pharmacyId "
					+"JOIN product AS pr ON pp.productId=pr.id "
					+"WHERE p.id = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, pharmacyId);
			ResultSet rs= p.executeQuery();
			List<Product> productsList = new ArrayList<Product>();
			boolean pharmacyCreated = false;
			while(rs.next()) {
				if(!pharmacyCreated) {
				   int newPharmacyId = rs.getInt(1);
				   String pharmacyName = rs.getString(2);
				   String pharmacyLocation = rs.getString(3);
				   int pharmacyContract_pid = rs.getInt(4);
				   
				   newPharmacy = new Pharmacy(newPharmacyId,pharmacyName,pharmacyContract_pid,pharmacyLocation);
				   pharmacyCreated = true;
				}
			   int productId = rs.getInt(7);
			   String productName = rs.getString(8);
			   Product newProducts = new Product(productId, productName);
			   productsList.add(newProducts);
			}
				newPharmacy.setProducts(productsList);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return newPharmacy;
	}
	
	public List<Pharmacy> showPharmacy(){
		List<Pharmacy> pharmaciesList = new ArrayList<Pharmacy>();
		try {
			String sql = "SELECT * FROM pharmacy";
			PreparedStatement prep = c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
			int id = rs.getInt("id");
			String pharmacyName = rs.getString("name");
			String pharmacyLocation = rs.getString("location");
			int pharmacyContract_pid = rs.getInt("contract_pid");
			
			Pharmacy newPharmacy = new Pharmacy(id,pharmacyName,pharmacyContract_pid, pharmacyLocation);
			//Add to component list
			pharmaciesList.add(newPharmacy);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return pharmaciesList;
	}
}


