package message;

import help.RandomGUID;

import org.jdom2.Element;
import org.jdom2.Namespace;

public class MessageA08v01 extends MessageCommon {
	
	public MessageA08v01() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageA08v01(int pERSON_SERDOC, int pERSON_NUMDOC,
			int pERSON_DOCPERSONID, int pERSON_SURNAME,
			int pERSON_KINDFIRSTNAME, int pERSON_KINDLASTNAME,
			int pERSON_BIRTHDAY, int pERSON_SEX,
			int pERSON_LINKSMOESTABLISHMENTID, int eNP, int pERSON_ADDRESSID,
			int pERSON_DATEINPUT, int sNILS, int bORN, int dATEPASSPORT,
			int eNP_PA, int vS_NUM, int vS_DATE, int zAD, int d2, int sMO,
			int d_12, int d_13, int oKATO_3, int tYPE_POL, int pOL, int eNP_1,
			int eNP_2, int p14cx1, int p14cx5, int p14cx6, int p14cx7,
			int xPN1, int xPN2, int xPN3, int uSERNAME, int zADMINUS1,
			int zADPLUS40, int nBLANC, int vS_DATEPLUS1, int uSER_ENP,
			int uSER_PERSON_SURNAME, int uSER_PERSON_KINDFIRSTNAME,
			int uSER_PERSON_KINDLASTNAME, int uSER_SMO, int uSER_D_12,
			int uSER_D_13, int uSER_OKATO_3, int uSER_TYPE_POL, int uSER_POL,
			int rUSSIAN, int d_V, int d_SER, int d_NUM, int pR_FAM, int pR_IM,
			int pR_OT, int lAST_FAM, int lAST_IM, int lAST_OT, int lAST_DR,
			int pFR_SNILS, int pFR_ID, int pFR_NOTID, int uSER_SERDOC,
			int uSER_NUMDOC, int uSER_DOCID, int uSER_DOC_DATE, int d_12_PLUS1) {
		super(pERSON_SERDOC, pERSON_NUMDOC, pERSON_DOCPERSONID, pERSON_SURNAME,
				pERSON_KINDFIRSTNAME, pERSON_KINDLASTNAME, pERSON_BIRTHDAY, pERSON_SEX,
				pERSON_LINKSMOESTABLISHMENTID, eNP, pERSON_ADDRESSID, pERSON_DATEINPUT,
				sNILS, bORN, dATEPASSPORT, eNP_PA, vS_NUM, vS_DATE, zAD, d2, sMO, d_12,
				d_13, oKATO_3, tYPE_POL, pOL, eNP_1, eNP_2, p14cx1, p14cx5, p14cx6,
				p14cx7, xPN1, xPN2, xPN3, uSERNAME, zADMINUS1, zADPLUS40, nBLANC,
				vS_DATEPLUS1, uSER_ENP, uSER_PERSON_SURNAME, uSER_PERSON_KINDFIRSTNAME,
				uSER_PERSON_KINDLASTNAME, uSER_SMO, uSER_D_12, uSER_D_13, uSER_OKATO_3,
				uSER_TYPE_POL, uSER_POL, rUSSIAN, d_V, d_SER, d_NUM, pR_FAM, pR_IM,
				pR_OT, lAST_FAM, lAST_IM, lAST_OT, lAST_DR, pFR_SNILS, pFR_ID,
				pFR_NOTID, uSER_SERDOC, uSER_NUMDOC, uSER_DOCID, uSER_DOC_DATE,
				d_12_PLUS1);
		// TODO Auto-generated constructor stub
	}

