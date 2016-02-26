package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
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
			if(m.getNpp()==0 &&  m.getIn1_35().equals("В") && m.getIn1_15().equals("50000") && m.getIn1_36().equals(vsNum) && (	m.getIn1_13() == null ||m.getIn1_13().getTime().after(dateNow)	 ))
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
			
			if(m.getNpp()==0 &&  m.getIn1_35().equals("В") && m.getIn1_15().equals("50000") && m.getIn1_36().equals(vsNum) && (	m.getIn1_13() == null ||m.getIn1_13().getTime().after(dateNow)	&& bythday.equals(responseBythday) ))
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
	
	@SuppressWarnings("deprecation")
	public  String enp_calc(String person_dr,int sex) throws ParseException
	{
		person_dr = person_dr.replaceAll("-", ".");
		Date date = new Date();
		Date date1880 = new Date();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		date1880 = df.parse("01.01.1880");
		
		if(!person_dr.equals("")){
			date = df.parse(person_dr);
		}
		else{	return ""; 	}
		
		if(date.after(new Date()) || date.before(date1880))
		{
			return "";
		}
		
		int d = date.getDate();
		int m = date.getMonth()+1;
		int y = date.getYear()+1900;
		
		if (d>0 && d<32 && m>0 && m <13 && y > 0){
	       if (y <= 1950){m=m+20;}
	       else
	       {
	       		if (y <=2000) {m=m+40;}
	       }
		}
		
		int cd;
		// правило расчета. Наращиваем коэфициент 50
		if(sex == 1 ){cd = d+50;}else{cd = d;}
		
		int cm = m;
		int cy = y;
		
		
		String stcd = process(cd);
		String stcm = process(cm);
		String stcy = processYear(cy);

		String faset = stcm + stcy + stcd;
		return faset;
	}

	/*
	 * Метод расчитывает преобразование даты (два варианта)
	 *    входит 05
		  1.	9 - 0 = 9
		  2.	9 - 5 = 4
		  3.	94
		  первый шаг пропускаем тк 9 - 0 всегда 9
		  
		  входит 56
		   1.	9 - 5 = 4
		   2.	9 - 6 = 3
		   3.	43
	 */
	private String process(int cd)
	{
		String st = "";
		
		if(cd<10)
		{
			cd = 9 - cd;
			st = "9"+String.valueOf(cd);
		}
		else
		{
			st = String.valueOf(	9 - Integer.valueOf(String.valueOf(cd).substring(0, 1))	) + String.valueOf(	9 - Integer.valueOf(String.valueOf(cd).substring(1, 2))	);
			
		}
		
		return st;
	}
	
	private String processYear(int cd)
	{
		String st = "";
		
		st = String.valueOf(	9 - Integer.valueOf(String.valueOf(cd).substring(3, 4))	) +
				String.valueOf(	9 -  Integer.valueOf(String.valueOf(cd).substring(2, 3))	)  +
				String.valueOf(	9 - Integer.valueOf(String.valueOf(cd).substring(1, 2))	)  +	
				String.valueOf(	9 - Integer.valueOf(String.valueOf(cd).substring(0, 1))	);  		
		
		return st;
	}
	
	

}
