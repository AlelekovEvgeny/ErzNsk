package message;

import file.FileTransferMock;
import file.TimeTaskImpl;
import help.Const;
import help.RandomGUID;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import oracle.ConnectionPoolOracle;
import oracle.TaskOracle;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import util.ConstantiNastrojki;

public abstract class MessageCommon implements Message {
	
	int PERSON_SERDOC = 0, PERSON_NUMDOC = 1, PERSON_DOCPERSONID = 2, PERSON_SURNAME = 3, PERSON_KINDFIRSTNAME = 4,
			PERSON_KINDLASTNAME = 5, PERSON_BIRTHDAY = 6, PERSON_SEX = 7, PERSON_LINKSMOESTABLISHMENTID = 8, ENP = 9, PERSON_ADDRESSID = 10,
			PERSON_DATEINPUT = 11, SNILS = 12, BORN = 13, DATEPASSPORT = 14, ENP_PA = 15, VS_NUM = 16, VS_DATE = 17, ZAD = 18, D2 = 19, SMO = 20,
			D_12 = 21, D_13 = 22, OKATO_3 = 23, TYPE_POL = 24, POL = 25, ENP_1 = 26, ENP_2 = 27, P14CX1 = 28, P14CX5 = 29, P14CX6 = 30, P14CX7 = 31,
			XPN1 = 32, XPN2 = 33, XPN3 = 34, USERNAME = 35, ZADMINUS1 = 36, ZADPLUS40 = 37, NBLANC = 38, VS_DATEPLUS1 = 39, USER_ENP = 40,
			USER_PERSON_SURNAME = 41, USER_PERSON_KINDFIRSTNAME = 42, USER_PERSON_KINDLASTNAME = 43, USER_SMO = 44, USER_D_12 = 45, USER_D_13 = 46, 
			USER_OKATO_3 = 47, USER_TYPE_POL = 48, USER_POL = 49, RUSSIAN = 50, D_V = 51, D_SER = 52, D_NUM = 53, PR_FAM = 54, PR_IM = 55, PR_OT = 56,
			LAST_FAM = 57, LAST_IM = 58, LAST_OT = 59, LAST_DR = 60, PFR_SNILS = 61, PFR_ID = 62, PFR_NOTID = 63, USER_SERDOC = 64, USER_NUMDOC = 65,
			USER_DOCID = 66, USER_DOC_DATE = 67, D_12_PLUS1 = 68;
	

	
	protected ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
	protected ArrayList<ArrayList<String>> personEnpGuid = new ArrayList<ArrayList<String>>();
	protected ArrayList<String> personEnpGuidRow;
	protected ArrayList<ArrayList<String>> personEnpOutput = new ArrayList<ArrayList<String>>();
	protected ArrayList<String> personEnpOutputRow;
	
	TaskOracle taskOracle = new TaskOracle();

	protected RandomGUID guidBhs;
	
	public ArrayList<ArrayList<String>> getPersonEnpGuid() {
		return personEnpGuid;
	}

	@Override
	public RandomGUID getGuidBhs() {
		return guidBhs;
	}

	@Override
	public ArrayList<ArrayList<String>> getDataList() {
		return dataList;
	}
	
	@Override
	public ArrayList<ArrayList<String>> getDataList(String userMachine) {
		prepareData(userMachine);
		return dataList;
	}

	@Override
	public boolean create(String userMachine) {
		
		long before = System.currentTimeMillis();
		
		prepareData(userMachine);
		int count = dataList.size();
		if(count < 10) { personEnpGuid.clear(); personEnpOutput.clear(); }
	
		Namespace namespaceXsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		Namespace namespaceRtc = Namespace.getNamespace("rtc", "http://www.rintech.ru");
		Namespace namespaceXsd = Namespace.getNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
		Namespace namespace = Namespace.getNamespace("", "urn:hl7-org:v2xml");
		
		Element rootElement = new Element("UPRMessageBatch", namespace);
		rootElement.addNamespaceDeclaration(namespaceXsi);
		rootElement.addNamespaceDeclaration(namespaceRtc);
		rootElement.addNamespaceDeclaration(namespaceXsd);
		Document doc = new Document(rootElement);
		
		Element bhs = new Element("BHS", namespace);
		rootElement.addContent(bhs);
		bhs.addContent(new Element("BHS.1", namespace).addContent("|"));
		bhs.addContent(new Element("BHS.2", namespace).addContent("^~\\&"));
		bhs.addContent(new Element("BHS.3", namespace).addContent(new Element("HD.1", namespace).addContent("СРЗ 54")));
		
		Element bhs4 = new Element("BHS.4", namespace);
		bhs.addContent(bhs4);
		bhs4.addContent(new Element("HD.1", namespace).addContent("54"));
		bhs4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		bhs4.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		bhs.addContent(new Element("BHS.5", namespace).addContent(new Element("HD.1", namespace).addContent("ЦК ЕРП")));
		Element bhs6 = new Element("BHS.6", namespace);
		bhs.addContent(bhs6);
		bhs6.addContent(new Element("HD.1", namespace).addContent("00"));
		bhs6.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		bhs6.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		String curDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()) + "+7:00";	
		bhs.addContent(new Element("BHS.7", namespace).addContent(curDate));
		guidBhs = new RandomGUID();
		bhs.addContent(new Element("BHS.11", namespace).addContent(guidBhs.toString()));
		
		
		/*
		 * создаем сам запрос
		 * Бежим по коллекции (снятые данные из сложного запроса) 
		 */
		createMiddle(count, namespace, rootElement, curDate);
		
		long timeGiud0 = System.currentTimeMillis();
		

		rememberGuid();
		long timeGiud1 = System.currentTimeMillis();
		System.out.println("timeGiud - " + (timeGiud1 - timeGiud0));
		if(count < 10) { 
			long timeArchive0 = System.currentTimeMillis();
			writeToArchive();
			long timeArchive1 = System.currentTimeMillis();
			System.out.println("timeArchive - " + (timeArchive1 - timeArchive0));
		}
		
