package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.other.ZP3;
import oracle.ConnectionPoolOracle;
import oracle.ConnectionPoolOracle_dawn;
import util.UtilParseDbXml;


public class Services {
	
	UtilParseDbXml utilparsedbXml = new UtilParseDbXml();
	dao.impl.DataSource dataSource = new dao.impl.DataSource();
	
	
	public void update_confl_person(String enpIN,String enpOUT_ZP3,String USERNAME,String POL_ZP3,String OKATO_npp0,String GUID_npp0) throws Exception{
		
		dataSource.update_confl_person(enpIN, enpOUT_ZP3, USERNAME, POL_ZP3,OKATO_npp0,GUID_npp0);
	}
	
	
	public void zp3process(String queryName,String prefix) throws Exception{
		
		List<ZP3> parsed_xml_zp3 = utilparsedbXml.unMarshalingGenralEnp(queryName, prefix);
		
		parsed_xml_zp3 = process_generalEnpOut(parsed_xml_zp3);
		
		utilparsedbXml.MarshalingGenralEnp(queryName,parsed_xml_zp3);
		
		//System.out.println("### "+ utilparsedbXml.unMarshalingZP3(queryName));
		
	}
	
	
	
	
	
	/**
	 * Метод разделяет логику главного метода. Реализуем ветвление если вернулось больше одного внутреннего енп или 
	 * вернулся один внутренний енп по внешнему енп
	 * @param parsed_xml_zp3 - содержит объекты распарссеного ответа zp3
	 * @return - если внешнее енп вернуло внутренний (по результатам запроса) то фиксируем его в переменную иначе переменная остается NULL 
	 * @throws Exception 
	 */
	private List<ZP3> process_generalEnpOut(List<ZP3> parsed_xml_zp3) throws Exception{
		
		ConnectionPoolOracle.getConnectionDataSource();
		
		for(int i = 0; i < parsed_xml_zp3.size(); i ++){
			
			//System.out.println("### "+parsed_xml_zp3.get(i)+ " - "+ i);
			List<String> ls = dataSource.getInEnp(parsed_xml_zp3.get(i).getENP_OUT());
			//  в базе больше двух  внутрених енп
			if(ls.size() > 2 && parsed_xml_zp3.get(i).getENP_IN() == null){
				
				parsed_xml_zp3.get(i).setENP_IN(ls.get(1));
				
				//System.out.println("@@ "+parsed_xml_zp3.get(i).hashCode() +" - "+ parsed_xml_zp3.get(i));
				
				ZP3 zp3 = parsed_xml_zp3.get(i).clone();
				zp3.setENP_IN(ls.get(3));
				parsed_xml_zp3.add(i,zp3);
				i = i + 1;
				//parsed_xml_zp3.add(zp3);
				//System.out.println("@@2 "+zp3.hashCode()+" - "+parsed_xml_zp3.get(parsed_xml_zp3.size()-1));
				
				//System.out.println("EE "+parsed_xml_zp3.get(i)+" - "+i);
			}
			// Добавить в zp3 енп внутр -- добавить clone
			if(ls.size() == 2 && parsed_xml_zp3.get(i).getENP_IN() == null){	
				parsed_xml_zp3.get(i).setENP_IN(ls.get(1));
				//System.out.println("EE "+parsed_xml_zp3.get(i)+" - "+i);
			}
			
			if(parsed_xml_zp3.get(i).getENP_IN() == null) parsed_xml_zp3.get(i).setENP_IN(parsed_xml_zp3.get(i).getENP_OUT_old_1());
			
			System.out.println("EE "+parsed_xml_zp3.get(i)+" - "+i);
		}
		
		ConnectionPoolOracle.printStatus();
		
		return parsed_xml_zp3;
	}
}
