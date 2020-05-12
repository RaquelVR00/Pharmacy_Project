package db.interfaces;

import java.util.List;
import db.pojos.ContractWorker;

public interface ContractWorkerManager {
	public void add(ContractWorker contract_w);
	 public ContractWorker getContract(int contractId);
	 public void update(ContractWorker contract_w);
	 public List<ContractWorker> showContracts();
}
