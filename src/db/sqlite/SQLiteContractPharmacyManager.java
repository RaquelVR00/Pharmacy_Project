package db.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.interfaces.ContractPharmacyManager;
import db.pojos.ContractPharmacy;

public class SQLiteContractPharmacyManager implements ContractPharmacyManager {
	private Connection c;
	public SQLiteContractPharmacyManager(Connection c) {
		this.c=c;
	}
	@Override
	public void add(ContractPharmacy contract_p) {
		try {
			String sql = "INSERT INTO contract_pharmacy (type, expenditure, n_products) "
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

}
