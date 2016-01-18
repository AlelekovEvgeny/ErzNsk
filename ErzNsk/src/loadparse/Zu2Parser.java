package loadparse;

import java.sql.SQLException;
import java.sql.Statement;

import oracle.ConnectOracle;
import oracle.Zu2Oracle;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Zu2Parser extends DefaultHandler {
	private String curElement = "";
	private Zu2Record zu2Record = new Zu2Record();
    Zu2Oracle zu2Table = new Zu2Oracle();
   	Statement statement;
	private String prevElement = "";
	private int counter = 0;
	
    public Zu2Parser() {
    	super();
    }    	
 
    @Override 
    public void startDocument() throws SAXException { 
    	try {
			statement = ConnectOracle.connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	System.out.println("Start parse XML...");
    } 
     
    @Override 
    public void endDocument() { 
    	try {
    		statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	System.out.println("Stop parse XML...");
    } 
    
    @Override 
    public void startElement(String namespaceURI, String localName,
    		String qName, Attributes atts) throws SAXException { 
    	curElement = qName;
    	if (curElement.equals("PID.3")) {
    		prevElement = "PID.3";
		}
    	if (curElement.equals("IN1.3")) {
    		prevElement = "IN1.3";
		}
    } 
    
    @Override 
    public void endElement(String namespaceURI,
    		String localName, String qName) throws SAXException {
    	curElement = qName;
    	if (curElement.equals("ZPI_ZU2")) {
    		zu2Table.zu2Insert(statement, zu2Record.getPid3cx1(), zu2Record.getIn13cx1(), zu2Record.getIn112(), zu2Record.getIn113(),
    				zu2Record.getIn115(), zu2Record.getIn135(), zu2Record.getIn136(), zu2Record.getIn145(), zu2Record.getBhs11(), 
    				zu2Record.getMsh10());
    		System.out.println(++counter);
        }
    	curElement = ""; 
    }
    
	@Override 
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (curElement.equals("CX.1") && prevElement.equals("PID.3")) { 
			zu2Record.setPid3cx1(new String(ch, start, length)); 
		}
		if (curElement.equals("CX.1") && prevElement.equals("IN1.3")) { 
			zu2Record.setIn13cx1(new String(ch, start, length)); 
		}
		
		if (curElement.equals("IN1.12") && start < 2038) {
			zu2Record.setIn112(
					new String(ch, start + 8, 2) + "." +
					new String(ch, start + 5, 2) + "." +
					new String(ch, start, 4));
		}
		
		if (curElement.equals("IN1.13") && start < 2038) { 
			zu2Record.setIn113(
					new String(ch, start + 8, 2) + "." +
					new String(ch, start + 5, 2) + "." +
					new String(ch, start, 4));
		} 
		if (curElement.equals("IN1.15")) { 
			zu2Record.setIn115(new String(ch, start, length)); 
		} 
		if (curElement.equals("IN1.35")) { 
			zu2Record.setIn135(new String(ch, start, length)); 
		} 
		if (curElement.equals("IN1.36")) { 
			zu2Record.setIn136(new String(ch, start, length)); 
		}
		if (curElement.equals("IN1.45")) { 
			zu2Record.setIn145(new String(ch, start, length)); 
		}
		if (curElement.equals("BHS.11")) { 
			zu2Record.setBhs11(new String(ch, start, 8) +
					new String(ch, start + 9, 4) + 
					new String(ch, start + 14, 4) +
					new String(ch, start + 19, 4) +
					new String(ch, start + 24, 12)); 
		}
	
		if (curElement.equals("MSH.10") && start < 2012) { 
			zu2Record.setMsh10(new String(ch, start, 8) +
					new String(ch, start + 9, 4) + 
					new String(ch, start + 14, 4) +
					new String(ch, start + 19, 4) +
					new String(ch, start + 24, 12)); 
		}
		
	} 

}
