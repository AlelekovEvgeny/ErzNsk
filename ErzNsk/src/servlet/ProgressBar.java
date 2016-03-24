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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * Servlet implementation class ActionServlet
 */

@WebServlet("/ProgressBar")
public class ProgressBar extends HttpServlet {
private static final long serialVersionUID = 1L;

    
    public ProgressBar() {
        // TODO Auto-generated constructor stub
    }
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	// этот атрибут сессии устанавливается в DelEmptyRowsFromWebExcel...
    	Integer fk ;
    	if(request.getSession().getAttribute("fa") != null){  fk = (int) request.getSession().getAttribute("fa");}else{fk = 0;}
    		response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    response.getWriter().write(String.valueOf(fk));
    }
  	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
  	 {
  		Integer ty ;
    	if(request.getSession().getAttribute("size") != null){  ty = (int) request.getSession().getAttribute("size");}else{ty = 0;}
    	
    		response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    response.getWriter().write(String.valueOf(ty));
  	 }

  	
 
}
