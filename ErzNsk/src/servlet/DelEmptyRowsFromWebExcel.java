package servlet;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.handsontable.ListWeb;
import model.handsontable.ListWebForXMLQuery;
import model.handsontable.ListWebForXMLQuery2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * Servlet implementation class ActionServlet
 */

@WebServlet("/DelEmptyRowsFromWebExcel")
public class DelEmptyRowsFromWebExcel extends HttpServlet {
private static final long serialVersionUID = 1L;

    
    public DelEmptyRowsFromWebExcel() {
        // TODO Auto-generated constructor stub
    }
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
  	  // 1. get received JSON data from request
  	  request.setCharacterEncoding("UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        String fg = URLEncoder.encode(json, "Cp1251");
        String fg2 = URLDecoder.decode(fg, "UTF-8");
        if(fg2.substring(2, 9).equals("disband"))
        {
      	  // 2. initiate jackson mapper
  	      ObjectMapper mapper = new ObjectMapper();
  	      // 3. Convert received JSON to Article
  	     // System.out.println("fg2 "+ fg2);
  	      ListWebForXMLQuery2 article = mapper.readValue(fg2, ListWebForXMLQuery2.class);
  	      ArrayList<ArrayList<String>> tab = disband(article,request);
	      Map<String, ArrayList<ArrayList<String>>> ind = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
		  ind.put("data1", tab );
     	 json= new Gson().toJson(ind);   
	     response.setContentType("application/json");
	     response.setCharacterEncoding("UTF-8");
	     response.getWriter().write(json.toString());
        }else
        {
  	      // 2. initiate jackson mapper
  	      ObjectMapper mapper = new ObjectMapper();
  	      // 3. Convert received JSON to Article
  	      ListWebForXMLQuery article = mapper.readValue(fg2, ListWebForXMLQuery.class);
  	      ArrayList<ArrayList<String>> tab = responseDelEmptyRows(article,request);
  	      Map<String, ArrayList<ArrayList<String>>> ind = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
  		  ind.put("data1", tab );
       	 json= new Gson().toJson(ind);   
  	     response.setContentType("application/json");
  	     response.setCharacterEncoding("UTF-8");
  	     response.getWriter().write(json.toString());
        }   
    }
  	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
  	 {
  		
  	 }

  	 private ArrayList<ArrayList<String>> responseDelEmptyRows(ListWebForXMLQuery listWeb,HttpServletRequest request) throws FileNotFoundException, IOException
  	 {
  		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>(); 
  		 int x = listWeb.getList1().size()-1;
  		 // задержка по умолчанию
  		 int zadershka=1000;
  		 // задаем задержку в зависимости от количества обрабатываемы строк (чем больше строк тем меньше задержка)
  		 if(x <= 10)zadershka = 1000;
  		 if(x <= 50 && x > 10)zadershka = 200;
  		 if(x <= 100 && x > 50)zadershka = 100;
  		 if(x <= 5000 && x > 100)zadershka = 10;
  		 double res =0;
  		
  		 for (int i = 0; i < listWeb.getList1().size(); i++)
  		 {
  			 String mas[] = listWeb.getList1().get(i).toString().split(",");
  			 
  			 if ( !mas[8].trim().equalsIgnoreCase("")) 
  			 {
  				 if(mas.length >= 24 && ((
	  						 mas[23].trim().contains("У данного ЗЛ уже проставлена ДС") || 
	  						 mas[23].trim().contains("нет в regi")) || (
	  						 mas[2].trim().contains("Окато не 50000 или открты СП в ЕС") ||
	  						 mas[2].trim().contains("Откреплен в РС"))
  						  )){
  					 
	  				/* ArrayList<String> rows = new ArrayList<String>();
	  				 for (int j = 0; j < mas.length; j++)
	  				 {
	  					 mas[j]=mas[j].replaceAll("\\?", "И").replaceAll("[^A-Za-zА-Яа-я0-9- -.-:-{-}-№-]", "").replaceAll("null", "").replaceAll("\\]", "").replaceAll("\\[", "").trim();
	  					 rows.add(mas[j]);
	  				 }
	  				 table.add(rows);
	  				 try {Thread.sleep(zadershka);} catch (InterruptedException e) {e.printStackTrace();}*/
  				 }else {
  					
  					ArrayList<String> rows = new ArrayList<String>();
	  				 for (int j = 0; j < mas.length; j++)
	  				 {
	  					 mas[j]=mas[j].replaceAll("\\?", "И").replaceAll("[^A-Za-zА-Яа-я0-9- -.-:-{-}-№-]", "").replaceAll("null", "").replaceAll("\\]", "").replaceAll("\\[", "").trim();
	  					 rows.add(mas[j]);
	  				 }
	  				 table.add(rows);
	  				 try {Thread.sleep(zadershka);} catch (InterruptedException e) {e.printStackTrace();}
  				 }
  			 }
  			 // вычисляем процент отработаннго по количеству переданых строк с листа 1
  			 res =(double)  i/x;
  			 int dd =(int) (res *100.0);
  			 //System.out.println("res "+dd);
  			 request.getSession().setAttribute("fa", dd); 
  		 }
  		 return table;
  	 }
  	 
  	 private ArrayList<ArrayList<String>> disband(ListWebForXMLQuery2 listWeb,HttpServletRequest request)
   	 {
  		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>(); 
 		 int x = listWeb.getDisband().size()-1;
 		 // задержка по умолчанию
 		 int zadershka=1000;
 		 // задаем задержку в зависимости от количества обрабатываемы строк (чем больше строк тем меньше задержка)
 		 if(x <= 10)zadershka = 1000;
 		 if(x <= 50 && x > 10)zadershka = 200;
 		 if(x <= 100 && x > 50)zadershka = 100;
 		 if(x <= 5000 && x > 100)zadershka = 10;
 		 double res =0;
 		 
 		for (int i = 0; i < listWeb.getDisband().size(); i++)
 		 {
 			String mas[] = listWeb.getDisband().get(i).toString().split(",");
 			 //System.out.println("test "+mas[4]+"  "+mas[4].trim().equals("")+" "+);
 			ArrayList<String> rows = new ArrayList<String>();
			 for (int j = 0; j < mas.length; j++)
			 {
				 mas[j] = mas[j].replaceAll("\\[", "").replaceAll("\\]", "");
				 if(mas[j].trim().equals("null")){ mas[j]="";}
				 if(j>2 && i>0)
				 {
					 mas[j]="";
				 }
				 rows.add(mas[j]);
			 }
			 table.add(rows);
			 try {Thread.sleep(zadershka);} catch (InterruptedException e) {e.printStackTrace();}
			 
			// вычисляем процент отработаннго по количеству переданых строк с листа 1
  			 res =(double)  i/x;
  			 int dd =(int) (res *100.0);
  			 //System.out.println("res "+dd);
  			 request.getSession().setAttribute("fa", dd); 
 		 }
 		 System.out.println("table "+ table);
  		 return table;
   	}
 
}
