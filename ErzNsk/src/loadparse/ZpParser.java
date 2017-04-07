package loadparse;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import oracle.ConnectOracle;
import oracle.ZpOracle;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.other.RecordH;

public class ZpParser extends DefaultHandler {
	private String curElement = "";
	String curDate = new SimpleDateFormat("dd.MM.yyyy").format(new java.util.Date());
	private int pidCounter = 0;
	private int nppCounter = 0;
	private int qriCounter = 0;
	public ZpRecord zpRecord = new ZpRecord();
    ZpOracle zpTable = new ZpOracle();
   	Statement statement;
   	int counter = 1;
   	String d = null;
	
    public ZpParser() {
    	super();
    }    	
 
    @Override 
    public void startDocument() throws SAXException
    { 
    	System.out.println("Start parse XML...");
    } 
     
    @Override 
    public void endDocument() 
    { 
    	System.out.println("1 "+ zpRecord);
    	System.out.println("Stop parse XML...");
    } 
    
    @Override 
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException 
    { 
    	curElement = qName;
    	if (curElement.equals("PID.3")) 
    	{ 
    		pidCounter++;
		}
    } 
	
    @Override 
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException 
    {
    	curElement = qName;
    	
    	if (curElement.equals("IN1")) 
    	{
    		zpRecord.setNpp(nppCounter - 1);
    		zpRecord.setDinput(curDate);
    		try { 
    			
    			//добовляем в таблицу
    			new RecordH().addrecord(zpRecord); 
    		} catch (SQLException e) { 
    			e.printStackTrace(); 
    		}
        }
    	if (curElement.equals("QRI"))
    	{
    		zpRecord.setDinput(curDate);
    		zpRecord.setNpp(100);
    		try { 
    			new RecordH().addrecord(zpRecord); 
    		} catch (SQLException e) { 
    			e.printStackTrace(); 
    		}
        }
    	curElement = "";
    }
    
	@Override 
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		switch (curElement) {
		case "PID.8": 
			zpRecord.setPID8(new String(ch, start, length));
			break;
			/*
			 * ОТМЕТКА О смерти
			 */
		case "PID.29": 
			zpRecord.setPID29(new String(ch, start, length));
			System.out.println("Через get "+zpRecord.getPID29()+ " "+zpRecord.getMSA_2());
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
				/*
				 * 
				 * если pid.3 первый
				 */
				if (pidCounter == 1)
				{ 
					zpRecord.setPID_3_CX_1_1(new String(ch, start, length));
					break;
				}
				/*
				 *  если pid.3 второй или третий
				 */
				if (pidCounter == 2 || pidCounter == 3)
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
					case 1: zpRecord.setQri1(new String(ch, start, length)); break;
					case 2: zpRecord.setQri2(new String(ch, start, length)); break;
					case 3: zpRecord.setQri3(new String(ch, start, length)); break;
					case 4: zpRecord.setQri4(new String(ch, start, length)); break; 
				}
				break;					
		}
	} 

}
