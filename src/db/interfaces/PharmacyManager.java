package db.interfaces;

import java.util.List;

import db.pojos.Pharmacy;

public interface PharmacyManager {
	public List<Pharmacy> searchByName(String name);
}
