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

import pylypiv.errorGZ.dao.Factory;
import pylypiv.errorGZ.domain.Person;
import pylypiv.errorGZ.main.Data;
import pylypiv.errorGZ.messages.MessageA08p03;
import pylypiv.errorGZ.messages.MessageA08p03pr;
import pylypiv.errorGZ.messages.MessageA08p03pr_newDr;
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
				             * ����� ���� ������������ ��� ����� ��� ������
				             * 
				             */
				            try
				            {
				            part.write(uploadFilePath + File.separator + fileName);
				            }
				            catch(Throwable e)
				            {
				            	System.out.println("�������� ������ �������������� ����!!! ������ � ���� �� ����������� "+fileName);
				           
				            	fileName = fileName.substring(fileName.indexOf("=")+2, fileName.length());
				            	
				            	System.out.println("���������� �� : "+fileName);
				            	
				            	part.write(uploadFilePath + File.separator + fileName);
				            }
				            // ��������� ���� ����!!
				            absolutePath = uploadFilePath + File.separator + fileName;
				            System.out.println("�������� !!"+absolutePath);
				            /*
				             * ������ ������ -�������� � ��������� - ������� ������ - �����������
				             */
				        
				            List<ArrayList<String>> bag = null;
							try { bag =  paseInputStreamExcelForQuery(absolutePath); } catch (Exception e) {e.printStackTrace();}
							// � ������� ��� ��������� ������������������ ��������
							List<ArrayList<String>> task = createTasks(bag);
							// ��������� � �����
							List<String> listEnpForDeleteFromExcel =  new ArrayList<String>();
							try { threads(task,listEnpForDeleteFromExcel); } catch (InterruptedException e) {e.printStackTrace();}
							System.out.println("listEnpForDeleteFromExcel "+listEnpForDeleteFromExcel);
							deleteFromTaskXLS(listEnpForDeleteFromExcel,absolutePath);
				            downloadExcel(response,absolutePath);
		            }
		            else
		            {
		            	
		            }
        }
    }

    
    /*
     * ����� ������ ����������� ������, ������� ������ � ���������� ��������� � 
     * ������� ������� � ��� �� ������� Person
     */
    private List<ArrayList<String>> paseInputStreamExcelForQuery(String absolutePath) throws Exception
    {
    	List<ArrayList<String>>  listRow = new ArrayList<ArrayList<String>>();
    	FileInputStream is = new FileInputStream(absolutePath);
    	POIFSFileSystem fs = new POIFSFileSystem(is);
		HSSFWorkbook wb = new HSSFWorkbook(fs);	
		
		HSSFSheet sheet = wb.getSheetAt(0);		
		int rowsIn = sheet.getPhysicalNumberOfRows();
		System.out.println("���������� �������� ����� "+ rowsIn);
		
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
      //  System.out.println("���������� ��������� ��� jetty "+part.getHeader("content-disposition").format("UTF-8",part.getHeader("content-disposition") ));
        
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) 
        {
        	/*
        	 * 
        	 * ��������� �� ��������� ������� � �������� ������� �����
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
     * ��������� ���� � ������� � �������
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
    			if(taskQueue.get(j).equals("������� �� ��������"))
    			{
    				try { 
	    				String filename = new MessageZp1(fileTransfer, res, person).create();
	    				zp = new ZpLoader().load(filename, taskQueue.get(0),"�������� ������ ZP1"); 
		    				if(ul.checkRespons(zp,taskQueue.get(0),person))
		    				{
		    					// �������� �� ������ ����� zp1 
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
		    					// ���������� �10
		    					// ������� ������ ����� ������ �� zp1 (��� ����� ����������� � ����������� � ����� pid2 � ������ ����� ��������� ������ �� ����� ����)
		    					person.setZp(person.getZpList().get(1));
		    					filename = new MessageA24p10(fileTransfer, res, person).create();
		    					
		    							if(ul.waitUprak2(filename, "������� ������ �� A24P10"))
		    							{
		    								// ��������� ����� zp1 ����� �����������
		    								filename = new MessageZp1(fileTransfer, res, person).create();
		    								zp = new ZpLoader().load(filename, taskQueue.get(0),"�������� ������ ZP1 ����� ������ ������� �����������");
		    								if(ul.checkRespons(zp,taskQueue.get(0),person))
		    								{
		    									person.setZp(person.getZpList().get(0));	System.out.println("Check is double response OK");
		    									
		    								}
		    								else
		    								{
		    									System.out.println("Check is double response BAD");
		    									// ������� ������ ����� ������ �� zp1 (��� ����� ����������� � ����������� � ����� pid2 � ������ ����� ��������� ������ �� ����� ����)
		    			    					person.setZp(person.getZpList().get(0));
		    			    					filename = new MessageA24p10(fileTransfer, res, person).create();
		    			    					if(ul.waitUprak2(filename, "������� ������ �� A24P10"))
				    							{
		    			    						// ��������� ����� zp1 ����� �����������
				    								filename = new MessageZp1(fileTransfer, res, person).create();
				    								zp = new ZpLoader().load(filename, taskQueue.get(0),"�������� ������ ZP1 ����� ������ �����������");
				    								if(ul.checkRespons(zp,taskQueue.get(0),person))
				    								{
					    								// ����������� ��������� ������ � npp
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
		    				// check vs
		    				if(ul.checkVs(zp,taskQueue.get(1),taskQueue.get(0)))
		    				{
		    					listcol.remove(0);
		    					listEnpForDeleteFromExcel.add(taskQueue.get(0));
		    					System.out.println("Exit from thread on OK VsNum");
		    					return;
	    					}else
		    				{
	    						if(getNppZero(zp).equals(String.valueOf(Integer.valueOf(person.getPerson_sex()))) )
	    						{
			    						/*
			    						 * Check if all fio and  bithday is empty, then send p03
			    						 * e.g. before we a union on p24 and now is send p03
			    						 */
			    						if(taskQueue.get(3).equals("") && taskQueue.get(4).equals("") && taskQueue.get(5).equals("") && taskQueue.get(6).equals(""))
			    						{
			    							filename = new MessageA08p03(fileTransfer, res, person).create(); System.out.println("Send A08P03 when all last empty "+ filename);
			    							if(ul.waitUprak2(filename, "Wait response A03P03 when all last empty "+filename))
			    							{
			    								// send and check resalt �03
			    								filename = new MessageZp1(fileTransfer, res, person).create();
			    								zp = new ZpLoader().load(filename, taskQueue.get(0),"Wait response ZP1 after A08P03 when all last empty ");
			    								if(ul.checkVs(zp,taskQueue.get(1),taskQueue.get(0))){listcol.remove(0); listEnpForDeleteFromExcel.add(taskQueue.get(0));	System.out.println("VSnum is OK after A08P03 (condition if last fiod and last birthday empty)"+ filename);System.out.println("Exit from thread on OK VsNum");return;}
			    								else
				    							{
				    								System.out.println("NO set VSNUm after A08P03 (condition if last fiod and last birthday empty)");
				    								System.out.println("Trying to learn a data our person from db");
				    								/*
					    								1. ������ ������ � ���� �� �� ��� ���������� �� zp1 
					    								2. ����� set ������������� ������� data  ����������� �� ����� 
					    								3. ��������� �03pr
					    							*/	 		
				    								for(Zp m : zp)
				    								{
				    									if(m.getNpp() == 0)
				    									{
				    										 Person p =  Factory.getInstance().getPersonDAO().getPerson(m.getPid3cx1_1());
				    										 // ���� �� �� ������ ������ ������ ���� ��������� (�.�. ���� �������=��� ��� ����������=������ � ��...)
				    										 if(equalsInitials(p,person))
				    										 {
				    											 person.getPerson().getPersonAdd().setLast_fam(p.getPerson_surname());
					    										 person.getPerson().getPersonAdd().setLast_im(p.getPerson_kindfirstname());
					    										 person.getPerson().getPersonAdd().setLast_ot(p.getPerson_kindlastname());
					    										 person.getPerson().getPersonAdd().setLast_dr(p.getPerson_birthday());
				    										 }else
				    										 {
				    											 System.out.println("���� ���������� ��� ��� �� ��� "+ filename);
				    										 }
				    										 
				    										 
				    									}
				    								} 
				    								
				    								filename = new MessageA08p03pr(fileTransfer, res, person).create(); System.out.println("Send A08P03pr after set last fiod "+ filename);
				    								if(ul.waitUprak2(filename, "Wait response A03P03pr after set last fiod "+filename))
					    							{
				    									// ��������� ��������� �03
					    								filename = new MessageZp1(fileTransfer, res, person).create();
					    								zp = new ZpLoader().load(filename, taskQueue.get(0),"Wait response ZP1 after A08P03pr after set last fiod ");
					    								if(ul.checkVs(zp,taskQueue.get(1),taskQueue.get(0)))
					    								{
					    									listcol.remove(0); listEnpForDeleteFromExcel.add(taskQueue.get(0));	System.out.println("VSnum is OK after A08P03pr "+ filename);
				    									}else
				    									{
				    										System.out.println("VSnum is NO after A08P03pr after set last fiod - EXIT");
				    										
				    									}
					    							}
				    								
				    							}
			    							}
			    							
			    						}
			    					
				    					//�������� ���� ��������
				    					if(taskQueue.get(2).equals(taskQueue.get(3)) ){
				    						System.out.println("Bithday OK");
				    						// check fam firtsname and lastname with old fio 
				    						if(	taskQueue.get(7).trim().equals(taskQueue.get(4).trim()) && taskQueue.get(5).trim().equals(taskQueue.get(8).trim()) && taskQueue.get(6).trim().equals(taskQueue.get(9).trim())	)
				    						{
				    							
				    							filename = new MessageA08p03(fileTransfer, res, person).create(); System.out.println("Send A08P03 when all last equalswith firs fiod"+ filename);
				    							if(ul.waitUprak2(filename, "Wait response A03P03 when all last equalswith firs fiod"+filename))
				    							{
				    								// send and check resalt �03
				    								filename = new MessageZp1(fileTransfer, res, person).create();
				    								zp = new ZpLoader().load(filename, taskQueue.get(0),"Wait response ZP1 after A08P03 when all last equalswith firs fiod");
				    								if(ul.checkVs(zp,taskQueue.get(1),taskQueue.get(0))){listcol.remove(0); listEnpForDeleteFromExcel.add(taskQueue.get(0));	System.out.println("VSnum is OK after A08P03 (condition if last fiod and last birthday equals first fiod first birthday )"+ filename);System.out.println("Exit from thread on OK VsNum");return;}
				    								else
					    							{
					    								System.out.println("NO set VSNUm after A08P03 (condition if last fiod and last birthday equals first fiod first birthday )");
					    							}
				    							}	
				    						}else
				    							{
				    								filename = new MessageA08p03pr(fileTransfer, res, person).create(); System.out.println("Send A08P03pr "+ filename);
				    								if(ul.waitUprak2(filename, "Wait response A03P03pr "+filename))
					    							{
				    									// ��������� ��������� �03
					    								filename = new MessageZp1(fileTransfer, res, person).create();
					    								zp = new ZpLoader().load(filename, taskQueue.get(0),"Wait response ZP1 after A08P03pr ");
					    								if(ul.checkVs(zp,taskQueue.get(1),taskQueue.get(0)))
					    								{
					    									listcol.remove(0); listEnpForDeleteFromExcel.add(taskQueue.get(0));	System.out.println("VSnum is OK after A08P03pr "+ filename);
				    									}
					    								else
					    								{ 
					    									System.out.println("NO set VSNUm after A08P03pr (condition if last birthday=bythday and diffrent last fio vs fio )");
						    								System.out.println("Trying to learn a data our person from db");
						    								/*
							    								1. ������ ������ � ���� �� �� ��� ���������� �� zp1 
							    								2. ����� set ������������� ������� data  ����������� �� ����� 
							    								3. ��������� �03pr
							    							*/	 		
						    								for(Zp m : zp)
						    								{
						    									if(m.getNpp() == 0)
						    									{
						    										 Person p =  Factory.getInstance().getPersonDAO().getPerson(m.getPid3cx1_1());
						    										 // ���� �� �� ������ ������ ������ ���� ��������� (�.�. ���� �������=��� ��� ����������=������ � ��...)
						    										 if(equalsInitials(p,person))
						    										 {
						    											 person.getPerson().getPersonAdd().setLast_fam(p.getPerson_surname());
							    										 person.getPerson().getPersonAdd().setLast_im(p.getPerson_kindfirstname());
							    										 person.getPerson().getPersonAdd().setLast_ot(p.getPerson_kindlastname());
							    										 person.getPerson().getPersonAdd().setLast_dr(p.getPerson_birthday());
						    										 }else
						    										 {
						    											 System.out.println("���� ���������� ��� ��� �� ��� "+ filename);
						    											 return;
						    										 }
						    										 
						    										 
						    									}
						    								}
						    								
						    								filename = new MessageA08p03pr(fileTransfer, res, person).create(); System.out.println("Send A08P03pr after set last fiod (condition if last birthday=bythday and diffrent last fio vs fio )"+ filename);
						    								if(ul.waitUprak2(filename, "Wait response A03P03pr after set last fiod (condition if last birthday=bythday and diffrent last fio vs fio ) "+filename))
							    							{
						    									// ��������� ��������� �03
							    								filename = new MessageZp1(fileTransfer, res, person).create();
							    								zp = new ZpLoader().load(filename, taskQueue.get(0),"Wait response ZP1 after A08P03pr after set last fiod (condition if last birthday=bythday and diffrent last fio vs fio )");
							    								if(ul.checkVs(zp,taskQueue.get(1),taskQueue.get(0)))
							    								{
							    									listcol.remove(0); listEnpForDeleteFromExcel.add(taskQueue.get(0));	System.out.println("VSnum is OK after A08P03pr (condition if last birthday=bythday and diffrent last fio vs fio ) "+ filename);
						    									}else
						    									{
						    										System.out.println("VSnum is NO after A08P03pr after set last fiod - EXIT; condition if last birthday=bythday and diffrent last fio vs fio ");
						    										
						    									}
							    							}
						    								
						    								
					    									
					    								}
					    							}
				    								
				    								
				    							}
				    					}else
				    					{
				    						System.out.println("Bythday different");
				    						/*
				    						 * ���� ���� �� !=�� � ���� �� =='' � ���� ��� �� ������ �� ���������� �� ����������
				    						 * ���������� �� ���� ��������� ������������ ������������� � ����� 7777777
				    						 */
				    						if(taskQueue.get(3).equals(""))
				    						{

			    								// �� ������ ����� ���
			    								if(	!taskQueue.get(4).trim().equals("") && !taskQueue.get(8).trim().equals("") && !taskQueue.get(9).trim().equals("")	)
					    						{
			    									filename = new MessageA08p03pr(fileTransfer, res, person).create(); System.out.println("Send A08P03pr (condition if last bythday!=bythday and lasr fio != '') ) - "+ filename);
				    								if(ul.waitUprak2(filename, "Wait response A03P03pr (condition if last bythday!=bythday and lasr fio != '') "+filename))
					    							{
				    									// ��������� ��������� �03
					    								filename = new MessageZp1(fileTransfer, res, person).create();
					    								zp = new ZpLoader().load(filename, taskQueue.get(0),"Wait response ZP1 after A08P03pr (condition if last bythday!=bythday and lasr fio != '') ");
					    								if(ul.checkVsandBythday(zp,taskQueue.get(1),taskQueue.get(0),taskQueue.get(2)))
					    								{
					    									listEnpForDeleteFromExcel.add(taskQueue.get(0));	System.out.println("VSnum is OK after A08P03pr (condition if last bythday!=bythday and lasr fio != '')"+ filename);
				    									}
					    								else
					    								{
				    										System.out.println("VSnum is NO after A08P03pr (condition if last bythday!=bythday and lasr fio != '') - "+filename);
				    									}
					    							}
					    						}
			    								else
			    								{
			    									System.out.println("�� ������� ����� ���" + filename);
			    								}
			    								
		    								
			    							}else
			    							{
			    								/* ���� ���� �� != �� � ���� �� �� �����
			    								   1.  �� ������ ����� ���.........?????? ������������ ����������
			    								���� �� �����   2.  �������� ��� �������������� ����� �� 50000 ����� (�� �� ������ ���������� ������ �������� ��)
			    								   3.  �16 �� ������� ������� ������ ����� ��
			    								   4.  �03pr ��� � ���������� �� � ������� �������
			    								   
			    								*/ 
			    								if(	!taskQueue.get(4).trim().equals("") && !taskQueue.get(8).trim().equals("") && !taskQueue.get(9).trim().equals("")	)
					    						{
			    									filename = new MessageA08p16(fileTransfer, res, person).create(); System.out.println("Send A08P16pr (condition if last bythday!= && !'' bythday && last fio!='' ) ) - "+ filename);
			    									if(ul.waitUprak2(filename, "Wait response A03P16pr (condition if last bythday!=bythday and lasr fio != '') "+filename))
					    							{
				    									filename = new MessageA08p03pr_newDr(fileTransfer, res, person).create(); System.out.println("Send A08P03pr (condition if last bythday!= && !'' bythday && last fio!='' ) ) - "+ filename);
				    									if(ul.waitUprak2(filename, "Wait response A03P03pr (condition if last bythday!=bythday and lasr fio != '') "+filename))
						    							{
				    										filename = new MessageZp1(fileTransfer, res, person).create();
						    								zp = new ZpLoader().load(filename, taskQueue.get(0),"Wait response ZP1 after A08P03pr (condition if last bythday!=bythday and lasr fio != '') -" + filename);
						    								if(ul.checkVsandBythday(zp,taskQueue.get(1),taskQueue.get(0),taskQueue.get(2)))
						    								{
						    									listEnpForDeleteFromExcel.add(taskQueue.get(0));	System.out.println("VSnum is OK after A08P16pr(bythday)+A08P03pr (condition if last bythday!=bythday and lasr fio != '')"+ filename);
					    									}else
					    									{
					    										System.out.println("VSnum is NO after A08P16pr(bythday)+A08P03pr (condition if last bythday!=bythday and lasr fio != '')"+ filename);
					    									}
						    							}
					    							}

					    						}
			    								else
			    								{
			    									System.out.println("�� ������� ����� ��� (condition if last bythday!= && !'' bythday)" + filename);
			    								}
			    							}
				    						
				    					}
		    					}
	    						else
	    						{
	    							System.out.println("������ ��� � ������ Zp1 � � ���� Person");
	    							
	    							if(	!taskQueue.get(4).trim().equals("") && !taskQueue.get(8).trim().equals("") && !taskQueue.get(9).trim().equals("")	)
		    						{
	    								filename = new MessageA08p16(fileTransfer, res, person).create(); System.out.println("Send A08P16pr the set new sex - "+ filename);
    									if(ul.waitUprak2(filename, "Wait response A03P16pr the set new sex -"+filename))
		    							{
    										filename = new MessageA08p03pr_newDr(fileTransfer, res, person).create(); System.out.println("Send A08P03pr after chanche sex - "+ filename);
	    									if(ul.waitUprak2(filename, "Wait response A03P03pr after chanche sex "+filename))
			    							{
	    										filename = new MessageZp1(fileTransfer, res, person).create();
			    								zp = new ZpLoader().load(filename, taskQueue.get(0),"Wait response ZP1 after A08P03pr after chanche sex -" + filename);
			    								if(ul.checkVsandBythday(zp,taskQueue.get(1),taskQueue.get(0),taskQueue.get(2)))
			    								{
			    									listEnpForDeleteFromExcel.add(taskQueue.get(0));	System.out.println("VSnum is OK after A08P16pr + A08P03pr after chanche sex"+ filename);
		    									}else
		    									{
		    										System.out.println("VSnum is NO after A08P16pr + A08P03pr after chanche sex"+ filename);
		    									}
			    							}
		    							}
		    						}
	    							
	    						}
		    				}
		    				
		    				if(listcol.size()>0){listcol.remove(0);}
		    				
		    				System.out.println("NTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
		    			    return;
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
			queue.add(ba.get(i).get(1));// ��� ����� 0
			queue.add(ba.get(i).get(66));// �� 1
			queue.add(ba.get(i).get(13));// ���� ����� 2
			queue.add(ba.get(i).get(74));// ���� ������ 3
			queue.add(ba.get(i).get(71));// ��� ���� 4
			queue.add(ba.get(i).get(72));// ��� ���� 5
			queue.add(ba.get(i).get(73));// �������� ���� 6
			queue.add(ba.get(i).get(10));// ��� 7
			queue.add(ba.get(i).get(11));// ��� 8
			queue.add(ba.get(i).get(12));// �������� 9
			queue.add("������� �� ��������");
			if(ba.get(i).get(13).equals(ba.get(i).get(74))){	queue.add("����������� �� �� ���������");	}else{	queue.add("����������� ��");	}
			taskQueue.add(queue);
			
		}
		return taskQueue;
    }
    
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
	
		int rowIndex = 0;
		for(int t=0;t< listEnpForDeleteFromExcel.size();t++)
		{
			int lastRowNum=sheet.getLastRowNum();
			String s = listEnpForDeleteFromExcel.get(t);
			for(int y=1;y<=lastRowNum;y++)
			  {
				  excelRow = sheet.getRow(y);
				  // ����� ������� � ��� � ������
				  excelCell = excelRow.getCell(1);
				  String enpfromExcel = excelCell.getStringCellValue();
				  // ����������� ��� �� ��������� � � ������� => ���� ����� �� ������� �������
				  if(s.equals(enpfromExcel))
				  {
					  System.out.println("Posicion row for delete "+excelCell.getRowIndex()+" Enp for deleting "+s+" lastRowNum= "+lastRowNum);
					  rowIndex = excelCell.getRowIndex();
				  }
			  }
			if(rowIndex>=0&&rowIndex<lastRowNum){ sheet.shiftRows(rowIndex+1,lastRowNum, -1);}
			if(rowIndex==lastRowNum)
			{
		        HSSFRow removingRow=sheet.getRow(rowIndex);
		        if(removingRow!=null){sheet.removeRow(removingRow);}
		    }
		}
		/*	
		for(int i=0;i<2;i++){
			int lastRowNum=sheet.getLastRowNum();
			System.out.println("lastRowNum "+ lastRowNum);
			if(2 > lastRowNum){lastRowNum = 2;}
			if(3 > lastRowNum){lastRowNum = 3;}
			sheet.shiftRows(2, 2, -1);
			sheet.shiftRows(3, 3, -1);
		}
		 */
		
	
		System.out.println("Auto �� ������ ����� ���������� = " +listEnpForDeleteFromExcel.size());
		FileOutputStream fileOut = new FileOutputStream(absolutePath);
		wb.write(fileOut);
		fileOut.close();
	}
	
	private boolean equalsInitials(Person p,Data person)
	{
		if(p.getPerson_kindfirstname().equals(person.getPerson().getPerson_kindfirstname())){return true;}
		if(p.getPerson_kindlastname().equals(person.getPerson().getPerson_kindlastname())){return true;}
		if(p.getPerson_surname().equals(person.getPerson().getPerson_surname()))		  {return true;}
		if(p.getPerson_birthday().equals(person.getPerson().getPerson_birthday()))		  {return true;}
		
		return false;
	}
	
	private String getNppZero(ArrayList<Zp>  zp)
	{
		String str = null;
		for(Zp m :zp)
		{
			if(m.getNpp()==0)
			{
				str = m.getPid8();
			}
		}
		
		return str;
	}
}
