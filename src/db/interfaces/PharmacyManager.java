package db.interfaces;

import java.util.List;
import db.pojos.Pharmacy;

public interface PharmacyManager {
	public List<Pharmacy> searchByName(String name);
	public void add(Pharmacy pharmacy);
	public void give(int pharmacyId,int productId);
	public List<Pharmacy>showPharmacy();
	public Pharmacy getPharmacy(int pharmacyId);
}
