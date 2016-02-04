package pylypiv.errorGZ.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSONADD")
public class PersonAdd {
	
	@Id@Column(name="PERSONADD_ADDRESSID") private Integer personadd_addressid;
	@Column(name="PERSONADD_PRIM") private String personadd_prim;
	@Column(name="SNILS") private String snils;
	@Column(name="BORN") private String born;
	@Column(name="DATEPASSPORT") private Calendar datepassport;
	@Column(nullable = true, name="RUSSIAN") private Integer russian;
	@Column(name="TELEDOM") private String teledom;
	@Column(name="TELEWORK") private String telework;
	@Column(name="EMAIL") private String email;
	@Column(name="TELE2") private String tele2;
	@Column(name="DOK_VI") private String dok_vi;
	@Column(name="ENP") private String enp;
	@Column(nullable = true, name="ZA") private Integer za;
	@Column(name="ZAD") private Calendar zad;
	@Column(nullable = true, name="ZAP") private Integer zap;
	@Column(nullable = true, name="PRED") private Integer pred;
	@Column(nullable = true, name="D_V") private Integer d_v;
	@Column(name="D_SER") private String d_ser;
	@Column(name="D_NUM") private String d_num;
	@Column(name="D_DATE") private Calendar d_date;
	@Column(nullable = true, name="METHOD") private Integer method;
	@Column(nullable = true, name="PETITION") private Integer petition;
	@Column(nullable = true, name="FPOLIC") private Integer fpolic;
	@Column(name="PR_FAM") private String pr_fam;
	@Column(name="PR_IM") private String pr_im;
	@Column(name="PR_OT") private String pr_ot;
	@Column(name="PR_TEL") private String pr_tel;
	@Column(name="PR_ADRES") private String pr_adres;
	@Column(name="VS_NUM") private String vs_num;
	@Column(name="VS_DATE") private Calendar vs_date;
	@Column(name="D1") private Calendar d1;
	@Column(name="D2") private Calendar d2;
	@Column(name="ENP_DATE") private Calendar enp_date;
	@Column(name="LAST_FAM") private String last_fam;
	@Column(name="LAST_IM") private String last_im;
	@Column(name="LAST_OT") private String last_ot;
	@Column(name="LAST_DR") private Calendar last_dr;
	@Column(nullable = true, name="KATEG") private Integer kateg;
	@Column(name="DATE_PRIK") private Calendar date_prik;
	
	public int getPersonadd_addressid() {
		return personadd_addressid;
	}
	public void setPersonadd_addressid(int personadd_addressid) {
		this.personadd_addressid = personadd_addressid;
	}
	public String getPersonadd_prim() {
		return personadd_prim;
	}
	public void setPersonadd_prim(String personadd_prim) {
		this.personadd_prim = personadd_prim;
	}
	public String getSnils() {
		return snils;
	}
	public void setSnils(String snils) {
		this.snils = snils;
	}
	public String getBorn() {
		return born;
	}
	public void setBorn(String born) {
		this.born = born;
	}
	public Calendar getDatepassport() {
		return datepassport;
	}
	public void setDatepassport(Calendar datepassport) {
		this.datepassport = datepassport;
	}
	public int getRussian() {
		return russian;
	}
	public void setRussian(int russian) {
		this.russian = russian;
	}
	public String getTeledom() {
		return teledom;
	}
	public void setTeledom(String teledom) {
		this.teledom = teledom;
	}
	public String getTelework() {
		return telework;
	}
	public void setTelework(String telework) {
		this.telework = telework;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTele2() {
		return tele2;
	}
	public void setTele2(String tele2) {
		this.tele2 = tele2;
	}
	public String getDok_vi() {
		return dok_vi;
	}
	public void setDok_vi(String dok_vi) {
		this.dok_vi = dok_vi;
	}
	public String getEnp() {
		return enp;
	}
	public void setEnp(String enp) {
		this.enp = enp;
	}
	public int getZa() {
		return za;
	}
	public void setZa(int za) {
		this.za = za;
	}
	public Calendar getZad() {
		return zad;
	}
	public void setZad(Calendar zad) {
		this.zad = zad;
	}
	public int getZap() {
		return zap;
	}
	public void setZap(int zap) {
		this.zap = zap;
	}
	public int getPred() {
		return pred;
	}
	public void setPred(int pred) {
		this.pred = pred;
	}
	public int getD_v() {
		return d_v;
	}
	public void setD_v(int d_v) {
		this.d_v = d_v;
	}
	public String getD_ser() {
		return d_ser;
	}
	public void setD_ser(String d_ser) {
		this.d_ser = d_ser;
	}
	public String getD_num() {
		return d_num;
	}
	public void setD_num(String d_num) {
		this.d_num = d_num;
	}
	public Calendar getD_date() {
		return d_date;
	}
	public void setD_date(Calendar d_date) {
		this.d_date = d_date;
	}
	public int getMethod() {
		return method;
	}
	public void setMethod(int method) {
		this.method = method;
	}
	public int getPetition() {
		return petition;
	}
	public void setPetition(int petition) {
		this.petition = petition;
	}
	public int getFpolic() {
		return fpolic;
	}
	public void setFpolic(int fpolic) {
		this.fpolic = fpolic;
	}
	public String getPr_fam() {
		return pr_fam;
	}
	public void setPr_fam(String pr_fam) {
		this.pr_fam = pr_fam;
	}
	public String getPr_im() {
		return pr_im;
	}
	public void setPr_im(String pr_im) {
		this.pr_im = pr_im;
	}
	public String getPr_ot() {
		return pr_ot;
	}
	public void setPr_ot(String pr_ot) {
		this.pr_ot = pr_ot;
	}
	public String getPr_tel() {
		return pr_tel;
	}
	public void setPr_tel(String pr_tel) {
		this.pr_tel = pr_tel;
	}
	public String getPr_adres() {
		return pr_adres;
	}
	public void setPr_adres(String pr_adres) {
		this.pr_adres = pr_adres;
	}
	public String getVs_num() {
		return vs_num;
	}
	public void setVs_num(String vs_num) {
		this.vs_num = vs_num;
	}
	public Calendar getVs_date() {
		return vs_date;
	}
	public void setVs_date(Calendar vs_date) {
		this.vs_date = vs_date;
	}
	public Calendar getD1() {
		return d1;
	}
	public void setD1(Calendar d1) {
		this.d1 = d1;
	}
	public Calendar getD2() {
		return d2;
	}
	public void setD2(Calendar d2) {
		this.d2 = d2;
	}
	public Calendar getEnp_date() {
		return enp_date;
	}
	public void setEnp_date(Calendar enp_date) {
		this.enp_date = enp_date;
	}
	public String getLast_fam() {
		return last_fam;
	}
	public void setLast_fam(String last_fam) {
		this.last_fam = last_fam;
	}
	public String getLast_im() {
		return last_im;
	}
	public void setLast_im(String last_im) {
		this.last_im = last_im;
	}
	public String getLast_ot() {
		return last_ot;
	}
	public void setLast_ot(String last_ot) {
		this.last_ot = last_ot;
	}
	public Calendar getLast_dr() {
		return last_dr;
	}
	public void setLast_dr(Calendar last_dr) {
		this.last_dr = last_dr;
	}
	public int getKateg() {
		return kateg;
	}
	public void setKateg(int kateg) {
		this.kateg = kateg;
	}
	public Calendar getDate_prik() {
		return date_prik;
	}
	public void setDate_prik(Calendar date_prik) {
		this.date_prik = date_prik;
	}
	
}
