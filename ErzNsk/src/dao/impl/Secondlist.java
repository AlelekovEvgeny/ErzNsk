package dao.impl;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "secondlist")
@XmlAccessorType (XmlAccessType.FIELD)
public class Secondlist {

	private ArrayList<String> second;

	public ArrayList<String> getSecond() {
		return second;
	}

	public void setSecond(ArrayList<String> second) {
		this.second = second;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SecondList [second=");
		builder.append(second);
		builder.append("]");
		return builder.toString();
	}
	
}
