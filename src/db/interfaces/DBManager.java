package db.interfaces;

public interface DBManager {

	public void connect();
	public void disconnect();
	public void createTables();
	
	public ProductManager getProductManager();
	public ComponentManager getComponentManager();
	public WorkerManager getWorkerManager();
	public PharmacyManager getPharmacyManager();
	public ContractWorkerManager getContractWorkerManager();
	public ContractPharmacyManager getContractPharmacyManager();
	
	public int getLastId();
}

