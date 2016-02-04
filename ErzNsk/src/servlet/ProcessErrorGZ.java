package servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
  
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.xml.sax.SAXException;

import pylypiv.errorGZ.main.Data;
import pylypiv.errorGZ.messages.MessageA08p03pr;
import pylypiv.errorGZ.messages.MessageA08p16;
import pylypiv.errorGZ.messages.MessageA24p10;
import pylypiv.errorGZ.messages.MessageZp1;
import pylypiv.errorGZ.parser.Zp;
import pylypiv.errorGZ.parser.ZpLoader;
import util.FileTransfer;
import util.ResObject;
import util.UtilForErrorGz;

  
@WebServlet("/processerror")
@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB 
                 maxFileSize=1024*1024*50,          // 50 MB
                 maxRequestSize=1024*1024*100)      // 100 MB

public class ProcessErrorGZ extends HttpServlet {
  
    private static final long serialVersionUID = 205242440643911308L;
     
    /**
     * Directory where uploaded files will be saved, its relative to
     * the web application directory.
     */
    private static final String UPLOAD_DIR = "uploads";
    private ArrayList<String> listcol = new ArrayList<String>();
      
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
				        
				            List<ArrayList<String>> bag = null;
							try { bag =  paseInputStreamExcelForQuery(absolutePath); } catch (Exception e) {e.printStackTrace();}
							// о каждому енп формируем последовательность действий
							List<ArrayList<String>> task = createTasks(bag);
							// запускаем в поток
							List<String> listEnpForDeleteFromExcel =  new ArrayList<String>();
							try {
								threads(task,listEnpForDeleteFromExcel);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("listEnpForDeleteFromExcel "+listEnpForDeleteFromExcel);
							deleteFromTaskXLS(listEnpForDeleteFromExcel,absolutePath);
				            
				         //   loadToExcelResalt(resaltQuery,absolutePath);
				            
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
		excelCell.setCellValue("Внешний ЕНП");
		
		//excelRow = sheet.createRow(0);
		excelRow = sheet.getRow(0);			
		excelCell = excelRow.createCell(1);
		excelCell = excelRow.getCell(1);
		excelCell.setCellValue("Внутренний ЕНП");
		
		
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
    	List<ArrayList<String>>  listRow = new ArrayList<ArrayList<String>>();
    	FileInputStream is = new FileInputStream(absolutePath);
    	POIFSFileSystem fs = new POIFSFileSystem(is);
		HSSFWorkbook wb = new HSSFWorkbook(fs);	
		
		HSSFSheet sheet = wb.getSheetAt(0);		
		int rowsIn = sheet.getPhysicalNumberOfRows();
		System.out.println("Количество входящих строк "+ rowsIn);
		
		Iterator<Row> rows = sheet.rowIterator();
		while (rows.hasNext())
		{
		    HSSFRow row = (HSSFRow) rows.next();
		    ArrayList<String> listCell = new ArrayList<String>();
		    for(int i = 0;i<75;i++)
		    {		    
		    	if(row.getCell(i) == null)
		    	{
		    		listCell.add("");
		    	}
		    	else {listCell.add(row.getCell(i).getStringCellValue());}
		    }
		    listRow.add(listCell);
		}
		
		FileOutputStream fileOut = new FileOutputStream(absolutePath);
		wb.write(fileOut);
		fileOut.close();
		is.close();
		
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
    	sqlStr.append("");
    	
    	for(int i=0;i<listRow.size();i++)
		{
			ArrayList<String> list = listRow.get(i);
			sqlStr.append("select ad.enp, t.enp from person t , personadd ad where t.person_addressid = ad.personadd_addressid and ad.enp='").append(list.get(0)).append("' union ");
			
		}
    	String query = sqlStr.toString();
    	// убираем последний union
    			query = query.substring(0, query.length()-6);
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
    
    public class AutoThread implements Runnable {
    	
		private ArrayList<String> taskQueue;
		private UtilForErrorGz ul;
		private List<String> listEnpForDeleteFromExcel;
		
		AutoThread(ArrayList<String> taskQueue,UtilForErrorGz ul,List<String> listEnpForDeleteFromExcel) {
			this.taskQueue = taskQueue;
			this.ul = ul;
			this.listEnpForDeleteFromExcel = listEnpForDeleteFromExcel;
		}

		@Override
		public void run() {
			// pass enp to query from bd (person and personadd)
			Data person = new Data(taskQueue.get(0));
			FileTransfer fileTransfer = new FileTransfer();
			ResObject res = new ResObject();
			ArrayList<Zp> zp = null;
			for (int j = 1; j < taskQueue.size(); j++)
			{
    			if(taskQueue.get(j).equals("Провека на дубликат"))
    			{
    				try { 
	    				String filename = new MessageZp1(fileTransfer, res, person).create();
	    				zp = new ZpLoader().load(filename, taskQueue.get(0),"Ожидание ответа ZP1"); 
		    				if(ul.checkRespons(zp,taskQueue.get(0),person))
		    				{
		    					// проверка на пустой ответ zp1 
		    					if(person.getZpList() !=null)
		    					{
		    						person.setZp(person.getZpList().get(0)); System.out.println("Check is double response OK");
		    					}
		    					else
		    					{
		    						listcol.remove(0);
		    						System.out.println("Exit from thread becouse null response on ZP1");
		    						return;
		    						
		    					}
		    					
	    					}else
		    				{
		    					// бъединение п10
		    					// цепляем вторую часть ответа из zp1 (она будет участвовать в объединении в блоке pid2 в первом блоке участвуют данные из нашей базы)
		    					person.setZp(person.getZpList().get(1));
		    					filename = new MessageA24p10(fileTransfer, res, person).create();
		    					
		    							if(ul.waitUprak2(filename, "Ожидаем ответа на A24P10"))
		    							{
		    								// проверяем ответ zp1 после объединения
		    								filename = new MessageZp1(fileTransfer, res, person).create();
		    								zp = new ZpLoader().load(filename, taskQueue.get(0),"Ожидание ответа ZP1 после объединения");
		    								if(ul.checkRespons(zp,taskQueue.get(0),person)){person.setZp(person.getZpList().get(0));	System.out.println("Check is double response OK");}else
		    								{
		    									System.out.println("Check is double response BAD");
		    									// цепляем вторую часть ответа из zp1 (она будет участвовать в объединении в блоке pid2 в первом блоке участвуют данные из нашей базы)
		    			    					person.setZp(person.getZpList().get(0));
		    			    					filename = new MessageA24p10(fileTransfer, res, person).create();
		    			    					if(ul.waitUprak2(filename, "Ожидаем ответа на A24P10"))
				    							{
		    			    						// проверяем ответ zp1 после объединения
				    								filename = new MessageZp1(fileTransfer, res, person).create();
				    								zp = new ZpLoader().load(filename, taskQueue.get(0),"Ожидание ответа ZP1 после объединения");
				    								if(ul.checkRespons(zp,taskQueue.get(0),person))
				    								{
					    								// присваиваем последнюю запись с npp
				    									person.setZp(person.getZpList().get(0));	
				    									System.out.println("Check is double response OK");
			    									}else
			    									{
			    										System.out.println("Check is double response BAD");
		    										}
				    							}
	    									}
		    							}
		    				}		
		    				if(ul.checkVs(zp,taskQueue.get(1),taskQueue.get(0)))
		    				{
		    					listcol.remove(0);
		    					listEnpForDeleteFromExcel.add(taskQueue.get(0));
		    					System.out.println("Exit from thread on OK VsNum");
		    					return;
	    					}else
		    				{
		    					//проверка даты рождения
		    					if(taskQueue.get(2).equals(taskQueue.get(3))){
		    						System.out.println("Bithday OK");
		    						if(	taskQueue.get(7).trim().equals(taskQueue.get(4).trim()) && taskQueue.get(5).trim().equals(taskQueue.get(8).trim()) && taskQueue.get(6).trim().equals(taskQueue.get(9).trim())	)
		    						{
		    							
		    						}else
		    							{
		    								filename = new MessageA08p03pr(fileTransfer, res, person).create(); System.out.println("Send A08P03pr"+ filename);
		    								if(ul.waitUprak2(filename, "Wait response A03P03pr "+filename))
			    							{
		    									// проверяем результат п03
			    								filename = new MessageZp1(fileTransfer, res, person).create();
			    								zp = new ZpLoader().load(filename, taskQueue.get(0),"Wait response ZP1 after A08P03pr ");
			    								if(ul.checkVs(zp,taskQueue.get(1),taskQueue.get(0))){listcol.remove(0); listEnpForDeleteFromExcel.add(taskQueue.get(0));	System.out.println("VSnum is OK after A08P03pr "+ filename);}
			    							}
		    								
		    								/* реализовать чтобы в п03 вставлялась место рождения предыдушего
		    								 * для этого из ответа zp1 ,берем гл енп...находим в нашей базе енп из персонаадд при условии линк смо 0 и выдергиваем месторождения 
		    								 */
		    							}
		    					}else{ if(!taskQueue.get(3).equals("")){System.out.println("разные даты рождения");}else{System.out.println("пустая др");}
		    						
		    					}
		    				}
		    				listcol.remove(0);
		    				System.out.println("NTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
    					} catch (SAXException | IOException | InterruptedException e) {e.printStackTrace();}	
    			}
			}
			
		}


		 
		
	}
    
    
    /*
     * Method calculate and get from excel error.
     * Error - this my ruls 
     */
    private List<ArrayList<String>> createTasks(List<ArrayList<String>> ba)
    {
    	List<ArrayList<String>> taskQueue = new ArrayList<ArrayList<String>>();
        ArrayList<String> queue = null;					
		for(int i=1;i<ba.size();i++)
		{
			queue = new ArrayList<String>();
			queue.add(ba.get(i).get(1));// ЕНП ВНУТР 0
			queue.add(ba.get(i).get(66));// вс 1
			queue.add(ba.get(i).get(13));// дата новая 2
			queue.add(ba.get(i).get(74));// дата старая 3
			queue.add(ba.get(i).get(71));// фам ласт 4
			queue.add(ba.get(i).get(72));// имя ласт 5
			queue.add(ba.get(i).get(73));// отчество ласт 6
			queue.add(ba.get(i).get(10));// фам 7
			queue.add(ba.get(i).get(11));// имя 8
			queue.add(ba.get(i).get(12));// отчество 9
			queue.add("Провека на дубликат");
			if(ba.get(i).get(13).equals(ba.get(i).get(74))){	queue.add("Исправление ДР не требуется");	}else{	queue.add("Исправление ДР");	}
			taskQueue.add(queue);
			
		}
		return taskQueue;
    }
    
	@SuppressWarnings("static-access")
	private void threads(List<ArrayList<String>> ts,List<String> listEnpForDeleteFromExcel) throws InterruptedException
    {
    	ExecutorService exec = Executors.newCachedThreadPool();
    	int numberOfTasks=0;
    	for (int i = 0; i < ts.size(); i++) {
            UtilForErrorGz ut = new UtilForErrorGz();    		
    		exec.execute(new AutoThread(ts.get(i),ut,listEnpForDeleteFromExcel));
    		listcol.add(String.valueOf(++numberOfTasks));
    	}
    	exec.shutdown();
    	while(listcol.size() !=0)
    	{
    		System.out.println("ls "+listcol);
    		Thread.sleep(10000);
    	}
    	
    }
    

	/*
	 * delete from task excel row that is OK 
	 */
	public void deleteFromTaskXLS(List<String> listEnpForDeleteFromExcel,String absolutePath) throws FileNotFoundException, IOException
	{
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(absolutePath));
		HSSFWorkbook wb = new HSSFWorkbook(fs);	
		HSSFSheet sheet = wb.getSheetAt(0);	
		HSSFRow excelRow = null;
		HSSFCell excelCell = null;
		int rows = sheet.getPhysicalNumberOfRows();
	
		int posrow = 0;
					// коллекция енп для удаления и эксель
					for(int t=0;t< listEnpForDeleteFromExcel.size();t++)
					{
						int lastRowNum=sheet.getLastRowNum();
						String s = listEnpForDeleteFromExcel.get(t);
					  // бежим по строкам экселя (после удаления последняя строка меняется)
					  for(int y=1;y<lastRowNum;y++)
					  {
						  excelRow = sheet.getRow(y);
						  // берем столбец с енп с экселя
						  excelCell = excelRow.getCell(1);
						  String enpfromExcel = excelCell.getStringCellValue();
						  // сравниеваем енп из коллекции и в эекселе => если равны то удаляем строчку
						  if(s.equals(enpfromExcel))
						  {
							  System.out.println("Posicion row for delete "+excelCell.getRowIndex()+" Enp for deleting "+s+" lastRowNum= "+lastRowNum);
							  posrow = excelCell.getRowIndex()+1;
							 // System.out.println("row = "+posrow);
						  }
						  
					  }
					  
					  sheet.shiftRows(posrow, lastRowNum, -1);
					}
		System.out.println("Auto на первом этапе обработала = " +listEnpForDeleteFromExcel.size());
		FileOutputStream fileOut = new FileOutputStream(absolutePath);
		wb.write(fileOut);
		fileOut.close();
	}
}
