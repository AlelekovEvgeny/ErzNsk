package message;

import java.text.SimpleDateFormat;
import java.util.Date;

import help.RandomGUID;

import org.jdom2.Element;
import org.jdom2.Namespace;

public class MessageP27 extends MessageCommon {
	
	public MessageP27() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageP27(int pERSON_SERDOC, int pERSON_NUMDOC,
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
		
		String curQuater = String.valueOf((Integer.parseInt(new SimpleDateFormat("MM").format(new Date()).replace("0", ""))) - 6);
		switch (curQuater) {
			case "1" : case "2" : case "3" : curQuater = "21"; break;
			case "4" : case "5" : case "6" : curQuater = "22"; break;
			case "7" : case "8" : case "9" : curQuater = "23"; break;
			case "10" : case "11" : case "12" : curQuater = "24"; break;
		}
		String curYear = new SimpleDateFormat("yyyy").format(new Date());
		
		Element zpi_zwi = new Element("ZPI_ZWI", namespace); rootElement.addContent(zpi_zwi);
		
		RandomGUID guidMsh = createMsh(namespace, curDate, zpi_zwi, "ZPI", "ZWI", "ZPI_ZWI");
		
		Element evn = new Element("EVN", namespace); zpi_zwi.addContent(evn);
		evn.addContent(new Element("EVN.2", namespace).addContent(curDate));
		evn.addContent(new Element("EVN.4", namespace).addContent("Ï26"));
		
		Element zwp = new Element("ZWP", namespace); zpi_zwi.addContent(zwp);
		Element zwp1 = new Element("ZWP.1", namespace); zwp.addContent(zwp1);
		zwp1.addContent(new Element("CNE.1", namespace).addContent(curQuater));
		zwp.addContent(new Element("ZWP.2", namespace).addContent(curYear));
		Element zwp3 = new Element("ZWP.3", namespace); zwp.addContent(zwp3);
		zwp3.addContent(new Element("CNE.1", namespace).addContent("50000"));

		Element pfr = new Element("PFR", namespace); zpi_zwi.addContent(pfr);
		pfr.addContent(new Element("PFR.1", namespace).addContent(dataList.get(1).get(PFR_SNILS)));
		pfr.addContent(new Element("PFR.2", namespace).addContent(dataList.get(1).get(PFR_ID)));
		pfr.addContent(new Element("PFR.3", namespace).addContent(dataList.get(1).get(PFR_NOTID)));
			
		listGuid(guidMsh, 1, "127");
	}
	
protected void createMiddle(int count, Namespace namespace,	Element rootElement, String curDate,boolean tt) {
		
		String curQuater = String.valueOf((Integer.parseInt(new SimpleDateFormat("MM").format(new Date()).replace("0", ""))) - 6);
		switch (curQuater) {
			case "1" : case "2" : case "3" : curQuater = "21"; break;
			case "4" : case "5" : case "6" : curQuater = "22"; break;
			case "7" : case "8" : case "9" : curQuater = "23"; break;
			case "10" : case "11" : case "12" : curQuater = "24"; break;
		}
		String curYear = new SimpleDateFormat("yyyy").format(new Date());
		
		Element zpi_zwi = new Element("ZPI_ZWI", namespace); rootElement.addContent(zpi_zwi);
		
		RandomGUID guidMsh = createMsh(namespace, curDate, zpi_zwi, "ZPI", "ZWI", "ZPI_ZWI");
		
		Element evn = new Element("EVN", namespace); zpi_zwi.addContent(evn);
		evn.addContent(new Element("EVN.2", namespace).addContent(curDate));
		evn.addContent(new Element("EVN.4", namespace).addContent("Ï26"));
		
		Element zwp = new Element("ZWP", namespace); zpi_zwi.addContent(zwp);
		Element zwp1 = new Element("ZWP.1", namespace); zwp.addContent(zwp1);
		zwp1.addContent(new Element("CNE.1", namespace).addContent(curQuater));
		zwp.addContent(new Element("ZWP.2", namespace).addContent(curYear));
		Element zwp3 = new Element("ZWP.3", namespace); zwp.addContent(zwp3);
		zwp3.addContent(new Element("CNE.1", namespace).addContent("50000"));

		Element pfr = new Element("PFR", namespace); zpi_zwi.addContent(pfr);
		pfr.addContent(new Element("PFR.1", namespace).addContent(dataList.get(1).get(PFR_SNILS)));
		pfr.addContent(new Element("PFR.2", namespace).addContent(dataList.get(1).get(PFR_ID)));
		pfr.addContent(new Element("PFR.3", namespace).addContent(dataList.get(1).get(PFR_NOTID)));
			
		listGuid(guidMsh, 1, "127");
	}

@Override
protected void createMiddle(int count, Namespace namespace,
		Element rootElement, String curDate, boolean tt, String kluch) {
	// TODO Auto-generated method stub
	
}

}
