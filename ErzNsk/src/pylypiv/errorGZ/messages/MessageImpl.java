package pylypiv.errorGZ.messages;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;



import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import pylypiv.errorGZ.main.Data;
import util.FileTransfer;
import util.RandomGuid;
import util.ResObject;

public abstract class MessageImpl implements Message {
	
	protected FileTransfer fileTransfer;
	protected ResObject res;
	protected Data data;
	
	public MessageImpl(FileTransfer fileTransfer2, ResObject res, Data data) {
		this.fileTransfer = fileTransfer2;
		this.res = res;
		this.data = data;
	}

	protected RandomGuid guidBhs;
	
	@Override
	public RandomGuid getGuidBhs() {
		return guidBhs;
	}

	@Override
	public String create() {
	
		Namespace namespaceXsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		Namespace namespaceRtc = Namespace.getNamespace("rtc", "http://www.rintech.ru");
		Namespace namespaceXsd = Namespace.getNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
		Namespace namespace = Namespace.getNamespace("", "urn:hl7-org:v2xml");
		
		Element rootElement = new Element("UPRMessageBatch", namespace);
		rootElement.addNamespaceDeclaration(namespaceXsi);
		rootElement.addNamespaceDeclaration(namespaceRtc);
		rootElement.addNamespaceDeclaration(namespaceXsd);
		Document doc = new Document(rootElement);
		
		Element bhs = new Element("BHS", namespace);
		rootElement.addContent(bhs);
		bhs.addContent(new Element("BHS.1", namespace).addContent("|"));
		bhs.addContent(new Element("BHS.2", namespace).addContent("^~\\&"));
		bhs.addContent(new Element("BHS.3", namespace)
		.addContent(new Element("HD.1", namespace).addContent("СРЗ 54")));
		Element bhs4 = new Element("BHS.4", namespace);
		bhs.addContent(bhs4);
		bhs4.addContent(new Element("HD.1", namespace).addContent("54"));
		bhs4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		bhs4.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		bhs.addContent(new Element("BHS.5", namespace).addContent(new Element("HD.1", namespace).addContent("ЦК ЕРП")));
		Element bhs6 = new Element("BHS.6", namespace);
		bhs.addContent(bhs6);
		bhs6.addContent(new Element("HD.1", namespace).addContent("00"));
		bhs6.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		bhs6.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		String curDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()) + "+7:00";	
		bhs.addContent(new Element("BHS.7", namespace).addContent(curDate));
		guidBhs = new RandomGuid();
		bhs.addContent(new Element("BHS.11", namespace).addContent(guidBhs.toString()));
		
		createMiddle(namespace, rootElement, curDate);
		
		Element bts = new Element("BTS", namespace);
		rootElement.addContent(bts);
		bts.addContent(new Element("BTS.1", namespace).addContent(String.valueOf(1)));
		bts.addContent(new Element("BTS.3", namespace).addContent(String.valueOf(rootElement.hashCode())));

