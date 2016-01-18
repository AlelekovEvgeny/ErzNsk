package model.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "db")
@XmlAccessorType (XmlAccessType.FIELD)
public class Db 
{

	private List<String> list = new ArrayList<String>();

	public List<String> getList() {
		return list;
	}
	
	public void setList(List<String> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Db [list=");
		builder.append(list);
		builder.append("]");
		return builder.toString();
	}
}
