package pylypiv.errorGZ.dao;

import java.util.Calendar;

import pylypiv.errorGZ.domain.Person;



public interface PersonDAO {

	public Person getPerson(String enp);

	String getNblanc(String enp, Calendar vs_date);

	int getPerson_docpersonid(int person_docpersonid);

	String getPerson_kindlastname(String person_kindlastname);

	String getSnils(String snils, String person_surname,
			String person_kindfirstname, String person_kindlastname,
			Calendar person_birthday);

	String getRussian(int russian);

	void newEnp(String enp);

}