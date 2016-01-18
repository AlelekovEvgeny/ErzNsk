package oracle;

import java.sql.*;

public class ErrorOracle extends ConnectOracle {
	
	public void insertError(Statement statement, String msa2, String err2, String err, String bhs11) {
		try {
			statement.executeQuery("insert into person_error values('"+msa2+"', '"+err2+"', '"+err+"', '"+bhs11+"', sysdate)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet selectError(Statement statement, String username) throws SQLException {
		ResultSet resultSet = statement.executeQuery("select p.enp, p.person_surname, p.person_kindfirstname, p.person_kindlastname, "
				+ "p.person_birthday, pe.* "
				+ "from person_error pe, person p, xml_person_enp x "
				+ "where p.enp in (select enp from xml_task where username = '"+username+"') and x.enp = p.enp and pe.msa2 = x.guid");
		return resultSet;
	}

}