		Element bts = new Element("BTS", namespace);
		rootElement.addContent(bts);
		bts.addContent(new Element("BTS.1", namespace).addContent(String.valueOf(count - 1)));
		bts.addContent(new Element("BTS.3", namespace).addContent(String.valueOf(rootElement.hashCode())));

		try {
		    XMLOutputter outputter = new XMLOutputter();
		    Format format = Format.getPrettyFormat();
	        format.setEncoding("Windows-1251");
		    outputter.setFormat(format);
		    FileWriter fw = new FileWriter(Const.PROGRAM_PATH + "50000-" + guidBhs + ".uprmes");
		    outputter.output(doc, fw);
		    fw.close();
		   // отладка xml 
		    System.out.println("ConstantiNastrojki "+ConstantiNastrojki.otladkaXML);
		      if(ConstantiNastrojki.otladkaXML.equals("0"))
		    {  	
			    new FileTransferMock().copy(Const.PROGRAM_PATH + "50000-" + guidBhs + ".uprmes", Const.AUTO_PATH  + "50000-" + guidBhs + ".uprmes");
			    new FileTransferMock().delete(Const.PROGRAM_PATH + "50000-" + guidBhs + ".uprmes");
		    }    
		} catch (Exception ex) {
		    System.out.println(ex.getMessage());
		}

		long after = System.currentTimeMillis();
		long diff = (int)(after - before) / count;
		System.out.println("All - " + diff * count + " one - " + diff);
		new TimeTaskImpl().oneTimeTasksWrite(diff);
		
