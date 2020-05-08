package db.pojos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;



public class Pharmacy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5227205464238664146L;
     
	private Integer id;
	private String name;
	private Integer contract_pid;
	private String location;
	
	public Pharmacy(){
		super();
	}
	
	public Pharmacy(String name, Integer contract_pid, String location){
		super();
		this.name = name;
		this.contract_pid =contract_pid;
		this.location = location;
	}
	
	public Pharmacy(Integer id, String name, Integer contract_pid, String location) {
		super();
		this.id = id;
		this.name = name;
		this.contract_pid = contract_pid;
		this.location = location;
	}

	public Pharmacy(String name,String location) {
		super();
		this.name= name;
		this.location=location;
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
		Pharmacy other = (Pharmacy) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Pharmacy [id=" + id + ", name=" + name + ",contrac_pid= " + contract_pid +
				", location=" + location + "]";
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

	public Integer getContract_pid() {
		return contract_pid;
	}

	public void setContract_pid(Integer contract_pid) {
		this.contract_pid = contract_pid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


}
