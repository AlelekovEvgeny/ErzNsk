package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.other.ZP3;
import util.UtilParseDbXml;


public class Services {
	
	UtilParseDbXml utilparsedbXml = new UtilParseDbXml();
	dao.impl.DataSource dataSource = new dao.impl.DataSource();
	
	
	public void zp3process(String queryName,String prefix) throws Exception{
		
		List<ZP3> parsed_xml_zp3 = utilparsedbXml.unMarshalingGenralEnp(queryName, prefix);
		
		for(int i = 0; i < parsed_xml_zp3.size(); i ++){
			
			List<String> ls = dataSource.getInEnp(parsed_xml_zp3.get(i).getENP_OUT());
			//  в базе больше двух  внутрених енп
			if(ls.size() > 2){	System.out.println("EE "+ls+" - "+i);	}
			// Добавить в zp3 енп внутр -- добавить clone 
			
		}
		
		
	}
}
