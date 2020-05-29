package db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class Worker implements Serializable {

	private static final long serialVersionUID = 8022340278172396644L;
	
	private Integer id;
	private String name;
	private String position;
	private Date start_date;
	private String nationality;
	private Integer contract_id;
	private int user_id;
	private List<ContractWorker> contract;
	
	public Worker() {
		super();
	}
	
	public Worker (String name, String position, Date start_date, String nationality, Integer contract_id) {
		super();
		this.name = name;
		this.position = position;
		this.start_date = start_date;
		this.nationality = nationality;
		this.contract_id = contract_id;
	}
	
	
	
	public Worker(Integer id, String name, String position, Date start_date, String nationality, Integer contract_id) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.start_date = start_date;
		this.nationality = nationality;
		this.contract_id = contract_id;
	}

	public Worker(String name, String position, Date start_date, String nationality, Integer contract_id,
			List<ContractWorker> contract) {
		super();
		this.name = name;
		this.position = position;
		this.start_date = start_date;
		this.nationality = nationality;
		this.contract_id = contract_id;
		this.contract = contract;
	}

	public Worker(String name, String position, Date start_date, String nationality) {
		super();
		this.name = name;
		this.position = position;
		this.start_date = start_date;
		this.nationality = nationality;
		
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
		Worker other = (Worker) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Worker [id=" + id + ", name=" + name + ", position=" + position + ", start_date=" + start_date
				+ ", nationality=" + nationality + ", contract_id=" + contract_id + "]";
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
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public Date getStart_date() {
		return start_date;
	}
	
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public Integer getContract_id() {
		return contract_id;
	}
	
	public void setContract_id(Integer contract_id) {
		this.contract_id = contract_id;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
}
