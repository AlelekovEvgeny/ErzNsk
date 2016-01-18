package servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
  
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import dao.impl.QueryAkaExcelfromPool;

  
@WebServlet("/FileUploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB 
                 maxFileSize=1024*1024*50,          // 50 MB
                 maxRequestSize=1024*1024*100)      // 100 MB

public class FileUploadServlet extends HttpServlet {
  
    private static final long serialVersionUID = 205242440643911308L;
     
    /**
     * Directory where uploaded files will be saved, its relative to
     * the web application directory.
     */
    private static final String UPLOAD_DIR = "uploads";
      
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        // gets absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");
        System.out.println(applicationPath);
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        System.out.println(uploadFilePath);
          
        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
         
        String fileName ="";
        String absolutePath = null;
        //Get all the parts from request and write it to the file on server
        for (Part part : request.getParts()) 
        {
		            fileName = getFileName(part);
		            
		            if(!fileName.equals(""))
		            {
				            /*
				             * 
				             * Ловим если неправильнре имя фаила при записи
				             * 
				             */
				            try
				            {
				            part.write(uploadFilePath + File.separator + fileName);
				            }
				            catch(Throwable e)
				            {
				            	System.out.println("Возникла ошибка преобразования пути!!! Попали в блок по исправлению "+fileName);
				           
				            	fileName = fileName.substring(fileName.indexOf("=")+2, fileName.length());
				            	
				            	System.out.println("Исправлено на : "+fileName);
				            	
				            	part.write(uploadFilePath + File.separator + fileName);
				            }
				            // склеиваем весь путь!!
				            absolutePath = uploadFilePath + File.separator + fileName;
				            System.out.println("Абсолюте !!"+absolutePath);
				            /*
				             * парсим эксель -загоняем в коллекцию - создаем запрос - запращиваем
				             */
				        
				            List<ArrayList<String>> resaltQuery = null;
									try {
										resaltQuery =  paseInputStreamExcelForQuery(absolutePath);
									} catch (Exception e) {
										e.printStackTrace();
									}
								
							
				            
				            loadToExcelResalt(resaltQuery,absolutePath);
				            
				            downloadExcel(response,absolutePath);
		            }
		            else
		            {
		            	
		            }
				            
		          
		        
		        
				
        }
            
