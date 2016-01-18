package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileItem;

import com.google.gson.Gson;

/**
 * Servlet implementation class ActionServlet
 */



@WebServlet("/ImportFromExcelToHandsontable")
public class ImportFromExcelToHandsontable extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	    
	    public ImportFromExcelToHandsontable() {
	    }
	    
	protected void doPost(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException {
	String ajaxUpdateResult = "";
	try {
	List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	for (FileItem item : items) {
		
		if (item.isFormField()) 
		{
			ajaxUpdateResult += "Field " + item.getFieldName() + " with value: " + item.getString() + " is successfully read\n\r";
			
		} else
		{
			String applicationPath = request.getServletContext().getRealPath("");
			applicationPath =applicationPath +File.separator;
			File fileSaveDir = new File(applicationPath);
		    if (!fileSaveDir.exists()) { fileSaveDir.mkdirs();}
		    
		    String fileName = item.getName();
		    if(!fileName.equals(""))
	      {
		    	item.write(new File(fileSaveDir + File.separator + fileName));
		    	String absolutePath = fileSaveDir + File.separator + fileName;
		    	System.out.println("absolutePath  "+absolutePath);
		    	String json = null ;
		      	ArrayList<ArrayList<String>> list1 = importExcelData(absolutePath,0);
		      	ArrayList<ArrayList<String>> list2 = importExcelData(absolutePath,1);
		      	ArrayList<ArrayList<String>> list3 = importExcelData(absolutePath,2);
		        
		      	ArrayList<ArrayList<String>> kluch = searchKey(list1);
		      	
		    	Map<String, ArrayList<	ArrayList<String>	>> ind = new LinkedHashMap<String, ArrayList<	ArrayList<String>	>>();
			  	ind.put("data1", list1);
			  	ind.put("data2", list2);
			  	ind.put("data3", list3);
			  	if (kluch != null) {	ind.put("kluch", kluch);			}
			  	
			  	File file =new File(absolutePath);
			  	file.delete();
			  	
			  	 json= new Gson().toJson(ind);   
			     response.setContentType("application/json");
			     response.setCharacterEncoding("UTF-8");
			     response.getWriter().write(json.toString());
			  	
	      }
		    
		      
			
			
		}
	}
	} catch (Exception e) {
	throw new ServletException("Parsing file upload failed.", e);
	}
	
	}
	
	private ArrayList<ArrayList<String>> importExcelData(String absolutePath, int quantityListForimportFromExcel) throws FileNotFoundException, IOException
	{
	POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(absolutePath));
	HSSFWorkbook wb = new HSSFWorkbook(fs);	
	
				HSSFSheet sheet = wb.getSheetAt(quantityListForimportFromExcel);		
				HSSFRow excelRow = null;
				HSSFCell excelCell = null;
				int rows = sheet.getPhysicalNumberOfRows();
				int f = sheet.getLastRowNum();
				int columns = sheet.getRow(0).getPhysicalNumberOfCells();
				System.out.println("getPhysicalNumberOfRows()  = "+rows+" "+columns);
				
				ArrayList<ArrayList<String>> List1 = new ArrayList<ArrayList<String>>();
				ArrayList<String> listRow = null;
				try
				{
				for(int i = 0; i < rows ; i++) 
				{
					excelRow = sheet.getRow(i);	
					listRow = new ArrayList<String>();
					for(int j = 0; j < columns; j++) 
					{
						excelCell = excelRow.getCell(j, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
						if (excelCell != null) 
					    {
						    if (excelCell.getCellType() == Cell.CELL_TYPE_NUMERIC)
						    {
						    	listRow.add(String.valueOf((int)excelCell.getNumericCellValue()));
						    } 
						    else
						    {
						    	listRow.add(excelCell.getStringCellValue());
						    }
					    } 
					    else
					    {
					    	listRow.add("");
					    }
					    
					}
					List1.add(listRow);
					
				}
				return List1;
				}finally
				{
					FileOutputStream fileOut = new FileOutputStream(absolutePath);
					wb.write(fileOut);
					fileOut.flush();
					fileOut.close();
					
				}
	
	}
	
	/*
	 * ћетод ищет ключ в импортируемом файле с целью активировать кнопку на клиенте (ид кнопки zaprosWebExcel - в index.jsp)
	 */
	private ArrayList<ArrayList<String>>  searchKey(ArrayList<ArrayList<String>> list1)
	{
		ArrayList<ArrayList<String>> kl = null;
		// последн€€ строка в коллекции
		int size = list1.size()-1;
		if( list1.get(size).get(0).contains("A08P02") )
		{
			kl = new ArrayList<ArrayList<String>>();
			ArrayList<String>  f = new ArrayList<String>();
			f.add("A08P02test");
			kl.add(f);
		}
		
		return kl;
	}
}



