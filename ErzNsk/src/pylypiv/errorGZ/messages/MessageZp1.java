package pylypiv.errorGZ.messages;



import org.jdom2.Element;
import org.jdom2.Namespace;

import pylypiv.errorGZ.main.Data;

import util.FileTransfer;
import util.ResObject;

public class MessageZp1 extends MessageImpl {
	
	public MessageZp1(FileTransfer fileTransfer, ResObject res, Data data) {
		super(fileTransfer, res, data);
	}

	protected void createMiddle(Namespace namespace, Element rootElement, String curDate) {
		
		Element qbp_zp1 = new Element("QBP_ZP1", namespace);
		rootElement.addContent(qbp_zp1);
		
		createMsh(namespace, curDate, qbp_zp1, "QBP", "ZP1", "QBP_ZP1");	
		
		Element qpd = new Element("QPD", namespace);
		qbp_zp1.addContent(qpd);
		
		Element qpd1 = new Element("QPD.1", namespace);
		qpd.addContent(qpd1);
		qpd1.addContent(new Element("CWE.1", namespace).addContent("ÑÏ"));
		qpd1.addContent(new Element("CWE.3", namespace).addContent("1.2.643.2.40.1.9"));
		
		qpd.addContent(new Element("QPD.3", namespace).addContent("Ó"));					
		
		//qpd.addContent(new Element("QPD.4", namespace).addContent(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));				
		
		Element qpd5_1 = new Element("QPD.5", namespace);
		qpd.addContent(qpd5_1);
		qpd5_1.addContent(new Element("CX.1", namespace)
		.addContent(
		(!"".equals(data.getPerson().getPerson_serdoc()) && null !=  data.getPerson().getPerson_serdoc()) 
			? data.getPerson().getPerson_serdoc() + " ¹ " + data.getPerson().getPerson_numdoc() 
			: data.getPerson().getPerson_numdoc()));
		qpd5_1.addContent(new Element("CX.5", namespace).addContent(String.valueOf(data.getPerson_docpersonid())));
		qpd5_1.addContent(new Element("CX.7", namespace).addContent(data.getDatepassport()));
		
		if (!"".equals(data.getSnils())) {
			Element qpd5_2 = new Element("QPD.5", namespace);
			qpd.addContent(qpd5_2);
			qpd5_2.addContent(new Element("CX.1", namespace).addContent(data.getSnils()));
			qpd5_2.addContent(new Element("CX.5", namespace).addContent("PEN"));
		}
		
		Element qpd5_3 = new Element("QPD.5", namespace);
		qpd.addContent(qpd5_3);
		if ("".equals(data.getPerson().getPersonAdd().getEnp()) || (data.getPerson().getPersonAdd().getEnp() == null)) {
			qpd5_3.addContent(new Element("CX.1", namespace).addContent(data.getPerson().getEnp()));
		} else {
			qpd5_3.addContent(new Element("CX.1", namespace).addContent(data.getPerson().getPersonAdd().getEnp()));
		}
		Element qpd5_3cx4 = new Element("CX.4", namespace);
		qpd5_3.addContent(qpd5_3cx4);
		qpd5_3cx4.addContent(new Element("HD.1", namespace).addContent("50000"));
		qpd5_3cx4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1"));
		qpd5_3cx4.addContent(new Element("HD.3", namespace).addContent("ISO"));
		qpd5_3.addContent(new Element("CX.5", namespace).addContent("NI"));
		
		Element qpd6 = new Element("QPD.6", namespace);
		qpd.addContent(qpd6);
		qpd6.addContent(new Element("XPN.1", namespace)
		.addContent(new Element("FN.1", namespace).addContent(data.getPerson().getPerson_surname())));
		qpd6.addContent(new Element("XPN.2", namespace).addContent(data.getPerson().getPerson_kindfirstname()));
		qpd6.addContent(new Element("XPN.3", namespace).addContent(data.getPerson_kindlastname()));
		
		qpd.addContent(new Element("QPD.7", namespace).addContent(data.getPerson_birthday()));
		
		qpd.addContent(new Element("QPD.8", namespace).addContent(data.getPerson_sex()));

		qpd.addContent(new Element("QPD.9", namespace).addContent(data.getPerson().getPersonAdd().getBorn()));

	}
}
