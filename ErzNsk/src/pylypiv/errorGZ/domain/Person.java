package pylypiv.errorGZ.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
public class Person {
	
	@Column(name = "PERSON_SURNAME") private String person_surname ;
	@Column(name = "PERSON_KINDFIRSTNAME") private String person_kindfirstname ;
	@Column(name = "PERSON_KINDLASTNAME") private String person_kindlastname ;
	@Column(name = "PERSON_BIRTHDAY") private Calendar person_birthday ;
	@Column(nullable = true, name = "PERSON_ADDRESSID") private Integer person_addressid ;
	@Column(name = "PERSON_SERPOLICY") private String person_serpolicy ;
	@Column(name = "PERSON_NUMPOLICY") private String person_numpolicy ;
	@Column(name = "PERSON_SEX") private String person_sex ;
	@Column(name = "PERSON_SERDOC") private String person_serdoc ;
	@Column(name = "PERSON_NUMDOC") private String person_numdoc ;
	@Column(name = "PERSON_REGNUMBER") private String person_regnumber ;
	@Column(nullable = true, name = "PERSON_LINKSMOESTABLISHMENTID") private Integer person_linksmoestablishmentid ;
	@Column(nullable = true, name = "PERSON_ESTABLISHMENTAMBUL") private Integer person_establishmentambul ;
	@Column(nullable = true, name = "PERSON_DATECHANGE") private Calendar person_datechange ;
	@Column(nullable = true, name = "PERSON_ESTABLISHMENTDENT") private Integer person_establishmentdent ;
	@Column(nullable = true, name = "PERSON_SOCIALID") private Integer person_socialid ;
	@Column(nullable = true, name = "PERSON_STATUSID") private Integer person_statusid ;
	@Column(nullable = true, name = "PERSON_DOCPERSONID") private Integer person_docpersonid ;
	@Column(nullable = true, name = "PERSON_INSPECTION") private Integer person_inspection ;
	@Column(nullable = true, name = "PERSON_OPERATION") private Integer person_operation ;
	@Column(nullable = true, name = "PERSON_STATUSREC") private Integer person_statusrec ;
	@Column(nullable = true, name = "PERSON_OUTID") private Integer person_outid ;
	@Column(nullable = true, name = "PERSON_INSPECTORID") private Integer person_inspectorid ;
	@Column(nullable = true, name = "PERSON_ESTABLISHMENTID") private Integer person_establishmentid ;
	@Column(name = "PERSON_DATEPOLICY") private Calendar person_datepolicy ;
	@Column(name = "PERSON_DATEINPUT") private Calendar person_dateinput ;
	@Id@Column(name = "ENP") private String enp ;
	@Column(nullable = true, name = "SMO_OLD") private Integer smo_old ;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="PERSON_ADDRESSID", referencedColumnName = "PERSONADD_ADDRESSID", insertable = false, updatable = false)
	private PersonAdd personAdd;
	
	public void setPersonAdd(PersonAdd personAdd) {
		this.personAdd = personAdd;
	}
	
	public PersonAdd getPersonAdd() {
	        return personAdd;
	}

	public String getPerson_surname() {
		return person_surname;
	}

	public void setPerson_surname(String person_surname) {
		this.person_surname = person_surname;
	}

	public String getPerson_kindfirstname() {
		return person_kindfirstname;
	}

	public void setPerson_kindfirstname(String person_kindfirstname) {
		this.person_kindfirstname = person_kindfirstname;
	}

	public String getPerson_kindlastname() {
		return person_kindlastname;
	}

	public void setPerson_kindlastname(String person_kindlastname) {
		this.person_kindlastname = person_kindlastname;
	}

	public Calendar getPerson_birthday() {
		return person_birthday;
	}

	public void setPerson_birthday(Calendar person_birthday) {
		this.person_birthday = person_birthday;
	}

	public int getPerson_addressid() {
		return person_addressid;
	}

	public void setPerson_addressid(int person_addressid) {
		this.person_addressid = person_addressid;
	}

	public String getPerson_serpolicy() {
		return person_serpolicy;
	}

	public void setPerson_serpolicy(String person_serpolicy) {
		this.person_serpolicy = person_serpolicy;
	}

	public String getPerson_numpolicy() {
		return person_numpolicy;
	}

	public void setPerson_numpolicy(String person_numpolicy) {
		this.person_numpolicy = person_numpolicy;
	}

	public String getPerson_sex() {
		return person_sex;
	}

	public void setPerson_sex(String person_sex) {
		this.person_sex = person_sex;
	}

	public String getPerson_serdoc() {
		return person_serdoc;
	}

	public void setPerson_serdoc(String person_serdoc) {
		this.person_serdoc = person_serdoc;
	}

	public String getPerson_numdoc() {
		return person_numdoc;
	}

	public void setPerson_numdoc(String person_numdoc) {
		this.person_numdoc = person_numdoc;
	}

	public String getPerson_regnumber() {
		return person_regnumber;
	}

	public void setPerson_regnumber(String person_regnumber) {
		this.person_regnumber = person_regnumber;
	}

	public Integer getPerson_linksmoestablishmentid() {
		return person_linksmoestablishmentid;
	}

	public void setPerson_linksmoestablishmentid(
			int person_linksmoestablishmentid) {
		this.person_linksmoestablishmentid = person_linksmoestablishmentid;
	}

	public int getPerson_establishmentambul() {
		return person_establishmentambul;
	}

	public void setPerson_establishmentambul(int person_establishmentambul) {
		this.person_establishmentambul = person_establishmentambul;
	}

	public Calendar getPerson_datechange() {
		return person_datechange;
	}

	public void setPerson_datechange(Calendar person_datechange) {
		this.person_datechange = person_datechange;
	}

	public int getPerson_establishmentdent() {
		return person_establishmentdent;
	}

	public void setPerson_establishmentdent(int person_establishmentdent) {
		this.person_establishmentdent = person_establishmentdent;
	}

	public int getPerson_socialid() {
		return person_socialid;
	}

	public void setPerson_socialid(int person_socialid) {
		this.person_socialid = person_socialid;
	}

	public int getPerson_statusid() {
		return person_statusid;
	}

	public void setPerson_statusid(int person_statusid) {
		this.person_statusid = person_statusid;
	}

	public int getPerson_docpersonid() {
		return person_docpersonid;
	}

	public void setPerson_docpersonid(int person_docpersonid) {
		this.person_docpersonid = person_docpersonid;
	}

	public int getPerson_inspection() {
		return person_inspection;
	}

	public void setPerson_inspection(int person_inspection) {
		this.person_inspection = person_inspection;
	}

	public int getPerson_operation() {
		return person_operation;
	}

	public void setPerson_operation(int person_operation) {
		this.person_operation = person_operation;
	}

	public int getPerson_statusrec() {
		return person_statusrec;
	}

	public void setPerson_statusrec(int person_statusrec) {
		this.person_statusrec = person_statusrec;
	}

	public int getPerson_outid() {
		return person_outid;
	}

	public void setPerson_outid(int person_outid) {
		this.person_outid = person_outid;
	}

	public int getPerson_inspectorid() {
		return person_inspectorid;
	}

	public void setPerson_inspectorid(int person_inspectorid) {
		this.person_inspectorid = person_inspectorid;
	}

	public int getPerson_establishmentid() {
		return person_establishmentid;
	}

	public void setPerson_establishmentid(int person_establishmentid) {
		this.person_establishmentid = person_establishmentid;
	}

	public Calendar getPerson_datepolicy() {
		return person_datepolicy;
	}

	public void setPerson_datepolicy(Calendar person_datepolicy) {
		this.person_datepolicy = person_datepolicy;
	}

	public Calendar getPerson_dateinput() {
		return person_dateinput;
	}

	public void setPerson_dateinput(Calendar person_dateinput) {
		this.person_dateinput = person_dateinput;
	}

	public String getEnp() {
		return enp;
	}

	public void setEnp(String enp) {
		this.enp = enp;
	}

	public int getSmo_old() {
		return smo_old;
	}

	public void setSmo_old(int smo_old) {
		this.smo_old = smo_old;
	}

	@Override
	public String toString() {
		return "Person [person_surname=" + person_surname
				+ ", person_kindfirstname=" + person_kindfirstname
				+ ", person_kindlastname=" + person_kindlastname
				+ ", person_birthday=" + person_birthday
				+ ", person_addressid=" + person_addressid
				+ ", person_serpolicy=" + person_serpolicy
				+ ", person_numpolicy=" + person_numpolicy + ", person_sex="
				+ person_sex + ", person_serdoc=" + person_serdoc
				+ ", person_numdoc=" + person_numdoc + ", person_regnumber="
				+ person_regnumber + ", person_linksmoestablishmentid="
				+ person_linksmoestablishmentid
				+ ", person_establishmentambul=" + person_establishmentambul
				+ ", person_datechange=" + person_datechange
				+ ", person_establishmentdent=" + person_establishmentdent
				+ ", person_socialid=" + person_socialid + ", person_statusid="
				+ person_statusid + ", person_docpersonid="
				+ person_docpersonid + ", person_inspection="
				+ person_inspection + ", person_operation=" + person_operation
				+ ", person_statusrec=" + person_statusrec + ", person_outid="
				+ person_outid + ", person_inspectorid=" + person_inspectorid
				+ ", person_establishmentid=" + person_establishmentid
				+ ", person_datepolicy=" + person_datepolicy
				+ ", person_dateinput=" + person_dateinput + ", enp=" + enp
				+ ", smo_old=" + smo_old + ", personAdd=" + personAdd + "]";
	}
	
}
