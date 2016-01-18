package oracle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import java.sql.*;

public class TaskOracle extends ConnectOracle {

	public void deleteOldTask(Statement statement, String username) throws SQLException {
		statement.executeQuery("delete from xml_task where username = '"+username+"'");
	}

	public void insertNewTask(Statement statement, String enp, String type, String check, String username, String user_enp, 
			String user_person_surname, String user_person_kindfirstname, String user_person_kindlastname, String user_smo, 
			String user_d_12, String user_d_13, String user_okato_3, String user_type_pol, String user_pol,
			String pfr_snils, String pfr_id, String pfr_notid, String USER_SERDOC, String USER_NUMDOC, String USER_DOCID, String USER_DOC_DATE) {
			try {
				statement.executeQuery("insert into xml_task values('"+enp+"', '"+type+"', '"+check+"', '"+username+"', '"+user_enp+"'"
						+ ", '"+user_person_surname+"', '"+user_person_kindfirstname+"', '"+user_person_kindlastname+"', '"+user_smo+"'"
						+ ", '"+user_d_12+"', '"+user_d_13+"', '"+user_okato_3+"', '"+user_type_pol+"', '"+user_pol+"', '"+pfr_snils+"'"
						+ ", '"+pfr_id+"', '"+pfr_notid+"', '"+USER_SERDOC+"', '"+USER_NUMDOC+"', '"+USER_DOCID+"', '"+USER_DOC_DATE+"' )");
			} catch (SQLException e) {
				System.out.println("Oracle Error when adding ");
				System.out.println("1 - ENP as not String");
				System.out.println("2 - Date format is wrong");				
				e.printStackTrace();
			}

	}
	
	public ResultSet selectData(Statement statement, String username) throws SQLException {
		ResultSet resultSet = statement.executeQuery(
				" select p.person_serdoc, p.person_numdoc, "+
				//" -- Функция ID УДЛ "+
				" nvl((select ffoms_id from developer.docffoms d where d.docperson_id = p.person_docpersonid ), 18) person_docpersonid, "+
				" p.person_surname, "+
				" p.person_kindfirstname, "+
				//" -- Функция Отчество "+
				" rtrim(case when ((p.person_kindlastname = 'НЕТ') or (p.person_kindlastname = '-')) then null else rtrim(p.person_kindlastname,'-') end,'-') person_kindlastname "+
				" , p.person_birthday, p.person_sex + 1 person_sex, "+
				" p.person_linksmoestablishmentid, p.enp, p.person_addressid, p.person_dateinput, "+
				//" -- Функция СНИЛС "+
				" nvl( "+
				"    (case  when (pa.snils is not null) and length(replace(replace(pa.snils,'-'),' ')) = 11 and "+
				"  pfr.kod_ss@pfr(substr(replace(replace(pa.snils,'-'),' '),1,9)) =  substr(replace(replace(pa.snils,'-'),' '),10,2) "+
				" then replace(replace(pa.snils,'-'),' ') else null end), "+
				"      replace(replace(pfr.fiod_ss@pfr(p.person_surname, p.person_kindfirstname, p.person_kindlastname, p.person_birthday),'-'),' ')) snils "+
				" , pa.born, trunc(pa.datepassport) datepassport, pa.enp enp_pa, pa.vs_num, pa.vs_date, "+
				" pa.zad, pa.d2, "+
				" pi.smo, pi.d_12, pi.d_13, pi.okato_3, pi.type_pol, pi.pol, pi.enp_1, pi.enp_2, "+
				" z.p14cx1, z.p14cx5, z.p14cx6, z.p14cx7, z.xpn1, z.xpn2, z.xpn3, "+
				" xt.username, "+
				" pa.zad - 1 zadminus1, pa.zad + 40 zadplus40, "+
				//" -- Функция NBLANC "+
				" ERZ.n_blank (pa.enp, pa.vs_date) nblanc, "+
				" pa.vs_date + 1 vs_dateplus1, "+
				" xt.user_enp, xt.user_person_surname, xt.user_person_kindfirstname, xt.user_person_kindlastname, xt.user_smo, "+
				" trunc(xt.user_d_12) user_d_12, trunc(xt.user_d_13) user_d_13, xt.user_okato_3, xt.user_type_pol, xt.user_pol, "+
				" nvl((select alfa3 from developer.oksm o where o.kod = russian and rownum = 1),'RUS'), pa.d_v, pa.d_ser, pa.d_num, pa.pr_fam, pa.pr_im, pa.pr_ot, pa.last_fam, pa.last_im, pa.last_ot, pa.last_dr, "+
				" xt.pfr_snils, xt.pfr_id, xt.pfr_notid, xt.USER_SERDOC, xt.USER_NUMDOC, xt.USER_DOCID, xt.USER_DOC_DATE, "+
				" trunc(pi.d_12 + 1) D_12_PLUS1 "+
				" from person p "+
				" left join personadd pa on p.person_addressid = pa.personadd_addressid "+
				" left join xml_person_enp_input pi on pi.gd in (select xp.guid from xml_person_enp xp where xp.enp = p.enp) and pi.npp = 0 and rownum = 1 "+
				" left join zu1 z on z.person_id = p.person_addressid "+
				" inner join xml_task xt on xt.enp = p.enp "+
				" where  xt.username = '"+username+"'  order by p.enp ");
		return resultSet;
	}
	