      // String ss = fileSaveDir.getAbsolutePath() + File.separator;
       //System.out.println("зфср1 "+ss);
        
  
       // request.getSession().setAttribute("message", fileName + " File uploaded successfully!");
       // request.getSession().setAttribute("absolutePath",absolutePath);
       // getServletContext().getRequestDispatcher("/response.jsp").forward(
       //         request, response);
    }
  
    private void loadToExcelResalt( List<ArrayList<String>> resaltQuery, String absolutePath) throws FileNotFoundException, IOException
    {
    	POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(absolutePath));
		HSSFWorkbook wb = new HSSFWorkbook(fs);	
		HSSFSheet sheet = wb.getSheetAt(1);	
		
		HSSFRow excelRow = null;
		HSSFCell excelCell = null;
		
		//wb.getSheetAt(1).shiftRows(0, 0, 1);
		
		//зачищаем лист
				int rows = sheet.getPhysicalNumberOfRows();
				for(int i = 0; i < rows ; i++)
				{
					excelRow = sheet.createRow(i + 1);
					sheet.removeRow(excelRow);
				}		
		
		excelRow = sheet.createRow(0);
		excelRow = sheet.getRow(0);			
		excelCell = excelRow.createCell(0);
		excelCell = excelRow.getCell(0);
		excelCell.setCellValue("Фамилия");
		
		//excelRow = sheet.createRow(0);
		excelRow = sheet.getRow(0);			
		excelCell = excelRow.createCell(1);
		excelCell = excelRow.getCell(1);
		excelCell.setCellValue("Имя");
		
		//excelRow = sheet.createRow(0);
		excelRow = sheet.getRow(0);			
		excelCell = excelRow.createCell(2);
		excelCell = excelRow.getCell(2);
		excelCell.setCellValue("Отчество");
		
		//excelRow = sheet.createRow(0);
		excelRow = sheet.getRow(0);			
		excelCell = excelRow.createCell(3);
		excelCell = excelRow.getCell(3);
		excelCell.setCellValue("Дата рождения");
		
		//excelRow = sheet.createRow(0);
		excelRow = sheet.getRow(0);			
		excelCell = excelRow.createCell(4);
		excelCell = excelRow.getCell(4);
		excelCell.setCellValue("ЕНП вн");
		
		
		
		// определяем количество строк в нашей коллекции
		rows = resaltQuery.size();
		System.out.println("Количество исходящих строк" + rows);
		
		 // Style the cell with borders all around.
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
   

		
		for(int i = 1; i <= rows ; i++)
		{
			excelRow = sheet.createRow(i);
			excelRow = sheet.getRow(i);			
			excelCell = excelRow.createCell(0);
			excelCell = excelRow.getCell(0);
			excelCell.setCellValue(resaltQuery.get(i-1).get(0));
			excelCell.setCellStyle(style);
			
			excelCell = excelRow.createCell(1);
			excelCell = excelRow.getCell(1);
			excelCell.setCellValue(resaltQuery.get(i-1).get(1));
			excelCell.setCellStyle(style);
			
			excelCell = excelRow.createCell(2);
			excelCell = excelRow.getCell(2);
			excelCell.setCellValue(resaltQuery.get(i-1).get(2));
			excelCell.setCellStyle(style);
			
			excelCell = excelRow.createCell(3);
			excelCell = excelRow.getCell(3);
			excelCell.setCellValue(resaltQuery.get(i-1).get(3));
			excelCell.setCellStyle(style);
			
			excelCell = excelRow.createCell(4);
			excelCell = excelRow.getCell(4);
			excelCell.setCellValue(resaltQuery.get(i-1).get(4));
			excelCell.setCellStyle(style);
		}	
		
		
		
		
		HSSFRow row = wb.getSheetAt(1).getRow(0);
		for(int colNum = 0; colNum< row.getLastCellNum();colNum++)    wb.getSheetAt(1).autoSizeColumn(colNum);
	
		wb.setSheetName(0, "Исходные данные");
		wb.setSheetName(1, "Результат");
		
		
		
		
		
		FileOutputStream fileOut = new FileOutputStream(absolutePath);
		wb.write(fileOut);
		fileOut.close();
    }
    
    /*
     * Метод парсит загруженный эксель, создает запрос и возвращает коллекцию с 
     * данными эексаля и енп из таблицы Person
     */
    private List<ArrayList<String>> paseInputStreamExcelForQuery(String absolutePath) throws Exception
    {
    	/*
    	 *  добовляем в эту коллекцию данные из подгрцжаемого екселя
    	 */
    	List<ArrayList<String>>  listRow = new ArrayList<ArrayList<String>>();
    	POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(absolutePath));
		HSSFWorkbook wb = new HSSFWorkbook(fs);	
		
		HSSFSheet sheet = wb.getSheetAt(0);		
		int rowsIn = sheet.getPhysicalNumberOfRows();
		System.out.println("Количество входящих строк "+ rowsIn);
		/*
		 * Пробегаем по всему экселю и считываем значения 
		 * (бежим строка - столбец)
		 * 
		 */
		
		Iterator<Row> rows = sheet.rowIterator();
		/*
		 * Бежим по строкам
		 */
		while (rows.hasNext())
		{
		    HSSFRow row = (HSSFRow) rows.next();

		    Iterator<Cell> cells = row.cellIterator();
		    
		    /*
		     * бежим по столбцам
		     */
		    ArrayList<String> listCell = new ArrayList<String>();
		    while (cells.hasNext())
		    {
		    	 HSSFCell cell = (HSSFCell) cells.next();
		    	 
			    	
			    	 switch (cell.getCellType()) 
			    	 {
			    	 
			            case HSSFCell.CELL_TYPE_NUMERIC:
			            	// for debug
			               // System.out.println( new SimpleDateFormat("dd.MM.yyyy").format(cell.getDateCellValue()));
			            	listCell.add(new SimpleDateFormat("dd.MM.yyyy").format(cell.getDateCellValue()).trim());
			            break;
	
			            
			            case HSSFCell.CELL_TYPE_STRING:
			            	//for debug
			                //System.out.println(cell.getStringCellValue());
			                
			                String []parseFIOtoTrim = cell.getStringCellValue().split(" ");
			                if(parseFIOtoTrim.length >3)
			                {
			                	//for debug
			                	//System.out.println("ФИО состоит из "+parseFIOtoTrim.length+"-х элементов");
			                	//System.out.println(parseFIOtoTrim[0]+"  "+parseFIOtoTrim[1]+"  "+parseFIOtoTrim[2]+"  "+parseFIOtoTrim[3]);
			                	// фамилия имя отчество разбивается на Петров Иван Иванович Оглы (на 4 элемента) 
			                	if(parseFIOtoTrim.length == 4)
			                	{
			                		String q=parseFIOtoTrim[0],w=parseFIOtoTrim[1],e=parseFIOtoTrim[2]+parseFIOtoTrim[3]; 
			                			parseFIOtoTrim = new String[3]; 
			                				parseFIOtoTrim[0] =q; parseFIOtoTrim[1] =w;parseFIOtoTrim[2] =e;
			                	}
			                	
			                	// фамилия имя отчество разбивается на Петров Иван Иванович Оглы Берды (на 5 элементов)
			                	if(parseFIOtoTrim.length == 5)
			                	{
			                		String q=parseFIOtoTrim[0],w=parseFIOtoTrim[1],e=parseFIOtoTrim[2]+parseFIOtoTrim[3]+parseFIOtoTrim[4]; 
			                			parseFIOtoTrim = new String[3]; 
			                				parseFIOtoTrim[0] =q; parseFIOtoTrim[1] =w;parseFIOtoTrim[2] =e;
			                	}
			                }
			                for(int i=0;i< parseFIOtoTrim.length;i++)
			                {
			                	
			                	listCell.add(parseFIOtoTrim[i].toUpperCase().trim());
			                	//System.out.println("Пробел "+parseFIOtoTrim[i]);
			                	
			                }
			            break;
			            default: break;
			        }
		    }
		    // добовляем обработанную строку по столбцам
		    listRow.add(listCell);
		    
		}
		// создаем текст запроса распарсеного с екселя
		String text = textQuery(listRow);
		// создаем подключение и запрос 
		//ConnectOracle.connect();
		//из ResultSet загружаем в коллекцию данные с енп
		//List<ArrayList<String>> listRowWithEnpPerson = new QueryAkaExcel().queryFIOD(text);
		List<ArrayList<String>> listRowWithEnpPerson = new QueryAkaExcelfromPool().queryFIOD(text);
		/*
		 * сравниваем две коллекции Загруженную из экселя и выгруженную из бд
		 * Причина: Из бд может не выгрузиться по подаваемым данным информация
		 * объединяем данные по ключу и выгружаем в лист результат
		 */
		for (int i = 0; i < listRow.size(); i++)
		{
			String strFam = listRow.get(i).get(0);
			String strName = listRow.get(i).get(1);
			String strLname = listRow.get(i).get(2);
			String strDate = listRow.get(i).get(3);
				for(int y=0;y<listRowWithEnpPerson.size();y++)
				{
					if(strFam.equalsIgnoreCase(listRowWithEnpPerson.get(y).get(0))	&& strName.equalsIgnoreCase(listRowWithEnpPerson.get(y).get(1)) &&
							strLname.equalsIgnoreCase(listRowWithEnpPerson.get(y).get(2)) && strDate.equalsIgnoreCase(listRowWithEnpPerson.get(y).get(3)) )
					{
						listRow.get(i).add(listRowWithEnpPerson.get(y).get(4));
						
					}
					else
					{
						/*
						 * Вставляем пробел на место енп вн тк нет совпадений между поданными данными фиод и данными из бд
						 * 
						 * 
						 */
						if(y == listRowWithEnpPerson.size()-1 && listRow.get(i).size() == 4)
						{
							listRow.get(i).add(" ");
						}
						
						
					}
				}
				//for debug
			//	System.out.println(listRow.get(i));
		}
		
		
				
		FileOutputStream fileOut = new FileOutputStream(absolutePath);
		wb.write(fileOut);
		fileOut.close();
		
		return listRow;
    }
    
    
    /**
     * Utility method to get file name from HTTP header content-disposition
     * 
     */
    private String getFileName(Part part) throws UnsupportedEncodingException {
        String contentDisp = part.getHeader("content-disposition");
      //  System.out.println("Исправляем кодировку для jetty "+part.getHeader("content-disposition").format("UTF-8",part.getHeader("content-disposition") ));
        
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) 
        {
        	/*
        	 * 
        	 * Извлекаем из подстроки начиная с крайнего правого слеша
        	 * 
        	 * 
        	 */
        	int count = 0;
            if (token.trim().startsWith("filename")) 
            {
            	String [] mas = token.split("");
            	
            	for(int i=0;i<mas.length;i++)
            	{
            		if(mas[i].equals("\\"))
            		{
            			count = i;
            		}
            	}
            	
            	if(count == 0)
            	{
            		System.out.println("!!!!!!!!1 "+ token.substring(token.indexOf("=")+2,token.length()-1));
	                return token.substring(token.indexOf("=")+2,token.length()-1);
            	}
            	else
            	{
	            	System.out.println("!!!!!!!!2 "+ token.substring(count,token.length()-1));
	                return token.substring(count,token.length()-1);
            	}
            }
        }
        return "";
    }
   /*
    * Формируем текст запроса 
    */
    private String textQuery(List<ArrayList<String>> listRow)
    {
    	StringBuilder sqlStr = new StringBuilder();
    	sqlStr.append("select t.PERSON_SURNAME,t.PERSON_KINDFIRSTNAME,t.PERSON_KINDLASTNAME,t.PERSON_BIRTHDAY,t.enp from person t where ");
    	
    	for(int i=0;i<listRow.size();i++)
		{
			ArrayList<String> list = listRow.get(i);
			sqlStr.append("t.PERSON_SURNAME='").append(list.get(0)).append("' and t.PERSON_KINDFIRSTNAME='").append(list.get(1)).append("' and t.PERSON_KINDLASTNAME='").append(list.get(2)).append("' and t.PERSON_BIRTHDAY='")
			.append(list.get(3)).append("' or ");
			
		}
    	String query = sqlStr.toString();
    	// убираем последний OR
    			query = query.substring(0, query.length()-4);
    			//for debug
    			//System.out.println("Test "+ query);
    			return query;
    }
    
    
    /*
     * Выргужаем фаил и удаляем с сервака
     * 
     */
    
    private void downloadExcel(HttpServletResponse response, String absolutePath) throws ServletException, IOException
    {
		System.out.println("pach....."+absolutePath);
		ServletOutputStream stream = null;
		BufferedInputStream buf = null;
		try {
			stream = response.getOutputStream();
			File doc = new File(absolutePath);
			response.setCharacterEncoding("application/msexcel");
			response.addHeader("Content-Disposition", "attachment; filename=" + absolutePath);
			response.setContentLength((int)doc.length());
			FileInputStream input = new FileInputStream(doc);
			buf = new BufferedInputStream(input);
			int readBytes = 0;
			while((readBytes = buf.read()) != -1) { stream.write(readBytes); }
		} catch (IOException ioe) {
			throw new ServletException(ioe.getMessage());
		} finally {
			if(stream != null) { stream.close(); }
			if(buf != null) { buf.close(); }
			
			File file =new File(absolutePath);
			System.out.println(file.delete());
		}
    }
}
