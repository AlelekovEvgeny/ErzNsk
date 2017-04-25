package model.other;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ZP3_ALL")
@XmlAccessorType (XmlAccessType.FIELD)
public class WrapZP3Marshaling{
	
	@XmlElement(name = "ZP3")
	private List<ZP3> list = null;
    

    public WrapZP3Marshaling(){}

    public List<ZP3> getList(){
        return list;
    }

	public void setList(List<ZP3> list) {
		this.list = list;
	}
}
