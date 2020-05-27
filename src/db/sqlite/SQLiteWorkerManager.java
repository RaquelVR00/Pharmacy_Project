package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.WorkerManager;
import db.pojos.Component;
import db.pojos.Worker;


public class SQLiteWorkerManager implements WorkerManager {

	private static Connection c;

	public SQLiteWorkerManager(Connection c) {
		this.c = c;
	}

	@Override
	public List<Worker> searchByName(String name) {
		// TODO Auto-generated method stub
		// return null;
		List<Worker> workersList = new ArrayList<Worker>();
		try {
			String sql = "SELECT * FROM worker WHERE name LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, "%" + name + "%");
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String workerName = rs.getString("name");
				String workerPosition = rs.getString("position");
				Date workerStartDate = rs.getDate("start_date");
				String workerNationality = rs.getString("nationality");
				Integer workerContract_id = rs.getInt("contract_id");
				Worker newworker = new Worker(id,workerName, workerPosition, workerStartDate, workerNationality,
						workerContract_id);
				workersList.add(newworker);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return workersList;
	}

	@Override
	public List<Worker> searchByPosition(String position) {
		// TODO Auto-generated method stub
		// return null;
		List<Worker> workersList = new ArrayList<Worker>();
		try {
			String sql = "SELECT * FROM worker WHERE position LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, "%" + position + "%");
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String workerName = rs.getString("name");
				String workerPosition = rs.getString("position");
				Date workerStartDate = rs.getDate("start_date");
				String workerNationality = rs.getString("nationality");
				Integer workerContract_id = rs.getInt("contract_id");
				Worker newworker = new Worker(id, workerName, workerPosition, workerStartDate, workerNationality,
						workerContract_id);
				workersList.add(newworker);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return workersList;
	}
	public Worker searchById(Integer id) {
		// TODO Auto-generated method stub
		// return null;
		Worker newworker = null;
		try {
			String sql = "SELECT * FROM worker WHERE id LIKE ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id );
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				String workerName = rs.getString("name");
				String workerPosition = rs.getString("position");
				Date workerStartDate = rs.getDate("start_date");
				String workerNationality = rs.getString("nationality");
				Integer workerContract_id = rs.getInt("contract_id");
				newworker = new Worker(id, workerName, workerPosition, workerStartDate, workerNationality,
						workerContract_id);
				System.out.println(newworker);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return newworker;
	}
	public Worker getWorker(int workerId) {
		Worker newWorker = null;
		try {
			String sql="SELECT * FROM worker"
					+" WHERE id = ?";
			PreparedStatement p= c.prepareStatement(sql);
			p.setInt(1, workerId);
			ResultSet rs= p.executeQuery();
			boolean workerCreated = false;
			while(rs.next()) {
				if(!workerCreated) {
			   int newWorkerId = rs.getInt(1);
			   String workerName = rs.getString(2);
			   String workerPosition = rs.getString(3);
				Date workerStartDate = rs.getDate(4);
				String workerNationality = rs.getString(5);
				Integer workerContract_id = rs.getInt(6);
				newWorker = new Worker(newWorkerId, workerName, workerPosition, workerStartDate, workerNationality,
						workerContract_id);
				System.out.println(newWorker);
				workerCreated = true;
				}
			  }
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return newWorker;
	}
	@Override
	public void add(Worker worker) {
		// TODO Auto-generated method stub
		try {
			String sql = "INSERT INTO worker (name, position, start_date, nationality, contract_id) "
					+ "VALUES (?,?,?,?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, worker.getName());
			prep.setString(2, worker.getPosition());
			prep.setDate(3, worker.getStart_date());
			prep.setString(4, worker.getNationality());
			prep.setInt(5, worker.getContract_id());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}

	public void fire(Integer worker_id) {
		// TODO Auto-generated method stub
		try {
			//String sql = "DELETE FROM workers WHERE name LIKE ?";
			String sql = "DELETE FROM worker WHERE id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, worker_id);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void printWorkers(){
		try {
		Statement stmt = c.createStatement();
		String sql = "SELECT * FROM worker";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String position = rs.getString("position");
			Date date = rs.getDate("start_date");
			String nationality = rs.getString("nationality");
			int contract_id = rs.getInt("contract_id");
			Worker worker = new Worker(id, name, position, date, nationality, contract_id);
			System.out.println(worker);	
		}
		}catch(SQLException e) {
			e.printStackTrace();//TODO
		}
		
	}
	
	public void give(int workerId, int productId) {
		//Link Product and Worker
				try {
					String sql = "INSERT INTO workerProducts (workerId,productId) "
							+ "VALUES (?,?)";
					PreparedStatement prep = c.prepareStatement(sql);
					prep.setInt(1,workerId);
					prep.setInt(2,productId);
					prep.executeUpdate();
					prep.close();
				} catch (SQLException e) {
					e.printStackTrace();	
			}
	}
}
