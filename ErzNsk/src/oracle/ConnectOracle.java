package oracle;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import servlet.LoginController;

public class ConnectOracle {

	protected static String driver;
	protected static String url;
	protected static String username;
	protected static String password;
	public static Connection connection;
	
	

	public static void connect() {
		Properties properties = new Properties();
		try {
			//properties.load(new FileInputStream("oracle.properties"));
			InputStream inputStream =ConnectOracle.class.getClassLoader().getResourceAsStream("oracle.properties");
			
			
			//for debag
			URL url123 = ConnectOracle.class.getClassLoader().getResource("oracle.properties");
			System.out.println(url123);
			
			
			properties.load(inputStream);
			
			
	        url = "jdbc:oracle:thin:@" + properties.getProperty("jdbc.server") 
	        		+ ":" + properties.getProperty("jdbc.port") + ":" 
	        		+ properties.getProperty("jdbc.sid");
			
	        driver = properties.getProperty("jdbc.driver");
			username = properties.getProperty("jdbc.username");
			System.out.println(username);
			password = properties.getProperty("jdbc.password");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
	      	connection = DriverManager.getConnection(url, username, password);
	      	
		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	
	
}