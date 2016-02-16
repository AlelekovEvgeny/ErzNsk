package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import pylypiv.errorGZ.main.Data;
import pylypiv.errorGZ.parser.Zp;

public class UtilForErrorGz {

	private String done ="D:\\output\\done\\"; 
	/*
	 * check double respons on zp1
	 * and add in model from db a zp answer or else double response add double
	 */
	public boolean checkRespons(ArrayList<Zp> zp,String enpinput,Data person)
	{
		List<Zp> ls = new ArrayList<Zp>();
		int i = 0;
		for(Zp m : zp)
		{
			if(m.getNpp()==0)
			{
				i ++;
				ls.add(m);
				person.setZpList(ls);
			}
		}
		if(i==2){return false;} else{	return true; }
		
	}
	
	
	/*
	 * Check VS
	 */
	public boolean checkVs(ArrayList<Zp> zp, String vsNum,String enpinput)
	{
		Calendar cal = Calendar.getInstance();
		Date dateNow = cal.getTime();
		int i = 0;
		for(Zp m : zp)
		{
			if(m.getNpp()==0 &&  m.getIn1_35().equals("Â") && m.getIn1_15().equals("50000") && m.getIn1_36().equals(vsNum) && (	m.getIn1_13() == null ||m.getIn1_13().getTime().after(dateNow)	 ))
			{
				//System.out.println("Check is Vsnum OK "+ m.getMsa2()+" "+enpinput+ " "+ m.getPid3cx1_1()+" "+m.getIn1_36()+" "+m.getIn1_35());
				i ++;
				
			}
			
		}
		
		if(i == 0){		System.out.println("Check is Vsnum NO "+enpinput);return false;}	else{return true;}
	}
	
	/*
	 * Check VS and bythday
	 */
	public boolean checkVsandBythday(ArrayList<Zp> zp, String vsNum,String enpinput,String bythday)
	{
		Calendar cal = Calendar.getInstance();
		Date dateNow = cal.getTime();
		int i = 0;
		for(Zp m : zp)
		{
			String responseBythday = new SimpleDateFormat("yyyy-MM-dd").format(m.getPid7().getTime());
			
			if(m.getNpp()==0 &&  m.getIn1_35().equals("Â") && m.getIn1_15().equals("50000") && m.getIn1_36().equals(vsNum) && (	m.getIn1_13() == null ||m.getIn1_13().getTime().after(dateNow)	&& bythday.equals(responseBythday) ))
			{
				//System.out.println("Check is Vsnum OK "+ m.getMsa2()+" "+enpinput+ " "+ m.getPid3cx1_1()+" "+m.getIn1_36()+" "+m.getIn1_35());
				i ++;
				
			}
			
		}
		
		if(i == 0){		System.out.println("Check is Vsnum NO "+enpinput);return false;}	else{return true;}
	}
	
	public boolean waitUprak2(String fileAsk,String status) throws InterruptedException
	{
		String fileAnswer = fileAsk.replaceAll(".uprmes",".uprak2");
		File file = new File(done + fileAnswer);
		
		while(!file.exists()) {
			System.out.println(file+ " - "+ status);
			Thread.sleep(10000);
		}
		
		return true;
	}
	
	
}
