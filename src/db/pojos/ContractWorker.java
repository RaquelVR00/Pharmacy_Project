package db.pojos;

import java.io.Serializable;
import java.util.List;

public class ContractWorker implements Serializable{
	private static final long serialVersionUID = -8915157235521255210L;

	private Integer ID;
	private Double bonus;
	private Double salary;
	private String type;
	private List<Worker> worker;
	
	public ContractWorker() {
		super();
	}
    
	public ContractWorker(Integer id, Double salary, Double bonus, String type) {
		this.ID=id;
		this.salary=salary;
		this.bonus=bonus;
		this.type=type;
	}

	public ContractWorker(Double bonus, Double salary, String type) {
		super();
		this.bonus = bonus;
		this.salary = salary;
		this.type = type;
	}


	public ContractWorker(Double salary, String type) {
		super();
		this.salary = salary;
		this.type = type;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
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
		ContractWorker other = (ContractWorker) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ContractWorker [ID=" + ID + ", bonus=" + bonus + ", salary=" + salary + ", type=" + type + "]";
	}


	public Integer getID() {
		return ID;
	}


	public void setID(Integer iD) {
		ID = iD;
	}


	public Double getBonus() {
		return bonus;
	}


	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}


	public Double getSalary() {
		return salary;
	}


	public void setSalary(Double salary) {
		this.salary = salary;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
	
}
