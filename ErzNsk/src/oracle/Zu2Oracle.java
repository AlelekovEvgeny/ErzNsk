package oracle;

import java.sql.*;

public class Zu2Oracle extends ConnectOracle {
	
	public void zu2Insert(Statement statement, String pid3cx1, String in13cx1,
			 String in112, String in113, String in115, String in135, String in136,
			 String in145, String bhs11, String msh10) {
		try {
			statement.executeQuery("insert into zu2 values('"+pid3cx1+"', "
					+ "'"+in13cx1+"', '"+in112+"', '"+in113+"', '"+in115+"', '"+in135+"', "
					+ "'"+in136+"', '"+in145+"', trunc(sysdate), '"+bhs11+"', '"+msh10+"', "
					+ "null, null, null, null)");
			statement.executeQuery("update zu2 z set z.adressid = (select pa.personadd_addressid from personadd pa where z.pidcx1 = pa.enp and rownum = 1) where z.adressid is null");
			statement.executeQuery("update zu2 z set z.adressid = (select p.person_addressid from person p where z.pidcx1 = p.enp) where z.adressid is null");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}