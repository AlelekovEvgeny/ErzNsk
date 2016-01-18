package model.zp1ajax;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
    	
    	if (curElement.equals("IN1")) 
    	{
    		zpRecord.setNpp(nppCounter - 1);
    		zpRecord.setDinput(curDate);
    		/*
    		try { 
    			
    			//äîáîâëÿåì â òàáëèöó
    			new RecordH().addrecord(zpRecord); 
    		} catch (SQLException e) { 
    			e.printStackTrace(); 
    		}
    		*/
    		
    		ArrayList<String> zpRecordOnList = new ArrayList<String>();
    		zpRecordOnList.add(zpRecord.toString());
    		cl.setListRows(zpRecordOnList);
    		

    		
    		
        }
    	if (curElement.equals("QRI"))
    	{
    		zpRecord.setDinput(curDate);
    		zpRecord.setNpp(100);
    		ArrayList<String> zpRecordOnList = new ArrayList<String>();
    		zpRecordOnList.add(zpRecord.toString());
    		cl.setListRows(zpRecordOnList);
    		

        }
    	curElement = "";
    }
    
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		
		switch (curElement) {
		case "PID.8": 
			zpRecord.setPID8(new String(ch, start, length));
			break;
			/*
			 * ÎÒÌÅÒÊÀ Î ñìåðòè
			 */
		case "PID.29":
			zpRecord.setPID29(new String(ch, start, length));
			//System.out.println("×åðåç get "+zpRecord.getPID29()+ " "+zpRecord.getMSA_2());
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
				 * åñëè pid.3 ïåðâûé
				 */
				if (pidCounter == 1)
				{ 
					zpRecord.setPID_3_CX_1_1(new String(ch, start, length));
					break;
				}
				/*
				 *  åñëè pid.3 âòîðîé èëè òðåòèé
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
					case 1: zpRecord.setQri1(new String(ch, start, length));break;
					case 2: zpRecord.setQri2(new String(ch, start, length));break;
					case 3: zpRecord.setQri3(new String(ch, start, length));break;
					case 4: zpRecord.setQri4(new String(ch, start, length));break; 
				}
				break;					
		}
	} 

}
