package db.sqlite;

import db.interfaces.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.interfaces.DBManager;

public class SQLiteManager implements DBManager {
	
	private Connection c;
	private ProductManager product;
	private ComponentManager component;
	private WorkerManager worker;
	private PharmacyManager pharmacy;
	private ContractWorkerManager contractWorker;
	private ContractPharmacyManager contractPharmacy;
	
	
	public SQLiteManager() {
		super();
	}

	@Override
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			this.c= DriverManager.getConnection("jdbc:sqlite:./db/pharmacy.db");
			c.createStatement().execute("PRAGMA foreing_keys=ON");
			//Create product
			product = new SQLiteProductManager(c);
			//Create component
			component = new SQLiteComponentManager(c);
			//Create worker
			worker =new SQLiteWorkerManager(c);
			//Create Pharmacy
			pharmacy = new SQLitePharmacyManager(c);
			//Create Contract Worker
			contractWorker = new SQLiteContractWorkerManager(c);
			//Create Contract Pharmacy
			contractPharmacy = new SQLiteContractPharmacyManager(c);
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	/*protected Connection getConnection() {
		return c;
	}*/

	@Override
	public void disconnect() {
		try {
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void createTables() {
		Statement stmt1;
		try {
		stmt1 = c.createStatement();
		String sql1 = "CREATE TABLE worker "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " name     TEXT     NOT NULL, "
				   + " position  TEXT	 NOT NULL,"
				   + " start_date DATE DEFAULT (CURRENT_DATE),"
				   + " nationality TEXT NOT NULL,"
				   + " contract_id INTEGER REFERENCES contract_worker(id) ON UPDATE CASCADE ON DELETE SET NULL)";
		stmt1.executeUpdate(sql1);
		
		stmt1 = c.createStatement();
		String sql2 = "CREATE TABLE pharmacy "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " name     TEXT     NOT NULL, "
				   + " location TEXT NOT NULL,"
				   +" contract_pid INTEGER REFERENCES contract_pharmacy(id) ON UPDATE CASCADE ON DELETE SET NULL)";
		stmt1.executeUpdate(sql2);
		
		stmt1 = c.createStatement();
		String sql3 = "CREATE TABLE product "
				   + "(id   INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " name     TEXT     NOT NULL, "
				   + " type  TEXT  	NOT NULL, "
				   + " price	INTEGER NOT NULL,"
				   + " n_products INTEGER NOT NULL)";
		stmt1.executeUpdate(sql3);
	
		stmt1 = c.createStatement();
		String sql4 = "CREATE TABLE component "
				   + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				   +"name TEXT NOT NULL,"
				   +"price FLOAT NOT NULL,"
				   +"supplier TEXT NOT NULL,"
				   + "numberComponents INTEGER NOT NULL)";
		stmt1.executeUpdate(sql4);
	
		stmt1=c.createStatement();
		String sql5 = "CREATE TABLE contract_worker"
				+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+"salary DOUBLE,"
				+"bonus DOUBLE,"
				+"type TEXT NOT NULL)";
		stmt1.executeUpdate(sql5);
		
		stmt1=c.createStatement();
		String sql6 = "CREATE TABLE contract_pharmacy"
				+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+" type TEXT NOT NULL,"
				+" expenditure FLOAT NOT NULL,"
				+" n_products INTEGER NOT NULL)";
		stmt1.executeUpdate(sql6);
		
		stmt1=c.createStatement();
		String sql7= "CREATE TABLE pharmacyProducts"
				+"(pharmacieId INTEGER REFERENCES pharmacy(id) ON UPDATE CASCADE ON DELETE SET NULL,"
				+"productId INTEGER REFERENCES product(id) ON UPDATE CASCADE ON DELETE SET NULL,"
				+"PRIMARY KEY(pharmacieId,productId))";
		stmt1.executeUpdate(sql7);
		
		stmt1=c.createStatement();
		String sql8= "CREATE TABLE productComponents"
                 +"(productId INTEGER REFERENCES product(id) ON UPDATE CASCADE ON DELETE SET NULL,"
				 +"componentId INTEGER REFERENCES component(id) ON UPDATE CASCADE ON DELETE SET NULL,"
                 +"PRIMARY KEY(productId,componentId))";
		stmt1.executeUpdate(sql8);
		
		stmt1.close();
		
		System.out.println("Tables created!!");
		
		}catch(Exception e) {
			if(e.getMessage().contains("already exists")) {
				//If tables already exist we are going to do nothing
			}else {
				e.printStackTrace();
			}
		}

	}
	
	@Override  
	public ProductManager getProductManager() {
		return product;
	}
	@Override  
	public ComponentManager getComponentManager() {
		return component;
	}
	@Override  
	public WorkerManager getWorkerManager() {
		return worker;
	}
	@Override  
	public PharmacyManager getPharmacyManager() {
		return pharmacy;
	}
	@Override
	public ContractWorkerManager getContractWorkerManager() {
		return contractWorker;
	}
	public ContractPharmacyManager getContractPharmacyManager() {
		return contractPharmacy;
	}
	@Override
	public int getLastId() {
		int result = 0;
		try {
			String query = "SELECT last_insert_rowid() AS lastId";
			PreparedStatement p = c.prepareStatement(query);
			ResultSet rs = p.executeQuery();
			result = rs.getInt("lastId");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
