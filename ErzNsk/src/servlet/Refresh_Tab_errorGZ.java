package servlet;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

import oracle.AppakOracle;
import oracle.ConnectionPoolOracle;

/**
 * Servlet implementation class ActionServlet
 */

@WebServlet("/refresh_tab_errorgz")
public class Refresh_Tab_errorGZ extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ResultSet rs = null;
	private   Connection conn = null;
	private  PreparedStatement stmt = null;
	private DataSource dataSource = null;
	private AppakOracle appakTable = new AppakOracle();

    
    public Refresh_Tab_errorGZ() {
        // TODO Auto-generated constructor stub
    }


  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   List<String> result = new ArrayList<String>();
   
   
   
   try {
		dataSource = ConnectionPoolOracle.setUp();
		conn = dataSource.getConnection();
		ConnectionPoolOracle.printStatus();
		
		//appakTable.goznak_update(stmt,rs,conn);
		appakTable.personAdd_script(stmt,rs,conn);
	//	appakTable.refresh_materialize_view(statement);
		
	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
   
   
   String json = new Gson().toJson(result);
   response.setContentType("application/json");
   response.setCharacterEncoding("UTF-8");
   response.getWriter().write(json); 
 }

  
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  
 }
}