	public void insertToPersonEnpOutput(Statement statement, String enp, String guid, String mes_type, String enp_pa) {
		try {
			statement.executeQuery("insert into person_enp_output values('"+enp+"', sysdate, 100, '"+mes_type+"', '"+guid+"', '"+enp_pa+"', 50000, 'Найти по guid')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertGuid(Statement statement, String enp, String guid) {
		try {
			statement.executeQuery("insert into xml_person_enp values('"+enp+"', '"+guid+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteGuid(Statement statement, String enp) {
		try {
			statement.executeQuery("delete from xml_person_enp_input where gd in (select guid from xml_person_enp where enp='"+enp+"') ");
			statement.executeQuery("delete from xml_person_enp where enp='"+enp+"' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public ResultSet selectDataForZPAjax(Statement statement, String username, ArrayList<String> enpForZp1) throws SQLException {
		
		StringBuilder sqlStr = new StringBuilder();
		for(int i=0;i<enpForZp1.size();i++)
		{
	    	sqlStr.append("select p.person_serdoc, p.person_numdoc, "+
					//" -- Функция ID УДЛ "+
					" nvl((select ffoms_id from developer.docffoms d where d.docperson_id = p.person_docpersonid ), 18) person_docpersonid, "+
					" p.person_surname, "+
					" p.person_kindfirstname, "+
					//" -- Функция Отчество "+
					" rtrim(case when ((p.person_kindlastname = 'НЕТ') or (p.person_kindlastname = '-')) then null else rtrim(p.person_kindlastname,'-') end,'-') person_kindlastname "+
					" , p.person_birthday, p.person_sex + 1 person_sex, "+
					" p.person_linksmoestablishmentid, p.enp, p.person_addressid, p.person_dateinput, "+
					//" -- Функция СНИЛС "+
					" nvl( "+
					"    (case  when (pa.snils is not null) and length(replace(replace(pa.snils,'-'),' ')) = 11 and "+
					"  pfr.kod_ss@pfr(substr(replace(replace(pa.snils,'-'),' '),1,9)) =  substr(replace(replace(pa.snils,'-'),' '),10,2) "+
					" then replace(replace(pa.snils,'-'),' ') else null end), "+
					"      replace(replace(pfr.fiod_ss@pfr(p.person_surname, p.person_kindfirstname, p.person_kindlastname, p.person_birthday),'-'),' ')) snils "+
					" , pa.born, trunc(pa.datepassport) datepassport, pa.enp enp_pa, pa.vs_num, pa.vs_date, "+
					" pa.zad, pa.d2, "+
					" pi.smo, pi.d_12, pi.d_13, pi.okato_3, pi.type_pol, pi.pol, pi.enp_1, pi.enp_2, "+
					" z.p14cx1, z.p14cx5, z.p14cx6, z.p14cx7, z.xpn1, z.xpn2, z.xpn3, "+
					" xt.username, "+
					" pa.zad - 1 zadminus1, pa.zad + 40 zadplus40, "+
					//" -- Функция NBLANC "+
					" ERZ.n_blank (pa.enp, pa.vs_date) nblanc, "+
					" pa.vs_date + 1 vs_dateplus1, "+
					" xt.user_enp, xt.user_person_surname, xt.user_person_kindfirstname, xt.user_person_kindlastname, xt.user_smo, "+
					" trunc(xt.user_d_12) user_d_12, trunc(xt.user_d_13) user_d_13, xt.user_okato_3, xt.user_type_pol, xt.user_pol, "+
					" nvl((select alfa3 from developer.oksm o where o.kod = russian and rownum = 1),'RUS'), pa.d_v, pa.d_ser, pa.d_num, pa.pr_fam, pa.pr_im, pa.pr_ot, pa.last_fam, pa.last_im, pa.last_ot, pa.last_dr, "+
					" xt.pfr_snils, xt.pfr_id, xt.pfr_notid, xt.USER_SERDOC, xt.USER_NUMDOC, xt.USER_DOCID, xt.USER_DOC_DATE, "+
					" trunc(pi.d_12 + 1) D_12_PLUS1 "+
					" from person p "+
					" left join personadd pa on p.person_addressid = pa.personadd_addressid "+
					" left join xml_person_enp_input pi on pi.gd in (select xp.guid from xml_person_enp xp where xp.enp = p.enp) and pi.npp = 0 and rownum = 1 "+
					" left join zu1 z on z.person_id = p.person_addressid "+
					" left join xml_task xt on xt.enp = p.enp "+
					" where   p.enp = '").append(enpForZp1.get(i)).append("'");
	    	        if(enpForZp1.size()-1 !=i){sqlStr.append(" union ");}

				}
		sqlStr.append(" order by enp ");
		    	String query = sqlStr.toString();
		    	//System.out.println(query);
		ResultSet resultSet = statement.executeQuery(query);
		return resultSet;
	}
		
		public ResultSet selectDataForZPAjaxQukly(Statement statement, String username, ArrayList<String> enpForZp1) throws SQLException {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("select * from person p left join personadd pa on p.person_addressid = pa.personadd_addressid where ");
		for(int i=0;i<enpForZp1.size();i++)
		{
	    	sqlStr.append("p.enp='").append(enpForZp1.get(i).trim()).append("' or ");
	    }
    	String query = sqlStr.toString();
    	query = query.substring(0, query.length()-4);
    	//System.out.println(query);
		ResultSet resultSet = statement.executeQuery(query);
		return resultSet;
		}
		
		public ResultSet selectDataForZPAjaxQukly2(Statement statement, String username, ArrayList<String> enpForZp1) throws SQLException {
			StringBuilder sqlStr = new StringBuilder();
			for(int i=0;i<enpForZp1.size();i++)
			{
		    	sqlStr.append("select * from person p left join personadd pa on p.person_addressid = pa.personadd_addressid where p.enp='").append(enpForZp1.get(i).trim()).append("' union select * from person p left join personadd pa on p.person_addressid = pa.personadd_addressid where pa.enp='").append(enpForZp1.get(i).trim()).append("' union ");
		    }
	    	String query = sqlStr.toString();
	    	query = query.substring(0, query.length()-6);
	    	//System.out.println("selectDataForZPAjaxQukly2 "+query);
			ResultSet resultSet = statement.executeQuery(query);
			return resultSet;
			}
		
}


