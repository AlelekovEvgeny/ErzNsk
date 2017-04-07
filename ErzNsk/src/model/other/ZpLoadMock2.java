package model.other;

import help.Const;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ZpLoadMock2 extends CollectList{
	
	public ArrayList<ArrayList<String>> load(String fileName)
	{
		File m = new File(Const.OUTPUTDONE + fileName + ".uprak2");
		System.out.println("Length "+fileName+"   "+m.length());
		
		XMLReader XMLReader;
		try {
			XMLReader = XMLReaderFactory.createXMLReader();
				ZpParser2 parser = new ZpParser2(); 
			
			XMLReader.setContentHandler(parser);
			XMLReader.setErrorHandler(parser);
			
			if (fileName.length() == 42) 
			{ 
				FileReader r = new FileReader(Const.OUTPUTDONE + fileName + ".uprak2");
				XMLReader.parse(new InputSource(r));
				return parser.cl.getListRows();
			} 
			else
			{
				return null;
			}
		} 
		catch (SAXException | IOException e)
		{
			return null;
		}
	}

	
}