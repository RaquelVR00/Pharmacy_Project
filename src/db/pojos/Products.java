package db.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Products implements Serializable {
	private static final long serialVersionUID = 1413835970238962198L;

	private Integer id;
	private String name;
	private String type;
	private Float price;
	private List<Component> components;
	
	
	public Products() {
		super();
	}


	public Products(Integer id, String name, String type, Float price) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
	}


	public Products(String name, String type, Float price) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.components = new ArrayList<Component>();
	}
	


	public Products(String name, Float price) {
		super();
		this.name = name;
		this.price = price;
		this.components = new ArrayList<Component>();

	}

	

	public Products(String type) {
		super();
		this.type = type;
		this.components = new ArrayList<Component>();

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
		Products other = (Products) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Products [id=" + id + ", name=" + name + ", type=" + type + ", price=" + price + ", components="
				+ components + "]";
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Float getPrice() {
		return price;
	}


	public void setPrice(Float price) {
		this.price = price;
	}


	public List<Component> getComponents() {
		return components;
	}


	public void setComponents(List<Component> components) {
		this.components = components;
	}
	
}
