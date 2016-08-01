package oracle;

import java.sql.*;

public class AppakOracle extends ConnectOracle {
	
	public void insertAppak(Statement statement, String msa2, String err2, String err, String bhs11) {
		try {
			statement.executeQuery("insert into person_appak values('"+msa2+"', '"+err2+"', '"+err+"', '"+bhs11+"', sysdate)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	public void goznak_update(PreparedStatement stmt,ResultSet rs,Connection conn) {
		try {
			String queryInDB = "insert into goznak_polmes  g select * from goznak_csv  where gd in (select replace(msa2,'-') from person_appak p where trunc(d_input) = trunc(sysdate) )";
			 stmt = conn.prepareStatement(queryInDB);
		     rs = stmt.executeQuery();
		     stmt.close();
		     
			queryInDB = "delete from goznak_csv b where gd in (select replace(msa2,'-') from person_appak p where trunc(d_input) = trunc(sysdate) )";
			 stmt = conn.prepareStatement(queryInDB);
		     rs = stmt.executeQuery();
		     stmt.close();
		     
		     queryInDB ="update  person_enp_output t "
						+"SET  t.status = -2 , t.prim =  'Отклоненные А08П01 перед типографией' || t.prim "
						+"where t.df > '15.08.2013' "
						+"and t.status   = 2 "
						+"and  t.gd in (select replace(msa2,'-') from person_appak p where trunc(p.d_input) = trunc(sysdate) )";
		     
		     stmt = conn.prepareStatement(queryInDB);
		     rs = stmt.executeQuery();
		     stmt.close();
		     
		} catch (SQLException e) {
			e.printStackTrace();
			e.getSQLState();
		}
	}	
		public void personAdd_script(PreparedStatement stmt,ResultSet rs,Connection conn) {
			try {
				String queryInDB = 
										// ===============Поиск дублей ЕНП в personadd============
										"update personadd pa set " 
										+"(pa.last_fam,pa.last_im,pa.last_ot,pa.last_dr ) "
										+"= (select p.person_surname,p.person_kindfirstname,p.person_kindlastname,p.person_birthday from person p where p.enp = pa.enp and p.enp not in (select pr.enp_in from error_goznak pr)) "
										+"where pa.personadd_addressid in (select personadd_addressid from error_goznak) "
										+"and pa.last_fam is null";
				stmt = conn.prepareStatement(queryInDB);
			    rs = stmt.executeQuery();
			    stmt.close();
										//============ Поиск дублей УДЛ в person===========================
										queryInDB = "update personadd pa set " 
										+"(pa.last_fam,pa.last_im,pa.last_ot,pa.last_dr ) "
										+"= (select p.person_surname,p.person_kindfirstname,p.person_kindlastname,p.person_birthday from person p, error_goznak pr where " 
										+"pa.personadd_addressid = pr.person_addressid and p.person_serdoc = pr.person_serdoc and p.person_numdoc = pr.person_numdoc and rownum < 2 "
										+"and p.enp not in (select pr.enp_in from error_goznak pr)) " 
										+"where pa.personadd_addressid in (select personadd_addressid from error_goznak) "
										+"and pa.last_fam is null";
				stmt = conn.prepareStatement(queryInDB);
			    rs = stmt.executeQuery();
			    stmt.close();						
										//============Поиск дублей УДЛ в person_doc_history====================
										queryInDB = "update personadd pa set " 
										+"(pa.last_fam,pa.last_im,pa.last_ot,pa.last_dr ) "
										+"= (select p.person_surname,p.person_kindfirstname,p.person_kindlastname,p.person_birthday from person p, error_goznak pr, person_doc_history ph where " 
										+"pa.personadd_addressid = pr.person_addressid and ph.serdoc = pr.person_serdoc and ph.numdoc = pr.person_numdoc and p.person_addressid=ph.id and rownum < 2 "
										+"and p.enp not in (select pr.enp_in from error_goznak pr)) " 
										+"where pa.personadd_addressid in (select personadd_addressid from error_goznak) "
										+"and pa.last_fam is null";
				stmt = conn.prepareStatement(queryInDB);
			    rs = stmt.executeQuery();
			    stmt.close();
										//========personold=========================
										queryInDB = "update personadd pa set " 
										+"(pa.last_fam,pa.last_im,pa.last_ot,pa.last_dr ) "
										+"= (select po.fam,im,ot,dr from personold po  where " 
										+"po.id = pa.personadd_addressid and rownum <=1 and po.fam is not null) " 
										+"where pa.personadd_addressid in (select personadd_addressid from error_goznak) "
										+"and pa.last_fam is null";
				stmt = conn.prepareStatement(queryInDB);
			    rs = stmt.executeQuery();
			    stmt.close();
										
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void refresh_materialize_view(PreparedStatement stmt,ResultSet rs,Connection conn) {
			
			String queryInDB ="begin DBMS_MVIEW.refresh('error_goznak',''); end;";
			try {
				stmt = conn.prepareStatement(queryInDB);
			    rs = stmt.executeQuery();
			    stmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

}

