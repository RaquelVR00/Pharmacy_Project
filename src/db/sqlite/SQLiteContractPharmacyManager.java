package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.ContractPharmacyManager;
import db.pojos.ContractPharmacy;
import db.pojos.ContractWorker;
import db.pojos.Worker;

public class SQLiteContractPharmacyManager implements ContractPharmacyManager {
	private Connection c;
	public SQLiteContractPharmacyManager(Connection c) {
		this.c=c;
	}
	@Override
	public void add(ContractPharmacy contract_p) {
		try {
			String sql = "INSERT INTO contract_pharmacy (type, expenditure, numberProducts) "
					+ "VALUES (?,?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, contract_p.getType());
			prep.setFloat(2, contract_p.getExpenditure());
			prep.setInt(3, contract_p.getN_products());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public ContractPharmacy getContract(int contractId) {
		// TODO Auto-generated method stub
		return null;
	}
	public int getId() {
		int id = 0;
		try {
			String sql = ("SELECT MAX(id) AS id FROM contract_pharmacy");
			PreparedStatement prep = c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			id = rs.getInt("id");
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void update(ContractPharmacy contract_p) {
		// TODO Auto-generated method stub
		try {
			// Update the expenditure of the contract
			String sql = "UPDATE product SET numberProducts=? WHERE id=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, contract_p.getId());
			s.setFloat(2, contract_p.getExpenditure());
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<ContractPharmacy> showContracts() {
		List<ContractPharmacy> contractsList = new ArrayList<ContractPharmacy>();
		try {
			String sql = "SELECT * FROM contract_pharmacy";
			PreparedStatement prep = c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String type = rs.getString("type");
				Float expenditure = rs.getFloat("expenditure");
				int numberProducts = rs.getInt("numberProducts");
				ContractPharmacy newContract = new ContractPharmacy(id, type, expenditure, numberProducts);
			//Add contract
			contractsList.add(newContract);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return contractsList;
	}

}
