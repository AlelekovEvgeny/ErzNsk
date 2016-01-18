package dao.impl;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "employee")
@XmlAccessorType (XmlAccessType.FIELD)
public class Employee 
{
	private Integer id;
	private String firstName;
	private String lastName;
	private double income;
	@XmlElement(name = "secondlist")
	private ArrayList<Secondlist> ls;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Employee [id=");
		builder.append(id);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", income=");
		builder.append(income);
		builder.append(", ls=");
		builder.append(ls);
		builder.append("]");
		return builder.toString();
	}
	public ArrayList<Secondlist> getLs() {
		return ls;
	}
	public void setLs(ArrayList<Secondlist> ls) {
		this.ls = ls;
	}
}
