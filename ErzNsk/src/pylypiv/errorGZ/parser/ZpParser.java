package pylypiv.errorGZ.parser;

import java.util.GregorianCalendar;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ZpParser extends DefaultHandler {
	private String curElement = "";
	private int pidCounter = 0;
	private int nppCounter = 0;
	private int qriCounter = 0;
	private Zp zp = new Zp();
    int counter = 1;
   	String d = null;
   	ZpLoader zpLoader;
	
    public ZpParser(ZpLoader zpLoader) {
    	super();
    	this.zpLoader = zpLoader;
    }    	
 
    @Override 
    public void startDocument() throws SAXException {  } 
     
    @Override 
    public void endDocument() {  } 
    
    @Override 
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException { 
    	curElement = qName;
    	if (curElement.equals("PID.3")) { 
    		pidCounter++;
		}
    	if (curElement.equals("RSP_ZK1.QUERY_RESPONSE")) { 
    		
    		nppCounter = 0;
		}
    } 
	
    @Override 
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
    	curElement = qName;
    	if (curElement.equals("IN1")) {
    		zp.setNpp(nppCounter - 1);
    		Zp zp1 = new Zp();
    		zp1.setMsa2(zp.getMsa2());
    		zp1.setPid3cx1_1(zp.getPid3cx1_1());
    		zp1.setPid3cx1_2(zp.getPid3cx1_2());
    		zp1.setOkato2(zp.getOkato2());
    		zp1.setIn1_3_cx1(zp.getIn1_3_cx1());
    		zp1.setIn1_12(zp.getIn1_12()) ;
    		zp1.setIn1_13(zp.getIn1_13());
    		zp1.setIn1_15(zp.getIn1_15());
    		zp1.setIn1_35(zp.getIn1_35());
    		zp1.setIn1_36(zp.getIn1_36());
    		zp1.setQri1(zp.getQri1());
    		zp1.setQri2(zp.getQri2());
    		zp1.setQri3(zp.getQri3());
    		zp1.setQri4(zp.getQri4());
    		zp1.setNpp(zp.getNpp());
    		zp1.setDateinput(zp.getDateinput());
    		zp1.setPid7(zp.getPid7());
   			zpLoader.getZp().add(zp1);
        }
    	curElement = "";
    }
    
	@Override 
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		switch (curElement) {
			case "PID.7": 
				String s = new String(ch, start, length);
				if(s.length() == 10) { 
					zp.setPid7(new GregorianCalendar(Integer.parseInt(s.substring(0, 4)), Integer.parseInt(s.substring(5, 7)) - 1, Integer.parseInt(s.substring(8))));
				} else {
					//zp.setPid7("");
				}
				break;
			case "MSA.2": 
				s = new String(ch, start, length);
				if(s.length() == 36) { 
					s = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)+ s.substring(19, 23) + s.substring(24);
					zp.setMsa2(s);
				} else {
					d = d + new String(ch, start, length);
					if(d.length() == 36) { 
						d = d.substring(0, 8) + d.substring(9, 13) + d.substring(14, 18)+ d.substring(19, 23) + d.substring(24);
						zp.setMsa2(d);
						d = null;
					}
				}
				nppCounter = 0;
				break;
			case "CX.1": 
				if (pidCounter == 1) { 
					zp.setPid3cx1_1(new String(ch, start, length));
					break;
				}
				if (pidCounter == 2 || pidCounter == 3) { 
					zp.setPid3cx1_2(new String(ch, start, length));
					break;
				}
				zp.setIn1_3_cx1(new String(ch, start, length));
				break;				
			case "IN1": 
				pidCounter = 0;
				nppCounter ++;
				break;	
			case "IN1.12":
			    s = new String(ch, start, length);
			    if(s.length() == 10) { 
			    	//s = s.substring(8) + "." + s.substring(5, 7) + "." + s.substring(0, 4);
			    	zp.setIn1_12(new GregorianCalendar(Integer.parseInt(s.substring(0, 4)), Integer.parseInt(s.substring(5, 7)) - 1, Integer.parseInt(s.substring(8))));
			    } else {
			    	//zp.setIn1_12("");
			    }
			    //zp.setIn1_13("");	
				break;	
			case "IN1.13": 
			    s = new String(ch, start, length);
			    if(s.length() == 10) { 
			    	zp.setIn1_13(new GregorianCalendar(Integer.parseInt(s.substring(0, 4)), Integer.parseInt(s.substring(5, 7)) - 1, Integer.parseInt(s.substring(8))));
			    } else {
			    	//zp.setIn1_13("");
			    }
				break;	
			case "IN1.15": 
				zp.setIn1_15(new String(ch, start, length));
				break;	
			case "IN1.35": 
				zp.setIn1_35(new String(ch, start, length));
				break;	
			case "IN1.36": 
				zp.setIn1_36(new String(ch, start, length));
				break;
			case "QRI.2": 
				qriCounter++;
				String q = new String(ch, start, length);
				switch (qriCounter) {
					case 1: for(Zp zpItem : zpLoader.getZp()) { zpItem.setQri1(q); }; break;
					case 2: for(Zp zpItem : zpLoader.getZp()) { zpItem.setQri2(q); }; break;
					case 3: for(Zp zpItem : zpLoader.getZp()) { zpItem.setQri3(q); }; break;
					case 4: for(Zp zpItem : zpLoader.getZp()) { zpItem.setQri4(q); }; break; 
				}
				break;							
		}
	} 
}
