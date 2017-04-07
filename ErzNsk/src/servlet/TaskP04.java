package servlet;

/*Запросы для дебага в базе
 * 
	select * from person_enp_output t where t.status=22
	insert into person_enp_output t values('5454545454',trunc(SYSDATE-1),100,22,'','',50000,'')
	update person_enp_output t set t.df='16.02.2016' where t.enp='5454545454' and t.status=22
	delete from person_enp_output t where t.status=22

select t.enp,d.serdoc,d.numdoc,d.docpersonid,d.date_input from person t, person_doc_history d  
where
 t.person_linksmoestablishmentid  > 0 and t.person_dateinput=d.date_input  and t.person_addressid=d.id  and t.person_dateinput between  '21.02.2016' and trunc(SYSDATE-1)
*/



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

@WebServlet("/TaskP04")
public class TaskP04 extends HttpServlet {
private static final long serialVersionUID = 1L;

    
    public TaskP04() {
        // TODO Auto-generated constructor stub
    }
    
    @SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	ArrayList<String> l = new ArrayList<String>();
    	request.getSession().setAttribute("fa", 0); 
		Statement stat = null;
		ResultSet res = null;
		Connection conn = null;
		String databegin =null;
		String datalimit =null;
		try
		{
		DataSource dataSource = ConnectionPoolOracle.setUp();
	    ConnectionPoolOracle.printStatus();
		    conn = dataSource.getConnection();
            ConnectionPoolOracle.printStatus();
	        stat = conn.createStatement();
	        // date of the begining 
	        res = stat.executeQuery("select max(r.df) from developer.person_enp_output r where r.pack_id=100 and r.status=22");
	        while(res.next()) {databegin =  res.getString(1);}
	        databegin = databegin.substring(8, 10)+"."+databegin.substring(5, 7)+"."+databegin.substring(0, 4);
	        
	        // lastes date (limit) - 1 day
	        res = stat.executeQuery("select max(t.d_insert) from goznak_csv t");
	        while(res.next()) {datalimit =  res.getString(1);}
	       int r= Integer.valueOf(datalimit.substring(8, 10))-1;
	       if(r == 0) r=30;
	       String q =String.valueOf(r);
	        datalimit = q+"."+datalimit.substring(5, 7)+"."+datalimit.substring(0, 4);
	        
	        System.out.println("Date from wich execute query for TaskP04, begin "+ databegin+ " date limit "+ datalimit);
	        
	        
	        res = stat.executeQuery("select t.enp,d.serdoc,d.numdoc,d.docpersonid,d.date_input from person t, person_doc_history d  "
	        		+ "where "
	        		+ "t.person_linksmoestablishmentid  > 0 and t.person_dateinput=d.date_input  and t.person_addressid=d.id  and t.person_dateinput between  '"+databegin+"' and '"+datalimit+"'");
			
			
			while(res.next()) {l.add(res.getString(1));}
	     
				
			for(int i=0;i<l.size();i++)
			{
				StringBuilder sqlStr = new StringBuilder();
	        	sqlStr.append("");
				sqlStr.append("insert into person_enp_output values('").append(l.get(i)).append("','"+datalimit+"',100,22,'','',50000,'user "+request.getSession().getAttribute("username")+"')");
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
	  		 if(x <= 999999999 && x > 100)zadershka = 1;
	  		 double res =0;
	  		
		  		 // добавляем 1-строку (шапку) из web листа 1
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
		  		 
	  		 	// добавляем остальные строки (выгруженные из базы енп)
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