	protected void createMiddle(int count, Namespace namespace,	Element rootElement, String curDate) {
		
		for (int i = 1; i < count; i++) {
			
			Element adt_a01 = new Element("ADT_A01", namespace);
			rootElement.addContent(adt_a01);

			RandomGUID guidMsh = createMsh(namespace, curDate, adt_a01, "ADT", "A08", "ADT_A01");	
				
			Element evn = new Element("EVN", namespace);
			adt_a01.addContent(evn);
			evn.addContent(new Element("EVN.2", namespace).addContent(curDate));
			evn.addContent(new Element("EVN.4", namespace).addContent("Â01"));

			Element pid = new Element("PID", namespace);
			adt_a01.addContent(pid);
			
			Element pid3_1 = new Element("PID.3", namespace);
			pid.addContent(pid3_1);
			String seria = dataList.get(i).get(PERSON_SERDOC);
			pid3_1.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " ¹ ") + dataList.get(i).get(PERSON_NUMDOC)));
			pid3_1.addContent(new Element("CX.5", namespace).addContent(dataList.get(i).get(PERSON_DOCPERSONID)));
			pid3_1.addContent(new Element("CX.7", namespace).addContent(dataList.get(i).get(DATEPASSPORT)));
			pid3_1.addContent(new Element("CX.8", namespace).addContent(dataList.get(i).get(D2)));

			if (dataList.get(i).get(PERSON_DOCPERSONID).equals("9")) {
				Element pid3_2 = new Element("PID.3", namespace);
				pid.addContent(pid3_2);
				pid3_2.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " ¹ ") + dataList.get(i).get(PERSON_NUMDOC)));
				pid3_2.addContent(new Element("CX.5", namespace).addContent("23"));
				pid3_2.addContent(new Element("CX.7", namespace).addContent(dataList.get(i).get(DATEPASSPORT)));
				pid3_2.addContent(new Element("CX.8", namespace).addContent(dataList.get(i).get(D2)));
			}
			
			if (dataList.get(i).get(PERSON_DOCPERSONID).equals("21")) {
				Element pid3_5 = new Element("PID.3", namespace);
				pid.addContent(pid3_5);
				pid3_5.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " ¹ ") + dataList.get(i).get(PERSON_NUMDOC)));
				pid3_5.addContent(new Element("CX.5", namespace).addContent("23"));
				String d2 = dataList.get(i).get(D2);
				String d2minus3year = String.valueOf(Integer.parseInt(d2.substring(0, 4)) - 3) + d2.substring(4);
				pid3_5.addContent(new Element("CX.7", namespace).addContent(d2minus3year));
				pid3_5.addContent(new Element("CX.8", namespace).addContent(dataList.get(i).get(D2)));
			}
			
			if (!"".equals(dataList.get(i).get(SNILS))) {	
				Element pid3_3 = new Element("PID.3", namespace);
				pid.addContent(pid3_3);
				pid3_3.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(SNILS)));
				pid3_3.addContent(new Element("CX.5", namespace).addContent("PEN"));
			}
						
			Element pid3_4 = new Element("PID.3", namespace);
			pid.addContent(pid3_4);
			if ("".equals(dataList.get(i).get(ENP_PA))) {
				pid3_4.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(ENP)));
			} else {
				pid3_4.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(ENP_PA)));
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
			.addContent(new Element("FN.1", namespace).addContent(dataList.get(i).get(PERSON_SURNAME))));
			pid5.addContent(new Element("XPN.2", namespace).addContent(dataList.get(i).get(PERSON_KINDFIRSTNAME)));
			pid5.addContent(new Element("XPN.3", namespace).addContent(dataList.get(i).get(PERSON_KINDLASTNAME)));

			pid.addContent(new Element("PID.7", namespace).addContent(dataList.get(i).get(PERSON_BIRTHDAY)));
			
			pid.addContent(new Element("PID.8", namespace).addContent(dataList.get(i).get(PERSON_SEX)));
			
			pid.addContent( new Element("PID.23", namespace).addContent(dataList.get(i).get(BORN)));

			Element pid26 = new Element("PID.26", namespace);
			pid.addContent(pid26);
			pid26.addContent(new Element("CWE.1", namespace).addContent(dataList.get(i).get(RUSSIAN)));
			pid26.addContent(new Element("CWE.3", namespace).addContent("1.2.643.2.40.5.0.25.3"));

			if (dataList.get(i).get(PERSON_DOCPERSONID).equals("1")) {		
				pid.addContent(new Element("PID.32", namespace).addContent("7"));
			}

			adt_a01.addContent(new Element("PV1", namespace).addContent(new Element("PV1.2", namespace).addContent("1")));
			
			Element adt_a01insurance = new Element("ADT_A01.INSURANCE", namespace);
			adt_a01.addContent(adt_a01insurance);
			
			Element in1 = new Element("IN1", namespace);
			adt_a01insurance.addContent(in1);
				
			in1.addContent(new Element("IN1.1", namespace).addContent("1"));

			in1.addContent(new Element("IN1.2", namespace).addContent(new Element("CWE.1", namespace).addContent("ÎÌÑ")));
						
			Element in1_3 = new Element("IN1.3", namespace);
			in1.addContent(in1_3);
			String smo;
			switch (dataList.get(i).get(PERSON_LINKSMOESTABLISHMENTID)) {
				case "1" : smo = "1025403200151";
				break;
				case "2" : smo = "1027739051460";
				break;
				case "3" : smo = "1024201390740";
				break;
				case "4" : smo = "1045207042528";
				break;
				default : smo = "0";
				break;
			}
			in1_3.addContent(new Element("CX.1", namespace).addContent(smo));
			in1_3.addContent(new Element("CX.5", namespace).addContent("NII"));

			in1.addContent(new Element("IN1.12", namespace).addContent(dataList.get(i).get(ZAD)));

			in1.addContent(new Element("IN1.13", namespace).addContent(dataList.get(i).get(ZADPLUS40)));

			in1.addContent(new Element("IN1.15", namespace).addContent("50000"));

			if ("".equals(dataList.get(i).get(VS_NUM)) || "NULL".equals(dataList.get(i).get(VS_NUM))) {
				in1.addContent(new Element("IN1.35", namespace).addContent("Ï"));
				in1.addContent(new Element("IN1.36", namespace).addContent(dataList.get(i).get(NBLANC)));				
			} else {
				in1.addContent(new Element("IN1.35", namespace).addContent("Â"));
				in1.addContent(new Element("IN1.36", namespace).addContent(dataList.get(i).get(VS_NUM)));	
			}
					
			listGuid(guidMsh, i, "1");
		}
	}

