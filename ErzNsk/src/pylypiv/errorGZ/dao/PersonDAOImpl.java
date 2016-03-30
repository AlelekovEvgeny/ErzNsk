package pylypiv.errorGZ.dao;

import java.util.Calendar;

import org.hibernate.Query;
import org.hibernate.Session;

import pylypiv.errorGZ.domain.Person;
import util.HibernateUtil;


public class PersonDAOImpl implements PersonDAO {

	@Override
	public Person getPerson(String enp) {
		Session session = null;
		Query query = null;
		Person p = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            query = session.createQuery("from Person where enp = :enp");
            query.setParameter("enp", enp);
            p = (Person) query.list().get(0);
        } catch (Exception e) {
            System.out.println("Hibernate Œ¯Ë·Í‡ getPerson " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
                
            }
        }
        return p;
	}
	
	@Override
	public String getNblanc(String enp, Calendar vs_date) {
		Session session = null;
		Query query = null;
		String s = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            query = session.createSQLQuery("{? = call ERZ.n_blank (:enp, :vs_date)}");
            query.setParameter("enp", enp);
            query.setParameter("vs_date", vs_date);
            s = (String) query.uniqueResult();
        } catch (Exception e) {
        	System.out.println("Hibernate Œ¯Ë·Í‡ getNblanc " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return s;
	}
	
	@Override
	public int getPerson_docpersonid(int person_docpersonid) {
		Session session = null;
		Query query = null;
		int s = 0;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            query = session.createSQLQuery("select nvl((select ffoms_id from developer.docffoms d where d.docperson_id = :person_docpersonid ), 18) from dual");
            query.setParameter("person_docpersonid", person_docpersonid);
            s = Integer.parseInt(query.uniqueResult().toString());
        } catch (Exception e) {
        	System.out.println("Hibernate Œ¯Ë·Í‡ getPerson_docpersonid " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return s;
	}
	
	@Override
	public String getPerson_kindlastname(String person_kindlastname) {
		Session session = null;
		Query query = null;
		String s = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            query = session.createSQLQuery("select rtrim(case when ((:person_kindlastname = 'Õ≈“') or (:person_kindlastname = '-')) then null else rtrim(:person_kindlastname,'-') end,'-') from dual");
            query.setParameter("person_kindlastname", person_kindlastname);
            s = (String) query.uniqueResult();
        } catch (Exception e) {
        	System.out.println("Hibernate Œ¯Ë·Í‡ getPerson_kindlastname " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return s;
	}
	 
	@Override
	public String getSnils(String snils, String person_surname, String person_kindfirstname, String person_kindlastname, Calendar person_birthday) {
		Session session = null;
		Query query = null;
		String s = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            query = session.createSQLQuery("select nvl((case  when (:snils is not null) and length(replace(replace(:snils,'-'),' ')) = 11 and pfr.kod_ss@pfr(substr(replace(replace(:snils,'-'),' '),1,9)) =  substr(replace(replace(:snils,'-'),' '),10,2) then replace(replace(:snils,'-'),' ') else null end), replace(replace(pfr.fiod_ss@pfr(:person_surname, :person_kindfirstname, :person_kindlastname, :person_birthday),'-'),' ')) from dual");
            query.setParameter("snils", snils);
            query.setParameter("person_surname", person_surname);
            query.setParameter("person_kindfirstname", person_kindfirstname);
            query.setParameter("person_kindlastname", person_kindlastname);
            query.setParameter("person_birthday", person_birthday);
            s = (String) query.uniqueResult();
        } catch (Exception e) {
        	System.out.println("Hibernate Œ¯Ë·Í‡ getSnils " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return s;
	}
		
	@Override
	public String getRussian(int russian) {
		Session session = null;
		Query query = null;
		String s = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            query = session.createSQLQuery("select nvl((select alfa3 from developer.oksm o where o.kod = :russian and rownum = 1),'RUS') from dual");
            query.setParameter("russian", russian);
            s = (String) query.uniqueResult();
        } catch (Exception e) {
        	System.out.println("Hibernate Œ¯Ë·Í‡ getRussian " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return s;
	}
	
	@Override
	public void  newEnp(String enp) {
		Session session = null;
		Query query = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            query = session.createSQLQuery("update personadd pa set pa.enp = (select  enp.calc_kr('54'|| enp.calc_mmggggdd(persoN_birthday,person_sex + 1) || (enp.calc_nnnnn(person_birthday,person_sex + 1))) from person pp where pp.person_addressid = pa.personadd_addressid) where pa.personadd_addressid in (select p.person_addressid from person p where p.enp = :enp)");
            query.setParameter("enp", enp);
            query.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Hibernate Œ¯Ë·Í‡ newEnp " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        
	}	
	
}