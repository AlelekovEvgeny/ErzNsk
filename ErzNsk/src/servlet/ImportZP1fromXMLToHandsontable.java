package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.zp1ajax.ZpLoadMock2;

import com.google.gson.Gson;

/**
 * Servlet implementation class ActionServlet
 */

@WebServlet("/ImportZP1fromXMLToHandsontable")
public class ImportZP1fromXMLToHandsontable extends HttpServlet {
	
	public ImportZP1fromXMLToHandsontable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private static final long serialVersionUID = 1L;


  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String json = null ;
	  //����� ������ � webSocketAnswer.js
	  String uprak2 = request.getParameter("uprak2");
	  String upr = request.getParameter("datauprmessZP");
	  ArrayList<ArrayList<String>> test = (ArrayList<ArrayList<String>>) request.getSession().getAttribute("collect");
	  
	  System.out.println("Test "+ test);
	  //String kluch = request.getParameter("kluch");
	  // ������ ������ � ����� ����
	  ArrayList<ArrayList<String>> parsedatauprmessZP1 = parsedatauprmessZP(upr);
	  System.out.println("����� �������� 3");
	  
	  // ������ uprak2. ���� ����� ��� ����������� ����� ����� ������ ������� ����� �������.    
	  ZpLoadMock2 zpLoad = new ZpLoadMock2();
	  ArrayList<ArrayList<String>>  parsedUprak2 = zpLoad.load(uprak2);
	  
	  ArrayList<ArrayList<String>> parsedatauprak2ZP1 = parse(parsedUprak2);
	  compare(parsedatauprmessZP1,parsedatauprak2ZP1);
	  Map<String, ArrayList<ArrayList<String>>> ind = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
	  ind.put("data1zp1ajax", parsedatauprak2ZP1);
	  ind.put("data2upr", parsedatauprmessZP1);
      json= new Gson().toJson(ind);   
     
	     response.setContentType("application/json");
	     response.setCharacterEncoding("UTF-8");
	     response.getWriter().write(json.toString());       
	  
  }

  
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  
 }
 
  private ArrayList<ArrayList<String>>  parse(ArrayList<ArrayList<String>> list)
  {
	  ArrayList<ArrayList<String>> lsb = new ArrayList<ArrayList<String>>();
	  /*
  	 	*Заполняем шапку третьего листа в web экселе  			

  	  */
  	ArrayList<String> zpRecordOnList = new ArrayList<String>();
  	
  	zpRecordOnList.add("ENP");zpRecordOnList.add("PERSON_SURNAME");zpRecordOnList.add("PERSON_KINDFIRSTNAME");zpRecordOnList.add("PERSON_KINDLASTNAME");zpRecordOnList.add("PERSON_BIRTHDAY");
  	zpRecordOnList.add("GD");zpRecordOnList.add("ENP_1");zpRecordOnList.add("ENP_2");zpRecordOnList.add("OKATO_2");
  	zpRecordOnList.add("SMO");zpRecordOnList.add("D_12");zpRecordOnList.add("D_13");zpRecordOnList.add("OKATO_3");zpRecordOnList.add("TYPE_POL");
  	zpRecordOnList.add("POL");zpRecordOnList.add("QRI_1");zpRecordOnList.add("QRI_2");zpRecordOnList.add("QRI_3");zpRecordOnList.add("QRI_4");zpRecordOnList.add("NPP");zpRecordOnList.add("D_INPUT");
  	zpRecordOnList.add("PID7");zpRecordOnList.add("PID8");zpRecordOnList.add("PID29");
  	 lsb.add(zpRecordOnList);  
	  //ïî ñòðî÷êàì
	  for(int i=0;i<list.size();i++)
	  {   
		  ArrayList<String> ls = new ArrayList<String>();
		  String [] mas = list.get(i).get(0).split(",");
				  for (int j = 0; j < mas.length; j++)
				  {
					//System.out.println(""+mas[j]+ " "+j);
					ls.add(mas[j]);
				  }
				  lsb.add(ls);
	  }
	  return lsb;
  }
  
  /*
   * Метод парсит datauprmessZP в коллекцию коллекции
   */
  private ArrayList<ArrayList<String>> parsedatauprmessZP(String upr) throws UnsupportedEncodingException
  {
	  ArrayList<ArrayList<String>> tabList2 = new ArrayList<ArrayList<String>>();
	  // оличество элементов в одной строке
	  int col = 68;
	  // iso-8859-1   Cp1251
	  String fg = URLEncoder.encode(upr, "iso-8859-1");
      String fg2 = URLDecoder.decode(fg, "UTF-8");
      
      fg2 = fg2.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(".fromdbforuprmess", "").trim();
     
      
      
	  String [] mas = fg2.trim().split(",");
	  // узнаем количество строк в в массив из расчтета что в одной строке содержится 68 элементов
	  int lenMas =  mas.length;
	  // заполняем ArrayList<ArrayList<String>>  такой формат необходим для передачи в json
	 
	  ArrayList<String> ls = new ArrayList<String>();
	
	  int scht = 68;
	  for (int j = 0; j < lenMas; j++)
	  {
		  ls.add(mas[j]);
		  if(j == scht) {  tabList2.add(ls);  ls = new ArrayList<String>(); scht = scht +col; }
	  }
	  return tabList2;
  }
  
  private void compare(ArrayList<ArrayList<String>> parsedatauprmessZP1,ArrayList<ArrayList<String>> parsedatauprak2ZP1 )
  {
	  for (int i = 0; i < parsedatauprmessZP1.size(); i++)
	  {
		for (int j = 0; j < parsedatauprak2ZP1.size() ; j++)
		{
			if(parsedatauprmessZP1.get(i).get(67).trim().equals(parsedatauprak2ZP1.get(j).get(0).trim()) )
			{
				parsedatauprak2ZP1.get(j).add(0, parsedatauprmessZP1.get(i).get(3));	// birthday
				parsedatauprak2ZP1.get(j).add(0, parsedatauprmessZP1.get(i).get(2));	// lastname
				parsedatauprak2ZP1.get(j).add(0, parsedatauprmessZP1.get(i).get(1));	// name
				parsedatauprak2ZP1.get(j).add(0, parsedatauprmessZP1.get(i).get(0));	// surname
				parsedatauprak2ZP1.get(j).add(0, parsedatauprmessZP1.get(i).get(26)); // enp
				// ��������� �� ������ ���� � ������� 
				parsedatauprmessZP1.get(i).add("1");
			}
			
		}
	  }
  }
}