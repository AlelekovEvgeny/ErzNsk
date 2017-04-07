package model.other;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ZpParser2 extends DefaultHandler {
	private String curElement = "";
	String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	private int pidCounter = 0;
	private int nppCounter = 0;
	private int qriCounter = 0;
	public ZpRecord2 zpRecord = new ZpRecord2();
	public CollectList cl = new CollectList();
	public List<String> ls = new ArrayList<String>();
   	int counter = 1;
   	String d = null;
	
    public ZpParser2() {
    	super();
    }    	
 
    public void startDocument() throws SAXException
    { 
    	
    	System.out.println("Start parse XML...");
    } 
     
    public void endDocument() 
    { 
    	System.out.println("Stop parse XML...");
    } 
    
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException 
    { 
    	curElement = qName;
    	
    	if (curElement.equals("PID.3")) 
    	{ 
    		pidCounter++;
		}
    } 
	
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException 
    {
    	curElement = qName;
    	ArrayList<String> zpRecordOnList = null;
    	
    	
    	    	
    	
    	if (curElement.equals("IN1")) 
    	{
    		zpRecord.setNpp(nppCounter - 1);
    		zpRecord.setDinput(curDate);
    		
    		zpRecordOnList = new ArrayList<String>();
    		//zpRecordOnList.add(zpRecord.toString());
    		zpRecordOnList.add(zpRecord.getMSA_2());
    		zpRecordOnList.add(zpRecord.getPID_3_CX_1_1());
    		zpRecordOnList.add(zpRecord.getPID_3_CX_1_2());
    		zpRecordOnList.add(zpRecord.getOkato2());
    		zpRecordOnList.add(zpRecord.getIN1_3_CX_1());
    		zpRecordOnList.add(zpRecord.getIN1_12());
    		zpRecordOnList.add(zpRecord.getIN1_13());
    		zpRecordOnList.add(zpRecord.getIN1_15());
    		zpRecordOnList.add(zpRecord.getIN1_35());
    		zpRecordOnList.add(zpRecord.getIN1_36());
    		zpRecordOnList.add("");
    		zpRecordOnList.add("");
    		zpRecordOnList.add("");
    		zpRecordOnList.add("");
    		//zpRecordOnList.add(zpRecord.getQri1());
    		//zpRecordOnList.add(zpRecord.getQri2());
    		//zpRecordOnList.add(zpRecord.getQri3());
    		//zpRecordOnList.add(zpRecord.getQri4());
    		zpRecordOnList.add(String.valueOf(zpRecord.getNpp()));
    		zpRecordOnList.add(zpRecord.getDinput());
    		zpRecordOnList.add(zpRecord.getPID7());
    		zpRecordOnList.add(zpRecord.getPID8());
    		zpRecordOnList.add(zpRecord.getPID29());
    		
    		cl.setListRows(zpRecordOnList);
    		
        }
    	if (curElement.equals("QRI"))
    	{
    		zpRecord.setDinput(curDate);
    		zpRecord.setNpp(100);
    		zpRecordOnList = new ArrayList<String>();
    		
    		zpRecordOnList.add(zpRecord.getMSA_2());
    		zpRecordOnList.add(zpRecord.getPID_3_CX_1_1());
    		zpRecordOnList.add(zpRecord.getPID_3_CX_1_2());
    		zpRecordOnList.add(zpRecord.getOkato2());
    		zpRecordOnList.add(zpRecord.getIN1_3_CX_1());
    		zpRecordOnList.add(zpRecord.getIN1_12());
    		zpRecordOnList.add(zpRecord.getIN1_13());
    		zpRecordOnList.add(zpRecord.getIN1_15());
    		zpRecordOnList.add(zpRecord.getIN1_35());
    		zpRecordOnList.add(zpRecord.getIN1_36());
    		zpRecordOnList.add(zpRecord.getQri1());
    		zpRecordOnList.add(zpRecord.getQri2());
    		zpRecordOnList.add(zpRecord.getQri3());
    		zpRecordOnList.add(zpRecord.getQri4());
    		zpRecordOnList.add(String.valueOf(zpRecord.getNpp()));
    		zpRecordOnList.add(zpRecord.getDinput());
    		zpRecordOnList.add(zpRecord.getPID7());
    		zpRecordOnList.add(zpRecord.getPID8());
    		zpRecordOnList.add(zpRecord.getPID29());
    		
    		cl.setListRows(zpRecordOnList);
    		qriCounter = 0;
    		zpRecord.setQri1("");
    		zpRecord.setQri2("");
    		zpRecord.setQri3("");
    		zpRecord.setQri4("");
    		
        }
    	curElement = "";
    }
    
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		  /*
		   * т.к при парсинге xml'я тег <PID29 /> не распознается то 
		   * алгоритм: В коллекцию складываем три подряд идущих тега pid8 p29 pid30
		   * если прилетает тэг <PID29 /> то коллекция заполнится pid8 pid30 ну и далее по коду ...if(ls.size() == 3) {}
		   * И после каждого QRI (взял за отметку нового ЗЛ) коллекция зачищается для слудеющего ЗЛ
		   */
		
		ls =  processemptyPID29(curElement,ls);
		if(curElement.equals("PID.30")) 
		{
			if(ls.size() == 3) {}
			else {zpRecord.setPID29("");}
		}
		
		switch (curElement) {
		case "PID.8": 
			zpRecord.setPID8(new String(ch, start, length));
			break;
			/*
			 * ГЋГ’ГЊГ…Г’ГЉГЂ ГЋ Г±Г¬ГҐГ°ГІГЁ
			 */
		case "PID.29":
			String s29 = new String(ch, start, length);
			zpRecord.setPID29(s29);
			
			break;
		
		
			
		case "PID.7": 
				String s = new String(ch, start, length);
				if(s.length() == 10) { 
					s = s.substring(8) + "." + s.substring(5, 7) + "." + s.substring(0, 4);
					zpRecord.setPID7(s);
				} else {
					zpRecord.setPID7("");
				}
				break;
		case "MSA.2": 
				s = new String(ch, start, length);
				if(s.length() == 36) { 
					s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)+ s.substring(19, 23) + s.substring(24);
					zpRecord.setMSA_2(s);
				} else {
					d = d + new String(ch, start, length);
					if(d.length() == 36) { 
						d = d.substring(0, 8) + d.substring(9, 13) + d.substring(14, 18)+ d.substring(19, 23) + d.substring(24);
						zpRecord.setMSA_2(d);
						d = null;
					}
				}
				nppCounter = 0;
				System.out.println(counter++);
				break;
			
			
			case "CX.1": 
				if (pidCounter == 1)
				{ 
					zpRecord.setPID_3_CX_1_1(new String(ch, start, length));
					break;
				}
				if (pidCounter > 1)
				{ 
					zpRecord.setPID_3_CX_1_2(new String(ch, start, length));
					break;
				}
				zpRecord.setIN1_3_CX_1(new String(ch, start, length));
				break;		
				
				
				
			case "IN1": 
				pidCounter = 0;
				nppCounter ++;
				break;	
			case "IN1.12":
			    s = new String(ch, start, length);
			    if(s.length() == 10) { 
			    	s = s.substring(8) + "." + s.substring(5, 7) + "." + s.substring(0, 4);
			    	zpRecord.setIN1_12(s);
			    } else {
			    	zpRecord.setIN1_12("");
			    }
			    zpRecord.setIN1_13("");
				break;	
			
			
			case "IN1.13": 
			    s = new String(ch, start, length);
			    if(s.length() == 10) { 
			    	s = s.substring(8) + "." + s.substring(5, 7) + "." + s.substring(0, 4);
			    	zpRecord.setIN1_13(s);
			    } else {
			    	zpRecord.setIN1_13("");
			    }
				break;	
			
			
			case "IN1.15": 
				zpRecord.setIN1_15(new String(ch, start, length));
				break;	
			case "IN1.35": 
				zpRecord.setIN1_35(new String(ch, start, length));
				break;	
			case "IN1.36": 
				zpRecord.setIN1_36(new String(ch, start, length));
				break;
			case "QRI.2": 
				qriCounter++;
				switch (qriCounter) {
				case 1:	zpRecord.setQri1(new String(ch, start, length));break;
				case 2:zpRecord.setQri2(new String(ch, start, length));break;
				case 3:zpRecord.setQri3(new String(ch, start, length));break;
				case 4:zpRecord.setQri4(new String(ch, start, length));break;
				}
				
				
				break;					
		}
	}
	
	private List<String> processemptyPID29(String var,List<String> list){
		
		if(var.equals("PID.8")) { list.add(var);}
		if(var.equals("PID.29")) { list.add(var);}
		if(var.equals("PID.30")) { list.add(var);}
		
		if(var.equals("QRI")) { list.clear();}
		
		
		return list;
	}

}
