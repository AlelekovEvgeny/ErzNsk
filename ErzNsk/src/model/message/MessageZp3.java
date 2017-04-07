package model.message;

import help.RandomGUID;

import org.jdom2.Element;
import org.jdom2.Namespace;

public class MessageZp3 extends MessageCommon {
	
	public MessageZp3() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageZp3(int pERSON_SERDOC, int pERSON_NUMDOC,
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

	protected void createMiddle(int count, Namespace namespace,Element rootElement, String curDate,boolean tt) {
		
			Element qbp_zp3 = new Element("QBP_ZP2", namespace);
			rootElement.addContent(qbp_zp3);
			
			RandomGUID guidMsh = createMsh(namespace, curDate, qbp_zp3, "QBP", "ZP3", "QBP_ZP2");	
			
			Element qpd = new Element("QPD", namespace);
			qbp_zp3.addContent(qpd);
			
			Element qpd1 = new Element("QPD.1", namespace);
			qpd.addContent(qpd1);
			qpd1.addContent(new Element("CWE.1", namespace).addContent("—Õ“"));
			qpd1.addContent(new Element("CWE.3", namespace).addContent("1.2.643.2.40.1.9"));
			
			Element qpd12 = new Element("QPD.12", namespace);
			qpd.addContent(qpd12);
			qpd12.addContent(new Element("HD.1", namespace).addContent("50000"));
			qpd12.addContent(new Element("HD.2", namespace).addContent("1.2.643.2.40.3.3.1.0"));
			qpd12.addContent(new Element("HD.3", namespace).addContent("ISO"));
			
			/*Element qpd14 = new Element("QPD.14", namespace);
			qpd.addContent(qpd14);
			qpd14.addContent(new Element("DR.1", namespace).addContent("2017-03-20"));
			qpd14.addContent(new Element("DR.2", namespace).addContent("2017-03-21"));*/
			
			
			
			//listGuid(guidMsh, 1, "108");
		
	}

	@Override
	protected void createMiddle(int count, Namespace namespace, Element rootElement, String curDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createMiddle(int count, Namespace namespace, Element rootElement, String curDate, boolean tt,
			String kluch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createMiddle(Namespace namespace, Element rootElement, String curDate) {
		// TODO Auto-generated method stub
		
	}

	


}
