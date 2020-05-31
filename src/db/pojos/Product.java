package db.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import xml.utils.SQLDateAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Products")
@XmlType(propOrder = {"name", "type", "price", "numberProducts", "components"})
public class Product implements Serializable {
	private static final long serialVersionUID = 1413835970238962198L;

	@XmlTransient
	private Integer id;
	@XmlAttribute
	private String name;
	@XmlElement
	private String type;
	@XmlElement
	private Float price;
	@XmlElement
	private int numberProducts;
	@XmlElement(name = "component")
    @XmlElementWrapper(name = "components")
	private List<Component> components;
	public Product() {
		super();
	}

	
	public Product(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	public Product(Integer id, String name, String type, Float price) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
	}


	public Product(String name, String type, Float price) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.components = new ArrayList<Component>();
	}
	


	public Product(String name, Float price) {
		super();
		this.name = name;
		this.price = price;
		this.components = new ArrayList<Component>();

	}

	

	public Product(String type) {
		super();
		this.type = type;
		this.components = new ArrayList<Component>();

	}


	public Product(int numberProducts) {
		super();
		this.numberProducts = numberProducts;
	}
	
	

	public Product(Integer id, String name, String type, Float price, int numberProducts) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.numberProducts = numberProducts;
	}


	public Product(String name, String type, Float price, int numberProducts) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.numberProducts = numberProducts;
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
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	
	

	/*@Override
	public String toString() {
			return "Products [id=" + id + ", name=" + name + ", type=" + type + ", price=" + price + ", numberProducts="
					+ numberProducts + ", components=" + components + "]";
	}*/


	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", type=" + type + ", price=" + price + ", numberProducts="
				+ numberProducts + "]";
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


	public int getNumberProducts() {
		return numberProducts;
	}


	public void setNumberProducts(int numberProducts) {
		this.numberProducts = numberProducts;
	}


	public List<Component> getComponents() {
		return components;
	}


	public void setComponents(List<Component> components) {
		this.components = components;
	}
}
