package db.pojos;

import java.io.Serializable;

public class ContractPharmacy implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1816642495004232407L;
	
	private Integer id;
	private String type;
	private Float expenditure;
	private int numberProducts;
	
	public ContractPharmacy(String type, Float expenditure, Integer n_products) {
		super();
		this.type = type;
		this.expenditure = expenditure;
		this.numberProducts = n_products;
	}

	public ContractPharmacy(Integer id, String type, Float expenditure, int n_products) {
		super();
		this.id = id;
		this.type = type;
		this.expenditure = expenditure;
		this.numberProducts = n_products;
	}
	public ContractPharmacy() {
		super();
		this.type = "Default";
		this.expenditure = 0F;
		this.numberProducts = 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContractPharmacy other = (ContractPharmacy) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContractPharmacy [id=" + id + ", type=" + type + ", n_products=" + numberProducts + ", expenditure="
				+ expenditure + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getN_products() {
		return numberProducts;
	}

	public void setN_products(int n_products) {
		this.numberProducts = n_products;
	}

	public Float getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(Float expenditure) {
		this.expenditure = expenditure;
	}
	
	
}
