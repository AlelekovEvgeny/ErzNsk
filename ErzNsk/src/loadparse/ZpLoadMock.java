package loadparse;

import help.Const;

import java.io.FileReader;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ZpLoadMock implements Load {
	
	public boolean load(String fileName)
	{
		XMLReader XMLReader;
		try {
			XMLReader = XMLReaderFactory.createXMLReader();
			ZpParser parser = new ZpParser();
			XMLReader.setContentHandler(parser);
			XMLReader.setErrorHandler(parser);
			
			if (fileName.length() == 42) 
			{ 
				FileReader r = new FileReader(Const.OUTPUTDONE + fileName + ".uprak2");
				XMLReader.parse(new InputSource(r));
				return true;
			} 
			else
			{
				return false;
			}
		} 
		catch (SAXException | IOException e)
		{
			return false;
		}
	}
}
