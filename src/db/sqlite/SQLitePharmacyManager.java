package db.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import db.interfaces.PharmacyManager;

import db.pojos.Pharmacy;
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
			int pharmacyContract_pid = rs.getInt("contrac_pid");
			String pharmacyLocation = rs.getString("location");
			Pharmacy newpharmacy = new Pharmacy(pharmacyName,pharmacyContract_pid,pharmacyLocation);
		   
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return pharmacyList;
	}
	}


