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

import oracle.ConnectionPoolOracle;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import model.other.ListWebForXMLQuery;

/**
 * Servlet implementation class ActionServlet
 */

@WebServlet("/TaskP03")
public class TaskP03 extends HttpServlet {
private static final long serialVersionUID = 1L;

    
    public TaskP03() {
        // TODO Auto-generated constructor stub
    }
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	
    	ArrayList<String> l = new ArrayList<String>();
		Statement stat = null;
		ResultSet res = null;
		Connection conn = null;
		try
		{
		DataSource dataSource = ConnectionPoolOracle.setUp();
	    ConnectionPoolOracle.printStatus();
		    conn = dataSource.getConnection();
            ConnectionPoolOracle.printStatus();
	        stat = conn.createStatement();
			res = stat.executeQuery("select enp from person_enp_output where pack_id=0 and status = 18");
			while(res.next()) {l.add(res.getString(1));}
	       /* l.add("454646546546");
	        l.add("46546546546546546");
	        l.add("454454546");
	        l.add("4654543221540000");
	       */ 
			//включить
			stat.executeQuery("update person_enp_output set pack_id = 100,df=SYSDATE,prim='"+request.getSession().getAttribute("username")+"'  where pack_id=0 and status = 18");
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
		if (stat != null) {  try {stat.close();} catch (SQLException e) {e.printStackTrace();}  }
        if (conn != null) { try {conn.close();} catch (SQLException e) {e.printStackTrace();} System.out.println("Скинули в пул!");}
        }
        ConnectionPoolOracle.printStatus();
           
    }
    
  	     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  { }

	  	 private ArrayList<ArrayList<String>> method(ListWebForXMLQuery listWeb,HttpServletRequest request,ArrayList<String> list) throws FileNotFoundException, IOException
	  	 {
	  		int x = 0;
	  		 ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>(); 
	  		 // добавляем 1-строку (шапку) из листа 1
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
	  			 
	  		 }	
	  		 if( list.size()>0 ){
		  		 x = list.size();
		  		 // задержка по умолчанию
		  		 int zadershka=1000;
		  		 // задаем задержку в зависимости от количества обрабатываемы строк (чем больше строк тем меньше задержка)
		  		 if(x <= 10)zadershka = 1000;
		  		 if(x <= 50 && x > 10)zadershka = 200;
		  		 if(x <= 100 && x > 50)zadershka = 100;
		  		 if(x <= 10000000 && x > 100)zadershka = 10;
		  		 double res =0;
		  		
		  		
		  		 // добвляем остальные строки (выгруженные из базы енп)
		  		    for (int i = 0; i < list.size(); i++) 
		  		    {
			  			ArrayList<String> row = new ArrayList<String>();
			  			row.add(list.get(i));
			  			table.add(row);
			  			try {Thread.sleep(zadershka);} catch (InterruptedException e) {e.printStackTrace();}
			  			// вычисляем процент отработаннго по количеству переданых строк с листа 1
			  			 res =(double)  i+1/x;
			  			 int dd =(int) (res *100.0);
			  			 request.getSession().setAttribute("fa", dd); 
				    }
	  		 }
	  		 else {request.getSession().setAttribute("fa", 100);}
	  		 return table;
  	     }
}
