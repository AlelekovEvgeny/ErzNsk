package loadparse;

import java.sql.SQLException;
import java.sql.Statement;

import oracle.ConnectOracle;
import oracle.ErrorOracle;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ErrorParser extends DefaultHandler {
	private String curElement = "";
	private ErrorRecord errorRecord = new ErrorRecord();
	private String error = "";
    ErrorOracle errorTable = new ErrorOracle();
   	Statement statement;
   	int counter = 1;
	
    public ErrorParser() {
    	super();
    }    	
 
    @Override 
    public void startDocument() throws SAXException { 
    	//ConnectOracle.connect();
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
    		//ConnectOracle.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	System.out.println("Stop parse XML...");
    } 
    
    @Override 
    public void startElement(String namespaceURI, String localName,
    		String qName, Attributes atts) throws SAXException { 
    	curElement = qName;
    } 
	
    @Override 
    public void endElement(String namespaceURI,
    		String localName, String qName) throws SAXException {
    	curElement = qName;
    	if (curElement.equals("ERR")) {
    		errorRecord.setERR(error);
    		errorTable.insertError(statement, errorRecord.getMSA_2(), errorRecord.getTAG(), errorRecord.getERR(), errorRecord.getFILE_NAME());
    		error = ""; 
        }
    	curElement = "";
    }
    
	@Override 
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		switch (curElement) {
			case "MSA.2": 
				if (start < 2012) { // Проверка границы массива символов
					errorRecord.setMSA_2(new String(ch, start, 8) +
							new String(ch, start + 9, 4) + 
							new String(ch, start + 14, 4) +
							new String(ch, start + 19, 4) +
							new String(ch, start + 24, 12));
				}
				System.out.println(counter++);
				break;
			case "BHS.11": 
				if (start < 2012) { // Проверка границы массива символов
					errorRecord.setFILE_NAME(new String(ch, start, 8) +
							new String(ch, start + 9, 4) + 
							new String(ch, start + 14, 4) +
							new String(ch, start + 19, 4) +
							new String(ch, start + 24, 12));
				}
				System.out.println(counter++);
				break;				
			case "CWE.1": 
				error = error + " " + new String(ch, start, length);
				break;
			case "CWE.2": 
				error = error + " " +  new String(ch, start, length);
				break;	
			case "ERL.1": 
				errorRecord.setTAG(new String(ch, start, length));
				break;
		}
	} 

}