		String filename = "50000-" + guidBhs + ".uprmes";
		try {
		    XMLOutputter outputter = new XMLOutputter();
		    Format format = Format.getPrettyFormat();
	        format.setEncoding("Windows-1251");
		    outputter.setFormat(format);
		    FileWriter fw = new FileWriter(filename);
		    outputter.output(doc, fw);
		    fw.close();
		    fileTransfer.copy(filename, /*res.getAutoProcPath()*/"D:\\output\\" + filename);
		    fileTransfer.delete(filename);
		} catch (Exception ex) {
		    System.out.println(ex.getMessage());
		}
		System.out.println("Создан файл " + filename);
		return filename;
	}

	protected abstract void createMiddle(Namespace namespace,
			Element rootElement, String curDate);

	protected void createMsh(Namespace namespace, String curDate, Element rootEl, String msg1, String msg2, String msg3) {
		
		Element msh = new Element("MSH", namespace);
		rootEl.addContent(msh);
		msh.addContent(new Element("MSH.1", namespace).addContent("|"));
		msh.addContent(new Element("MSH.2", namespace).addContent("^~\\&"));
		msh.addContent(new Element("MSH.3", namespace)
		.addContent(new Element("HD.1", namespace).addContent("СРЗ 54")));
		Element msh4 = new Element("MSH.4", namespace);
		msh.addContent(msh4);
		msh4.addContent(new Element("HD.1", namespace).addContent("54"));
		msh4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		msh4.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		msh.addContent(new Element("MSH.5", namespace)
		.addContent(new Element("HD.1", namespace).addContent("ЦК ЕРП")));
		Element msh6 = new Element("MSH.6", namespace);
		msh.addContent(msh6);
		msh6.addContent(new Element("HD.1", namespace).addContent("00"));
		msh6.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));			
		msh6.addContent(new Element("HD.3", namespace).addContent("ISO"));			
		msh.addContent(new Element("MSH.7", namespace).addContent(curDate));
		Element msh9 = new Element("MSH.9", namespace);
		msh.addContent(msh9);
		msh9.addContent(new Element("MSG.1", namespace).addContent(msg1));
		msh9.addContent(new Element("MSG.2", namespace).addContent(msg2));
		msh9.addContent(new Element("MSG.3", namespace).addContent(msg3));
		RandomGuid guidMsh = new RandomGuid();					
		msh.addContent(new Element("MSH.10", namespace).addContent(guidMsh.toString()));	
		msh.addContent(new Element("MSH.11", namespace)
		.addContent(new Element("PT.1", namespace).addContent("P")));
		msh.addContent(new Element("MSH.12", namespace)
		.addContent(new Element("VID.1", namespace).addContent("2.6")));
		msh.addContent(new Element("MSH.15", namespace).addContent("AL"));	
		msh.addContent(new Element("MSH.16", namespace).addContent("AL"));
		// добовляем индификатор к модели
		data.setMsh10(guidMsh.toString());
		
	}
	
	protected void createPid(Namespace namespace, Element rootEl) {
		Element pid = new Element("PID", namespace);
		rootEl.addContent(pid);
		
		Element pid3_1 = new Element("PID.3", namespace);
		pid.addContent(pid3_1);
		String seria = data.getPerson().getPerson_serdoc();
		pid3_1.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " № ") + data.getPerson().getPerson_numdoc()));
		pid3_1.addContent(new Element("CX.5", namespace).addContent(String.valueOf(data.getPerson_docpersonid())));
		pid3_1.addContent(new Element("CX.7", namespace).addContent(data.getDatepassport()));
		pid3_1.addContent(new Element("CX.8", namespace).addContent(data.getD2()));
		
		if (data.getPerson_docpersonid() == 9) {
			Element pid3_2 = new Element("PID.3", namespace);
			pid.addContent(pid3_2);
			pid3_2.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " № ") + data.getPerson().getPerson_numdoc()));
			pid3_2.addContent(new Element("CX.5", namespace).addContent("23"));
			pid3_2.addContent(new Element("CX.7", namespace).addContent(data.getDatepassport()));
			pid3_2.addContent(new Element("CX.8", namespace).addContent(data.getD2()));
		}
		
		if (data.getPerson_docpersonid() == 21) {
			Element pid3_5 = new Element("PID.3", namespace);
			pid.addContent(pid3_5);
			pid3_5.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " № ") + data.getPerson().getPerson_numdoc()));
			pid3_5.addContent(new Element("CX.5", namespace).addContent("23"));
			String d2 = data.getD2();
			String d2minus3year = String.valueOf(Integer.parseInt(d2.substring(0, 4)) - 3) + d2.substring(4);
			pid3_5.addContent(new Element("CX.7", namespace).addContent(d2minus3year));
			pid3_5.addContent(new Element("CX.8", namespace).addContent(data.getD2()));
		}
		
		/*if (!"".equals(data.getSnils())) {	
			Element pid3_3 = new Element("PID.3", namespace);
			pid.addContent(pid3_3);
			pid3_3.addContent(new Element("CX.1", namespace).addContent(data.getSnils()));
			pid3_3.addContent(new Element("CX.5", namespace).addContent("PEN"));
		}*/
					
		Element pid3_4 = new Element("PID.3", namespace);
		pid.addContent(pid3_4);
		if ("".equals(data.getPerson().getPersonAdd().getEnp()) || (data.getPerson().getPersonAdd().getEnp() == null)) {
			pid3_4.addContent(new Element("CX.1", namespace).addContent(data.getPerson().getEnp()));
		} else {
			pid3_4.addContent(new Element("CX.1", namespace).addContent(data.getPerson().getPersonAdd().getEnp()));
		}
		Element pid3_4cx4 = new Element("CX.4", namespace);
		pid3_4.addContent(pid3_4cx4);
		pid3_4cx4.addContent(new Element("HD.1", namespace).addContent("50000"));
		pid3_4cx4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1"));
		pid3_4cx4.addContent(new Element("HD.3", namespace).addContent("ISO"));
		pid3_4.addContent(new Element("CX.5", namespace).addContent("NI"));
				
		Element pid5 = new Element("PID.5", namespace);
		pid.addContent(pid5);
		pid5.addContent(new Element("XPN.1", namespace)
		.addContent(new Element("FN.1", namespace).addContent(data.getPerson().getPerson_surname())));
		pid5.addContent(new Element("XPN.2", namespace).addContent(data.getPerson().getPerson_kindfirstname()));
		pid5.addContent(new Element("XPN.3", namespace).addContent(data.getPerson_kindlastname()));

		pid.addContent(new Element("PID.7", namespace).addContent(data.getPerson_birthday()));
		
		pid.addContent(new Element("PID.8", namespace).addContent(data.getPerson_sex()));
		
		pid.addContent( new Element("PID.23", namespace).addContent(data.getPerson().getPersonAdd().getBorn()));

		Element pid26 = new Element("PID.26", namespace);
		pid.addContent(pid26);
		pid26.addContent(new Element("CWE.1", namespace).addContent(String.valueOf(data.getRussian())));
		pid26.addContent(new Element("CWE.3", namespace).addContent("1.2.643.2.40.5.0.25.3"));

		if (data.getPerson_docpersonid() == 1) {		
			pid.addContent(new Element("PID.32", namespace).addContent("7"));
		}
	}
	
	protected void createPidpr(Namespace namespace, Element rootEl) {
		Element pid = new Element("PID", namespace);
		rootEl.addContent(pid);
		
		Element pid3_1 = new Element("PID.3", namespace);
		pid.addContent(pid3_1);
		String seria = data.getPerson().getPerson_serdoc();
		pid3_1.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " № ") + data.getPerson().getPerson_numdoc()));
		pid3_1.addContent(new Element("CX.5", namespace).addContent(String.valueOf(data.getPerson_docpersonid())));
		pid3_1.addContent(new Element("CX.7", namespace).addContent(data.getDatepassport()));
		
		if (data.getPerson_docpersonid() == 9) {
			Element pid3_2 = new Element("PID.3", namespace);
			pid.addContent(pid3_2);
			pid3_2.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " № ") + data.getPerson().getPerson_numdoc()));
			pid3_2.addContent(new Element("CX.5", namespace).addContent("23"));
			pid3_2.addContent(new Element("CX.7", namespace).addContent(data.getDatepassport()));
			pid3_2.addContent(new Element("CX.8", namespace).addContent(data.getD2()));
		}
		
		if (data.getPerson_docpersonid() == 21) {
			Element pid3_5 = new Element("PID.3", namespace);
			pid.addContent(pid3_5);
			pid3_5.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " № ") + data.getPerson().getPerson_numdoc()));
			pid3_5.addContent(new Element("CX.5", namespace).addContent("23"));
			String d2 = data.getD2();
			String d2minus3year = String.valueOf(Integer.parseInt(d2.substring(0, 4)) - 3) + d2.substring(4);
			pid3_5.addContent(new Element("CX.7", namespace).addContent(d2minus3year));
			pid3_5.addContent(new Element("CX.8", namespace).addContent(data.getD2()));
		}
		
		/*if (!"".equals(data.getSnils())) {	
			Element pid3_3 = new Element("PID.3", namespace);
			pid.addContent(pid3_3);
			pid3_3.addContent(new Element("CX.1", namespace).addContent(data.getSnils()));
			pid3_3.addContent(new Element("CX.5", namespace).addContent("PEN"));
		}*/
					
		Element pid3_4 = new Element("PID.3", namespace);
		pid.addContent(pid3_4);
		if ("".equals(data.getPerson().getPersonAdd().getEnp()) || (data.getPerson().getPersonAdd().getEnp() == null)) {
			pid3_4.addContent(new Element("CX.1", namespace).addContent(data.getPerson().getEnp()));
		} else {
			pid3_4.addContent(new Element("CX.1", namespace).addContent(data.getPerson().getPersonAdd().getEnp()));
		}
		Element pid3_4cx4 = new Element("CX.4", namespace);
		pid3_4.addContent(pid3_4cx4);
		pid3_4cx4.addContent(new Element("HD.1", namespace).addContent("50000"));
		pid3_4cx4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1"));
		pid3_4cx4.addContent(new Element("HD.3", namespace).addContent("ISO"));
		pid3_4.addContent(new Element("CX.5", namespace).addContent("NI"));
				
		Element pid5 = new Element("PID.5", namespace);
		pid.addContent(pid5);
		pid5.addContent(new Element("XPN.1", namespace)
		.addContent(new Element("FN.1", namespace).addContent(data.getPerson().getPersonAdd().getLast_fam())));
		pid5.addContent(new Element("XPN.2", namespace).addContent(data.getPerson().getPersonAdd().getLast_im()));
		pid5.addContent(new Element("XPN.3", namespace).addContent(data.getPerson().getPersonAdd().getLast_ot()));

		pid.addContent(new Element("PID.7", namespace).addContent(data.getLast_dr()));
		//pid.addContent(new Element("PID.7", namespace).addContent(data.getPerson_birthday()));
		
		pid.addContent(new Element("PID.8", namespace).addContent(data.getPerson_sex()));
		
		pid.addContent( new Element("PID.23", namespace).addContent(data.getPerson().getPersonAdd().getBorn()));

		Element pid26 = new Element("PID.26", namespace);
		pid.addContent(pid26);
		pid26.addContent(new Element("CWE.1", namespace).addContent(String.valueOf(data.getRussian())));
		pid26.addContent(new Element("CWE.3", namespace).addContent("1.2.643.2.40.5.0.25.3"));

		if (data.getPerson_docpersonid() == 1) {		
			pid.addContent(new Element("PID.32", namespace).addContent("7"));
		}
	}
}
