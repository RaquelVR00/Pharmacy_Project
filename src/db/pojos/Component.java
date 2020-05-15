package db.pojos;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import xml.utils.SQLDateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "name", "price", "supplier", "numberComponents"})


public class Component implements Serializable {

	private static final long serialVersionUID = -6472568147682931329L;
	
	@XmlAttribute
	private Integer id;
	@XmlAttribute
	private String name;
	@XmlElement
	private Float price;
	@XmlElement
	private String supplier;
	@XmlElement
	private int numberComponents;
	@XmlTransient
	private List<Products> products;
	
	public Component() {
		super();
		products = new ArrayList<Products>();
		// TODO Auto-generated constructor stub
	}

	public Component(String name, Float price, String supplier) {
		super();
		this.name = name;
		this.price = price;
		this.supplier = supplier;
		products = new ArrayList<Products>();

	}
	
	
	public Component(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
		products = new ArrayList<Products>();

	}

	public Component(Integer id, String name, Float price, String supplier) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.supplier = supplier;
		products = new ArrayList<Products>();

	}
	

	public Component(Integer id, String name, Float price, String supplier, List<Products> products) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.supplier = supplier;
		this.products = products;
	}
	
	
	public Component(Integer id, String name, Float price, String supplier, int numberComponents) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.supplier = supplier;
		this.numberComponents = numberComponents;
	}

	public Component(String name, Float price, String supplier, int numberComponents) {
		super();
		this.name = name;
		this.price = price;
		this.supplier = supplier;
		this.numberComponents = numberComponents;
	}

	public Component(Integer numberComponents) {
		super();
		this.numberComponents = numberComponents;
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

	

	/*public Component(String name) {
		super();
		this.name = name;
	}*/

	@Override
	public String toString() {
		return "Component [id=" + id + ", name=" + name + ", price=" + price + ", supplier=" + supplier
				+ ", numberComponents=" + numberComponents + "]";
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

	public int getNumberComponents() {
		return numberComponents;
	}

	public void setNumberComponents(Integer numberComponents) {
		this.numberComponents = numberComponents;
	}

	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}	
}
