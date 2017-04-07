package servlet;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import model.other.ListWeb;

/**
 * Servlet implementation class ActionServlet
 */

public class List1 extends HttpServlet {
private static final long serialVersionUID = 1L;

    
    public List1() {
        // TODO Auto-generated constructor stub
    }
    

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
	  // 1. get received JSON data from request
      BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
      String json = "";
      if(br != null){
          json = br.readLine();
      }
      System.out.println(json);
      
   // 2. initiate jackson mapper
      ObjectMapper mapper = new ObjectMapper();

      // 3. Convert received JSON to Article
      ListWeb article = mapper.readValue(json, ListWeb.class);
      
     // List<ListWeb1> userList =
    //		    mapper.readValue(json, new TypeReference<List<ListWeb1>>() {});
      
      System.out.println("object from list1 "+article);
      System.out.println("size list1 "+article.getList1().size());
      
          
         
 }
  

  
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  
 }
 
}
