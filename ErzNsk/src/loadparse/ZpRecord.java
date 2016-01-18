package loadparse;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;





@Entity
@Table(name="Xml_Person_Enp_Input")
public class ZpRecord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String MSA_2;
	private String PID_3_CX_1_1;
	private String PID_3_CX_1_2;
	private String okato2;
	private String IN1_3_CX_1;	
	private String IN1_12;
	private String IN1_13;
	private String IN1_15;
	private String IN1_35;
	private String IN1_36;
	private String qri1;
	private String qri2;
	private String qri3;
	private String qri4;
	private int npp;
	private String dinput;
	private String PID7;
	private String PID29;
	   private String PID8;
		
    @Column(name="PID7")
	public String getPID7() {
		return PID7;
	}

	public void setPID7(String PID7) {
		this.PID7 = PID7;
	}
	
    @Column(name="QRI_1")
	public String getQri1() {
		return qri1;
	}

	public void setQri1(String qri1) {
		this.qri1 = qri1;
	}

    @Column(name="QRI_2")
	public String getQri2() {
		return qri2;
	}

	public void setQri2(String qri2) {
		this.qri2 = qri2;
	}

    @Column(name="QRI_3")
	public String getQri3() {
		return qri3;
	}

	public void setQri3(String qri3) {
		this.qri3 = qri3;
	}

    @Column(name="QRI_4")
	public String getQri4() {
		return qri4;
	}

	public void setQri4(String qri4) {
		this.qri4 = qri4;
	}

	@Id
	@Column(name="NPP")
	public int getNpp() {
		return npp;
	}

	public void setNpp(int npp) {
		this.npp = npp;
	}

    @Column(name="D_INPUT")
	public String getDinput() {
		return dinput;
	}

	public void setDinput(String dinput) {
		this.dinput = dinput;
	}

	public void setMSA_2(String MSA_2) {
		this.MSA_2 = MSA_2;
	}
	
	public void setPID_3_CX_1_1(String PID_3_CX_1_1) {
		this.PID_3_CX_1_1 = PID_3_CX_1_1;
	}
	
	public void setPID_3_CX_1_2(String PID_3_CX_1_2) {
		this.PID_3_CX_1_2 = PID_3_CX_1_2;
	}
	
	public void setOkato2(String okato2) {
		this.okato2 = okato2;
	}
	
	public void setIN1_3_CX_1(String IN1_3_CX_1) {
		this.IN1_3_CX_1 = IN1_3_CX_1;
	}
		
	public void setIN1_12(String IN1_12) {
		this.IN1_12 = IN1_12;
	}
	
	public void setIN1_13(String IN1_13) {
		this.IN1_13 = IN1_13;
	}
	
	public void setIN1_15(String IN1_15) {
		this.IN1_15 = IN1_15;
	}
	
	public void setIN1_35(String IN1_35) {
		this.IN1_35 = IN1_35;
	}
	
	public void setIN1_36(String IN1_36) {
		this.IN1_36 = IN1_36;
	}	

	@Id
    @Column(name="GD")
	public String getMSA_2() {
		return this.MSA_2;
	}

    @Column(name="ENP_1")
	public String getPID_3_CX_1_1() {
		return this.PID_3_CX_1_1;
	}	
	
    @Column(name="ENP_2")
	public String getPID_3_CX_1_2() {
		return this.PID_3_CX_1_2;
	}	
	
    @Column(name="OKATO_2")
	public String getOkato2() {
		return this.okato2;
	}	
	
    @Column(name="SMO")
	public String getIN1_3_CX_1() {
		return this.IN1_3_CX_1;
	}
	
    @Column(name="D_12")
	public String getIN1_12() {
		return this.IN1_12;
	}			
	
    @Column(name="D_13")
	public String getIN1_13() {
		return this.IN1_13;
	}
	
    @Column(name="OKATO_3")
	public String getIN1_15() {
		return this.IN1_15;
	}
	
    @Column(name="TYPE_POL")
	public String getIN1_35() {
		return this.IN1_35;
	}
	
    @Column(name="POL")
	public String getIN1_36() {
		return this.IN1_36;
	}
    
 
	
    @Column(name="PID8")
	public String getPID8() {
		return PID8;
	}

	public void setPID8(String PID8) {
		this.PID8 = PID8;
	}
	
	
	
		
	    @Column(name="PID29")
		public String getPID29() {
			return PID29;
		}

		public void setPID29(String PID29) {
			this.PID29 = PID29;
		}

		@Override
		public String toString() {
			return "ZpRecord [MSA_2=" + MSA_2 + ", PID_3_CX_1_1="
					+ PID_3_CX_1_1 + ", PID_3_CX_1_2=" + PID_3_CX_1_2
					+ ", okato2=" + okato2 + ", IN1_3_CX_1=" + IN1_3_CX_1
					+ ", IN1_12=" + IN1_12 + ", IN1_13=" + IN1_13 + ", IN1_15="
					+ IN1_15 + ", IN1_35=" + IN1_35 + ", IN1_36=" + IN1_36
					+ ", qri1=" + qri1 + ", qri2=" + qri2 + ", qri3=" + qri3
					+ ", qri4=" + qri4 + ", npp=" + npp + ", dinput=" + dinput
					+ ", PID7=" + PID7 + ", PID29=" + PID29 + ", PID8=" + PID8
					+ "]";
		}


}
