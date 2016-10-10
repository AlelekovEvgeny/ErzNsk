package servlet;




import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.handsontable.ListWebForXMLQuery;
import oracle.ConnectionPoolOracle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * Servlet implementation class ActionServlet
 */

@WebServlet("/TaskP02")
public class TaskP02 extends HttpServlet {
private static final long serialVersionUID = 1L;

    
    public TaskP02() {
        // TODO Auto-generated constructor stub
    }
    
//and t. enp not in '5454545454545454'
    @SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	ArrayList<String> l = new ArrayList<String>();
    	request.getSession().setAttribute("fa", 0); 
		Statement stat = null;
		ResultSet res = null;
		Connection conn = null;
		String data =null;
		try
		{
		DataSource dataSource = ConnectionPoolOracle.setUp();
	    ConnectionPoolOracle.printStatus();
		    conn = dataSource.getConnection();
            ConnectionPoolOracle.printStatus();
	        stat = conn.createStatement();
	        res = stat.executeQuery("select max(r.df) from developer.person_enp_output r where r.pack_id=100 and r.status=20");
	        while(res.next()) {data =  res.getString(1);}
	        data = data.substring(8, 10)+"."+data.substring(5, 7)+"."+data.substring(0, 4);
	        System.out.println("Date from whcih execute query for TaskP02 "+ data);
	        
			//res = stat.executeQuery("select t.enp from person t, personadd d where t.person_linksmoestablishmentid='0' and t.person_addressid=d.personadd_addressid and d.russian != '643' and d.d2 between '"+data+"' and SYSDATE");
	        
			/*res = stat.executeQuery("select t.enp from person t, personadd d where t.person_linksmoestablishmentid  <= 0 and t.person_addressid=d.personadd_addressid and d.russian != 643 and d.russian !=0 and t.person_docpersonid != 28 and t.person_dateinput between '"+data+"' and trunc(SYSDATE-1)"
					+ "union "
					+ "select t.enp from person t, personadd d where t.person_linksmoestablishmentid <= 0 and t.person_addressid=d.personadd_addressid and d.russian = 0 and t.person_docpersonid =22  and t.person_dateinput between '"+data+"' and trunc(SYSDATE-1)");
			*/
	        
	        res = stat.executeQuery("select t.enp from person t, personadd d where t.person_linksmoestablishmentid  = 0 and t.person_addressid=d.personadd_addressid  and t.person_dateinput between  '"+data+"' and trunc(SYSDATE-7)");
			
			data = null;
			
			while(res.next()) {l.add(res.getString(1));}
	     
				
			for(int i=0;i<l.size();i++)
			{
				StringBuilder sqlStr = new StringBuilder();
	        	sqlStr.append("");
				sqlStr.append("insert into person_enp_output values('").append(l.get(i)).append("',SYSDATE-7,100,20,'','',50000,'')");
				stat.executeQuery(sqlStr.toString());
				
			}	
			 // 1. get received JSON data from request
		  	    request.setCharacterEncoding("UTF-8");
		        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		        String json = "";
		        if(br != null){  json = br.readLine();}
		        String fg = URLEncoder.encode(json, "Cp1251");
		        String fg2 = URLDecoder.decode(fg, "UTF-8");
	  	      // 2. initiate jackson mapper
	  	      ObjectMapper mapper = new ObjectMapper();
	  	      // 3. Convert received JSON to Article
	  	      ListWebForXMLQuery article = mapper.readValue(fg2, ListWebForXMLQuery.class);
	  	     
	  	      
	  	      ArrayList<ArrayList<String>> tab = method(article,request,l);
	  	      Map<String, ArrayList<ArrayList<String>>> ind = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
	  		  ind.put("data1", tab );
	       	 json= new Gson().toJson(ind);
	  	     response.setContentType("application/json");
	  	     response.setCharacterEncoding("UTF-8");
	  	     response.getWriter().write(json.toString());
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{ 
			if (res != null) {  try {res.close();} catch (SQLException e) {e.printStackTrace();}  }
		if (stat != null) {  try {stat.close();} catch (SQLException e) {e.printStackTrace();}  }
        if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();} System.out.println("Скинули в пул!");}
        }
        ConnectionPoolOracle.printStatus();
           
    }
    
  	     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  { }

	  	 private ArrayList<ArrayList<String>> method(ListWebForXMLQuery listWeb,HttpServletRequest request,ArrayList<String> list) throws FileNotFoundException, IOException
	  	 {
	  		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>(); 
	  		 int x = list.size()-1;
	  		 // задержка по умолчанию
	  		 int zadershka=1000;
	  		 // задаем задержку в зависимости от количества обрабатываемы строк (чем больше строк тем меньше задержка)
	  		 if(x <= 10)zadershka = 1000;
	  		 if(x <= 50 && x > 10)zadershka = 200;
	  		 if(x <= 100 && x > 50)zadershka = 100;
	  		 if(x <= 999999999 && x > 100)zadershka = 2;
	  		 double res =0;
	  		
	  		 // добовляем 1-строку (шапку) из листа 1
	  		 for (int i = 0; i < 1; i++)
	  		 {
	  			 // берем шапку
	  			 String mas[] = listWeb.getList1().get(i).toString().split(",");
	  			ArrayList<String> row = new ArrayList<String>();
	  			 for (int j = 0; j < mas.length; j++)
	  			 {
	  				mas[j]=  mas[j].replaceAll("[^A-Za-zА-Яа-я0-9- -]", "");
 					row.add(mas[j]); 
	  			 }
	  			 table.add(row); 
	  			 if(list.size() == 0 ){request.getSession().setAttribute("fa", 100);}
	  			 
	  		 }	
	  		 // добовляем остальные строки (выгруженные из базы енп)
	  		    for (int i = 0; i < list.size(); i++) 
	  		    {
		  			ArrayList<String> row = new ArrayList<String>();
		  			row.add(list.get(i));
		  			try {Thread.sleep(zadershka);} catch (InterruptedException e) {e.printStackTrace();}
		  			table.add(row);
		  			
		  			// вычисляем процент отработаннго по количеству переданых строк с листа 1
		  			 res =(double)  i/x;
		  			 int dd =(int) (res *100.0);
		  			 request.getSession().setAttribute("fa", dd); 
			    }
	  		 
	  		 return table;
  	     }
  	 
  	 
 
}