protected void createMiddle(int count, Namespace namespace,	Element rootElement, String curDate,boolean tt) {
		
		for (int i = 1; i < count; i++) {
			
			Element adt_a01 = new Element("ADT_A01", namespace);
			rootElement.addContent(adt_a01);

			RandomGUID guidMsh = createMsh(namespace, curDate, adt_a01, "ADT", "A08", "ADT_A01");	
				
			Element evn = new Element("EVN", namespace);
			adt_a01.addContent(evn);
			evn.addContent(new Element("EVN.2", namespace).addContent(curDate));
			evn.addContent(new Element("EVN.4", namespace).addContent("Â01"));

			Element pid = new Element("PID", namespace);
			adt_a01.addContent(pid);
			
			Element pid3_1 = new Element("PID.3", namespace);
			pid.addContent(pid3_1);
			String seria = dataList.get(i).get(PERSON_SERDOC);
			pid3_1.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " ¹ ") + dataList.get(i).get(PERSON_NUMDOC)));
			pid3_1.addContent(new Element("CX.5", namespace).addContent(dataList.get(i).get(PERSON_DOCPERSONID)));
			pid3_1.addContent(new Element("CX.7", namespace).addContent(dataList.get(i).get(DATEPASSPORT)));
			pid3_1.addContent(new Element("CX.8", namespace).addContent(dataList.get(i).get(D2)));

			if (dataList.get(i).get(PERSON_DOCPERSONID).equals("9")) {
				Element pid3_2 = new Element("PID.3", namespace);
				pid.addContent(pid3_2);
				pid3_2.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " ¹ ") + dataList.get(i).get(PERSON_NUMDOC)));
				pid3_2.addContent(new Element("CX.5", namespace).addContent("23"));
				pid3_2.addContent(new Element("CX.7", namespace).addContent(dataList.get(i).get(DATEPASSPORT)));
				pid3_2.addContent(new Element("CX.8", namespace).addContent(dataList.get(i).get(D2)));
			}
			
			if (dataList.get(i).get(PERSON_DOCPERSONID).equals("21")) {
				Element pid3_5 = new Element("PID.3", namespace);
				pid.addContent(pid3_5);
				pid3_5.addContent(new Element("CX.1", namespace).addContent((("".equals(seria) || seria == null) ? "" : seria + " ¹ ") + dataList.get(i).get(PERSON_NUMDOC)));
				pid3_5.addContent(new Element("CX.5", namespace).addContent("23"));
				String d2 = dataList.get(i).get(D2);
				String d2minus3year = String.valueOf(Integer.parseInt(d2.substring(0, 4)) - 3) + d2.substring(4);
				pid3_5.addContent(new Element("CX.7", namespace).addContent(d2minus3year));
				pid3_5.addContent(new Element("CX.8", namespace).addContent(dataList.get(i).get(D2)));
			}
			
			if (!"".equals(dataList.get(i).get(SNILS))) {	
				Element pid3_3 = new Element("PID.3", namespace);
				pid.addContent(pid3_3);
				pid3_3.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(SNILS)));
				pid3_3.addContent(new Element("CX.5", namespace).addContent("PEN"));
			}
						
			Element pid3_4 = new Element("PID.3", namespace);
			pid.addContent(pid3_4);
			if ("".equals(dataList.get(i).get(ENP_PA))) {
				pid3_4.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(ENP)));
			} else {
				pid3_4.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(ENP_PA)));
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
			.addContent(new Element("FN.1", namespace).addContent(dataList.get(i).get(PERSON_SURNAME))));
			pid5.addContent(new Element("XPN.2", namespace).addContent(dataList.get(i).get(PERSON_KINDFIRSTNAME)));
			pid5.addContent(new Element("XPN.3", namespace).addContent(dataList.get(i).get(PERSON_KINDLASTNAME)));

			pid.addContent(new Element("PID.7", namespace).addContent(dataList.get(i).get(PERSON_BIRTHDAY)));
			
			pid.addContent(new Element("PID.8", namespace).addContent(dataList.get(i).get(PERSON_SEX)));
			
			pid.addContent( new Element("PID.23", namespace).addContent(dataList.get(i).get(BORN)));

			Element pid26 = new Element("PID.26", namespace);
			pid.addContent(pid26);
			pid26.addContent(new Element("CWE.1", namespace).addContent(dataList.get(i).get(RUSSIAN)));
			pid26.addContent(new Element("CWE.3", namespace).addContent("1.2.643.2.40.5.0.25.3"));

			if (dataList.get(i).get(PERSON_DOCPERSONID).equals("1")) {		
				pid.addContent(new Element("PID.32", namespace).addContent("7"));
			}

			adt_a01.addContent(new Element("PV1", namespace).addContent(new Element("PV1.2", namespace).addContent("1")));
			
			Element adt_a01insurance = new Element("ADT_A01.INSURANCE", namespace);
			adt_a01.addContent(adt_a01insurance);
			
			Element in1 = new Element("IN1", namespace);
			adt_a01insurance.addContent(in1);
				
			in1.addContent(new Element("IN1.1", namespace).addContent("1"));

			in1.addContent(new Element("IN1.2", namespace).addContent(new Element("CWE.1", namespace).addContent("ÎÌÑ")));
						
			Element in1_3 = new Element("IN1.3", namespace);
			in1.addContent(in1_3);
			String smo;
			switch (dataList.get(i).get(PERSON_LINKSMOESTABLISHMENTID)) {
				case "1" : smo = "1025403200151";
				break;
				case "2" : smo = "1027739051460";
				break;
				case "3" : smo = "1024201390740";
				break;
				case "4" : smo = "1045207042528";
				break;
				default : smo = "0";
				break;
			}
			in1_3.addContent(new Element("CX.1", namespace).addContent(smo));
			in1_3.addContent(new Element("CX.5", namespace).addContent("NII"));

			in1.addContent(new Element("IN1.12", namespace).addContent(dataList.get(i).get(ZAD)));

			in1.addContent(new Element("IN1.13", namespace).addContent(dataList.get(i).get(ZADPLUS40)));

			in1.addContent(new Element("IN1.15", namespace).addContent("50000"));

			if ("".equals(dataList.get(i).get(VS_NUM)) || "NULL".equals(dataList.get(i).get(VS_NUM))) {
				in1.addContent(new Element("IN1.35", namespace).addContent("Ï"));
				in1.addContent(new Element("IN1.36", namespace).addContent(dataList.get(i).get(NBLANC)));				
			} else {
				in1.addContent(new Element("IN1.35", namespace).addContent("Â"));
				in1.addContent(new Element("IN1.36", namespace).addContent(dataList.get(i).get(VS_NUM)));	
			}
					
			listGuid(guidMsh, i, "1");
		}
	}

@Override
protected void createMiddle(int count, Namespace namespace,
		Element rootElement, String curDate, boolean tt, String kluch) {
	// TODO Auto-generated method stub
	
}

}
