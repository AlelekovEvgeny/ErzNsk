package loadparse;

import java.io.FileReader;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ErrorLoadMock implements Load {
	
	public boolean load(String fileName) {
		XMLReader XMLReader;
		try {
			XMLReader = XMLReaderFactory.createXMLReader();
			ErrorParser parser = new ErrorParser();
			XMLReader.setContentHandler(parser);
			XMLReader.setErrorHandler(parser);
			if (fileName.length() == 49) { 
				FileReader r = new FileReader("D://output/done/" + fileName);
				XMLReader.parse(new InputSource(r));
				return true;
			} else {
				return false;
			}
		} catch (SAXException | IOException e) {
			return false;
		}
	}
}
