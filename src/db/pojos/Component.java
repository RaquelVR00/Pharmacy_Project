package db.pojos;

import java.io.Serializable;

public class Component implements Serializable {

	private static final long serialVersionUID = -6472568147682931329L;
	
	private Integer id;
	private String name;
	private Float price;
	private String supplier;
	
	public Component() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Component(String name, Float price, String supplier) {
		super();
		this.name = name;
		this.price = price;
		this.supplier = supplier;
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
		Component other = (Component) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Component [id=" + id + ", name=" + name + ", price=" + price + ", supplier=" + supplier + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	
	

}
