package model.message;

import help.RandomGUID;

import org.jdom2.Element;
import org.jdom2.Namespace;

public class MessageA08p03kol extends MessageCommon {
	
	public MessageA08p03kol() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageA08p03kol(int pERSON_SERDOC, int pERSON_NUMDOC,
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
		
			Element adt_a01 = new Element("ADT_A01", namespace);
			rootElement.addContent(adt_a01);
	
			RandomGUID guidMsh = createMsh(namespace, curDate, adt_a01, "ADT", "A08", "ADT_A01");	
					
			Element evn = new Element("EVN", namespace);
			adt_a01.addContent(evn);
			evn.addContent(new Element("EVN.2", namespace).addContent(curDate));
			evn.addContent(new Element("EVN.4", namespace).addContent("Ï03"));
					
			createPid(namespace, i, adt_a01);
	
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
			in1_1_3.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(USER_SMO)));
			in1_1_3.addContent(new Element("CX.5", namespace).addContent("NII"));
			
			in1_1.addContent(new Element("IN1.12", namespace).addContent(dataList.get(i).get(USER_D_12)));
	
			in1_1.addContent(new Element("IN1.13", namespace).addContent(dataList.get(i).get(USER_D_13)));
	
			in1_1.addContent(new Element("IN1.15", namespace).addContent(dataList.get(i).get(USER_OKATO_3)));
		
			Element in1_1_16 = new Element("IN1.16", namespace);
			in1_1.addContent(in1_1_16);
			in1_1_16.addContent(new Element("XPN.1", namespace).addContent(new Element("FN.1", namespace).addContent(dataList.get(i).get(USER_PERSON_SURNAME))));
			in1_1_16.addContent(new Element("XPN.2", namespace).addContent(dataList.get(i).get(USER_PERSON_KINDFIRSTNAME)));
			in1_1_16.addContent(new Element("XPN.3", namespace).addContent(dataList.get(i).get(USER_PERSON_KINDLASTNAME)));
	
			in1_1.addContent(new Element("IN1.18", namespace).addContent(dataList.get(i).get(PERSON_BIRTHDAY)));
			
			in1_1.addContent(new Element("IN1.35", namespace).addContent(dataList.get(i).get(USER_TYPE_POL)));
			
			in1_1.addContent(new Element("IN1.36", namespace).addContent(dataList.get(i).get(USER_POL)));
	
			in1_1.addContent(new Element("IN1.52", namespace).addContent(dataList.get(i).get(BORN)));
	
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
			switch (dataList.get(i).get(PERSON_LINKSMOESTABLISHMENTID)) {
				case "1" : smo = "1025403200151";
				break;
				case "2" : smo = "1177746612581";
				break;
				case "3" : smo = "1024201390740";
				break;
				case "4" : smo = "1045207042528";
				break;
				default : smo = "0";
				break;
			}
			in1_2_3.addContent(new Element("CX.1", namespace).addContent(smo));
			in1_2_3.addContent(new Element("CX.5", namespace).addContent("NII"));
	
			in1_2.addContent(new Element("IN1.12", namespace).addContent(dataList.get(i).get(ZAD)));
	
			in1_2.addContent(new Element("IN1.13", namespace).addContent(dataList.get(i).get(ZADPLUS40)));
	
			in1_2.addContent(new Element("IN1.15", namespace).addContent("50000"));
	
			if ("".equals(dataList.get(i).get(VS_NUM)) || "NULL".equals(dataList.get(i).get(VS_NUM))) {
				if(dataList.get(i).get(TYPE_POL).equals("Ý")){
					in1_2.addContent(new Element("IN1.35", namespace).addContent("Ý"));
					in1_2.addContent(new Element("IN1.36", namespace).addContent(dataList.get(i).get(NBLANC)));
				}else{
					in1_2.addContent(new Element("IN1.35", namespace).addContent("Ï"));
					in1_2.addContent(new Element("IN1.36", namespace).addContent(dataList.get(i).get(NBLANC)));	
				}				
			} else {
				in1_2.addContent(new Element("IN1.35", namespace).addContent("Â"));
				in1_2.addContent(new Element("IN1.36", namespace).addContent(dataList.get(i).get(VS_NUM)));	
			}	
					
			listGuid(guidMsh, i, "3");
		}
	}

	protected void createMiddle(int count, Namespace namespace,
			Element rootElement, String curDate,boolean tt) {
		for (int i = 1; i < count; i++) {
		
			Element adt_a01 = new Element("ADT_A01", namespace);
			rootElement.addContent(adt_a01);
	
			RandomGUID guidMsh = createMsh(namespace, curDate, adt_a01, "ADT", "A08", "ADT_A01");	
					
			Element evn = new Element("EVN", namespace);
			adt_a01.addContent(evn);
			evn.addContent(new Element("EVN.2", namespace).addContent(curDate));
			evn.addContent(new Element("EVN.4", namespace).addContent("Ï03"));
					
			createPid(namespace, i, adt_a01);
	
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
			in1_1_3.addContent(new Element("CX.1", namespace).addContent(dataList.get(i).get(USER_SMO)));
			in1_1_3.addContent(new Element("CX.5", namespace).addContent("NII"));
			
			in1_1.addContent(new Element("IN1.12", namespace).addContent(dataList.get(i).get(USER_D_12)));
	
			in1_1.addContent(new Element("IN1.13", namespace).addContent(dataList.get(i).get(USER_D_13)));
	
			in1_1.addContent(new Element("IN1.15", namespace).addContent(dataList.get(i).get(USER_OKATO_3)));
		
			Element in1_1_16 = new Element("IN1.16", namespace);
			in1_1.addContent(in1_1_16);
			in1_1_16.addContent(new Element("XPN.1", namespace).addContent(new Element("FN.1", namespace).addContent(dataList.get(i).get(USER_PERSON_SURNAME))));
			in1_1_16.addContent(new Element("XPN.2", namespace).addContent(dataList.get(i).get(USER_PERSON_KINDFIRSTNAME)));
			in1_1_16.addContent(new Element("XPN.3", namespace).addContent(dataList.get(i).get(USER_PERSON_KINDLASTNAME)));
	
			in1_1.addContent(new Element("IN1.18", namespace).addContent(dataList.get(i).get(PERSON_BIRTHDAY)));
			
			in1_1.addContent(new Element("IN1.35", namespace).addContent(dataList.get(i).get(USER_TYPE_POL)));
			
			in1_1.addContent(new Element("IN1.36", namespace).addContent(dataList.get(i).get(USER_POL)));
	
			in1_1.addContent(new Element("IN1.52", namespace).addContent(dataList.get(i).get(BORN)));
	
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
			switch (dataList.get(i).get(PERSON_LINKSMOESTABLISHMENTID)) {
				case "1" : smo = "1025403200151";
				break;
				case "2" : smo = "1177746612581";
				break;
				case "3" : smo = "1024201390740";
				break;
				case "4" : smo = "1045207042528";
				break;
				default : smo = "0";
				break;
			}
			in1_2_3.addContent(new Element("CX.1", namespace).addContent(smo));
			in1_2_3.addContent(new Element("CX.5", namespace).addContent("NII"));
	
			in1_2.addContent(new Element("IN1.12", namespace).addContent(dataList.get(i).get(ZAD)));
	
			in1_2.addContent(new Element("IN1.13", namespace).addContent(dataList.get(i).get(ZADPLUS40)));
	
			in1_2.addContent(new Element("IN1.15", namespace).addContent("50000"));
	
			if ("".equals(dataList.get(i).get(VS_NUM)) || "NULL".equals(dataList.get(i).get(VS_NUM))) {
				if(dataList.get(i).get(TYPE_POL).equals("Ý")){
					in1_2.addContent(new Element("IN1.35", namespace).addContent("Ý"));
					in1_2.addContent(new Element("IN1.36", namespace).addContent(dataList.get(i).get(NBLANC)));
				}else{
					in1_2.addContent(new Element("IN1.35", namespace).addContent("Ï"));
					in1_2.addContent(new Element("IN1.36", namespace).addContent(dataList.get(i).get(NBLANC)));	
				}				
			} else {
				in1_2.addContent(new Element("IN1.35", namespace).addContent("Â"));
				in1_2.addContent(new Element("IN1.36", namespace).addContent(dataList.get(i).get(VS_NUM)));	
			}	
					
			listGuid(guidMsh, i, "3");
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
