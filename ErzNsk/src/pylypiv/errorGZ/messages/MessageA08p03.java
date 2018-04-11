package pylypiv.errorGZ.messages;

import org.jdom2.Element;
import org.jdom2.Namespace;

import pylypiv.errorGZ.main.Data;
import util.FileTransfer;
import util.ResObject;

public class MessageA08p03 extends MessageImpl {
	
	public MessageA08p03(FileTransfer fileTransfer, ResObject res, Data data) {
		super(fileTransfer, res, data);
	}

	protected void createMiddle(Namespace namespace,
			Element rootElement, String curDate) {
	
		Element adt_a01 = new Element("ADT_A01", namespace);
		rootElement.addContent(adt_a01);
		
		createMsh(namespace, curDate, adt_a01, "ADT", "A08", "ADT_A01");		
				
		Element evn = new Element("EVN", namespace);
		adt_a01.addContent(evn);
		evn.addContent(new Element("EVN.2", namespace).addContent(curDate));
		evn.addContent(new Element("EVN.4", namespace).addContent("Ï03"));
				
		createPid(namespace, adt_a01);
		
		adt_a01.addContent(new Element("PV1", namespace).addContent(new Element("PV1.2", namespace).addContent("1")));
		
		//IN1-1
		Element adt_a01_1insurance = new Element("ADT_A01.INSURANCE", namespace);
		adt_a01.addContent(adt_a01_1insurance);
		
		Element in1_1 = new Element("IN1", namespace);
		adt_a01_1insurance.addContent(in1_1);
			
		in1_1.addContent(new Element("IN1.1", namespace).addContent("1"));

		in1_1.addContent(new Element("IN1.2", namespace).addContent(new Element("CWE.1", namespace).addContent("ÎÌÑ")));
					
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
		in1_1_16.addContent(new Element("XPN.3", namespace).addContent(data.getPerson_kindlastname()));

		in1_1.addContent(new Element("IN1.18", namespace).addContent(data.getPerson_birthday()));
		
		in1_1.addContent(new Element("IN1.35", namespace).addContent(data.getZp().getIn1_35()));
		
		in1_1.addContent(new Element("IN1.36", namespace).addContent(data.getZp().getIn1_36()));

		in1_1.addContent(new Element("IN1.52", namespace).addContent(data.getPerson().getPersonAdd().getBorn()));

		//IN1-2
		Element adt_a01_2insurance = new Element("ADT_A01.INSURANCE", namespace);
		adt_a01.addContent(adt_a01_2insurance);
		
		Element in1_2 = new Element("IN1", namespace);
		adt_a01_2insurance.addContent(in1_2);
			
		in1_2.addContent(new Element("IN1.1", namespace).addContent("2"));

		in1_2.addContent(new Element("IN1.2", namespace).addContent(new Element("CWE.1", namespace).addContent("ÎÌÑ")));
					
		Element in1_2_3 = new Element("IN1.3", namespace);
		in1_2.addContent(in1_2_3);
		String smo;
		switch (data.getPerson().getPerson_linksmoestablishmentid()) {
			case 1 : smo = "1025403200151";
			break;
			case 2 : smo = "1177746612581";
			break;
			case 3 : smo = "1024201390740";
			break;
			case 4 : smo = "1045207042528";
			break;
			default : smo = "0";
			break;
		}
		in1_2_3.addContent(new Element("CX.1", namespace).addContent(smo));
		in1_2_3.addContent(new Element("CX.5", namespace).addContent("NII"));

		in1_2.addContent(new Element("IN1.12", namespace).addContent(data.getZad()));
		
		if ("".equals(data.getPerson().getPersonAdd().getVs_num()) || "NULL".equals(data.getPerson().getPersonAdd().getVs_num())) {
			in1_2.addContent(new Element("IN1.13", namespace).addContent(""));			
		} else {
			in1_2.addContent(new Element("IN1.13", namespace).addContent(data.getZadplus40()));
		}

		in1_2.addContent(new Element("IN1.15", namespace).addContent("50000"));

		if ("".equals(data.getPerson().getPersonAdd().getVs_num()) || "NULL".equals(data.getPerson().getPersonAdd().getVs_num())) {
			in1_2.addContent(new Element("IN1.35", namespace).addContent("Ï"));
			in1_2.addContent(new Element("IN1.36", namespace).addContent(data.getNlanc()));				
		} else {
			in1_2.addContent(new Element("IN1.35", namespace).addContent("Â"));
			in1_2.addContent(new Element("IN1.36", namespace).addContent(data.getPerson().getPersonAdd().getVs_num()));	
		}	
	}
}
