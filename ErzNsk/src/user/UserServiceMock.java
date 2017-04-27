package user;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import oracle.ConnectOracle;
import oracle.ConnectionPoolOracle;
import oracle.UserOracle;
import util.CryptoUtils;

public class UserServiceMock implements UserService {

	
	@Override
	public boolean checkUser(String userName, String password) throws Exception {
		ResultSet resultSet =null;
		boolean accept = false;
		Statement statement = null;
		Connection conn = null;
		
		try {
			
			conn = ConnectionPoolOracle.getConnectionDataSource().getConnection();
			statement = conn.createStatement();
			// выдергиваем из таблицы пользователей
			resultSet = new UserOracle().selectUsers(statement);
			while (resultSet.next()) {
				try {
					if (userName.equals(resultSet.getString(1)) && /*password.equals(resultSet.getString(2)))*/ 
							new CryptoUtils().hash(password).equals(resultSet.getString(2))) {
						accept = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try
			{
				if(resultSet != null){resultSet.close();}
				if (statement != null) statement.close();
				if(conn !=null)
				{
					conn.close();
				}
			}  catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return accept; 
	}

	@Override
	public HttpServletRequest setSessionUsername (HttpServletRequest request) throws UnknownHostException {
		
		HttpSession session = request.getSession(false);
        if (session != null) 
        {
		  session.invalidate();
        }
        session = request.getSession(true);
        synchronized (session) 
        {
        	InetAddress addr = InetAddress.getLocalHost();
    		String hostname = addr.getHostName();
    		System.out.println("Хост имя "+hostname);
    		request.getSession().setAttribute("usercomp", hostname);
    		request.getSession().setAttribute("username", request.getParameter("username"));
        }
        
        return request;
       
	}


}
