package oracle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import java.sql.*;

public class ZpOracle extends ConnectOracle {
	
	public void insertZp(Statement statement, String msa2, String pid3cx1, String pid3cx2, String in13cx1,
			 String in112, String in113, String in115, String in135, String in136, int nppCounter) {
		try {
			statement.executeQuery("insert into xml_person_enp_input values('"+msa2+"',"
					+ "'"+pid3cx1+"', '"+pid3cx2+"', null, '"+in13cx1+"', '"+in112+"',"
					+ "'"+in113+"', '"+in115+"', '"+in135+"', '"+in136+"',"
					+ "null, null, null, null, '"+nppCounter+"', sysdate)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	public ResultSet selectZp(Statement statement, String username) throws SQLException {
		ResultSet resultSet = statement.executeQuery("select p.enp, p.person_surname, p.person_kindfirstname, p.person_kindlastname, "
				+ "trunc(p.person_birthday), pi.* "
				+ "from xml_person_enp_input pi, person p, xml_person_enp x "
				+ "where p.enp in (select enp from xml_task where username = '"+username+"') and x.enp = p.enp and pi.gd = x.guid");
		return resultSet;
	}
	
}

