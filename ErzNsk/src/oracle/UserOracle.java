package oracle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import java.sql.*;

public class UserOracle extends ConnectOracle {
	
	public ResultSet selectUsers(Statement statement) {
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery("select * from xml_user");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet; 
	}	

}