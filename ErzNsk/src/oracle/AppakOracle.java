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

}

