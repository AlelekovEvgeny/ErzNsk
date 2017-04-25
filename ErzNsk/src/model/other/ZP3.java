package model.other;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс, объект которого является частью распарсеного ответа на запрос ZP3 
 * @author pylypiv.sergey
 *
 */

@XmlRootElement(name = "ZP3")
@XmlAccessorType (XmlAccessType.FIELD)
public class ZP3 implements Cloneable {

	public ZP3() {
	}
	
	//@XmlElement(name = "ENP_OUT")
	private String ENP_OUT;
	//@XmlElement(name = "ENP_OUT_old_1")
	private String ENP_OUT_old_1;
	//@XmlElement(name = "NUM_INSUR")
	private String NUM_INSUR;
	//@XmlElement(name = "IN1_12")
	private String IN1_12;
	//@XmlElement(name = "IN1_13")
	private String IN1_13;
	//@XmlElement(name = "OKATO")
	private String OKATO;
	//@XmlElement(name = "TYPE")
	private String TYPE;
	//@XmlElement(name = "NUM_POL")
	private String NUM_POL;
	//@XmlElement(name = "ENP_IN")
	private String ENP_IN;
	
	public String getENP_OUT() {
		return ENP_OUT;
	}
	public void setENP_OUT(String eNP_OUT) {
		ENP_OUT = eNP_OUT;
	}
	public String getNUM_INSUR() {
		return NUM_INSUR;
	}
	public void setNUM_INSUR(String nUM_INSUR) {
		NUM_INSUR = nUM_INSUR;
	}
	public String getIN1_12() {
		return IN1_12;
	}
	public void setIN1_12(String iN1_12) {
		IN1_12 = iN1_12;
	}
	public String getIN1_13() {
		return IN1_13;
	}
	public void setIN1_13(String iN1_13) {
		IN1_13 = iN1_13;
	}
	public String getOKATO() {
		return OKATO;
	}
	public void setOKATO(String oKATO) {
		OKATO = oKATO;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getNUM_POL() {
		return NUM_POL;
	}
	public void setNUM_POL(String nUM_POL) {
		NUM_POL = nUM_POL;
	}
	public String getENP_IN() {
		return ENP_IN;
	}
	public void setENP_IN(String eNP_IN) {
		ENP_IN = eNP_IN;
	}
	
	
	@Override
    public ZP3 clone() throws CloneNotSupportedException {
      return (ZP3)super.clone();
    }
	
	public String getENP_OUT_old_1() {
		return ENP_OUT_old_1;
	}
	public void setENP_OUT_old_1(String eNP_OUT_old_1) {
		ENP_OUT_old_1 = eNP_OUT_old_1;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ZP3 [ENP_OUT=");
		builder.append(ENP_OUT);
		builder.append(", ENP_OUT_old_1=");
		builder.append(ENP_OUT_old_1);
		builder.append(", NUM_INSUR=");
		builder.append(NUM_INSUR);
		builder.append(", IN1_12=");
		builder.append(IN1_12);
		builder.append(", IN1_13=");
		builder.append(IN1_13);
		builder.append(", OKATO=");
		builder.append(OKATO);
		builder.append(", TYPE=");
		builder.append(TYPE);
		builder.append(", NUM_POL=");
		builder.append(NUM_POL);
		builder.append(", ENP_IN=");
		builder.append(ENP_IN);
		builder.append("]");
		return builder.toString();
	}    
	
		
}
	
