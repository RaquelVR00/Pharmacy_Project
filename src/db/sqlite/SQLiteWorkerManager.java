package db.sqlite;

import java.util.ArrayList;
import java.util.List;

import db.interfaces.WorkerManager;
import db.pojos.Worker;

public class SQLiteWorkerManager implements WorkerManager {

	@Override
	public List<Worker> searchByName(String name) {
		// TODO Auto-generated method stub
		//return null;
		List<Worker> workersList = new ArrayList<Worker>();
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public List<Worker> searchByPosition(String position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Worker worker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fire(Worker worker) {
		// TODO Auto-generated method stub

	}

}
