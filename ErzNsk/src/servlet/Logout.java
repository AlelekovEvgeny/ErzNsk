package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.ConnectOracle;

public class Logout extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws java.io.IOException {
		request.getSession().invalidate();
		try {
			ConnectOracle.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("login.jsp");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		doGet(request, response);
	}
	
}