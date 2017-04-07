package servlet;

import help.Const;
import model.other.ListWeb;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * Servlet implementation class ActionServlet
 */

//@WebServlet("/ExportToExcelFromEmdedTable")
public class ExportToExcelFromEmdedTable extends HttpServlet {
private static final long serialVersionUID = 1L;


    
    public ExportToExcelFromEmdedTable() {
        // TODO Auto-generated constructor stub
    }


  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  
	  // 1. get received JSON data from request
      BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
      String json = "";
      if(br != null){ json = br.readLine(); }
      /*
       * Для корректного отображения кирилицы при экспорте с вэбэкселя в эксель возникает некорректное отображение кирилицы
       * Исправляем его так
       */
      String fg = URLEncoder.encode(json, "Cp1251");
      String fg2 = URLDecoder.decode(fg, "UTF-8");
      
      // 2. initiate jackson mapper
      ObjectMapper mapper = new ObjectMapper();
      // 3. Convert received JSON to Article
      ListWeb article = mapper.readValue(fg2, ListWeb.class);
      String user = article.getGouser().replaceAll("user=", "");
      exportExcelData(user,3,article) ;
      
      Map<String, ArrayList<ArrayList<String>>> ind = new LinkedHashMap<String, ArrayList<ArrayList<String>>>();
      json= new Gson().toJson(ind);   
      response.setContentType("application/json");
	  response.setCharacterEncoding("UTF-8");
	  response.getWriter().write(json.toString());  
      
	  
  }

  
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  
 }
 
 private void exportExcelData(String user, int quantityListForimportFromExcel,ListWeb listWeb) throws FileNotFoundException, IOException {
		// Directory path where the xls file will be created
		String destinationFilePath = Const.PROGRAM_PATH+"export/" + user+" export "+new Random(10000).toString()+".xls";
		
	    // Create object of FileOutputStream
	    FileOutputStream fout = new FileOutputStream(destinationFilePath);

	    // Build the Excel File
	    HSSFWorkbook wb = new HSSFWorkbook();

	    // Create the spreadsheet
	    HSSFSheet spreadSheet = wb.createSheet("1");
	    HSSFSheet spreadSheet1 = wb.createSheet("2");
	    HSSFSheet spreadSheet2 = wb.createSheet("3");
	   
			ArrayList<List<Object>> web = new ArrayList<List<Object>>();
			web.add((List<Object>) listWeb.getList1());
			web.add((List<Object>) listWeb.getList2());
			web.add((List<Object>) listWeb.getList3());
			//System.out.println("лист1 "+ listWeb.getList1());
			//System.out.println("лист2 "+ listWeb.getList2());
			//System.out.println("лист3 "+ listWeb.getList3());
			
			
		    for(int k = 0;k < quantityListForimportFromExcel; k++)
		    {
					HSSFSheet sheet = wb.getSheetAt(k);		
					//System.out.println("Работает лист "+ k);
					HSSFRow excelRow = null;
					HSSFCell excelCell = null;
					int rows = sheet.getPhysicalNumberOfRows();
					
					for(int i = 0; i < web.get(k).size() ; i++) 
					{
						//System.out.println("Тест "+web.get(0).get(0));
						excelRow = sheet.createRow(i);
						excelRow = sheet.getRow(i);	
						String perStroka[] = web.get(k).get(i).toString().replaceAll("\\?", "И").replaceAll("[^A-Za-zА-Яа-я0-9- -.-:-{-}-№-]", "").replaceAll("\\[", "").replaceAll("\\]", "").replace("null", "").trim().split(",");
						//String perStroka[] = web.get(k).get(i).toString().split(",");
						for (int j = 0; j < perStroka.length; j++) 
						{
							
						//	perStroka[j] = perStroka[j].replaceAll("\\?", "И").replaceAll("[^A-Za-zА-Яа-я0-9- -.-:-{-}-№-]", "").replaceAll("\\[", "").replaceAll("\\]", "").trim();
							
							System.out.println(j+ " "+perStroka[j]);
							// хреновым способом добовляем /
							if(perStroka[j].equalsIgnoreCase("БГ")){perStroka[j]= "Б/Г";}
							excelCell = excelRow.createCell(j);
							excelCell = excelRow.getCell(j);
							excelCell.setCellValue(perStroka[j]);
							
							// если первый лист и последняя строка и последний символ в строке
							if(k == 0 && i ==  web.get(k).size()-1 && j == perStroka.length-1)
							{
								excelRow = sheet.createRow(i+1);
								excelRow = sheet.getRow(i+1);	
								
								excelCell = excelRow.createCell(0);
								excelCell = excelRow.getCell(0);
								excelCell.setCellValue(listWeb.getButtonQuery());
							}
						}
						
					}
					
					HSSFRow row = sheet.getRow(0);
					for(int colNum = 0; colNum< row.getLastCellNum();colNum++)    sheet.autoSizeColumn(colNum);
		    }		
		    
		
		wb.write(fout);
		fout.flush();
		fout.close();
	
	}
 
 

 
 
 		
}
