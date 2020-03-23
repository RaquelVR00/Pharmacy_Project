package db.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import db.interfaces.DBManager;

public class SQLiteManager implements DBManager {
	
	private Connection c;
	
	public SQLiteManager() {
		super();
	}

	@Override
	public void connect() {
		try {
			Class.forName("org.sqlite.Pharmacy");
			this.c= DriverManager.getConnection("pharmacy:sqlite./db/pharmacy.db");
			c.createStatement().execute("PRAGMA foreing_keys=ON");
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	protected Connection getConnection() {
		return c;
	}

	@Override
	public void disconnect() {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void createTables() {
		try {
		Statement stmt1 = c.createStatement();
		String sql1 = "CREATE TABLE worker "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " name     TEXT     NOT NULL, "
				   + " position  TEXT	 NOT NULL,"
				   + "start_date DATE DEFAULT (CURRENT DATE),"
				   + "nationality TEXT NOT NULL,"
				   + "contract_id INTEGER REFERENCES contract_worker(id)"
				   ;
		stmt1.executeUpdate(sql1);
		stmt1.close();
		Statement stmt2 = c.createStatement();
		String sql2 = "CREATE TABLE pharmacy "
				   + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " name     TEXT     NOT NULL, "
				   + " location TEXT NOT NULL,"
				   +"contract_pid INTEGER REFERENCES contract_pharmacy(id)"
				  ;
		stmt2.executeUpdate(sql2);
		stmt2.close();
		Statement stmt3 = c.createStatement();
		String sql3 = "CREATE TABLE product "
				   + "(id   INTEGER  PRIMARY KEY AUTOINCREMENT,"
				   + " name     TEXT     NOT NULL, "
				   + " type  TEXT  	NOT NULL, "
				   + " price	INTEGER NOT NULL,"
				   + " n_products INTEGER)";
		stmt3.executeUpdate(sql3);
		stmt3.close();
		Statement stmt4 = c.createStatement();
		String sql4 = "CREATE TABLE component "
				   + "(id INTEGER PRIMARY KEY AUTOINCRIMENT,"
				   +"name TEXT NOT NULL,"
				   +"price INTEGER NOT NULL,"
				   +"supplier TEXT NOT NULL)";
		stmt4.executeUpdate(sql4);
		stmt4.close();
		Statement stmt5=c.createStatement();
		String sql5 = "CREATE TABLE contract_worker"
				+"(id INTEGER PRIMARY KEY AUTOINCRIMENT,"
				+"salary DOUBLE,"
				+"bonus DOUBLE,"
				+"type TEXT NOT NULL)";
		stmt5.executeUpdate(sql5);
		stmt5.close();
		Statement stmt6=c.createStatement();
		String sql6 = "CREATE TABLE contract_pharmacy"
				+"(id INTEGER PRIMARY KEY AUTOINCRIMENT,"
				+" type TEXT NOT NULL,"
				+" expediture DOUBLE NOT NULL,"
				+" nproducts INTEGER NOT NULL)";
		stmt6.executeUpdate(sql6);
		stmt6.close();
		Statement stmt7=c.createStatement();
		String sql7= "CREATE TABLE pharmacyandproducts"
				+"(p_id INTEGER REFERENCES pharmacy(id),"
				+"pr_id INTEGER REFERENCES product(id))";
		stmt7.executeUpdate(sql7);
		stmt7.close();
		Statement stmt8=c.createStatement();
		String sql8= "CREATE TABLE productandcomponents"
                 +"(p_id INTEGER REFERENCES product(id),"
				 +"c_id INTEGER REFERENCES component(id))";
		stmt8.executeUpdate(sql8);
		stmt8.close();
		
				System.out.println("Tables created!!");
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