		return true;
	}

	protected abstract void createMiddle(int count, Namespace namespace,
			Element rootElement, String curDate);
	
	protected abstract void createMiddle(int count, Namespace namespace,
			Element rootElement, String curDate,boolean tt);
	
	protected abstract void createMiddle(int count, Namespace namespace,Element rootElement, String curDate,boolean tt,String kluch);

	@Override
	public boolean prepareData(String userMachine, ArrayList<ArrayList<String>> listList1) {
		// зачищаем от предыдущих дынных
		dataList.clear();
		dataList = listList1;
		System.out.println("ТЕСТ@@@ "+ dataList);
		return true;
	}
	
	@Override
	public boolean rememberGuid() {
		Statement statement = null;
		Connection conn = null;
		try {
			conn = ConnectionPoolOracle.setUp().getConnection();
			statement = conn.createStatement();
			for (int i = 0; i < personEnpGuid.size(); i++) 
			{
				taskOracle.deleteGuid(statement, personEnpGuid.get(i).get(0));
			}
			
			for (int i = 0; i < personEnpGuid.size(); i++)
			{
				taskOracle.insertGuid(statement, personEnpGuid.get(i).get(0), personEnpGuid.get(i).get(1).replace("-", ""));
				System.out.println("rememberGuid" + i);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { statement.close();
			conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	@Override
	public boolean writeToArchive() 
	{
		Statement statement = null;
		Connection conn = null;
		try {
			conn = ConnectionPoolOracle.setUp().getConnection();
			statement = conn.createStatement();
			for (int i = 0; i < personEnpOutput.size(); i++) {
				taskOracle.insertToPersonEnpOutput(statement, personEnpOutput.get(i).get(0), personEnpOutput.get(i).get(1).replace("-", "")
						, personEnpOutput.get(i).get(2), personEnpOutput.get(i).get(3));
				System.out.println("write to archive - " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { statement.close();
			conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	void listGuid(Object guidMsh, int i, String mes_type) {		
		personEnpGuidRow = new ArrayList<String>();	
		personEnpGuidRow.add(dataList.get(i).get(ENP));
		personEnpGuidRow.add(guidMsh.toString());
		personEnpGuid.add(personEnpGuidRow);
		
		personEnpOutputRow = new ArrayList<String>();	
		personEnpOutputRow.add(dataList.get(i).get(ENP));
		personEnpOutputRow.add(guidMsh.toString());
		personEnpOutputRow.add(mes_type);
		personEnpOutputRow.add(dataList.get(i).get(ENP_PA));
		personEnpOutput.add(personEnpOutputRow);
	}

	public ArrayList<ArrayList<String>> getPersonEnpOutput() {
		return personEnpOutput;
	}
	
	protected RandomGUID createMsh(Namespace namespace, String curDate,
			Element rootEl, String msg1, String msg2, String msg3) {
		Element msh = new Element("MSH", namespace);
		rootEl.addContent(msh);
		msh.addContent(new Element("MSH.1", namespace).addContent("|"));
		msh.addContent(new Element("MSH.2", namespace).addContent("^~\\&"));
		msh.addContent(new Element("MSH.3", namespace)
		.addContent(new Element("HD.1", namespace).addContent("СРЗ 54")));
		Element msh4 = new Element("MSH.4", namespace);
		msh.addContent(msh4);
		msh4.addContent(new Element("HD.1", namespace).addContent("54"));
		msh4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		msh4.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		msh.addContent(new Element("MSH.5", namespace)
		.addContent(new Element("HD.1", namespace).addContent("ЦК ЕРП")));
		Element msh6 = new Element("MSH.6", namespace);
		msh.addContent(msh6);
		msh6.addContent(new Element("HD.1", namespace).addContent("00"));
		msh6.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		msh6.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		msh.addContent(new Element("MSH.7", namespace).addContent(curDate));
		Element msh9 = new Element("MSH.9", namespace);
		msh.addContent(msh9);
		msh9.addContent(new Element("MSG.1", namespace).addContent(msg1));
		msh9.addContent(new Element("MSG.2", namespace).addContent(msg2));
		msh9.addContent(new Element("MSG.3", namespace).addContent(msg3));
		RandomGUID guidMsh = new RandomGUID();	
		// for debug 
		msh.addContent(new Element("MSH.10", namespace).addContent(guidMsh.toString()));	
		msh.addContent(new Element("MSH.11", namespace)
		.addContent(new Element("PT.1", namespace).addContent("P")));
		msh.addContent(new Element("MSH.12", namespace)
		.addContent(new Element("VID.1", namespace).addContent("2.6")));
		msh.addContent(new Element("MSH.15", namespace).addContent("AL"));	
		msh.addContent(new Element("MSH.16", namespace).addContent("AL"));
		return guidMsh;
	}
	
	protected void createPid(Namespace namespace, int i, Element rootEl) {
		Element pid = new Element("PID", namespace);
		rootEl.addContent(pid);
		
		Element pid3_1 = new Element("PID.3", namespace);
		pid.addContent(pid3_1);
		String seria = dataList.get(i).get(PERSON_SERDOC);
		pid3_1.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " № ") + dataList.get(i).get(PERSON_NUMDOC)));
		pid3_1.addContent(new Element("CX.5", namespace).addContent(dataList.get(i).get(PERSON_DOCPERSONID)));
		//pid3_1.addContent(new Element("CX.5", namespace).addContent("9"));
		pid3_1.addContent(new Element("CX.7", namespace).addContent(dataList.get(i).get(DATEPASSPORT)));
		pid3_1.addContent(new Element("CX.8", namespace).addContent(dataList.get(i).get(D2)));
		
		
		if (dataList.get(i).get(PERSON_DOCPERSONID).equals("9")) {
			Element pid3_2 = new Element("PID.3", namespace);
			pid.addContent(pid3_2);
			pid3_2.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " № ") + dataList.get(i).get(PERSON_NUMDOC)));
			pid3_2.addContent(new Element("CX.5", namespace).addContent("23"));
			pid3_2.addContent(new Element("CX.7", namespace).addContent(dataList.get(i).get(DATEPASSPORT)));
			pid3_2.addContent(new Element("CX.8", namespace).addContent(dataList.get(i).get(D2)));
		}
		
		if (dataList.get(i).get(PERSON_DOCPERSONID).equals("21")) {
			Element pid3_5 = new Element("PID.3", namespace);
			pid.addContent(pid3_5);
			pid3_5.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " № ") + dataList.get(i).get(PERSON_NUMDOC)));
			pid3_5.addContent(new Element("CX.5", namespace).addContent("23"));
			String d2 = dataList.get(i).get(D2);
			String d2minus3year ="";
			if(!d2.equals("")) {	d2minus3year = String.valueOf(Integer.parseInt(d2.substring(0, 4)) - 3) + d2.substring(4);}
			pid3_5.addContent(new Element("CX.7", namespace).addContent(d2minus3year));
			pid3_5.addContent(new Element("CX.8", namespace).addContent(dataList.get(i).get(D2)));
		}

		/*if (!"".equals(dataList.get(i).get(SNILS))) {	
			Element pid3_3 = new Element("PID.3", namespace);
			pid.addContent(pid3_3);
			pid3_3.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(SNILS)));
			pid3_3.addContent(new Element("CX.5", namespace).addContent("PEN"));
		}*/
					
		Element pid3_4 = new Element("PID.3", namespace);
		pid.addContent(pid3_4);
		if ("".equals(dataList.get(i).get(ENP_PA))) {
			pid3_4.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(ENP)));
		} else {
			pid3_4.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(ENP_PA)));
		}
		Element pid3_4cx4 = new Element("CX.4", namespace);
		pid3_4.addContent(pid3_4cx4);
		pid3_4cx4.addContent(new Element("HD.1", namespace).addContent("50000"));
		pid3_4cx4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1"));
		pid3_4cx4.addContent(new Element("HD.3", namespace).addContent("ISO"));
		pid3_4.addContent(new Element("CX.5", namespace).addContent("NI"));
				
		Element pid5 = new Element("PID.5", namespace);
		pid.addContent(pid5);
		pid5.addContent(new Element("XPN.1", namespace)
		.addContent(new Element("FN.1", namespace).addContent(dataList.get(i).get(PERSON_SURNAME))));
		pid5.addContent(new Element("XPN.2", namespace).addContent(dataList.get(i).get(PERSON_KINDFIRSTNAME)));
		
		if(dataList.get(i).get(PERSON_KINDLASTNAME).equals("-") || dataList.get(i).get(PERSON_KINDLASTNAME).equals("НЕТ")){		pid5.addContent(new Element("XPN.3", namespace).addContent(""));	}
		else
		{	pid5.addContent(new Element("XPN.3", namespace).addContent(dataList.get(i).get(PERSON_KINDLASTNAME)));	}	
		
		
		//pid5.addContent(new Element("XPN.3", namespace).addContent(dataList.get(i).get(PERSON_KINDLASTNAME)));

		pid.addContent(new Element("PID.7", namespace).addContent(dataList.get(i).get(PERSON_BIRTHDAY)));
		
		pid.addContent(new Element("PID.8", namespace).addContent(dataList.get(i).get(PERSON_SEX)));
		
		pid.addContent( new Element("PID.23", namespace).addContent(dataList.get(i).get(BORN)));

		Element pid26 = new Element("PID.26", namespace);
		pid.addContent(pid26);
		pid26.addContent(new Element("CWE.1", namespace).addContent(dataList.get(i).get(RUSSIAN)));
		pid26.addContent(new Element("CWE.3", namespace).addContent("1.2.643.2.40.5.0.25.3"));

		if (dataList.get(i).get(PERSON_DOCPERSONID).equals("1")) {		
			pid.addContent(new Element("PID.32", namespace).addContent("7"));
		}
	}
	
	@Override
	public boolean create(String userMachine,ArrayList<ArrayList<String>> listList1,String kluch) {
	
		if(kluch.equals("list1enpzp9"))
		{
			prepareDataQukly2(userMachine,listList1.toString());
		}
		else
		{
			if(kluch.equals("list1passportzp9"))
			{
				prepareDataQukly(userMachine,listList1.toString());	
			}
			else
			{
				prepareData(userMachine,listList1);
			}

		}
		
		int count = dataList.size();
		if(count < 10) { personEnpGuid.clear(); personEnpOutput.clear(); }
	
		System.out.println("Тесттттттттт  "+ENP);
		
		Namespace namespaceXsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		Namespace namespaceRtc = Namespace.getNamespace("rtc", "http://www.rintech.ru");
		Namespace namespaceXsd = Namespace.getNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
		Namespace namespace = Namespace.getNamespace("", "urn:hl7-org:v2xml");
		
		Element rootElement = new Element("UPRMessageBatch", namespace);
		rootElement.addNamespaceDeclaration(namespaceXsi);
		rootElement.addNamespaceDeclaration(namespaceRtc);
		rootElement.addNamespaceDeclaration(namespaceXsd);
		Document doc = new Document(rootElement);
		
		Element bhs = new Element("BHS", namespace);
		rootElement.addContent(bhs);
		bhs.addContent(new Element("BHS.1", namespace).addContent("|"));
		bhs.addContent(new Element("BHS.2", namespace).addContent("^~\\&"));
		bhs.addContent(new Element("BHS.3", namespace).addContent(new Element("HD.1", namespace).addContent("СРЗ 54")));
		
		Element bhs4 = new Element("BHS.4", namespace);
		bhs.addContent(bhs4);
		bhs4.addContent(new Element("HD.1", namespace).addContent("54"));
		bhs4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		bhs4.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		bhs.addContent(new Element("BHS.5", namespace).addContent(new Element("HD.1", namespace).addContent("ЦК ЕРП")));
		Element bhs6 = new Element("BHS.6", namespace);
		bhs.addContent(bhs6);
		bhs6.addContent(new Element("HD.1", namespace).addContent("00"));
		bhs6.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		bhs6.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		String curDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()) + "+7:00";	
		bhs.addContent(new Element("BHS.7", namespace).addContent(curDate));
		guidBhs = new RandomGUID();
		bhs.addContent(new Element("BHS.11", namespace).addContent(guidBhs.toString()));
		
		
		/*
		 * создаем сам запрос
		 * Бежим по коллекции (снятые данные из сложного запроса)
		 * Аргумент true требуется для определения по какому принципу строится xml'ка (старый - Пономарев А) новый Пылыпив C
		 * разница заключается в порядке столбцов коллекции из которой берутся данные для формирования xml  
		 */
		if(kluch.equals("list1enpzp9"))
		{
			createMiddle(count, namespace, rootElement, curDate,true,"list1enpzp9");
			count = count - 1;
		}
		else
		{
			if(kluch.equals("list1passportzp9"))
			{
				
				createMiddle(count, namespace, rootElement, curDate,true,"list1passportzp9");
				count = count - 1;
			}
			else
			{
				createMiddle(count, namespace, rootElement, curDate,true);
			}
		}
		
		

		Element bts = new Element("BTS", namespace);
		rootElement.addContent(bts);
		bts.addContent(new Element("BTS.1", namespace).addContent(String.valueOf(count)));
		bts.addContent(new Element("BTS.3", namespace).addContent(String.valueOf(rootElement.hashCode())));

		try {
		    XMLOutputter outputter = new XMLOutputter();
		    Format format = Format.getPrettyFormat();
	        format.setEncoding("Windows-1251");
		    outputter.setFormat(format);
		    FileWriter fw = new FileWriter(Const.PROGRAM_PATH + "50000-" + guidBhs + ".uprmes");
		    outputter.output(doc, fw);
		    fw.close();
		   // отладка xml 
		    System.out.println("ConstantiNastrojki "+ConstantiNastrojki.otladkaXML);
		      if(ConstantiNastrojki.otladkaXML.equals("0"))
		    {  	
			    new FileTransferMock().copy(Const.PROGRAM_PATH + "50000-" + guidBhs + ".uprmes", Const.AUTO_PATH  + "50000-" + guidBhs + ".uprmes");
			    new FileTransferMock().delete(Const.PROGRAM_PATH + "50000-" + guidBhs + ".uprmes");
		    }    
		} catch (Exception ex) {
		    System.out.println(ex.getMessage());
		}
		return true;
	}
	
	@Override
	public boolean prepareData(String userMachine) {
		long timePrepare0 = System.currentTimeMillis();
		dataList.clear();
		
		Statement statement = null;
		Connection conn = null;
		ResultSet resultSet = null;
		
		try {
			long timeSelect0 = System.currentTimeMillis();
			conn = ConnectionPoolOracle.setUp().getConnection();
			statement = conn.createStatement();
			
			// ДЕЛАЕМ запрос select из таблиц
			resultSet = new TaskOracle().selectData(statement, userMachine);
			System.out.println("ОТРАБОТАЛ БОЛЬШОЙ ЗАПРОС");
			// берем мета данные 
			ResultSetMetaData metaData = resultSet.getMetaData();
			long timeSelect1 = System.currentTimeMillis();
			
			System.out.println("timeSelect - " + (timeSelect1 - timeSelect0));
			
			int prepareData = 0;
			System.out.println(prepareData = 1);
			
			int numcols = metaData.getColumnCount();
			ArrayList<String> dataListRow;
			
			dataListRow = new ArrayList<String>();
			for (int i = 0; i < numcols; i++) 
			{
				dataListRow.add(metaData.getColumnLabel(i + 1));
			}
			dataList.add(dataListRow);
			
			while (resultSet.next())
			{
				prepareData++;
				
				dataListRow = new ArrayList<String>();
				
				for (int i = 0; i < numcols; i++) {
					if (resultSet.getObject(i + 1) != null && !"null".equals(resultSet.getObject(i + 1)))
					{
						if (i == PERSON_BIRTHDAY || i == PERSON_DATEINPUT || i == DATEPASSPORT || i == VS_DATE || i == ZAD || i == D2
								|| i == D_12 || i == D_13 || i == ZADMINUS1 || i == ZADPLUS40 || i == VS_DATEPLUS1 || i == USER_D_12 || i == USER_D_13
								|| i == LAST_DR || i == USER_DOC_DATE)
						{
							dataListRow.add(new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getDate(i + 1)));
						} else {
							dataListRow.add(resultSet.getString(i + 1));
						}
					} 
					else
					{
						dataListRow.add("");
					}
				}
				dataList.add(dataListRow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try
			{
				resultSet.close();
				statement.close();
				  conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		for(int i=0;i<dataList.size();i++)
		{
			System.out.println("Rjkk "+ dataList.get(i));
		}
		long timePrepare1 = System.currentTimeMillis();
		System.out.println("timePrepare - " + (timePrepare1 - timePrepare0));
		return true;
	}

	public MessageCommon(int pERSON_SERDOC, int pERSON_NUMDOC,
			int pERSON_DOCPERSONID, int pERSON_SURNAME,
			int pERSON_KINDFIRSTNAME, int pERSON_KINDLASTNAME,
			int pERSON_BIRTHDAY, int pERSON_SEX,
			int pERSON_LINKSMOESTABLISHMENTID, int eNP, int pERSON_ADDRESSID,
			int pERSON_DATEINPUT, int sNILS, int bORN, int dATEPASSPORT,
			int eNP_PA, int vS_NUM, int vS_DATE, int zAD, int d2, int sMO,
			int d_12, int d_13, int oKATO_3, int tYPE_POL, int pOL, int eNP_1,
			int eNP_2, int p14cx1, int p14cx5, int p14cx6, int p14cx7,
			int xPN1, int xPN2, int xPN3, int uSERNAME, int zADMINUS1,
			int zADPLUS40, int nBLANC, int vS_DATEPLUS1, int uSER_ENP,
			int uSER_PERSON_SURNAME, int uSER_PERSON_KINDFIRSTNAME,
			int uSER_PERSON_KINDLASTNAME, int uSER_SMO, int uSER_D_12,
			int uSER_D_13, int uSER_OKATO_3, int uSER_TYPE_POL, int uSER_POL,
			int rUSSIAN, int d_V, int d_SER, int d_NUM, int pR_FAM, int pR_IM,
			int pR_OT, int lAST_FAM, int lAST_IM, int lAST_OT, int lAST_DR,
			int pFR_SNILS, int pFR_ID, int pFR_NOTID, int uSER_SERDOC,
			int uSER_NUMDOC, int uSER_DOCID, int uSER_DOC_DATE, int d_12_PLUS1) {
		
		PERSON_SERDOC = pERSON_SERDOC;
		PERSON_NUMDOC = pERSON_NUMDOC;
		PERSON_DOCPERSONID = pERSON_DOCPERSONID;
		PERSON_SURNAME = pERSON_SURNAME;
		PERSON_KINDFIRSTNAME = pERSON_KINDFIRSTNAME;
		PERSON_KINDLASTNAME = pERSON_KINDLASTNAME;
		PERSON_BIRTHDAY = pERSON_BIRTHDAY;
		PERSON_SEX = pERSON_SEX;
		PERSON_LINKSMOESTABLISHMENTID = pERSON_LINKSMOESTABLISHMENTID;
		ENP = eNP;
		PERSON_ADDRESSID = pERSON_ADDRESSID;
		PERSON_DATEINPUT = pERSON_DATEINPUT;
		SNILS = sNILS;
		BORN = bORN;
		DATEPASSPORT = dATEPASSPORT;
		ENP_PA = eNP_PA;
		VS_NUM = vS_NUM;
		VS_DATE = vS_DATE;
		ZAD = zAD;
		D2 = d2;
		SMO = sMO;
		D_12 = d_12;
		D_13 = d_13;
		OKATO_3 = oKATO_3;
		TYPE_POL = tYPE_POL;
		POL = pOL;
		ENP_1 = eNP_1;
		ENP_2 = eNP_2;
		P14CX1 = p14cx1;
		P14CX5 = p14cx5;
		P14CX6 = p14cx6;
		P14CX7 = p14cx7;
		XPN1 = xPN1;
		XPN2 = xPN2;
		XPN3 = xPN3;
		USERNAME = uSERNAME;
		ZADMINUS1 = zADMINUS1;
		ZADPLUS40 = zADPLUS40;
		NBLANC = nBLANC;
		VS_DATEPLUS1 = vS_DATEPLUS1;
		USER_ENP = uSER_ENP;
		USER_PERSON_SURNAME = uSER_PERSON_SURNAME;
		USER_PERSON_KINDFIRSTNAME = uSER_PERSON_KINDFIRSTNAME;
		USER_PERSON_KINDLASTNAME = uSER_PERSON_KINDLASTNAME;
		USER_SMO = uSER_SMO;
		USER_D_12 = uSER_D_12;
		USER_D_13 = uSER_D_13;
		USER_OKATO_3 = uSER_OKATO_3;
		USER_TYPE_POL = uSER_TYPE_POL;
		USER_POL = uSER_POL;
		RUSSIAN = rUSSIAN;
		D_V = d_V;
		D_SER = d_SER;
		D_NUM = d_NUM;
		PR_FAM = pR_FAM;
		PR_IM = pR_IM;
		PR_OT = pR_OT;
		LAST_FAM = lAST_FAM;
		LAST_IM = lAST_IM;
		LAST_OT = lAST_OT;
		LAST_DR = lAST_DR;
		PFR_SNILS = pFR_SNILS;
		PFR_ID = pFR_ID;
		PFR_NOTID = pFR_NOTID;
		USER_SERDOC = uSER_SERDOC;
		USER_NUMDOC = uSER_NUMDOC;
		USER_DOCID = uSER_DOCID;
		USER_DOC_DATE = uSER_DOC_DATE;
		D_12_PLUS1 = d_12_PLUS1;
	}
	
	public MessageCommon(){}

	@Override	
	 public boolean create(String userMachine, String stList) 
	 {

		System.out.println("непонятно "+ personEnpGuid+"   "+ personEnpOutput);
		prepareData(userMachine,stList);
		int count = dataList.size();
		if(count < 10) { personEnpGuid.clear(); personEnpOutput.clear(); }
	
		Namespace namespaceXsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		Namespace namespaceRtc = Namespace.getNamespace("rtc", "http://www.rintech.ru");
		Namespace namespaceXsd = Namespace.getNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
		Namespace namespace = Namespace.getNamespace("", "urn:hl7-org:v2xml");
		
		Element rootElement = new Element("UPRMessageBatch", namespace);
		rootElement.addNamespaceDeclaration(namespaceXsi);
		rootElement.addNamespaceDeclaration(namespaceRtc);
		rootElement.addNamespaceDeclaration(namespaceXsd);
		Document doc = new Document(rootElement);
		
		Element bhs = new Element("BHS", namespace);
		rootElement.addContent(bhs);
		bhs.addContent(new Element("BHS.1", namespace).addContent("|"));
		bhs.addContent(new Element("BHS.2", namespace).addContent("^~\\&"));
		bhs.addContent(new Element("BHS.3", namespace).addContent(new Element("HD.1", namespace).addContent("СРЗ 54")));
		
		Element bhs4 = new Element("BHS.4", namespace);
		bhs.addContent(bhs4);
		bhs4.addContent(new Element("HD.1", namespace).addContent("54"));
		bhs4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		bhs4.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		bhs.addContent(new Element("BHS.5", namespace).addContent(new Element("HD.1", namespace).addContent("ЦК ЕРП")));
		Element bhs6 = new Element("BHS.6", namespace);
		bhs.addContent(bhs6);
		bhs6.addContent(new Element("HD.1", namespace).addContent("00"));
		bhs6.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		bhs6.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		String curDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()) + "+7:00";	
		bhs.addContent(new Element("BHS.7", namespace).addContent(curDate));
		guidBhs = new RandomGUID();
		bhs.addContent(new Element("BHS.11", namespace).addContent(guidBhs.toString()));
		
		
		/*
		 * создаем сам запрос
		 * Бежим по коллекции (снятые данные из сложного запроса) 
		 */
		createMiddle(count, namespace, rootElement, curDate);
		
		Element bts = new Element("BTS", namespace);
		rootElement.addContent(bts);
		bts.addContent(new Element("BTS.1", namespace).addContent(String.valueOf(count - 1)));
		bts.addContent(new Element("BTS.3", namespace).addContent(String.valueOf(rootElement.hashCode())));

		try {
		    XMLOutputter outputter = new XMLOutputter();
		    Format format = Format.getPrettyFormat();
	        format.setEncoding("Windows-1251");
		    outputter.setFormat(format);
		    FileWriter fw = new FileWriter(Const.PROGRAM_PATH + "50000-" + guidBhs + ".uprmes");
		    outputter.output(doc, fw);
		    fw.close();
		   // отладка xml 
		    System.out.println("ConstantiNastrojki "+ConstantiNastrojki.otladkaXML);
		      if(ConstantiNastrojki.otladkaXML.equals("0"))
		    {  	
			    new FileTransferMock().copy(Const.PROGRAM_PATH + "50000-" + guidBhs + ".uprmes", Const.AUTO_PATH  + "50000-" + guidBhs + ".uprmes");
			    new FileTransferMock().delete(Const.PROGRAM_PATH + "50000-" + guidBhs + ".uprmes");
		    }    
		} catch (Exception ex) {
		    System.out.println(ex.getMessage());
		}
		
		
		return true;
	 }
	
	public boolean prepareData(String userMachine,String stList) 
	 {
	    // очищаем старую коллекцию со старым содержимым зп1
		dataList.clear();
		// преобразуем вошедшую строку в коллекцию. Это данные с листа1 (енп) 
		ArrayList<String> parseStList = new ArrayList<String>();
		String []vr = stList.split(",");
		for (int i = 0; i < vr.length; i++) 
		{
			String vrm = vr[i].replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("Zp1Ajax:", "");
			parseStList.add(vrm);
		}
		
		
		Statement statement = null;
		Connection conn = null;
		ResultSet resultSet = null;
		
		try {
			conn = ConnectionPoolOracle.setUp().getConnection();
			statement = conn.createStatement();
			
			// ДЕЛАЕМ запрос select из таблиц
			resultSet = new TaskOracle().selectDataForZPAjax(statement, userMachine,parseStList);
			System.out.println("ОТРАБОТАЛ БОЛЬШОЙ ЗАПРОС");
			// берем мета данные  
			
			ResultSetMetaData metaData = resultSet.getMetaData();
			int prepareData = 1;

			// запаковываем первую строку с названиями столбцов
			int numcols = metaData.getColumnCount();
			ArrayList<String> dataListRow = new ArrayList<String>();
			for (int i = 0; i < numcols; i++) 
			{
				dataListRow.add(metaData.getColumnLabel(i + 1));
			}
			// добовляем (почему- то при прогрузке на второй лист это помогло вывести  номер MSA)
			dataListRow.add("MSA");
			dataList.add(dataListRow);
			
			
			// вытаскием остальные значания построчено
			while (resultSet.next())
			{
			prepareData++;
				
				dataListRow = new ArrayList<String>();
				
				for (int i = 0; i < numcols; i++) {
					if (resultSet.getObject(i + 1) != null && !"null".equals(resultSet.getObject(i + 1)))
					{
						if (i == PERSON_BIRTHDAY || i == PERSON_DATEINPUT || i == DATEPASSPORT || i == VS_DATE || i == ZAD || i == D2
								|| i == D_12 || i == D_13 || i == ZADMINUS1 || i == ZADPLUS40 || i == VS_DATEPLUS1 || i == USER_D_12 || i == USER_D_13
								|| i == LAST_DR || i == USER_DOC_DATE)
						{
							dataListRow.add(new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getDate(i + 1)));
						} else {
							dataListRow.add(resultSet.getString(i + 1));
						}
					} 
					else
					{
						dataListRow.add("");
					}
				}
				dataList.add(dataListRow);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try
			{
				resultSet.close();
				statement.close();
				  conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	 }
	
	/*
	 * Переопределяем для ZP1Ajax БЫСТРЫЙ ЗАПРОС !!!!!
	 * (non-Javadoc)
	 * @see message.Message#create(java.lang.String)
	 */
	@Override	
	public boolean create(String userMachine, String stList, int r) 
	{
		
		System.out.println("непонятно "+ personEnpGuid+"   "+ personEnpOutput);
		prepareDataQukly(userMachine,stList);
		int count = dataList.size();
		if(count < 10) { personEnpGuid.clear(); personEnpOutput.clear(); }
	
		Namespace namespaceXsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		Namespace namespaceRtc = Namespace.getNamespace("rtc", "http://www.rintech.ru");
		Namespace namespaceXsd = Namespace.getNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
		Namespace namespace = Namespace.getNamespace("", "urn:hl7-org:v2xml");
		
		Element rootElement = new Element("UPRMessageBatch", namespace);
		rootElement.addNamespaceDeclaration(namespaceXsi);
		rootElement.addNamespaceDeclaration(namespaceRtc);
		rootElement.addNamespaceDeclaration(namespaceXsd);
		Document doc = new Document(rootElement);
		
		Element bhs = new Element("BHS", namespace);
		rootElement.addContent(bhs);
		bhs.addContent(new Element("BHS.1", namespace).addContent("|"));
		bhs.addContent(new Element("BHS.2", namespace).addContent("^~\\&"));
		bhs.addContent(new Element("BHS.3", namespace).addContent(new Element("HD.1", namespace).addContent("СРЗ 54")));
		
		Element bhs4 = new Element("BHS.4", namespace);
		bhs.addContent(bhs4);
		bhs4.addContent(new Element("HD.1", namespace).addContent("54"));
		bhs4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		bhs4.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		bhs.addContent(new Element("BHS.5", namespace).addContent(new Element("HD.1", namespace).addContent("ЦК ЕРП")));
		Element bhs6 = new Element("BHS.6", namespace);
		bhs.addContent(bhs6);
		bhs6.addContent(new Element("HD.1", namespace).addContent("00"));
		bhs6.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		bhs6.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		String curDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()) + "+7:00";	
		bhs.addContent(new Element("BHS.7", namespace).addContent(curDate));
		guidBhs = new RandomGUID();
		bhs.addContent(new Element("BHS.11", namespace).addContent(guidBhs.toString()));
		
		//для обычного zp1
		if(r == 0)
		{
			createMiddle(count, namespace, rootElement, curDate);
		}
		//для zp1 for a08p04 т.е. в запрос по fiod		
		if(r == 1)
		{
			createMiddle(count, namespace, rootElement, curDate,true,"Zp1forA08P04");
		}
		
		
		Element bts = new Element("BTS", namespace);
		rootElement.addContent(bts);
		bts.addContent(new Element("BTS.1", namespace).addContent(String.valueOf(count - 1)));
		bts.addContent(new Element("BTS.3", namespace).addContent(String.valueOf(rootElement.hashCode())));

		try {
		    XMLOutputter outputter = new XMLOutputter();
		    Format format = Format.getPrettyFormat();
	        format.setEncoding("Windows-1251");
		    outputter.setFormat(format);
		    FileWriter fw = new FileWriter(Const.PROGRAM_PATH + "50000-" + guidBhs + ".uprmes");
		    outputter.output(doc, fw);
		    fw.close();
		   // отладка xml 
		    System.out.println("ConstantiNastrojki "+ConstantiNastrojki.otladkaXML);
		      if(ConstantiNastrojki.otladkaXML.equals("0"))
		    {  	
			    new FileTransferMock().copy(Const.PROGRAM_PATH + "50000-" + guidBhs + ".uprmes", Const.AUTO_PATH  + "50000-" + guidBhs + ".uprmes");
			    new FileTransferMock().delete(Const.PROGRAM_PATH + "50000-" + guidBhs + ".uprmes");
		    }    
		} catch (Exception ex) {
		    System.out.println(ex.getMessage());
		}
		
		
		
		return true;
	}
	
	/*
	 * Переопределяем для Zp1Ajax
	 * (non-Javadoc)
	 * @see message.Message#prepareData(java.lang.String)
	 */
	
 public boolean prepareDataQukly(String userMachine,String stList) 
 {
    // очищаем старую коллекцию со старым содержимым зп1
	dataList.clear();
	// преобразуем вошедшую строку в коллекцию. Это данные с листа1 (енп) 
	ArrayList<String> parseStList = new ArrayList<String>();
	String []vr = stList.split(",");
	for (int i = 0; i < vr.length; i++) 
	{
		String vrm = vr[i].replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("Zp1Ajax:", "").replaceAll("Zp1taskA8P4:", "");
		parseStList.add(vrm);
	}
	
	Statement statement = null;
	Connection conn = null;
	ResultSet resultSet = null;

	try {
		conn = ConnectionPoolOracle.setUp().getConnection();
		statement = conn.createStatement();
		
		// ДЕЛАЕМ запрос select из таблиц
		resultSet = new TaskOracle().selectDataForZPAjaxQukly(statement, userMachine,parseStList);
		System.out.println("ОТРАБОТАЛ БОЛЬШОЙ ЗАПРОС");
		// берем мета данные  
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		int prepareData = 1;

		// запаковываем первую строку с названиями столбцов
		int numcols = metaData.getColumnCount();
		ArrayList<String> dataListRow = new ArrayList<String>();
		for (int i = 0; i < numcols; i++) 
		{
			dataListRow.add(metaData.getColumnLabel(i + 1));
		}
		// добовляем (почему- то при прогрузке на второй лист это помогло вывести  номер MSA)
		dataListRow.add("MSA");
		dataListRow.add("ZP1ok");
		dataListRow.add("UDLfromZp1fiod");
		dataListRow.add("EnpOutOur=EnpOutFedF");
		dataList.add(dataListRow);
		
		
		
		// вытаскием остальные значания построчено
		while (resultSet.next())
		{
			prepareData++;
			dataListRow = new ArrayList<String>();
			
			for (int i = 0; i < numcols; i++) {
				if (resultSet.getObject(i + 1) != null && !"null".equals(resultSet.getObject(i + 1)))
				{
						
						// пребразуем даты
						if (i == 3 || i == 13 || i == 24 || i == 25 || i == 32 || i == 41 || i == 57 || i == 60 || i==59)
						{
							dataListRow.add(new SimpleDateFormat("yyyy-MM-dd",java.util.Locale.ENGLISH).format(resultSet.getDate(i + 1)));
						} else
						{
							editPERSON_DOCPERSONID(resultSet,dataListRow,i);
						}
						
				} 
				else
				{
					dataListRow.add("");
				}
			}
			dataList.add(dataListRow);
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		try
		{
			resultSet.close();
			statement.close();
			  conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return true;
 }

 /*
  * Метод парсит из нашей базы PERSON_DOCPERSONID и подставляет соответствующий который в федеральном фонде
  * данные взяты из таблицы developer.docffoms
  */
 private void editPERSON_DOCPERSONID(ResultSet resultSet,ArrayList<String> dataListRow, int i) throws SQLException
 {
    	if(i == 7)
		{
		     if(resultSet.getString(i + 1).equals("0"))
			{
				dataListRow.add("1");
				return;
			}
		     if(resultSet.getString(i + 1).equals("1"))
				{
					dataListRow.add("2");
					return;
				}
		}
    	
    	
	    if(i == 17)
		{
			if(resultSet.getString(i + 1).equals("2"))
			{
				dataListRow.add("3");
			}
			if(resultSet.getString(i + 1).equals("27"))
			{
				dataListRow.add("27");
			}
			if(resultSet.getString(i + 1).equals("28"))
			{
				dataListRow.add("28");
			}
			if(resultSet.getString(i + 1).equals("1"))
			{
				dataListRow.add("1");
			}
			if(resultSet.getString(i + 1).equals("3"))
			{
				dataListRow.add("18");
			}
			if(resultSet.getString(i + 1).equals("4"))
			{
				dataListRow.add("9");
			}
			if(resultSet.getString(i + 1).equals("5"))
			{
				dataListRow.add("14");
			}
			if(resultSet.getString(i + 1).equals("6"))
			{
				dataListRow.add("18");
			}
			if(resultSet.getString(i + 1).equals("7"))
			{
				dataListRow.add("18");
			}
			if(resultSet.getString(i + 1).equals("8"))
			{
				dataListRow.add("13");
			}
			if(resultSet.getString(i + 1).equals("9"))
			{
				dataListRow.add("11");
			}
			if(resultSet.getString(i + 1).equals("10"))
			{
				dataListRow.add("24");
			}
			if(resultSet.getString(i + 1).equals("11"))
			{
				dataListRow.add("18");
			}
			if(resultSet.getString(i + 1).equals("0"))
			{
				dataListRow.add("");
			}
			if(resultSet.getString(i + 1).equals("14"))
			{
				dataListRow.add("10");
			}
			if(resultSet.getString(i + 1).equals("21"))
			{
				dataListRow.add("21");
			}
			if(resultSet.getString(i + 1).equals("22"))
			{
				dataListRow.add("22");
			}
			if(resultSet.getString(i + 1).equals("23"))
			{
				dataListRow.add("23");
			}
			if(resultSet.getString(i + 1).equals("12"))
			{
				dataListRow.add("12");
			}
			if(resultSet.getString(i + 1).equals("25"))
			{
				dataListRow.add("25");
			}
			if(resultSet.getString(i + 1).equals("26"))
			{
				dataListRow.add("26");
			}
		}
		else
		{
			dataListRow.add(resultSet.getString(i + 1).replace(",", ""));
		}
 }
 
 public boolean prepareDataQukly2(String userMachine,String stList) 
 {
    // очищаем старую коллекцию со старым содержимым зп1
	dataList.clear();
	// преобразуем вошедшую строку в коллекцию. Это данные с листа1 (енп) 
	ArrayList<String> parseStList = new ArrayList<String>();
	String []vr = stList.split(",");
	for (int i = 0; i < vr.length; i++) 
	{
		String vrm = vr[i].replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("Zp1Ajax:", "").replaceAll("Zp1taskA8P4:", "");
		parseStList.add(vrm);
	}
	
	Statement statement = null;
	Connection conn = null;
	ResultSet resultSet = null;
	
	try {
		conn = ConnectionPoolOracle.setUp().getConnection();
		statement = conn.createStatement();
		
		// ДЕЛАЕМ запрос select из таблиц
		resultSet = new TaskOracle().selectDataForZPAjaxQukly2(statement, userMachine,parseStList);
		System.out.println("ОТРАБОТАЛ БОЛЬШОЙ ЗАПРОС");
		// берем мета данные  
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		int prepareData = 1;

		// запаковываем первую строку с названиями столбцов
		int numcols = metaData.getColumnCount();
		ArrayList<String> dataListRow = new ArrayList<String>();
		for (int i = 0; i < numcols; i++) 
		{
			dataListRow.add(metaData.getColumnLabel(i + 1));
		}
		// добовляем (почему- то при прогрузке на второй лист это помогло вывести  номер MSA)
		dataListRow.add("MSA");
		dataListRow.add("ZP1ok");
		dataListRow.add("UDLfromZp1fiod");
		dataListRow.add("EnpOutOur=EnpOutFedF");
		dataList.add(dataListRow);
		
		
		// вытаскием остальные значания построчено
		while (resultSet.next())
		{
			prepareData++;
			dataListRow = new ArrayList<String>();
			
			for (int i = 0; i < numcols; i++) {
				if (resultSet.getObject(i + 1) != null && !"null".equals(resultSet.getObject(i + 1)))
				{
						
						// пребразуем даты
						if (i == 3 || i == 13 || i == 24 || i == 25 || i == 32 || i == 41 || i == 57 || i == 60)
						{
							dataListRow.add(new SimpleDateFormat("yyyy-MM-dd",java.util.Locale.ENGLISH).format(resultSet.getDate(i + 1)));
						} else
						{
							editPERSON_DOCPERSONID(resultSet,dataListRow,i);
						}
						
				} 
				else
				{
					dataListRow.add("");
				}
			}
			dataList.add(dataListRow);
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		try
		{
			resultSet.close();
			statement.close();
			  conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return true;
 }
 
 
 
 
}


