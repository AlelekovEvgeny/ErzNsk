package servlet;

import help.Const;

import java.util.List;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import loadparse.Load;
import loadparse.ZpLoadMock;
import message.Message;
import message.MessageA03p07;
import message.MessageA08p01;
import message.MessageA08p02;
import message.MessageA08p03;
import message.MessageA08p03For;
import message.MessageA08p03kol;
import message.MessageA08p03pr;
import message.MessageA08p04;
import message.MessageA08p06;
import message.MessageA08p16;
import message.MessageA08v01;
import message.MessageA13p09;
import message.MessageA24p10;
import message.MessageA24p102;
import message.MessageP26;
import message.MessageP27;
import message.MessageZp1;
import message.MessageZp1Fiod;
import message.MessageZp1pr;
import message.MessageZp9;
import model.handsontable.ListWebForXMLQuery;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import answer.AnswerData;
import answer.AnswerZp;
import task.Task;
import task.TaskMock;
import util.ConstantiNastrojki;
import util.UtilParseDbXml;

@SuppressWarnings("deprecation")
public class WsAnswer extends WebSocketServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected StreamInbound createWebSocketInbound(String arg0,HttpServletRequest request) {

		return new SocketMess(request);
	}

	private class SocketMess extends MessageInbound {
		// коллекция под ZP1, складывает ZP1 и 0 или 1 для вывода или нет екселя
		// после
		List<String> listKluchi = new ArrayList<String>();
		/*
		 * При загрузке и ОБНОВЛЕНИИ index.jsp стартует соединение по сокету и
		 * сразу же отправляет на сервер имя пользователя с ключом USER
		 * (USERPonomarev) это парситься и складывается в стринг. Иначе передать
		 * имя не удалось.
		 */
		String userMachine;
		private Message messageZp1;
		private Task task;
		private HttpServletRequest request;
		WsOutbound myoutbound;
		private SocketMess(HttpServletRequest request){
			this.request= request;
		}

		@Override
		protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.catalina.websocket.MessageInbound#onTextMessage(java.nio
		 * .CharBuffer) Алгоритм При старте index.jsp подлючается скрипт
		 * webSocketAnswer и происходит подлючение по сокету При каждом нажатии
		 * на запросы фомс отправляется на сервер имя пользователя При нажатии
		 * на zp1 отправляется что сообщение zp1 и при выборе варианта экселя
		 * отправляется 0 или 1 Эти 2 ключа кладутся в коллекцию и запускается
		 * по ифу то что пришло с клиента.
		 */
		protected void onTextMessage(CharBuffer arg0) throws IOException 
		{

			// отметаем входящие ключи меньше 15символов. Иначе метод будет
			// вызывать исключение
			//if (arg0.length() > 15 && arg0.length() < 25)
			//if(arg0.toString().equalsIgnoreCase("USERENTERSergeyPylypiv") || arg0.toString().substring(0, 17).equalsIgnoreCase("xmlotladkazaprosa"))
			if(arg0.toString().contains("USERENTERINTOSYSTEM") || arg0.toString().contains("xmlotladkazaprosa"))
			{
				// инициальзация вошедшего при нажатии нв запросы фомс
				userMachine = initUsername(arg0);
				// флаг отладки (блокируем копирование xml запросов в папку
				// output)
				flagOtladkiXML(arg0);
			} else
				{
				// ловим комбинацию ключей для zp1
				if (arg0.toString().equals("ZP1")|| arg0.toString().equals("0"))
				{ System.out.println("Добавлено в ZP1 в коллекцию  "+ arg0.toString()); parseEnterMesForZP1(arg0);}
					else
					{
						if(arg0.toString().equals("Zp1Ajax"))
						{
							listKluchi.add(arg0.toString());
							System.out.println("Зашла на сервер сокет первая часть запроса zp1ajax "+ listKluchi);
						}
						else if(arg0.toString().equals("Zp1taskA8P4")){
							listKluchi.add(arg0.toString());
							System.out.println("Зашла на сервер сокет первая часть запроса Zp1taskA8P4 "+ listKluchi);
						}
						else if(arg0.toString().length() >= 11 && arg0.toString().substring(2, 13).equals("Zp1taskA8P4"))
						{
							listKluchi.add(arg0.toString());
							System.out.println("Зашла на сервер сокет второя часть запроса Zp1taskA8P4 ");
						}
						else
						{
							if(arg0.toString().length() >= 11 && arg0.toString().substring(2, 9).equals("Zp1Ajax"))
							{
									listKluchi.add(arg0.toString());
									System.out.println("Зашла на сервер сокет второя часть запроса zp1ajax ");
							}
							else
							{
								if(arg0.toString().equals("A08P02test"))
								{
									listKluchi.add(arg0.toString());
									System.out.println("Зашла на сервер сокет первая часть A08P02test "+ listKluchi);
								}
								else
								{
									if(arg0.toString().length() >= 10 && arg0.toString().substring(2, 7).equals("list1"))
									{
											listKluchi.add(arg0.toString());
											System.out.println("Зашла на сервер сокет второя часть запроса A08P02test "+ listKluchi);
									}
									else
									{
										if(arg0.toString().equals("buttonZP9"))
										{
											listKluchi.add(arg0.toString());
											System.out.println("Зашла на сервер сокет первая часть ZP9test "+ listKluchi);
										}
										else
										{
											if(arg0.toString().length() >= 10 && (	arg0.toString().contains("list1enpzp9") ||	arg0.toString().contains("list1passportzp9") ||	arg0.toString().contains("list1snilszp9")	)	)
											{
													listKluchi.add(arg0.toString());
													System.out.println("Зашла на сервер сокет второя часть запроса ZP9test "+ listKluchi);
											}
											else
											{
												// доработать для остальных
												listKluchi.add(arg0.toString());
												listKluchi.add("ostalnizaprosi");
												System.out.println("Зашла на сервер остальные запросы "+ listKluchi);
											}
										}
									}
								}
							}
						}
					}		
				}			
				// for debug
				 System.out.println("Итог пойманных ключей в серверной вэбсокета");
				
				
				// распознаем вошли ли 2 ключа : zp1 и 1 или 0
				if (listKluchi.size() == 2 && listKluchi.get(0).equals("ZP1") && (listKluchi.get(1).equals("1") || listKluchi.get(1).equals("0"))) 
				{
					methoZp1(myoutbound);
					// очищаем коллекцию ключей 
					listKluchi.clear();
				} else 
				{
					
					// если не зп и вторым ключом идут...
					if (listKluchi.size() == 2 && !listKluchi.get(0).equals("ZP1")) 
					{
						
						// ловим второе сообщение (как ключ) для zp1ajax
							
							if(listKluchi.get(1).substring(2, 9).equals("Zp1Ajax"))
							{
								
								System.out.println("ловим ajax ");
								try {
										methoZp1(request,myoutbound,listKluchi.get(1),0);
								} catch (JAXBException e) {
										e.printStackTrace();
								}
								// очищаем коллекцию ключей 
								listKluchi.clear();
							}
							else if(listKluchi.get(1).substring(2, 13).equals("Zp1taskA8P4"))
							{
								System.out.println("ловим ZP1 для A08P04");
								try {
										methoZp1(request,myoutbound,listKluchi.get(1),1);
								} catch (JAXBException e) {
										e.printStackTrace();
								}
								// очищаем коллекцию ключей 
								listKluchi.clear();
							}
							else
							{
										try 
										{
											System.out.println("ДОЛЖНЫ ВОЙТИ");
											lovim_ostalnieZaprosi(myoutbound, listKluchi.get(0),listKluchi.get(1));
											// очищаем коллекцию ключей 
											listKluchi.clear();
										} catch (Exception e) {
											e.printStackTrace();
										}
							}
						
						
						
					}

				}
		}

		public void onOpen(WsOutbound outbound) {
			System.out.println("Open Client websocket");
			this.myoutbound = outbound;
		}

		public void onClose(int status) {
			System.out.println("Close Server websocket");

		}

		/*
		 * При загрузке и ОБНОВЛЕНИИ index.jsp стартует соединение по сокету и
		 * сразу же отправляет на сервер имя пользователя с ключом USERENTERINTOSYSTEM
		 * (USERENTERINTOSYSTEMPonomarev) это парситься и складывается в стринг. Иначе передать
		 * имя не удалось.
		 */
		private String initUsername(CharBuffer arg0) {
			String vr = null;
			if (arg0.toString().contains("USERENTERINTOSYSTEM")) {
				vr = arg0.toString().substring(19);
			}

			return vr;
		}

		/*
		 * Ловим со скрипта послание либо xmlotladkazaprosa1 либо
		 * xmlotladkazaprosa0 Анализируем ключ xmlotladkazaprosa и выполняем
		 * действие по этому ключу
		 */
		private void flagOtladkiXML(CharBuffer arg0) {
			if (arg0.toString().substring(0, 17)
					.equalsIgnoreCase("xmlotladkazaprosa")) {
				ConstantiNastrojki.otladkaXML = arg0.toString().substring(17);
				System.out.println("ConstantiNastrojki.otladkaXML= "
						+ ConstantiNastrojki.otladkaXML);
			}
		}

		/*
		 * Ловим комбинацию ключей для определения что нужен zp1
		 */
		private void parseEnterMesForZP1(CharBuffer arg0) {

			if (arg0.toString().equals("0")) {
				System.out.println("получил 0");
				listKluchi.add(arg0.toString());
			}
			if (arg0.toString().equals("1")) {
				System.out.println("получил 1");
				listKluchi.add(arg0.toString());
			}
			if (arg0.toString().equals("ZP1")) {
				System.out.println("получил zp1");
				listKluchi.add(arg0.toString());
				
			}
			System.out.println("size " + listKluchi.size());
		}

		private void methoZp1(WsOutbound myoutbound) throws IOException {

			messageZp1 = new MessageZp1();
			task = new TaskMock();

			try {
				if (task.add(userMachine)) {
					if (messageZp1.create(userMachine)) {
						new AnswerData().loadToExcel(messageZp1.getDataList(),userMachine + ".xls");
						String file = "50000-" + messageZp1.getGuidBhs() + ".uprmes";
						String fileUpr2 = "50000-" + messageZp1.getGuidBhs();
						String sentMessages = "";
						File to_file = new File(Const.OUTPUTDONE + fileUpr2+ ".uprak2");

						CharBuffer buffer3 = CharBuffer.wrap("Отправлен "
								+ file);
						myoutbound.writeTextMessage(buffer3);

						while ("".equals(sentMessages)) {
							try {
								Thread.sleep(60000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							if (to_file.exists()) {
								sentMessages = "> Получен " + to_file.toString();
								CharBuffer buffer5 = CharBuffer.wrap(sentMessages);
								// условие открытия эксель
								if (ConstantiNastrojki.openWorkbookXls == 1) myoutbound.writeTextMessage(buffer5);
							} else 
							{
								CharBuffer buffer6 = CharBuffer.wrap("> ожидаем ответа uprak2");
								myoutbound.writeTextMessage(buffer6);
							}
						}

						Load zpLoad = new ZpLoadMock();
						if (zpLoad.load(fileUpr2)) {
							String excelFile = userMachine + ".xls";
							File excelFileEssence = new File("\\\\asu-paa\\ErzNsk\\" + excelFile);
							if (excelFileEssence.exists()) {
								new AnswerZp().loadToExcel(userMachine);
								CharBuffer buffer4 = CharBuffer
										.wrap("> загружено в эксель");
								myoutbound.writeTextMessage(buffer4);
								System.out.println("ПУТЬ эеселя "
										+ excelFileEssence);
								// если ключ пришел 1 или 0
								if (listKluchi.get(1).equals("1")) {
									if (Desktop.isDesktopSupported()) {
										Desktop.getDesktop().open(
												excelFileEssence);
									}

								}

							} else {
								System.out
										.println("загрузки в эксель не произошло");
							}

						} else {
							System.out.println("ошибка в парсинге упр2");
						}

					} else {
						System.out.println("ошибка create");
					}
				} else {
					System.out.println("ошибка add");
				}
			} catch (Exception e) {
				CharBuffer err = CharBuffer
						.wrap("> возникла ошибка, проверьте закрыт ли эксель или бд");
				myoutbound.writeTextMessage(err);
				onClose(0);
				e.printStackTrace();
			} 

		}
		
		private void methoZp1(HttpServletRequest request,WsOutbound myoutbound,String stList, int kl) throws IOException, JAXBException {
			 int pERSON_SURNAME = 0,pERSON_KINDFIRSTNAME = 1,pERSON_KINDLASTNAME = 2,pERSON_BIRTHDAY = 3, pERSON_ADDRESSID= 4,  PERSON_SERPOLICY = 5, PERSON_NUMPOLICY = 6,pERSON_SEX=7,pERSON_SERDOC = 8,pERSON_NUMDOC = 9,PERSON_REGNUMBER = 10,pERSON_LINKSMOESTABLISHMENTID = 11,PERSON_ESTABLISHMENTAMBUL = 12,PERSON_DATECHANGE = 13, PERSON_ESTABLISHMENTDENT = 14,PERSON_SOCIALID = 15,PERSON_STATUSID = 16,pERSON_DOCPERSONID = 17,PERSON_INSPECTION = 18,PERSON_OPERATION = 19,PERSON_STATUSREC = 20,PERSON_OUTID = 21,PERSON_INSPECTORID = 22,PERSON_ESTABLISHMENTID = 23,PERSON_DATEPOLICY = 24,pERSON_DATEINPUT = 25,eNP = 26,SMO_OLD = 27,PERSONADD_ADDRESSID = 28,PERSONADD_PRIM = 29,sNILS = 30,bORN = 31,dATEPASSPORT = 32,rUSSIAN = 33,TELEDOM = 34,TELEWORK = 35,EMAIL = 36,TELE2 = 37,DOK_VI = 38,eNP_PA= 39,ZA = 40,zAD= 41,ZAP = 42,PRED = 43,d_V= 44,d_SER= 45,d_NUM= 46,D_DATE = 47,METHOD = 48,PETITION = 49,FPOLIC = 50,pR_FAM = 51,pR_IM= 52,pR_OT= 53,PR_TEL = 54,PR_ADRES = 55,vS_NUM= 56,vS_DATE= 57,D1 = 58,d2=59,ENP_DATE = 60,lAST_FAM =61,lAST_IM= 62,lAST_OT= 63,lAST_DR= 64,KATEG = 65,DATE_PRIK = 66,MSA =67;    
			 int d_13= 777, eNP_1= 777,eNP_2= 777, p14cx1= 777,p14cx5= 777,p14cx6= 777,p14cx7= 777,uSER_PERSON_SURNAME= 777,xPN1= 777,xPN2 = 777,xPN3= 777,uSERNAME= 777,zADMINUS1= 777,zADPLUS40= 777,nBLANC= 777,vS_DATEPLUS1= 777,uSER_ENP= 777,uSER_PERSON_KINDFIRSTNAME= 777,uSER_PERSON_KINDLASTNAME= 777,uSER_SMO= 777,uSER_D_12= 777, d_12_PLUS1= 777,uSER_DOC_DATE= 777,uSER_DOCID= 777,uSER_NUMDOC= 777,uSER_SERDOC= 777,pFR_NOTID= 777,pFR_ID= 777, pFR_SNILS= 777,uSER_POL= 777,uSER_D_13= 777,uSER_OKATO_3= 777,uSER_TYPE_POL= 777,oKATO_3 = 777, tYPE_POL = 777,d_12 = 777,pOL = 777;
			 UtilParseDbXml utilparsedbXml = new UtilParseDbXml();
			 
			
			 
			 messageZp1 = new MessageZp1(pERSON_SERDOC, pERSON_NUMDOC, pERSON_DOCPERSONID, pERSON_SURNAME, pERSON_KINDFIRSTNAME, pERSON_KINDLASTNAME, pERSON_BIRTHDAY, pERSON_SEX, pERSON_LINKSMOESTABLISHMENTID, eNP, pERSON_ADDRESSID, pERSON_DATEINPUT, sNILS, bORN, dATEPASSPORT, eNP_PA, vS_NUM, vS_DATE, zAD, d2, pERSON_LINKSMOESTABLISHMENTID, d_12, d_13, oKATO_3, tYPE_POL, pOL, eNP_1, eNP_2, p14cx1, p14cx5, p14cx6, p14cx7, xPN1, xPN2, xPN3, uSERNAME, zADMINUS1, zADPLUS40, nBLANC, vS_DATEPLUS1, uSER_ENP, uSER_PERSON_SURNAME, uSER_PERSON_KINDFIRSTNAME, uSER_PERSON_KINDLASTNAME, uSER_SMO, uSER_D_12, uSER_D_13, uSER_OKATO_3, uSER_TYPE_POL, uSER_POL, rUSSIAN, d_V, d_SER, d_NUM, pR_FAM, pR_IM, pR_OT, lAST_FAM, lAST_IM, lAST_OT, lAST_DR, pFR_SNILS, pFR_ID, pFR_NOTID, uSER_SERDOC, uSER_NUMDOC, uSER_DOCID, uSER_DOC_DATE, d_12_PLUS1);
			//	messageZp1 = new MessageZp1();
				task = new TaskMock();
			/* если передаем аргумент 0 то отрабатывают методы быстрого запроса если ничего(и убрать запятую те только два аргумента) то большой запрос отрабатывает
			 * if kl ==0 zp1ajax else if kl ==1 zp1 for a08p04 on fiod
			 */
			if (messageZp1.create(userMachine,stList,kl))
			{
				String file = "50000-" + messageZp1.getGuidBhs()+ ".uprmes";
				String fileUpr2 = "50000-" + messageZp1.getGuidBhs();
				String sentMessages = "";
				File to_file = new File(Const.OUTPUTDONE + fileUpr2+ ".uprak2");
				utilparsedbXml.marshaling("50000-" + messageZp1.getGuidBhs(),messageZp1.getDataList());
				
				CharBuffer buffer3 = CharBuffer.wrap("Отправлен "+ file);
				myoutbound.writeTextMessage(buffer3);
				
				while ("".equals(sentMessages)) {
					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//отправив это сообщение на клиента начинает работать скрипт по заполнению третьего листа
					if (to_file.exists())
					{
						// отправляем на клиента название упрака2
						sentMessages = "> получен " + fileUpr2+".uprak2"+" zp1";
						CharBuffer buffer5 = CharBuffer.wrap(sentMessages);
						     /*
							 * ПЕРЕДАЕМ  в wbAnswer.js json формат Uprmes'a (коллекция которая формирует uprmess)
							 * на основе этих данных формируется второй лист web экселя
							 * 
							 * По этому ключу .fromdbforuprmess, будем ловить на клиентской стороне сокета название файла и дальше его там передать 
							 */
						// дописываем в конце ключ чтобы парсить на клиенте и передавать дальше
						CharBuffer buffer777 = CharBuffer.wrap("50000-" + messageZp1.getGuidBhs()+".fromdbforuprmess");
						// улетает на сокет wbAnswer.js  c данными для упрмеса (данные которые из базы)
						myoutbound.writeTextMessage(buffer777);
						// отправляем сообщение что получен упрак2
						myoutbound.writeTextMessage(buffer5);
					} else 
					{
						CharBuffer buffer6 = CharBuffer
								.wrap("> ожидаем ответа uprak2");
						myoutbound.writeTextMessage(buffer6);
					}
				}
			}
		}

		private void lovim_ostalnieZaprosi(WsOutbound myoutbound,String vidZaprosa, String jsonString) throws Exception
        {
			 int sMO = 4,d_12 = 5,pERSON_DATEINPUT = 6,oKATO_3 =7,pERSON_SURNAME = 1,pERSON_KINDFIRSTNAME = 2,pERSON_KINDLASTNAME = 3,pERSON_BIRTHDAY = 21,tYPE_POL = 8,
       			 pOL = 9,bORN = 18,eNP = 0,pERSON_SERDOC = 13,pERSON_NUMDOC = 14, pERSON_DOCPERSONID = 20,dATEPASSPORT = 15,d2=17,pERSON_SEX=16, rUSSIAN = 19,pid29 = 777;
			int pERSON_LINKSMOESTABLISHMENTID= 0;
			int pERSON_ADDRESSID= 0;
			int sNILS = 0;
			int eNP_PA= 0;
			int vS_NUM= 0;
			int vS_DATE= 0;
			int zAD= 0;
			int d_13= 0;
			int eNP_1= 0;
			int eNP_2= 0;
			int p14cx1= 0;
			int p14cx5= 0;
			int p14cx6= 0;
			int p14cx7= 0;
			int d_SER= 0;
			int d_V= 0;
			int d_NUM= 0;
			int uSER_PERSON_SURNAME= 0;
			int xPN1= 0;
			int xPN2 = 0;
			int xPN3= 0;
			int uSERNAME= 0;
			int zADMINUS1= 0;
			int zADPLUS40= 0;
			int nBLANC= 0;
			int vS_DATEPLUS1= 0;
			int uSER_ENP= 0;
			int uSER_PERSON_KINDFIRSTNAME= 0;
			int uSER_PERSON_KINDLASTNAME= 0;
			int uSER_SMO= 0;
			int uSER_D_12= 0;
			int d_12_PLUS1= 0;
			int uSER_DOC_DATE= 0;
			int uSER_DOCID= 0;
			int uSER_NUMDOC= 0;
			int uSER_SERDOC= 0;
			int pFR_NOTID= 0;
			int pFR_ID= 0;
			int pFR_SNILS= 0;
			int lAST_DR= 0;
			int lAST_OT= 0;
			int lAST_FAM= 0;
			int pR_OT= 0;
			int pR_FAM= 0;
			int uSER_POL= 0;
			int uSER_D_13= 0;
			int uSER_OKATO_3= 0;
			int uSER_TYPE_POL= 0;
			int pR_IM= 0;
			int lAST_IM= 0;
			
        	task = new TaskMock();
        	// парсим входящий json объект
        	ArrayList<ArrayList<String>> list=null;
        	
        	if(vidZaprosa.toString().equals("A08P03For"))
			{
				System.out.println("Поймали А08П03For");
				Message messageA08p03For = new MessageA08p03For();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA08p03For,myoutbound);
				}
			}
        	
        	if(vidZaprosa.toString().equals("A03P07"))
			{
				System.out.println("Поймали A03P07");
				Message messageA03P07 = new MessageA03p07();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA03P07,myoutbound);
				}
			}
        	
        	if(vidZaprosa.toString().equals("ZP9"))
			{
				System.out.println("Поймали ZP9");
				Message messageZP9 = new MessageZp9();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageZP9,myoutbound);
				}
			}
        	
        	if(vidZaprosa.toString().equals("A08P03kol"))
			{
				System.out.println("Поймали А08П03kol");
				Message messageA08P03 = new MessageA08p03kol();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA08P03,myoutbound);
				}
			}
        	
        	
        	if(vidZaprosa.toString().equals("A08P06"))
			{
				System.out.println("Поймали A08P06");
				Message messageA08P06 = new MessageA08p06();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA08P06,myoutbound);
				}
			}
        	if(vidZaprosa.toString().equals("A08P02"))
			{
				System.out.println("Поймали A08P02");
				Message messageA08P02 = new MessageA08p02();
				
				//if (task.add(userMachine))
				//{
					messageforallquery(messageA08P02,myoutbound);
				//}
			}
        	
        	if(vidZaprosa.toString().equals("buttonZP9"))
			{
        		if(!jsonString.equals("ostalnizaprosi"))
	        	{
        			if(jsonString.contains("list1enpzp9"))
        			{
        				pERSON_SURNAME = 0;pERSON_KINDFIRSTNAME = 1;pERSON_KINDLASTNAME = 2;pERSON_BIRTHDAY = 3; pERSON_ADDRESSID= 4; int PERSON_SERPOLICY = 5; int PERSON_NUMPOLICY = 6;pERSON_SEX=7;pERSON_SERDOC = 8;pERSON_NUMDOC = 9;int PERSON_REGNUMBER = 10;pERSON_LINKSMOESTABLISHMENTID = 11;int PERSON_ESTABLISHMENTAMBUL = 12;int PERSON_DATECHANGE = 13,PERSON_ESTABLISHMENTDENT = 14,PERSON_SOCIALID = 15,PERSON_STATUSID = 16;pERSON_DOCPERSONID = 17; int PERSON_INSPECTION = 18,PERSON_OPERATION = 19,PERSON_STATUSREC = 20,PERSON_OUTID = 21,PERSON_INSPECTORID = 22,PERSON_ESTABLISHMENTID = 23,PERSON_DATEPOLICY = 24;pERSON_DATEINPUT = 25;eNP = 26;int SMO_OLD = 27,PERSONADD_ADDRESSID = 28,PERSONADD_PRIM = 29;sNILS = 30;bORN = 31;dATEPASSPORT = 32;rUSSIAN = 33;int TELEDOM = 34,TELEWORK = 35,EMAIL = 36,TELE2 = 37,DOK_VI = 38;eNP_PA= 39;int ZA = 40;zAD= 41; int ZAP = 42;int PRED = 43;d_V= 44;d_SER= 45;d_NUM= 46;int D_DATE = 47,METHOD = 48,PETITION = 49,FPOLIC = 50;pR_FAM = 51;pR_IM= 52;pR_OT= 53;int PR_TEL = 54;int PR_ADRES = 55;vS_NUM= 56;vS_DATE= 57;int D1 = 58;d2=59;int ENP_DATE = 60;lAST_FAM =61;lAST_IM= 62;lAST_OT= 63;lAST_DR= 64;int KATEG = 65,DATE_PRIK = 66,MSA =67;    
       				   d_13= 777; eNP_1= 777;eNP_2= 777; p14cx1= 777;p14cx5= 777;p14cx6= 777;p14cx7= 777;uSER_PERSON_SURNAME= 777;xPN1= 777;xPN2 = 777;xPN3= 777;uSERNAME= 777;zADMINUS1= 777;zADPLUS40= 777;nBLANC= 777;vS_DATEPLUS1= 777;uSER_ENP= 777;uSER_PERSON_KINDFIRSTNAME= 777;uSER_PERSON_KINDLASTNAME= 777;uSER_SMO= 777;uSER_D_12= 777; d_12_PLUS1= 777;uSER_DOC_DATE= 777;uSER_DOCID= 777;uSER_NUMDOC= 777;uSER_SERDOC= 777;pFR_NOTID= 777;pFR_ID= 777; pFR_SNILS= 777;uSER_POL= 777;uSER_D_13= 777;uSER_OKATO_3= 777;uSER_TYPE_POL= 777;oKATO_3 = 777; tYPE_POL = 777;d_12 = 777;pOL = 777;
        				
        				jsonString = jsonString.replaceAll("list1enpzp9", "list1");
        				System.out.println("Поймали buttonZP9");
            			System.out.println("DataEnp "+ jsonString);
            			
            			list = parseStringFromList1(jsonString);
            			System.out.println("parse into ArrayList<ArrayList<String>> "+list);
            			
            			Message messageZP9enp = new MessageZp9(pERSON_SERDOC, pERSON_NUMDOC, pERSON_DOCPERSONID, pERSON_SURNAME, pERSON_KINDFIRSTNAME, pERSON_KINDLASTNAME, pERSON_BIRTHDAY, pERSON_SEX, pERSON_LINKSMOESTABLISHMENTID, eNP, pERSON_ADDRESSID, pERSON_DATEINPUT, sNILS, bORN, dATEPASSPORT, eNP_PA, vS_NUM, vS_DATE, zAD, d2, sMO, d_12, d_13, oKATO_3, tYPE_POL, pOL, eNP_1, eNP_2, p14cx1, p14cx5, p14cx6, p14cx7, xPN1, xPN2, xPN3, uSERNAME, zADMINUS1, zADPLUS40, nBLANC, vS_DATEPLUS1, uSER_ENP, uSER_PERSON_SURNAME, uSER_PERSON_KINDFIRSTNAME, uSER_PERSON_KINDLASTNAME, uSER_SMO, uSER_D_12, uSER_D_13, uSER_OKATO_3, uSER_TYPE_POL, uSER_POL, rUSSIAN, d_V, d_SER, d_NUM, pR_FAM, pR_IM, pR_OT, lAST_FAM, lAST_IM, lAST_OT, lAST_DR, pFR_SNILS, pFR_ID, pFR_NOTID, uSER_SERDOC, uSER_NUMDOC, uSER_DOCID, uSER_DOC_DATE, d_12_PLUS1);
            			messageforallquery2(messageZP9enp,myoutbound,list,"list1enpzp9");
        			}
        			
        			if(jsonString.contains("list1passportzp9"))
        			{
        				pERSON_SURNAME = 0;pERSON_KINDFIRSTNAME = 1;pERSON_KINDLASTNAME = 2;pERSON_BIRTHDAY = 3; pERSON_ADDRESSID= 4; int PERSON_SERPOLICY = 5; int PERSON_NUMPOLICY = 6;pERSON_SEX=7;pERSON_SERDOC = 8;pERSON_NUMDOC = 9;int PERSON_REGNUMBER = 10;pERSON_LINKSMOESTABLISHMENTID = 11;int PERSON_ESTABLISHMENTAMBUL = 12;int PERSON_DATECHANGE = 13,PERSON_ESTABLISHMENTDENT = 14,PERSON_SOCIALID = 15,PERSON_STATUSID = 16;pERSON_DOCPERSONID = 17; int PERSON_INSPECTION = 18,PERSON_OPERATION = 19,PERSON_STATUSREC = 20,PERSON_OUTID = 21,PERSON_INSPECTORID = 22,PERSON_ESTABLISHMENTID = 23,PERSON_DATEPOLICY = 24;pERSON_DATEINPUT = 25;eNP = 26;int SMO_OLD = 27,PERSONADD_ADDRESSID = 28,PERSONADD_PRIM = 29;sNILS = 30;bORN = 31;dATEPASSPORT = 32;rUSSIAN = 33;int TELEDOM = 34,TELEWORK = 35,EMAIL = 36,TELE2 = 37,DOK_VI = 38;eNP_PA= 39;int ZA = 40;zAD= 41; int ZAP = 42;int PRED = 43;d_V= 44;d_SER= 45;d_NUM= 46;int D_DATE = 47,METHOD = 48,PETITION = 49,FPOLIC = 50;pR_FAM = 51;pR_IM= 52;pR_OT= 53;int PR_TEL = 54;int PR_ADRES = 55;vS_NUM= 56;vS_DATE= 57;int D1 = 58;d2=59;int ENP_DATE = 60;lAST_FAM =61;lAST_IM= 62;lAST_OT= 63;lAST_DR= 64;int KATEG = 65,DATE_PRIK = 66,MSA =67;    
      				   d_13= 777; eNP_1= 777;eNP_2= 777; p14cx1= 777;p14cx5= 777;p14cx6= 777;p14cx7= 777;uSER_PERSON_SURNAME= 777;xPN1= 777;xPN2 = 777;xPN3= 777;uSERNAME= 777;zADMINUS1= 777;zADPLUS40= 777;nBLANC= 777;vS_DATEPLUS1= 777;uSER_ENP= 777;uSER_PERSON_KINDFIRSTNAME= 777;uSER_PERSON_KINDLASTNAME= 777;uSER_SMO= 777;uSER_D_12= 777; d_12_PLUS1= 777;uSER_DOC_DATE= 777;uSER_DOCID= 777;uSER_NUMDOC= 777;uSER_SERDOC= 777;pFR_NOTID= 777;pFR_ID= 777; pFR_SNILS= 777;uSER_POL= 777;uSER_D_13= 777;uSER_OKATO_3= 777;uSER_TYPE_POL= 777;oKATO_3 = 777; tYPE_POL = 777;d_12 = 777;pOL = 777;
        				
      				   jsonString = jsonString.replaceAll("list1passportzp9", "list1");
        				System.out.println("Поймали buttonZP9");
            			System.out.println("DataPassport "+ jsonString);
            			
            			list = parseStringFromList1(jsonString);
            			System.out.println("parse into ArrayList<ArrayList<String>> "+list);
            			
            			Message messageZP9passport = new MessageZp9(pERSON_SERDOC, pERSON_NUMDOC, pERSON_DOCPERSONID, pERSON_SURNAME, pERSON_KINDFIRSTNAME, pERSON_KINDLASTNAME, pERSON_BIRTHDAY, pERSON_SEX, pERSON_LINKSMOESTABLISHMENTID, eNP, pERSON_ADDRESSID, pERSON_DATEINPUT, sNILS, bORN, dATEPASSPORT, eNP_PA, vS_NUM, vS_DATE, zAD, d2, pERSON_LINKSMOESTABLISHMENTID, d_12, d_13, oKATO_3, tYPE_POL, pOL, eNP_1, eNP_2, p14cx1, p14cx5, p14cx6, p14cx7, xPN1, xPN2, xPN3, uSERNAME, zADMINUS1, zADPLUS40, nBLANC, vS_DATEPLUS1, uSER_ENP, uSER_PERSON_SURNAME, uSER_PERSON_KINDFIRSTNAME, uSER_PERSON_KINDLASTNAME, uSER_SMO, uSER_D_12, uSER_D_13, uSER_OKATO_3, uSER_TYPE_POL, uSER_POL, rUSSIAN, d_V, d_SER, d_NUM, pR_FAM, pR_IM, pR_OT, lAST_FAM, lAST_IM, lAST_OT, lAST_DR, pFR_SNILS, pFR_ID, pFR_NOTID, uSER_SERDOC, uSER_NUMDOC, uSER_DOCID, uSER_DOC_DATE, d_12_PLUS1);
            			messageforallquery2(messageZP9passport,myoutbound,list,"list1passportzp9");
        			}
        			
        			if(jsonString.contains("list1snilszp9"))
        			{
        				pERSON_SURNAME = 0;pERSON_KINDFIRSTNAME = 1;pERSON_KINDLASTNAME = 2;pERSON_BIRTHDAY = 3; pERSON_ADDRESSID= 4; int PERSON_SERPOLICY = 5; int PERSON_NUMPOLICY = 6;pERSON_SEX=7;pERSON_SERDOC = 8;pERSON_NUMDOC = 9;int PERSON_REGNUMBER = 10;pERSON_LINKSMOESTABLISHMENTID = 11;int PERSON_ESTABLISHMENTAMBUL = 12;int PERSON_DATECHANGE = 13,PERSON_ESTABLISHMENTDENT = 14,PERSON_SOCIALID = 15,PERSON_STATUSID = 16;pERSON_DOCPERSONID = 17; int PERSON_INSPECTION = 18,PERSON_OPERATION = 19,PERSON_STATUSREC = 20,PERSON_OUTID = 21,PERSON_INSPECTORID = 22,PERSON_ESTABLISHMENTID = 23,PERSON_DATEPOLICY = 24;pERSON_DATEINPUT = 25;eNP = 26;int SMO_OLD = 27,PERSONADD_ADDRESSID = 28,PERSONADD_PRIM = 29;sNILS = 30;bORN = 31;dATEPASSPORT = 32;rUSSIAN = 33;int TELEDOM = 34,TELEWORK = 35,EMAIL = 36,TELE2 = 37,DOK_VI = 38;eNP_PA= 39;int ZA = 40;zAD= 41; int ZAP = 42;int PRED = 43;d_V= 44;d_SER= 45;d_NUM= 46;int D_DATE = 47,METHOD = 48,PETITION = 49,FPOLIC = 50;pR_FAM = 51;pR_IM= 52;pR_OT= 53;int PR_TEL = 54;int PR_ADRES = 55;vS_NUM= 56;vS_DATE= 57;int D1 = 58;d2=59;int ENP_DATE = 60;lAST_FAM =61;lAST_IM= 62;lAST_OT= 63;lAST_DR= 64;int KATEG = 65,DATE_PRIK = 66,MSA =67;    
       				    d_13= 777; eNP_1= 777;eNP_2= 777; p14cx1= 777;p14cx5= 777;p14cx6= 777;p14cx7= 777;uSER_PERSON_SURNAME= 777;xPN1= 777;xPN2 = 777;xPN3= 777;uSERNAME= 777;zADMINUS1= 777;zADPLUS40= 777;nBLANC= 777;vS_DATEPLUS1= 777;uSER_ENP= 777;uSER_PERSON_KINDFIRSTNAME= 777;uSER_PERSON_KINDLASTNAME= 777;uSER_SMO= 777;uSER_D_12= 777; d_12_PLUS1= 777;uSER_DOC_DATE= 777;uSER_DOCID= 777;uSER_NUMDOC= 777;uSER_SERDOC= 777;pFR_NOTID= 777;pFR_ID= 777; pFR_SNILS= 777;uSER_POL= 777;uSER_D_13= 777;uSER_OKATO_3= 777;uSER_TYPE_POL= 777;oKATO_3 = 777; tYPE_POL = 777;d_12 = 777;pOL = 777;
        				
        				jsonString = jsonString.replaceAll("list1snilszp9", "list1");
        				System.out.println("Поймали buttonZP9");
            			System.out.println("DataPassport "+ jsonString);
            			
            			list = parseStringFromList1(jsonString);
            			System.out.println("parse into ArrayList<ArrayList<String>> "+list);
            			
            			Message messageZP9snils = new MessageZp9(pERSON_SERDOC, pERSON_NUMDOC, pERSON_DOCPERSONID, pERSON_SURNAME, pERSON_KINDFIRSTNAME, pERSON_KINDLASTNAME, pERSON_BIRTHDAY, pERSON_SEX, pERSON_LINKSMOESTABLISHMENTID, eNP, pERSON_ADDRESSID, pERSON_DATEINPUT, sNILS, bORN, dATEPASSPORT, eNP_PA, vS_NUM, vS_DATE, zAD, d2, pERSON_LINKSMOESTABLISHMENTID, d_12, d_13, oKATO_3, tYPE_POL, pOL, eNP_1, eNP_2, p14cx1, p14cx5, p14cx6, p14cx7, xPN1, xPN2, xPN3, uSERNAME, zADMINUS1, zADPLUS40, nBLANC, vS_DATEPLUS1, uSER_ENP, uSER_PERSON_SURNAME, uSER_PERSON_KINDFIRSTNAME, uSER_PERSON_KINDLASTNAME, uSER_SMO, uSER_D_12, uSER_D_13, uSER_OKATO_3, uSER_TYPE_POL, uSER_POL, rUSSIAN, d_V, d_SER, d_NUM, pR_FAM, pR_IM, pR_OT, lAST_FAM, lAST_IM, lAST_OT, lAST_DR, pFR_SNILS, pFR_ID, pFR_NOTID, uSER_SERDOC, uSER_NUMDOC, uSER_DOCID, uSER_DOC_DATE, d_12_PLUS1);
            			messageforallquery2(messageZP9snils,myoutbound,list,"list1snilszp9");
        			}
	        	}
			}
        	
        	if(vidZaprosa.toString().equals("A08P02test"))
			{
        		 eNP_PA=0;	 pERSON_SURNAME=1;	  pERSON_KINDFIRSTNAME=2;	  pERSON_KINDLASTNAME=3;	 sMO=4;	 d_12=5;	 d_13=6;	 oKATO_3=7;	 tYPE_POL=8;	 pOL=9;	 pERSON_SERDOC=10;	 pERSON_NUMDOC=11;	 dATEPASSPORT=12;	 bORN=13;	 rUSSIAN=14;	 pERSON_DOCPERSONID=15;	 pERSON_SEX=16;	 pERSON_BIRTHDAY=17;d2=19;pid29=20;

				System.out.println("Поймали A08P02test"); 
				Message messageA08P02 = new MessageA08p02(pERSON_SERDOC, pERSON_NUMDOC, pERSON_DOCPERSONID, pERSON_SURNAME, pERSON_KINDFIRSTNAME, pERSON_KINDLASTNAME, pERSON_BIRTHDAY, pERSON_SEX, pERSON_LINKSMOESTABLISHMENTID, eNP, pERSON_ADDRESSID, pERSON_DATEINPUT, sNILS, bORN, dATEPASSPORT, eNP_PA, vS_NUM, vS_DATE, zAD, d2, sMO, d_12, d_13, oKATO_3, tYPE_POL, pOL, eNP_1, eNP_2, p14cx1, p14cx5, p14cx6, p14cx7, xPN1, xPN2, xPN3, uSERNAME, zADMINUS1, zADPLUS40, nBLANC, vS_DATEPLUS1, uSER_ENP, uSER_PERSON_SURNAME, uSER_PERSON_KINDFIRSTNAME, uSER_PERSON_KINDLASTNAME, uSER_SMO, uSER_D_12, uSER_D_13, uSER_OKATO_3, uSER_TYPE_POL, uSER_POL, rUSSIAN, d_V, d_SER, d_NUM, pR_FAM, pR_IM, pR_OT, lAST_FAM, lAST_IM, lAST_OT, lAST_DR, pFR_SNILS, pFR_ID, pFR_NOTID, uSER_SERDOC, uSER_NUMDOC, uSER_DOCID, uSER_DOC_DATE, d_12_PLUS1,pid29);
				if(!jsonString.equals("ostalnizaprosi"))
	        	{
	        		list = parseStringFromList1(jsonString);
	        	}
				
				messageforallquery(messageA08P02,myoutbound,list,"");
			}
        	
        	if(vidZaprosa.toString().equals("A08B01"))
			{
				System.out.println("Поймали A08B01");
				Message messageA08B01 = new MessageA08v01();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA08B01,myoutbound);
				}
			}
        	
        	if(vidZaprosa.toString().equals("ZP1Fiod"))
			{
				System.out.println("Поймали ZP1Fiod");
				Message messageZP1Fiod = new MessageZp1Fiod();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageZP1Fiod,myoutbound);
				}
			}
        	
        	if(vidZaprosa.toString().equals("A24P102"))
			{
				System.out.println("Поймали A24P102");
				Message messageA24P102 = new MessageA24p102();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA24P102,myoutbound);
				}
			}
        	
        	if(vidZaprosa.toString().equals("ZP1pr"))
			{
				System.out.println("Поймали ZP1pr");
				Message messageZP1pr = new MessageZp1pr();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageZP1pr,myoutbound);
				}
			}
        	
        	if(vidZaprosa.toString().equals("A24P10"))
			{
				System.out.println("Поймали A24P10");
				Message messageA24P10 = new MessageA24p10();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA24P10,myoutbound);
				}
			}
			
			if(vidZaprosa.toString().equals("A08P01"))
			{
				System.out.println("Поймали A08P01");
				Message messageA08P01 = new MessageA08p01();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA08P01,myoutbound);
				}
			}
			
			if(vidZaprosa.toString().equals("P27"))
			{
				System.out.println("Поймали P27");
				Message messageP27 = new MessageP27();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageP27,myoutbound);
				}
			}
			
			if(vidZaprosa.toString().equals("P26"))
			{
				System.out.println("Поймали P26");
				Message messageP26 = new MessageP26();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageP26,myoutbound);
				}
			}
			
			if(vidZaprosa.toString().equals("A24P10com"))
			{
				System.out.println("Поймали A24P10com");
				Message messageA24P10 = new MessageA24p10();
				Message messageA24p102 = new MessageA24p102();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA24P10,myoutbound);
					messageforallquery(messageA24p102,myoutbound);
				}
			}
			
			if(vidZaprosa.toString().equals("A08P03pr"))
			{
				System.out.println("Поймали A08P03pr");
				Message messageA08P03pr = new MessageA08p03pr();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA08P03pr,myoutbound);
				}
			}
			
			if(vidZaprosa.toString().equals("A13P09"))
			{
				System.out.println("Поймали A13P09");
				Message messageA13P09 = new MessageA13p09();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA13P09,myoutbound);
				}
			}
			
			if(vidZaprosa.toString().equals("A08P04"))
			{
				System.out.println("Поймали A08P04");
				Message messageA08P04 = new MessageA08p04();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA08P04,myoutbound);
				}
			}
			
			if(vidZaprosa.toString().equals("A08P16"))
			{
				System.out.println("Поймали A08P16");
				Message messageA08P16 = new MessageA08p16();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA08P16,myoutbound);
				}
			}
			
			if(vidZaprosa.toString().equals("A08P03"))
			{
				System.out.println("Поймали A08P03");
				Message messageA08P03 = new MessageA08p03();
				
				if (task.add(userMachine))
				{
					messageforallquery(messageA08P03,myoutbound);
				}
			}
			
			
        }

		private void messageforallquery(Message mes, WsOutbound myoutbound,	ArrayList<ArrayList<String>> list, String kluch) throws Exception {
			if (mes.create(userMachine, list,kluch)) 
			{
				String file = "50000-" + mes.getGuidBhs() + ".uprmes";
				String fileUpr2 = "50000-" + mes.getGuidBhs();
				String sentMessages = "";
				File to_file = new File(Const.OUTPUTDONE + fileUpr2 + ".uprak2");

				CharBuffer buffer3 = CharBuffer.wrap("Отправлен " + file);
				myoutbound.writeTextMessage(buffer3);

				if (ConstantiNastrojki.otladkaXML.equals("0")) {
					while ("".equals(sentMessages)) {
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						if (to_file.exists()) {
							sentMessages = "> пришел " + to_file.toString();
							CharBuffer buffer5 = CharBuffer.wrap(sentMessages);
							myoutbound.writeTextMessage(buffer5);

							CharBuffer buffer66 = CharBuffer.wrap("q");
							myoutbound.writeTextMessage(buffer66);
						} else {
							CharBuffer buffer6 = CharBuffer
									.wrap("> ожидаем ответа uprak2");
							myoutbound.writeTextMessage(buffer6);
						}
					}
				} else {
					CharBuffer buffer5 = CharBuffer.wrap("> режим отладки");
					myoutbound.writeTextMessage(buffer5);
				}
			}
		}
		
		private void messageforallquery2(Message mes, WsOutbound myoutbound,
				ArrayList<ArrayList<String>> list, String kluch) throws Exception {
			if (mes.create(userMachine, list,kluch)) {
				
				UtilParseDbXml utilparsedbXml = new UtilParseDbXml();
				
				String file = "50000-" + mes.getGuidBhs() + ".uprmes";
				String fileUpr2 = "50000-" + mes.getGuidBhs();
				String sentMessages = "";
				File to_file = new File(Const.OUTPUTDONE + fileUpr2 + ".uprak2");
				
				utilparsedbXml.marshaling("50000-" + mes.getGuidBhs(),mes.getDataList());

				CharBuffer buffer3 = CharBuffer.wrap("Отправлен " + file);
				myoutbound.writeTextMessage(buffer3);

				if (ConstantiNastrojki.otladkaXML.equals("0"))
				{
					while ("".equals(sentMessages)) {
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						if (to_file.exists()) {
							sentMessages = "> получен " + fileUpr2+".uprak2 "+ kluch;
							CharBuffer buffer5 = CharBuffer.wrap(sentMessages);
							//

						    /*
							 * ПЕРЕДАЕМ  в wbAnswer.js json формат Uprmes'a (коллекция которая формирует uprmess)
							 * на основе этих данных формируется второй лист web экселя
							 * 
							 */
							// дописываем в конце ключ чтобы порсить на клиенте и передавать дальше
							CharBuffer buffer777 = CharBuffer.wrap("50000-" + mes.getGuidBhs()+".fromdbforuprmess");
							// улетает на сокет wbAnswer.js  c данными для упмеса (данные которые из базы)
							myoutbound.writeTextMessage(buffer777);
							// отправляем сообщение что получен упрак2
							myoutbound.writeTextMessage(buffer5);
						}
						else
						{
							CharBuffer budfer232 = CharBuffer
									.wrap("> ожидаем ответа uprak2");
							myoutbound.writeTextMessage(budfer232);
						}
					}
				} else {
					CharBuffer buffer5 = CharBuffer.wrap("> режим отладки");
					myoutbound.writeTextMessage(buffer5);
				}
			}
		}


		/*
		 * Перегружаем для работы по старому
		 *
		 */
		private void messageforallquery(Message mes, WsOutbound myoutbound) throws Exception {
			if (mes.create(userMachine)) {
				 new AnswerData().loadToExcel(mes.getDataList(), userMachine +".xls");
				String file = "50000-" + mes.getGuidBhs() + ".uprmes";
				String fileUpr2 = "50000-" + mes.getGuidBhs();
				String sentMessages = "";
				File to_file = new File(Const.OUTPUTDONE + fileUpr2 + ".uprak2");

				CharBuffer buffer3 = CharBuffer.wrap("Отправлен " + file);
				myoutbound.writeTextMessage(buffer3);

				if (ConstantiNastrojki.otladkaXML.equals("0")) {
					while ("".equals(sentMessages)) {
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						if (to_file.exists()) {
							sentMessages = "> Получен " + to_file.toString();
							CharBuffer buffer5 = CharBuffer.wrap(sentMessages);
							myoutbound.writeTextMessage(buffer5);

							CharBuffer buffer66 = CharBuffer.wrap("q");
							myoutbound.writeTextMessage(buffer66);
						} else {
							CharBuffer buffer6 = CharBuffer
									.wrap("> ожидаем ответа uprak2");
							myoutbound.writeTextMessage(buffer6);
						}
					}
				} else {
					CharBuffer buffer5 = CharBuffer.wrap("> режим отладки");
					myoutbound.writeTextMessage(buffer5);
				}
			}
		}
		
		@SuppressWarnings("unchecked")
		private ArrayList<ArrayList<String>> parseStringFromList1(String jsonString) throws JsonParseException,
				JsonMappingException, IOException, ParseException {
			// System.out.println(jsonString);
			jsonString = jsonString.replaceAll("\\?", "И").replaceAll("[^A-Za-zА-Яа-я0-9- -.-:-\\]-\\[-{-}-№-]", "");
			// initiate jackson mapper
			ObjectMapper mapper = new ObjectMapper();
			// Convert received JSON to Article
			ListWebForXMLQuery article = mapper.readValue(jsonString,ListWebForXMLQuery.class);
			// закидываем в коллекцию т.к. разбивает по строкам весь массив
			// данных из таблицы
			ArrayList<List<Object>> web = new ArrayList<List<Object>>();
			web.add(article.getList1());
			//
			ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
			DateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
			// бежим по строкам и добовляем в dataList
			for (int i = 0; i < web.get(0).size(); i++)
			{
				// подставляем в дату тире и меняет формат
				ArrayList<String> colums = (ArrayList<String>) web.get(0).get(i);
				for(int j = 0; j < colums.size(); j++)
				{
					// уходим от NULL POINTER EXEPTION
					if(colums.get(j)== null){colums.set(j, "");}
					// хреновым способом добовляем /
					if(colums.get(j).equalsIgnoreCase("БГ")){colums.set(j, "Б/Г");}
					//System.out.println(colums.get(j));
					if(colums.get(j).trim().length() == 10 && colums.get(j).substring(2, 3).equals(".") && colums.get(j).substring(5, 6).equals("."))
					{
						
						colums.set(j, new SimpleDateFormat("yyyy-MM-dd").format(inputFormat.parse(colums.get(j))	));
						web.get(0).set(i, colums);
						
					}
				}
				
				dataList.add((ArrayList<String>) web.get(0).get(i));
			}
			return dataList;
		}

	
	}

}



