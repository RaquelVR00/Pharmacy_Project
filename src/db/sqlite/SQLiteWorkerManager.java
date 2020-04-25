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
			String sql = "SELECT * from workers WHERE name LIKE ?";
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
				Worker newworker = new Worker(workerName, workerPosition, workerStartDate, workerNationality,
						workerContract_id);
				workersList.add(newworker);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return workersList;
	}

	@Override
	public List<Worker> searchByPosition(String position) {
		// TODO Auto-generated method stub
		// return null;
		List<Worker> workersList = new ArrayList<Worker>();
		try {
			String sql = "SELECT * from workers WHERE position LIKE ?";
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
				Worker newworker = new Worker(workerName, workerPosition, workerStartDate, workerNationality,
						workerContract_id);
				workersList.add(newworker);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return workersList;
	}

	@Override
	public void add(Worker worker) {
		// TODO Auto-generated method stub
		try {
			String sql = "INSERT INTO workers (name, position, start_date, nationality, contract_id) "
					+ "Values (?,?,?,?,?);";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(2, worker.getName());
			prep.setString(3, worker.getPosition());
			prep.setDate(4, worker.getStart_date());
			prep.setString(5, worker.getNationality());
			prep.setInt(6, worker.getContract_id());
			prep.close();
		} catch (Exception e) {
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
			c.close();
		} catch (Exception e) {
			// TODO: handle exception
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
			int contract_id = rs.getInt("contract id");
			Worker worker = new Worker(id, name, position, date, nationality, contract_id);
			System.out.println(worker);
		}
		rs.close();
		stmt.close();
		}catch(SQLException e) {
			//TODO
		}
		
	}
	


}
