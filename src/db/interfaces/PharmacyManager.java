package db.interfaces;

import java.util.List;

import db.pojos.Pharmacy;
import db.pojos.Products;

public interface PharmacyManager {
	public List<Pharmacy> searchByName(String name);
	public void add(Pharmacy pharmacy);
}
