package db.interfaces;

import java.util.List;
import db.pojos.Worker;

public interface WorkerManager {
	
	public List<Worker> searchByName(String name);
	public List<Worker> searchByPosition(String position);
	public void add(Worker worker);
	public void fire(Integer worker_id);
	public void printWorkers();
	
}