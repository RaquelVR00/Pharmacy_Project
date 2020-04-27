package db.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.ContractWorkerManager;
import db.pojos.Component;
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
		ContractWorker newContract = null;
				try {
					String sql="SELECT * FROM contract_worker"
							+"WHERE id = ?";
					PreparedStatement p= c.prepareStatement(sql);
					p.setInt(1, contractId);
					ResultSet rs= p.executeQuery();
					boolean contractCreated = false;
					while(rs.next()) {
						if(!contractCreated) {
					   Integer newContractId = rs.getInt(1);
					   Double newContractSalary = rs.getDouble(2);
					   Double newContractBonus = rs.getDouble(3);
					   String newContractType = rs.getString(4);
					   newContract = new ContractWorker(newContractId,newContractSalary,newContractBonus,newContractType);
					   contractCreated = true;
						}
					  }
				}catch (SQLException e) {
					e.printStackTrace();
				}
				return newContract;
		
	}
	public List<ContractWorker> showContracts(){
		List<ContractWorker> contractsList = new ArrayList<ContractWorker>();
		try {
			String sql = "SELECT * FROM contract_worker";
			PreparedStatement prep = c.prepareStatement(sql);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
			Integer id = rs.getInt("id");
			Double salary = rs.getDouble("salary");
			Double bonus = rs.getDouble("bonus");
			String type = rs.getString("type");
			ContractWorker newContract = new ContractWorker(id,salary,bonus,type);
			//Add contract
			contractsList.add(newContract);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return contractsList;
	}

	@Override
	public void update(ContractWorker contract_w) {
		// TODO Auto-generated method stub
		try {
			// Update the salary of the employee
			String sql = "UPDATE product SET numberProducts=? WHERE id=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, contract_w.getID());
			s.setDouble(2, contract_w.getSalary());
			s.executeUpdate();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
