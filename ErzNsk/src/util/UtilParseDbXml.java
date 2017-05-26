package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import help.Const;
import model.other.Employee;
import model.other.Employees;
import model.other.WrapZP3Marshaling;
import model.other.Secondlist;
import model.other.ZP3;
import model.other.ZpParser2;


public class UtilParseDbXml {

	public  void marshalingExample(String name,Employees em) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(em.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(em, new File(Const.OUTPUTDONE+name+".xml"));
	}
		
	public  ArrayList<ArrayList<String>> unMarshalingExample(String name) throws JAXBException {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Employees emps = (Employees) jaxbUnmarshaller.unmarshal(new File(Const.OUTPUTDONE+name+".xml"));

		ArrayList<ArrayList<String>> colBig = new ArrayList<ArrayList<String>>();
		
		for (Employee emp : emps.getEmployees()) {
			ArrayList<Secondlist> sl = emp.getLs();
			
			colBig.add(sl.get(0).getSecond());
		}
		
		return colBig;
	}
	
	public  ArrayList<ArrayList<String>> unMarshalingZP3(String name) throws JAXBException {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(WrapZP3Marshaling.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		WrapZP3Marshaling emps = (WrapZP3Marshaling) jaxbUnmarshaller.unmarshal(new File(Const.OUTPUTDONE+name+".xml"));
		ArrayList<ArrayList<String>> colBig = new ArrayList<ArrayList<String>>();
		
		for (ZP3 zp3 : emps.getList()) {
			
			ArrayList<String> sl = new ArrayList<String>();
			
			sl.add(zp3.getENP_IN());
			sl.add("");
			sl.add("");
			sl.add(zp3.getENP_OUT());
			//sl.add(zp3.getENP_OUT_old_1());
			sl.add("");
			sl.add("");
			sl.add("");
			sl.add(zp3.getNUM_INSUR());
			sl.add(zp3.getIN1_12());
			sl.add(zp3.getIN1_13());
			sl.add(zp3.getOKATO());
			sl.add(zp3.getTYPE());
			sl.add(zp3.getNUM_POL());
			
			
			colBig.add(sl);
		}
		
		return colBig;
	}

	
	
	
	/**
	 * Метод unmarshaled необходимые данные  из ответа (uprak2) запроса zp3
	 * @param name - имя файла (ответа) 
	 * @param prefix - расширение файла (ответа)
	 * @return - коллекцию объектов ZP3
	 * @throws JAXBException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public  List<ZP3> unMarshalingGenralEnp(String name,String prefix) throws JAXBException, ParserConfigurationException, SAXException, IOException {
		
		ZP3 zp3;
		List<ZP3> zp3_list = new ArrayList<ZP3>();
		//String name = "50000-60C89D04-6FF4-7FED-23E1-AFFC4239350F";
		//String prefix = "uprak2";
		// TODO Auto-generated method stub
		 File fXmlFile = new File(Const.OUTPUTDONE+name+"."+prefix);
		 
		 if(fXmlFile.canExecute()){
			 System.out.println("WERWE "+fXmlFile.length());	 
		 }
		 
		 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		 
		 Document doc = dBuilder.parse(fXmlFile);
		 doc.getDocumentElement().normalize();
		 
		 NodeList nl = doc.getElementsByTagName("RSP_ZK2.QUERY_RESPONSE");
		 
		 for (int i = 0; i < nl.getLength(); i++)
		 {
			 Node rsp_zk2_qery_response = nl.item(i);
			 zp3  = new ZP3();
			 
			 if (rsp_zk2_qery_response.getNodeType() == Node.ELEMENT_NODE)
			 {
				 Element eElement = (Element) rsp_zk2_qery_response;
				 NodeList ALL_PID3 = eElement.getElementsByTagName("PID.3");
				 // get first actual ENP from all tags a pid.3
				 Node pid3 = ALL_PID3.item(0);
				 Element el = (Element) pid3;
				 
				 zp3.setENP_OUT(el.getElementsByTagName("CX.1").item(0).getTextContent());
				 
				 // get second actual (i.e. old enp ) ENP from all tags a pid.3
				 Node pid3_2 = ALL_PID3.item(1);
				 Element pid3_2_element = (Element) pid3_2;
				 
				 zp3.setENP_OUT_old_1(pid3_2_element.getElementsByTagName("CX.1").item(0).getTextContent());
				 
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

				 zp3_list.add(zp3);
				 System.out.println(zp3.toString());
				 
				 
			 }
		 }
		 
		 return zp3_list;
	}
	
	/**
	 * Метод осуществяет маршалинг коллекции с объектами ZP3
	 * @param name - имя будующего файла.
	 * @param to_xml - коллеция для маршалинга
	 * @throws JAXBException
	 */
	public  void MarshalingGenralEnp(String name,List<ZP3> to_xml) throws JAXBException{
		
		WrapZP3Marshaling jaxbList = new WrapZP3Marshaling();
		jaxbList.setList(to_xml);
		
		JAXBContext jaxbContext = JAXBContext.newInstance(WrapZP3Marshaling.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(jaxbList, new File(Const.OUTPUTDONE+name+".xml"));
	}
	
	

	
	public void marshaling(String name,ArrayList<ArrayList<String>> ls ) throws JAXBException
	{
		Employees employees = new Employees();
		employees.setEmployees(new ArrayList<Employee>());
		
		Employee emp = new Employee();
		Secondlist second = new Secondlist();
		ArrayList<Secondlist> secondAL = new ArrayList<Secondlist>();
		
		for(ArrayList<String> sm : ls)
		{
			emp = new Employee();
			second = new Secondlist();
			second.setSecond(sm);

			secondAL = new ArrayList<Secondlist>();
			secondAL.add(second);

			emp.setLs(secondAL);
			employees.getEmployees().add(emp);

		}
		
		marshalingExample(name,employees);
	}

}
