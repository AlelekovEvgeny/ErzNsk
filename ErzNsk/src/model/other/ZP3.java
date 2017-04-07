package model.other;

/**
 * Класс, объект которого является частью распарсеного ответа на запрос ZP3 
 * @author pylypiv.sergey
 *
 */
public class ZP3 {

	public ZP3() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private String ENP_OUT;
	private String NUM_INSUR;
	private String IN1_12;
	private String IN1_13;
	private String OKATO;
	private String TYPE;
	private String NUM_POL;
	
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ZP3 [ENP_OUT=");
		builder.append(ENP_OUT);
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
		builder.append("]");
		return builder.toString();
	}
	
}
	
