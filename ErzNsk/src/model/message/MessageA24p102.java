package model.message;

import help.RandomGUID;

import org.jdom2.Element;
import org.jdom2.Namespace;

public class MessageA24p102 extends MessageCommon {
	
	public MessageA24p102() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageA24p102(int pERSON_SERDOC, int pERSON_NUMDOC,
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

	protected void createMiddle(int count, Namespace namespace,
			Element rootElement, String curDate) {
		for (int i = 1; i < count; i++) {
		
			Element adt_a24 = new Element("ADT_A24", namespace);
			rootElement.addContent(adt_a24);
			
			RandomGUID guidMsh = createMsh(namespace, curDate, adt_a24, "ADT", "A24", "ADT_A24");	
			
			Element evn = new Element("EVN", namespace);
			adt_a24.addContent(evn);
			evn.addContent(new Element("EVN.2", namespace).addContent(curDate));
			evn.addContent(new Element("EVN.4", namespace).addContent("Ï10"));
			
			//PID-2
			Element pid = new Element("PID", namespace);
			adt_a24.addContent(pid);
			
			Element pid3_1 = new Element("PID.3", namespace);
			pid.addContent(pid3_1);
			pid3_1.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(PERSON_SERDOC) + " ¹ " + dataList.get(i).get(PERSON_NUMDOC)));
			pid3_1.addContent(new Element("CX.5", namespace).addContent(dataList.get(i).get(PERSON_DOCPERSONID)));
			pid3_1.addContent(new Element("CX.7", namespace).addContent(dataList.get(i).get(DATEPASSPORT)));
	
			if (dataList.get(i).get(PERSON_DOCPERSONID).equals("9")) {
				Element pid3_2 = new Element("PID.3", namespace);
				pid.addContent(pid3_2);
				pid3_2.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(PERSON_SERDOC) + " ¹ " + dataList.get(i).get(PERSON_NUMDOC)));
				pid3_2.addContent(new Element("CX.5", namespace).addContent("23"));
				pid3_2.addContent(new Element("CX.7", namespace).addContent(dataList.get(i).get(DATEPASSPORT)));
			}
				
			Element pid3_3 = new Element("PID.3", namespace);
			pid.addContent(pid3_3);
			pid3_3.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(SNILS)));
			pid3_3.addContent(new Element("CX.5", namespace).addContent("PEN"));
			
			Element pid3_4 = new Element("PID.3", namespace);
			pid.addContent(pid3_4);
			pid3_4.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(USER_ENP)));
			Element pid3_4cx4 = new Element("CX.4", namespace);
			pid3_4.addContent(pid3_4cx4);
			pid3_4cx4.addContent(new Element("HD.1", namespace).addContent(dataList.get(i).get(USER_OKATO_3)));
			pid3_4cx4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1"));
			pid3_4cx4.addContent(new Element("HD.3", namespace).addContent("ISO"));
			pid3_4.addContent(new Element("CX.5", namespace).addContent("NI"));
					
			Element pid5 = new Element("PID.5", namespace);
			pid.addContent(pid5);
			pid5.addContent(new Element("XPN.1", namespace)
			.addContent(new Element("FN.1", namespace).addContent(dataList.get(i).get(USER_PERSON_SURNAME))));
			pid5.addContent(new Element("XPN.2", namespace).addContent(dataList.get(i).get(USER_PERSON_KINDFIRSTNAME)));
			pid5.addContent(new Element("XPN.3", namespace).addContent(dataList.get(i).get(USER_PERSON_KINDLASTNAME)));
	
			pid.addContent(new Element("PID.7", namespace).addContent(dataList.get(i).get(PERSON_BIRTHDAY)));
			
			pid.addContent(new Element("PID.8", namespace).addContent(dataList.get(i).get(PERSON_SEX)));
			
			pid.addContent( new Element("PID.23", namespace).addContent(dataList.get(i).get(BORN)));
	
			Element pid26 = new Element("PID.26", namespace);
			pid.addContent(pid26);
			pid26.addContent(new Element("CWE.1", namespace).addContent("RUS"));
			pid26.addContent(new Element("CWE.3", namespace).addContent("1.2.643.2.40.5.0.25.3"));
	
			if (dataList.get(i).get(PERSON_DOCPERSONID).equals("1")) {		
				pid.addContent(new Element("PID.32", namespace).addContent("7"));
			}
			
			//PID-1
			createPid(namespace, i, adt_a24);	
				
			listGuid(guidMsh, i, "111");

		}
	}

	protected void createMiddle(int count, Namespace namespace,
			Element rootElement, String curDate,boolean tt) {
		for (int i = 1; i < count; i++) {
		
			Element adt_a24 = new Element("ADT_A24", namespace);
			rootElement.addContent(adt_a24);
			
			RandomGUID guidMsh = createMsh(namespace, curDate, adt_a24, "ADT", "A24", "ADT_A24");	
			
			Element evn = new Element("EVN", namespace);
			adt_a24.addContent(evn);
			evn.addContent(new Element("EVN.2", namespace).addContent(curDate));
			evn.addContent(new Element("EVN.4", namespace).addContent("Ï10"));
			
			//PID-2
			Element pid = new Element("PID", namespace);
			adt_a24.addContent(pid);
			
			Element pid3_1 = new Element("PID.3", namespace);
			pid.addContent(pid3_1);
			pid3_1.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(PERSON_SERDOC) + " ¹ " + dataList.get(i).get(PERSON_NUMDOC)));
			pid3_1.addContent(new Element("CX.5", namespace).addContent(dataList.get(i).get(PERSON_DOCPERSONID)));
			pid3_1.addContent(new Element("CX.7", namespace).addContent(dataList.get(i).get(DATEPASSPORT)));
	
			if (dataList.get(i).get(PERSON_DOCPERSONID).equals("9")) {
				Element pid3_2 = new Element("PID.3", namespace);
				pid.addContent(pid3_2);
				pid3_2.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(PERSON_SERDOC) + " ¹ " + dataList.get(i).get(PERSON_NUMDOC)));
				pid3_2.addContent(new Element("CX.5", namespace).addContent("23"));
				pid3_2.addContent(new Element("CX.7", namespace).addContent(dataList.get(i).get(DATEPASSPORT)));
			}
				
			Element pid3_3 = new Element("PID.3", namespace);
			pid.addContent(pid3_3);
			pid3_3.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(SNILS)));
			pid3_3.addContent(new Element("CX.5", namespace).addContent("PEN"));
			
			Element pid3_4 = new Element("PID.3", namespace);
			pid.addContent(pid3_4);
			pid3_4.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(USER_ENP)));
			Element pid3_4cx4 = new Element("CX.4", namespace);
			pid3_4.addContent(pid3_4cx4);
			pid3_4cx4.addContent(new Element("HD.1", namespace).addContent(dataList.get(i).get(USER_OKATO_3)));
			pid3_4cx4.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1"));
			pid3_4cx4.addContent(new Element("HD.3", namespace).addContent("ISO"));
			pid3_4.addContent(new Element("CX.5", namespace).addContent("NI"));
					
			Element pid5 = new Element("PID.5", namespace);
			pid.addContent(pid5);
			pid5.addContent(new Element("XPN.1", namespace)
			.addContent(new Element("FN.1", namespace).addContent(dataList.get(i).get(USER_PERSON_SURNAME))));
			pid5.addContent(new Element("XPN.2", namespace).addContent(dataList.get(i).get(USER_PERSON_KINDFIRSTNAME)));
			pid5.addContent(new Element("XPN.3", namespace).addContent(dataList.get(i).get(USER_PERSON_KINDLASTNAME)));
	
			pid.addContent(new Element("PID.7", namespace).addContent(dataList.get(i).get(PERSON_BIRTHDAY)));
			
			pid.addContent(new Element("PID.8", namespace).addContent(dataList.get(i).get(PERSON_SEX)));
			
			pid.addContent( new Element("PID.23", namespace).addContent(dataList.get(i).get(BORN)));
	
			Element pid26 = new Element("PID.26", namespace);
			pid.addContent(pid26);
			pid26.addContent(new Element("CWE.1", namespace).addContent("RUS"));
			pid26.addContent(new Element("CWE.3", namespace).addContent("1.2.643.2.40.5.0.25.3"));
	
			if (dataList.get(i).get(PERSON_DOCPERSONID).equals("1")) {		
				pid.addContent(new Element("PID.32", namespace).addContent("7"));
			}
			
			//PID-1
			createPid(namespace, i, adt_a24);	
				
			listGuid(guidMsh, i, "111");

		}
	}

	@Override
	protected void createMiddle(int count, Namespace namespace,
			Element rootElement, String curDate, boolean tt, String kluch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createMiddle(Namespace namespace, Element rootElement, String curDate) {
		// TODO Auto-generated method stub
		
	}

}
