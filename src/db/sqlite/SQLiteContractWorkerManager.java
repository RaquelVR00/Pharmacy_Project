package db.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;

import db.interfaces.ContractWorkerManager;
import db.pojos.ContractWorker;

public class SQLiteContractWorkerManager implements ContractWorkerManager {
	private Connection c;
	public SQLiteContractWorkerManager(Connection c) {
		this.c=c;
	}
	@Override
	public void add(ContractWorker contract_w) {
		// TODO Auto-generated method stub

	}

	@Override
	public ContractWorker getContract(int contractId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ContractWorker contract_w) {
		// TODO Auto-generated method stub
		try {
			// Update the salary of the employee
			String sql = "UPDATE product SET numberProducts=? WHERE id=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, contract_w.getID());
			s.setFloat(2, contract_w.getSalary());
			s.executeUpdate();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
