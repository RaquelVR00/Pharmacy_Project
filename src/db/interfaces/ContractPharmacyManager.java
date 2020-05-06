package db.interfaces;


import java.util.List;

import db.pojos.ContractPharmacy;

public interface ContractPharmacyManager {
	 public void add(ContractPharmacy contract_p);
	 public ContractPharmacy getContract(int contractId);
	 public void update(ContractPharmacy contract_p);
	 public List<ContractPharmacy> showContracts();
}
