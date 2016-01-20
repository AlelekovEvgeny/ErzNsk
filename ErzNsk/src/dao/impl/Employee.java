package dao.impl;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "person")
@XmlAccessorType (XmlAccessType.FIELD)
public class Employee 
{
	@XmlElement(name = "secondlist")
	private ArrayList<Secondlist> ls;
	
	@Override
	public String toString() {
		return "Employee [ls=" + ls + "]";
	}
	
	public ArrayList<Secondlist> getLs() {
		return ls;
	}
	public void setLs(ArrayList<Secondlist> ls) {
		this.ls = ls;
	}
}
