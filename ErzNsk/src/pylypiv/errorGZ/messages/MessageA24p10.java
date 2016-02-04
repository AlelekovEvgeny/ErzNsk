package pylypiv.errorGZ.messages;


import org.jdom2.Element;
import org.jdom2.Namespace;

import pylypiv.errorGZ.main.Data;
import util.FileTransfer;
import util.ResObject;

public class MessageA24p10 extends MessageImpl {

	public MessageA24p10(FileTransfer fileTransfer, ResObject res, Data data) {
		super(fileTransfer, res, data);
	}

	protected void createMiddle(Namespace namespace,
			Element rootElement, String curDate) {
		
		Element adt_a24 = new Element("ADT_A24", namespace);
		rootElement.addContent(adt_a24);
		
		createMsh(namespace, curDate, adt_a24, "ADT", "A24", "ADT_A24");	
		
		Element evn = new Element("EVN", namespace);
		adt_a24.addContent(evn);
		evn.addContent(new Element("EVN.2", namespace).addContent(curDate));
		evn.addContent(new Element("EVN.4", namespace).addContent("Ï10"));
		
		//PID-1
		createPid(namespace, adt_a24);	
			
		//PID-2
		Element pid = new Element("PID", namespace);
		adt_a24.addContent(pid);
		
		Element pid3_1 = new Element("PID.3", namespace);
		pid.addContent(pid3_1);
		pid3_1.addContent(new Element("CX.1", namespace).addContent(data.getPerson().getPerson_serdoc() + " ¹ " + data.getPerson().getPerson_numdoc()));
		pid3_1.addContent(new Element("CX.5", namespace).addContent(String.valueOf(data.getPerson_docpersonid())));
		pid3_1.addContent(new Element("CX.7", namespace).addContent(data.getDatepassport()));

		if (String.valueOf(data.getPerson_docpersonid()).equals("9")) {
			Element pid3_2 = new Element("PID.3", namespace);
			pid.addContent(pid3_2);
			pid3_2.addContent(new Element("CX.1", namespace).addContent(data.getPerson().getPerson_serdoc() + " ¹ " + data.getPerson().getPerson_numdoc()));
			pid3_2.addContent(new Element("CX.5", namespace).addContent("23"));
			pid3_2.addContent(new Element("CX.7", namespace).addContent(data.getDatepassport()));
		}
			
		Element pid3_3 = new Element("PID.3", namespace);
		pid.addContent(pid3_3);
		pid3_3.addContent(new Element("CX.1", namespace).addContent(data.getSnils()));
		pid3_3.addContent(new Element("CX.5", namespace).addContent("PEN"));
		
		Element pid3_4 = new Element("PID.3", namespace);
		pid.addContent(pid3_4);
		pid3_4.addContent(new Element("CX.1", namespace).addContent(data.getZp().getPid3cx1_1()));
		Element pid3_4cx4 = new Element("CX.4", namespace);
		pid3_4.addContent(pid3_4cx4);
		pid3_4cx4.addContent(new Element("HD.1", namespace).addContent(data.getZp().getIn1_15()));
		pid3_4cx4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1"));
		pid3_4cx4.addContent(new Element("HD.3", namespace).addContent("ISO"));
		pid3_4.addContent(new Element("CX.5", namespace).addContent("NI"));
				
		Element pid5 = new Element("PID.5", namespace);
		pid.addContent(pid5);
		pid5.addContent(new Element("XPN.1", namespace)
		.addContent(new Element("FN.1", namespace).addContent(data.getPerson().getPerson_surname())));
		pid5.addContent(new Element("XPN.2", namespace).addContent(data.getPerson().getPerson_kindfirstname()));
		pid5.addContent(new Element("XPN.3", namespace).addContent(data.getPerson().getPerson_kindlastname()));

		pid.addContent(new Element("PID.7", namespace).addContent(data.getPerson_birthday()));
		
		pid.addContent(new Element("PID.8", namespace).addContent(data.getPerson_sex()));
		
		pid.addContent( new Element("PID.23", namespace).addContent(data.getPerson().getPersonAdd().getBorn()));

		Element pid26 = new Element("PID.26", namespace);
		pid.addContent(pid26);
		pid26.addContent(new Element("CWE.1", namespace).addContent("RUS"));
		pid26.addContent(new Element("CWE.3", namespace).addContent("1.2.643.2.40.5.0.25.3"));

		if (String.valueOf(data.getPerson_docpersonid()).equals("1")) {		
			pid.addContent(new Element("PID.32", namespace).addContent("7"));
		}

	}

}
