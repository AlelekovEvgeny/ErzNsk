package pylypiv.errorGZ.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import pylypiv.errorGZ.dao.Factory;
import pylypiv.errorGZ.domain.Person;
import pylypiv.errorGZ.parser.Zp;



public class Data {
	
	private Person person;
	private String user_smo;
	private String user_d_12;
	private String user_d_13;
	private String user_okato_3;
	private String user_person_surname;
	private String user_person_kindfirstname;
	private String user_person_kindlastname;
	private String user_type_pol;
	private String user_pol;
	private String user_serdoc;
	private String user_numdoc;
	private String user_docid;
	private String user_doc_date;
	private String user_enp;
	private String msh10;
	
	public Data(String enp) {
		// достаем все из персона
		this.person = Factory.getInstance().getPersonDAO().getPerson(enp);
		
		
		
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public String getZad() {
		Calendar c = Calendar.getInstance();
		Calendar zad = person.getPersonAdd().getZad();
		c.set(Calendar.YEAR, zad.get(Calendar.YEAR));
		c.set(Calendar.MONTH, zad.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, zad.get(Calendar.DATE));
		String s = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return s;
	}
	
	public String getZadplus40() {
		Calendar c = Calendar.getInstance();
		Calendar zad = person.getPersonAdd().getZad();
		c.set(Calendar.YEAR, zad.get(Calendar.YEAR));
		c.set(Calendar.MONTH, zad.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, zad.get(Calendar.DATE));
		c.add(Calendar.DAY_OF_YEAR, 40);
		String s = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return s;
	}
	
	public String getZadminus1() {
		Calendar c = Calendar.getInstance();
		Calendar zad = person.getPersonAdd().getZad();
		c.set(Calendar.YEAR, zad.get(Calendar.YEAR));
		c.set(Calendar.MONTH, zad.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, zad.get(Calendar.DATE));
		c.add(Calendar.DAY_OF_YEAR, -1);
		String s = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return s;
	}
	
	public String getNlanc() {
		return Factory.getInstance().getPersonDAO().getNblanc(person.getEnp(), person.getPersonAdd().getVs_date());
	}
	
	public String getPerson_birthday() {
		return new SimpleDateFormat("yyyy-MM-dd").format(person.getPerson_birthday().getTime());
	}

	public String getDatepassport() {
		return new SimpleDateFormat("yyyy-MM-dd").format(person.getPersonAdd().getDatepassport().getTime());
	}
	
	public String getD2() {
		if(person.getPersonAdd().getD2() == null) { return ""; }
		else { return new SimpleDateFormat("yyyy-MM-dd").format(person.getPersonAdd().getD2().getTime()); }
	}
	
	public int getPerson_docpersonid() {
		return Factory.getInstance().getPersonDAO().getPerson_docpersonid((person.getPerson_docpersonid()));
	}
	
	public String getPerson_kindlastname() {
		return Factory.getInstance().getPersonDAO().getPerson_kindlastname((person.getPerson_kindlastname()));
	}
	
	public String getSnils() {
		return Factory.getInstance().getPersonDAO().getSnils(person.getPersonAdd().getSnils(), 
				person.getPerson_surname(), person.getPerson_kindfirstname(), person.getPerson_kindlastname(), person.getPerson_birthday());
	}
	
	public String getRussian() {
		return Factory.getInstance().getPersonDAO().getRussian(person.getPersonAdd().getRussian());
	}
	
	public String getPerson_sex() {
		return String.valueOf(Integer.parseInt(person.getPerson_sex()) + 1);
	}

	private Zp zp;
	private List<Zp> zpList;
	
	public List<Zp> getZpList() {
		return zpList;
	}

	public void setZpList(List<Zp> zpList) {
		this.zpList = zpList;
	}

	public Zp getZp() {
		return zp;
	}

	public void setZp(Zp zp) {
		this.zp = zp;
	}
	
	public String getVs_date() {
		Calendar c = Calendar.getInstance();
		Calendar vs_date = person.getPersonAdd().getVs_date();
		c.set(Calendar.YEAR, vs_date.get(Calendar.YEAR));
		c.set(Calendar.MONTH, vs_date.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, vs_date.get(Calendar.DATE));
		String s = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return s;
	}
	
	public String getVs_dateplus1() {
		Calendar c = Calendar.getInstance();
		Calendar vs_date = person.getPersonAdd().getVs_date();
		c.set(Calendar.YEAR, vs_date.get(Calendar.YEAR));
		c.set(Calendar.MONTH, vs_date.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, vs_date.get(Calendar.DATE));
		c.add(Calendar.DAY_OF_YEAR, 1);
		String s = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return s;
	}
	
	public String getVs_dateplus2() {
		Calendar c = Calendar.getInstance();
		Calendar vs_date = person.getPersonAdd().getVs_date();
		c.set(Calendar.YEAR, vs_date.get(Calendar.YEAR));
		c.set(Calendar.MONTH, vs_date.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, vs_date.get(Calendar.DATE));
		c.add(Calendar.DAY_OF_YEAR, 2);
		String s = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return s;
	}

	public String getUser_smo() {
		return user_smo;
	}

	public void setUser_smo(String user_smo) {
		this.user_smo = user_smo;
	}

	public String getUser_d_12() {
		return user_d_12;
	}

	public void setUser_d_12(String user_d_12) {
		this.user_d_12 = user_d_12;
	}

	public String getUser_d_13() {
		return user_d_13;
	}

	public void setUser_d_13(String user_d_13) {
		this.user_d_13 = user_d_13;
	}

	public String getUser_okato_3() {
		return user_okato_3;
	}

	public void setUser_okato_3(String user_okato_3) {
		this.user_okato_3 = user_okato_3;
	}

	public String getUser_person_surname() {
		return user_person_surname;
	}

	public void setUser_person_surname(String user_person_surname) {
		this.user_person_surname = user_person_surname;
	}

	public String getUser_person_kindfirstname() {
		return user_person_kindfirstname;
	}

	public void setUser_person_kindfirstname(String user_person_kindfirstname) {
		this.user_person_kindfirstname = user_person_kindfirstname;
	}

	public String getUser_person_kindlastname() {
		return user_person_kindlastname;
	}

	public void setUser_person_kindlastname(String user_person_kindlastname) {
		this.user_person_kindlastname = user_person_kindlastname;
	}

	public String getUser_type_pol() {
		return user_type_pol;
	}

	public void setUser_type_pol(String user_type_pol) {
		this.user_type_pol = user_type_pol;
	}

	public String getUser_pol() {
		return user_pol;
	}

	public void setUser_pol(String user_pol) {
		this.user_pol = user_pol;
	}

	public String getUser_serdoc() {
		return user_serdoc;
	}

	public void setUser_serdoc(String user_serdoc) {
		this.user_serdoc = user_serdoc;
	}

	public String getUser_numdoc() {
		return user_numdoc;
	}

	public void setUser_numdoc(String user_numdoc) {
		this.user_numdoc = user_numdoc;
	}

	public String getUser_docid() {
		return user_docid;
	}

	public void setUser_docid(String user_docid) {
		this.user_docid = user_docid;
	}

	public String getUser_doc_date() {
		return user_doc_date;
	}

	public void setUser_doc_date(String user_doc_date) {
		this.user_doc_date = user_doc_date;
	}

	public String getUser_enp() {
		return user_enp;
	}

	public void setUser_enp(String user_enp) {
		this.user_enp = user_enp;
	}
	
	public String getLast_dr() {
		if(person.getPersonAdd().getLast_dr() == null) { return ""; }
		else { return new SimpleDateFormat("yyyy-MM-dd").format(person.getPersonAdd().getLast_dr().getTime()); }
	}
	
	public String getZpIn1_12() {
		if(zp.getIn1_12() == null) { return ""; }
		else { return new SimpleDateFormat("yyyy-MM-dd").format(zp.getIn1_12().getTime()); }
	}
	
	public String getZpIn1_12plus1() {
		Calendar c = Calendar.getInstance();
		Calendar zpIn1_12 = zp.getIn1_12();
		c.set(Calendar.YEAR, zpIn1_12.get(Calendar.YEAR));
		c.set(Calendar.MONTH, zpIn1_12.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, zpIn1_12.get(Calendar.DATE));
		c.add(Calendar.DAY_OF_YEAR, 1);
		String s = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return s;
	}
	
	public String getZpIn1_13() {
		if(zp.getIn1_13() == null) { return ""; }
		else { return new SimpleDateFormat("yyyy-MM-dd").format(zp.getIn1_13().getTime()); }
	}
	
	public String getZpPid7() {
		if(zp.getPid7() == null) { return ""; }
		else { return new SimpleDateFormat("yyyy-MM-dd").format(zp.getPid7().getTime()); }
	}
	
	public String getZpDateinput() {
		return new SimpleDateFormat("yyyy-MM-dd").format(zp.getDateinput().getTime());
	}
	
	public void newEnp() {
		Factory.getInstance().getPersonDAO().newEnp(person.getEnp());
	}
	
	public void updatePerson() {
		this.person = Factory.getInstance().getPersonDAO().getPerson(person.getEnp());
	}
	
	public void wrongUdl() {
		switch((null == this.person.getPerson_serdoc()) ? "" : this.person.getPerson_serdoc()) {
			case "I-ЕТ" : this.person.setPerson_serdoc("I-ET"); break;
			case "II-ЕТ" : this.person.setPerson_serdoc("II-ET"); break;
			case "III-ЕТ" : this.person.setPerson_serdoc("III-ET"); break;
			default : this.person.setPerson_serdoc("77 01"); break;
		}
	}
	
	public void fillEnpPa() {
		this.getPerson().getPersonAdd().setEnp(this.getPerson().getEnp());
	}
	
	public void increaseZad() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -30);
		person.getPersonAdd().setZad(c);
	}

	@Override
	public String toString() {
		return "Data [person=" + person + ", user_smo=" + user_smo
				+ ", user_d_12=" + user_d_12 + ", user_d_13=" + user_d_13
				+ ", user_okato_3=" + user_okato_3 + ", user_person_surname="
				+ user_person_surname + ", user_person_kindfirstname="
				+ user_person_kindfirstname + ", user_person_kindlastname="
				+ user_person_kindlastname + ", user_type_pol=" + user_type_pol
				+ ", user_pol=" + user_pol + ", user_serdoc=" + user_serdoc
				+ ", user_numdoc=" + user_numdoc + ", user_docid=" + user_docid
				+ ", user_doc_date=" + user_doc_date + ", user_enp=" + user_enp
				+ ", zp=" + zp + "]";
	}

	public String getMsh10() {
		return msh10;
	}

	public void setMsh10(String msh10) {
		this.msh10 = msh10;
	}
	
}
