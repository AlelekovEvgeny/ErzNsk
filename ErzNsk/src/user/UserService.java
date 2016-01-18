package user;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public interface UserService {

	boolean checkUser(String userName, String password) throws Exception;

	HttpServletRequest setSessionUsername(HttpServletRequest request) throws UnknownHostException;
}
