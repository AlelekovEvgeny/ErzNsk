package servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import util.UtilParseDbXml;

import com.google.gson.Gson;

import help.Const;
import model.other.ZpLoadMock2;
import services.Services;

/**
 * Servlet implementation class ActionServlet
 */

@WebServlet("/ImportZP3fromXMLToHandsontable")
public class ImportZP3fromXMLToHandsontable extends HttpServlet {
	
	public ImportZP3fromXMLToHandsontable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private static final long serialVersionUID = 1L;


  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  try {	  
		  String json = null ;
		  String dataZp3 = request.getParameter("dataZP3");
		  String processed_name = dataZp3.replaceAll(".zp3","").replaceAll("> выгрузка на первый лист ", "").replaceAll(".uprak2","").trim();
		  
		  
		  File f = new File(Const.OUTPUTDONE+processed_name+".xml");
		  if(!f.exists()){
			  Services services = new Services();
			  services.zp3process(processed_name,"uprak2");
		  }
		  
		  ArrayList<ArrayList<String>>  unmarhsalingZP3 = null;
		  unmarhsalingZP3 = new UtilParseDbXml().unMarshalingZP3(processed_name);
		  unmarhsalingZP3 = addHeader(unmarhsalingZP3);
		  
		  Map<String, ArrayList<ArrayList<String>>> ind = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
		  ind.put("data2upr", unmarhsalingZP3);
	      json= new Gson().toJson(ind);   
	     
	     response.setContentType("application/json");
	     response.setCharacterEncoding("UTF-8");
	     response.getWriter().write(json.toString());
     
	  } catch (Exception e) {
			e.printStackTrace();
		  }
	  
  }

  
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  
 }
 
  private ArrayList<ArrayList<String>>  parse(ArrayList<ArrayList<String>> list)
  {
	  ArrayList<String> zpRecordOnList = new ArrayList<String>();
  	
  	zpRecordOnList.add("ENP");zpRecordOnList.add("PERSON_SURNAME");zpRecordOnList.add("PERSON_KINDFIRSTNAME");zpRecordOnList.add("PERSON_KINDLASTNAME");zpRecordOnList.add("PERSON_BIRTHDAY");
  	zpRecordOnList.add("GD");zpRecordOnList.add("ENP_1");zpRecordOnList.add("ENP_2");zpRecordOnList.add("OKATO_2");
  	zpRecordOnList.add("SMO");zpRecordOnList.add("D_12");zpRecordOnList.add("D_13");zpRecordOnList.add("OKATO_3");zpRecordOnList.add("TYPE_POL");
  	zpRecordOnList.add("POL");zpRecordOnList.add("QRI_1");zpRecordOnList.add("QRI_2");zpRecordOnList.add("QRI_3");zpRecordOnList.add("QRI_4");zpRecordOnList.add("NPP");zpRecordOnList.add("D_INPUT");
  	zpRecordOnList.add("PID7");zpRecordOnList.add("PID8");zpRecordOnList.add("PID29");
  	 list.add(0, zpRecordOnList);
	  return list;
  }
  
  private ArrayList<ArrayList<String>> addHeader(ArrayList<ArrayList<String>>  unmarhsalingZP3)
  {
 	 ArrayList<String> f = new ArrayList<String>();
 	 
	f.add("ENP");
	f.add("");
	f.add("");
	f.add("USER_ENP");
	f.add("PERSON_SURNAME");
	f.add("PERSON_KINDFIRSTNAME");
	f.add("PERSON_KINDLASTNAME");
	f.add("SMO");
	f.add("D_12");
	f.add("D_13");
	f.add("OKATO");
	f.add("TYPE_POL");
	f.add("POL");
	f.add("PASS_DOC");
	f.add("NUM_DOC");
	f.add("DATE_DOC");
	f.add("BORN");
	f.add("GOVER");
	f.add("CODEDOC");
	f.add("SEX");
	f.add("BIRTHDAY");
	f.add("");
	f.add("D2");
	f.add("");
	f.add("KATEG");
	 
	 unmarhsalingZP3.add(0,f);
	 return unmarhsalingZP3;
  }
  

}