package db.pojos;

import java.io.Serializable;

public class ContractPharmacy implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1816642495004232407L;
	
	private Integer id;
	private Integer type;
	private Integer n_products;
	private Float expenditure;
	
	public ContractPharmacy() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContractPharmacy(Integer type, Integer n_products, Float expenditure) {
		super();
		this.type = type;
		this.n_products = n_products;
		this.expenditure = expenditure;
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
		return "ContractPharmacy [id=" + id + ", type=" + type + ", n_products=" + n_products + ", expenditure="
				+ expenditure + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getN_products() {
		return n_products;
	}

	public void setN_products(Integer n_products) {
		this.n_products = n_products;
	}

	public Float getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(Float expenditure) {
		this.expenditure = expenditure;
	}
	
	
}
