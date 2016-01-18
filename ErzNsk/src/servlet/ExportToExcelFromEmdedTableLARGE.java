package servlet;

import help.Const;

import java.io.BufferedReader;
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

import model.handsontable.ListWeb;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * Servlet implementation class ActionServlet
 * This servlet can process large count rows. 1 million rows.
 */

@WebServlet("/ExportToExcelFromEmdedTable")
public class ExportToExcelFromEmdedTableLARGE extends HttpServlet {
private static final long serialVersionUID = 1L;


    
    public ExportToExcelFromEmdedTableLARGE() {
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
		String destinationFilePath = Const.PROGRAM_PATH+"export/" + user+" export "+new Random(10000).toString()+".xlsx";
		
	       // XSSFWorkbook wb_template = new XSSFWorkbook();
	        SXSSFWorkbook wb = new SXSSFWorkbook(); 
	        wb.setCompressTempFiles(true);

	        SXSSFSheet sh1 = (SXSSFSheet) wb.createSheet("1");
	        SXSSFSheet sh2 = (SXSSFSheet) wb.createSheet("2");
	        SXSSFSheet sh3 = (SXSSFSheet) wb.createSheet("3");
	        
	        ArrayList<List<?>> web = new ArrayList<List<?>>();
			web.add(listWeb.getList1());
			web.add(listWeb.getList2());
			web.add(listWeb.getList3());
	        
	        sh1.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
	        sh2.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
	        sh3.setRandomAccessWindowSize(100);// keep 100 rows in memory, exceeding rows will be flushed to disk
	        
	        for(int k = 0;k < quantityListForimportFromExcel; k++)
		    {
	        	Sheet sheet = wb.getSheetAt(k);
	        	Row excelRow = null;
				Cell excelCell = null;
	        	for(int i = 0; i < web.get(k).size() ; i++) 
				{
	        		excelRow = sheet.createRow(i);
	        		String perStroka[] = web.get(k).get(i).toString().replaceAll("\\?", "И").replaceAll("[^A-Za-zА-Яа-я0-9- -.-:-{-}-№-]", "").replaceAll("\\[", "").replaceAll("\\]", "").replace("null", "").trim().split(",");
	        		for (int j = 0; j < perStroka.length; j++) 
					{
	        			// хреновым способом добовляем /
						if(perStroka[j].equalsIgnoreCase("БГ")){perStroka[j]= "Б/Г";}
						excelCell = excelRow.createCell(j);
						excelCell.setCellValue(perStroka[j].trim());
						
						// если первый лист и последняя строка и последний символ в строке
						if(k == 0 && i ==  web.get(k).size()-1 && j == perStroka.length-1)
						{
							excelRow = sheet.createRow(i+1);
							excelRow = sheet.getRow(i+1);	
							
							excelCell = excelRow.createCell(0);
							excelCell.setCellValue(listWeb.getButtonQuery());
						}
					}
				}
				//for(int colNum = 1; colNum< 70 ;colNum++)  { System.out.println(colNum); sheet.autoSizeColumn(colNum,false);}
		    }

	    FileOutputStream out = new FileOutputStream(destinationFilePath);
	    wb.write(out);
	    out.close();
	
 }
 
 

 
 
 		
}
