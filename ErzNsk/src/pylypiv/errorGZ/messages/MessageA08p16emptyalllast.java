package pylypiv.errorGZ.messages;


import org.jdom2.Element;
import org.jdom2.Namespace;

import pylypiv.errorGZ.main.Data;
import util.FileTransfer;
import util.ResObject;

public class MessageA08p16emptyalllast extends MessageImpl {
	
	public MessageA08p16emptyalllast(FileTransfer fileTransfer, ResObject res, Data data) {
		super(fileTransfer, res, data);
	}

	protected void createMiddle(Namespace namespace,	Element rootElement, String curDate) {
			
		Element adt_a01 = new Element("ADT_A01", namespace);
		rootElement.addContent(adt_a01);

		createMsh(namespace, curDate, adt_a01, "ADT", "A08", "ADT_A01");	
			
		Element evn = new Element("EVN", namespace);
		adt_a01.addContent(evn);
		evn.addContent(new Element("EVN.2", namespace).addContent(curDate));
		evn.addContent(new Element("EVN.4", namespace).addContent("о16"));

		Element pid = new Element("PID", namespace);
		adt_a01.addContent(pid);
		
		Element pid3_1 = new Element("PID.3", namespace);
		pid.addContent(pid3_1);
		pid3_1.addContent(new Element("CX.1", namespace).addContent(data.getPerson().getPerson_serdoc() + " ╧ " + data.getPerson().getPerson_numdoc()));
		pid3_1.addContent(new Element("CX.5", namespace).addContent(String.valueOf(data.getPerson_docpersonid())));
		pid3_1.addContent(new Element("CX.7", namespace).addContent(data.getDatepassport()));

		if (String.valueOf(data.getPerson_docpersonid()).equals("9")) {
			Element pid3_2 = new Element("PID.3", namespace);
			pid.addContent(pid3_2);
			pid3_2.addContent(new Element("CX.1", namespace).addContent(data.getPerson().getPerson_serdoc() + " ╧ " + data.getPerson().getPerson_numdoc()));
			pid3_2.addContent(new Element("CX.5", namespace).addContent("23"));
			pid3_2.addContent(new Element("CX.7", namespace).addContent(data.getDatepassport()));
			pid3_2.addContent(new Element("CX.8", namespace).addContent(data.getD2()));
		}
		
		String snils = data.getSnils();
		if (!"".equals(snils) && (snils != null)) {	
			Element pid3_3 = new Element("PID.3", namespace);
			pid.addContent(pid3_3);
			pid3_3.addContent(new Element("CX.1", namespace).addContent(snils));
			pid3_3.addContent(new Element("CX.5", namespace).addContent("PEN"));
		}
					
		Element pid3_4 = new Element("PID.3", namespace);
		pid.addContent(pid3_4);
		pid3_4.addContent(new Element("CX.1", namespace).addContent(data.getZp().getPid3cx1_1()));
		Element pid3_4cx4 = new Element("CX.4", namespace);
		pid3_4.addContent(pid3_4cx4);
		pid3_4cx4.addContent(new Element("HD.1", namespace).addContent("50000"));
		pid3_4cx4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1"));
		pid3_4cx4.addContent(new Element("HD.3", namespace).addContent("ISO"));
		pid3_4.addContent(new Element("CX.5", namespace).addContent("NI"));
		
		/* Enter new fio and bythday 
		 * 
		 */
		
		Element pid5 = new Element("PID.5", namespace);
		pid.addContent(pid5);
		pid5.addContent(new Element("XPN.1", namespace)
		.addContent(new Element("FN.1", namespace).addContent(data.getPerson().getPerson_surname())));
		pid5.addContent(new Element("XPN.2", namespace).addContent(data.getPerson().getPerson_kindfirstname()));
		if(data.getPerson().getPerson_kindlastname().equals("мер"))
		{
			pid5.addContent(new Element("XPN.3", namespace).addContent(""));
		}else{pid5.addContent(new Element("XPN.3", namespace).addContent(data.getPerson().getPerson_kindlastname()));}
		
		
		/* Bythday is new 
		 * 
		 */
		
		pid.addContent(new Element("PID.7", namespace).addContent(data.getPerson_birthday()));
		
		pid.addContent(new Element("PID.8", namespace).addContent(data.getPerson_sex()));
		
		pid.addContent( new Element("PID.23", namespace).addContent(data.getPerson().getPersonAdd().getBorn()));

		Element pid26 = new Element("PID.26", namespace);
		pid.addContent(pid26);
		pid26.addContent(new Element("CWE.1", namespace).addContent(String.valueOf(data.getRussian())));
		pid26.addContent(new Element("CWE.3", namespace).addContent("1.2.643.2.40.5.0.25.3"));
		
		if (String.valueOf(data.getPerson_docpersonid()).equals("1")) {		
			pid.addContent(new Element("PID.32", namespace).addContent("7"));
		}

		adt_a01.addContent(new Element("PV1", namespace).addContent(new Element("PV1.2", namespace).addContent("1")));
		
		Element adt_a01_1insurance = new Element("ADT_A01.INSURANCE", namespace);
		adt_a01.addContent(adt_a01_1insurance);
		
		Element in1_1 = new Element("IN1", namespace);
		adt_a01_1insurance.addContent(in1_1);
			
		in1_1.addContent(new Element("IN1.1", namespace).addContent("1"));

		in1_1.addContent(new Element("IN1.2", namespace).addContent(new Element("CWE.1", namespace).addContent("нля")));
					
		Element in1_1_3 = new Element("IN1.3", namespace);
		in1_1.addContent(in1_1_3);
		in1_1_3.addContent(new Element("CX.1", namespace).addContent((data.getZp().getIn1_3_cx1())));
		in1_1_3.addContent(new Element("CX.5", namespace).addContent("NII"));

		in1_1.addContent(new Element("IN1.12", namespace).addContent(data.getZpIn1_12()));

		in1_1.addContent(new Element("IN1.13", namespace).addContent(data.getZpIn1_13()));

		in1_1.addContent(new Element("IN1.15", namespace).addContent(data.getZp().getIn1_15()));
	
		Element in1_1_16 = new Element("IN1.16", namespace);
		in1_1.addContent(in1_1_16);
		in1_1_16.addContent(new Element("XPN.1", namespace).addContent(new Element("FN.1", namespace).addContent(data.getPerson().getPerson_surname())));
		in1_1_16.addContent(new Element("XPN.2", namespace).addContent(data.getPerson().getPerson_kindfirstname()));
		
		if(data.getPerson().getPerson_kindlastname().equals("мер") || data.getPerson().getPerson_kindlastname().equals("-"))
		{
			in1_1_16.addContent(new Element("XPN.3", namespace).addContent(""));
		}
		else
		{
			in1_1_16.addContent(new Element("XPN.3", namespace).addContent(data.getPerson().getPerson_kindlastname()));
		}
		

		in1_1.addContent(new Element("IN1.18", namespace).addContent(data.getZpPid7()));
		
		in1_1.addContent(new Element("IN1.35", namespace).addContent(data.getZp().getIn1_35()));
		
		in1_1.addContent(new Element("IN1.36", namespace).addContent(data.getZp().getIn1_36()));
		
		in1_1.addContent(new Element("IN1.43", namespace).addContent(data.getPerson_sex()));
		
		Element in1_1_49 = new Element("IN1.49", namespace);
		in1_1.addContent(in1_1_49);
		in1_1_49.addContent(new Element("CX.1", namespace).addContent(data.getZp().getPid3cx1_1()));
		Element in1_1_49cx4 = new Element("CX.4", namespace);
		in1_1_49.addContent(in1_1_49cx4);
		in1_1_49cx4.addContent(new Element("HD.1", namespace).addContent("50000"));
		in1_1_49cx4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1"));
		in1_1_49cx4.addContent(new Element("HD.3", namespace).addContent("ISO"));
		in1_1_49.addContent(new Element("CX.5", namespace).addContent("NI"));

		in1_1.addContent(new Element("IN1.52", namespace).addContent(data.getPerson().getPersonAdd().getBorn()));

	}

}
