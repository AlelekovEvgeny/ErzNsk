package servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import help.Const;
import model.other.ZP3;
import services.Services;

public class Test {

	
	 
	
	public static void main(String[] args) throws Exception {
		
		Services services = new Services();
		try {
			services.zp3process("50000-60C89D04-6FF4-7FED-23E1-AFFC4239350F","uprak2");
		} catch (JAXBException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*ZP3 zp3 = new ZP3();
		String name = "50000-60C89D04-6FF4-7FED-23E1-AFFC4239350F";
		String prefix = "uprak2";
		// TODO Auto-generated method stub
		 File fXmlFile = new File(Const.OUTPUTDONE+name+"."+prefix);
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		 
		 Document doc = dBuilder.parse(fXmlFile);
		 doc.getDocumentElement().normalize();
		 
		 NodeList nl = doc.getElementsByTagName("RSP_ZK2.QUERY_RESPONSE");
		 
		 for (int i = 0; i < nl.getLength(); i++)
		 {
			 Node rsp_zk2_qery_response = nl.item(i);
			 
			 if (rsp_zk2_qery_response.getNodeType() == Node.ELEMENT_NODE)
			 {
				 Element eElement = (Element) rsp_zk2_qery_response;
				 NodeList ALL_PID3 = eElement.getElementsByTagName("PID.3");
				 // get first actual ENP from all tags a pid.3
				 Node pid3 = ALL_PID3.item(0);
				 Element el = (Element) pid3;
				 
				 zp3.setENP_OUT(el.getElementsByTagName("CX.1").item(0).getTextContent());
				 
				 NodeList IN1_3 = eElement.getElementsByTagName("IN1.3");
				 Node ALL_IN1_3 = IN1_3.item(0);
				 Element IN1_3_CX_1 = (Element) ALL_IN1_3;
				 
				 zp3.setNUM_INSUR(IN1_3_CX_1.getElementsByTagName("CX.1").item(0).getTextContent());
				 
				 zp3.setIN1_12(eElement.getElementsByTagName("IN1.12").item(0).getTextContent().substring(0, 10));
				 try{
					 zp3.setIN1_13(eElement.getElementsByTagName("IN1.13").item(0).getTextContent().substring(0, 10));
				 }catch(Exception ex){zp3.setIN1_13("");}
				 zp3.setOKATO(eElement.getElementsByTagName("IN1.15").item(0).getTextContent());
				 zp3.setTYPE(eElement.getElementsByTagName("IN1.35").item(0).getTextContent());
				 zp3.setNUM_POL(eElement.getElementsByTagName("IN1.36").item(0).getTextContent());

				 System.out.println(zp3.toString());
				 
				 
			 }
		 }*/
	}

}
