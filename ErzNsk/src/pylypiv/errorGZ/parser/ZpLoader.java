package pylypiv.errorGZ.parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;



public class ZpLoader {
	
	//protected ResObject res;
	private ArrayList<Zp> zp = new ArrayList<Zp>();;
	
	public ZpLoader() {
	
	}
	
	public ArrayList<Zp> load(String fileAsk, String enp,String status) throws SAXException, IOException, InterruptedException {
		XMLReader XMLReader;
		
		XMLReader = XMLReaderFactory.createXMLReader();
		ZpParser parser = new ZpParser(this);
		XMLReader.setContentHandler(parser);
		XMLReader.setErrorHandler(parser);
		
		String fileAnswer = fileAsk.replaceAll( /*res.getFileAskExt()*/".uprmes",  /*res.getFileAnswerExt2()*/".uprak2");
		File file = new File(/*res.getAutoProcPathAnswer()*/"D:\\output\\done\\" + fileAnswer);
		
		while(!file.exists()) {
			System.out.println("enp " + enp + " - "+ status);
			Thread.sleep(10000);
		}
		FileReader r = new FileReader(/*res.getAutoProcPathAnswer()*/"D:\\output\\done\\" + fileAnswer);
		XMLReader.parse(new InputSource(r));
		System.out.println("enp " + enp + " - Файл ZP загружен");
		return zp;
	}

	public ArrayList<Zp> getZp() {
		return zp;
	}

	public void setZp(ArrayList<Zp> zp) {
		this.zp = zp;
	}
}
