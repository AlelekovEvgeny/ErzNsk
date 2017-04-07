package servlet;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import task.Task;
import task.TaskMock;
import answer.AnswerData;
import model.message.Message;
import model.message.MessageA03p07;
import model.message.MessageA08p01;
import model.message.MessageA08p02;
import model.message.MessageA08p03;
import model.message.MessageA08p03For;
import model.message.MessageA08p03kol;
import model.message.MessageA08p03pr;
import model.message.MessageA08p04;
import model.message.MessageA08p06;
import model.message.MessageA08p16;
import model.message.MessageA08v01;
import model.message.MessageA13p09;
import model.message.MessageA24p10;
import model.message.MessageA24p102;
import model.message.MessageP26;
import model.message.MessageP27;
import model.message.MessageZp1;
import model.message.MessageZp1Fiod;
import model.message.MessageZp1pr;
import model.message.MessageZp9;

public class MessageController extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Task task;
	
	private Message messageZp1;
	private Message messageA08p01;
	private Message messageA08p02;
	private Message messageA08p03;
	private Message messageA08p06;
	private Message messageA08p03kol;
	private Message messageA24p10;
	private Message messageA08p16;
	private Message messageA13p09;
	private Message messageZp9;
	private Message messageZp1pr;
	private Message messageA24p102;
	private Message messageA08p03pr;
	private Message messageA08p04;
	private Message messageA03p07;
	private Message messageA08p03For;			
	private Message messageZp1Fiod;
	private Message messageA08v01;
	
	private Message messageP26;
	private Message messageP27;
	
	public MessageController()
	{
		this.task = new TaskMock();
	}

	public MessageController(Task task, Message messageZp1, Message messageA08p01, Message messageA08p02, Message messageA08p03, Message messageA08p06, Message messageA08p03kol, Message messageA24p10) {
		this.task = task;
		this.messageZp1 = messageZp1;
		this.messageA08p01 = messageA08p01;
		this.messageA08p02 = messageA08p02;
		this.messageA08p03 = messageA08p03;
		this.messageA08p06 = messageA08p06;
		this.messageA08p03kol = messageA08p03kol;		
		this.messageA24p10 = messageA24p10;
	}

	/*
	 *	Алгоритм создания запроса
	 *  1. Инициализируется объект messageZp1 (к примеру)
	 *  2. В методе messageCreate создаем подкл к БД...УДАЛЯЕМ старое задание из табл xml_task по юзеру 
	 *  3. Считываем с экселя (страница 1) новое данные и заполняем в xml_task
	 *  4. Создаем xml структуру запроса, предварительно выдергнув из бд данные из таблиц и заполняем в коллекцию
	 *  5. Заполняем из коллекции на второй лист данные
	 * 	
	 */
	
	@Override
	public void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException 
	{
		String userMachine = request.getParameter("username");
		
		switch (request.getParameter("mestype"))
		{
			//case "108": this.messageZp1 = new MessageZp1(); messageCreate(request, response, userMachine, messageZp1); break;
			case "1":  this.messageA08p01 = new MessageA08p01(); messageCreate(request, response, userMachine, messageA08p01); break;
			case "102": this.messageA08p02 = new MessageA08p02();  messageCreate(request, response, userMachine, messageA08p02); break;
			case "3": this.messageA08p03 = new MessageA08p03(); messageCreate(request, response, userMachine, messageA08p03); break;
			case "6": this.messageA08p06 = new MessageA08p06(); messageCreate(request, response, userMachine, messageA08p06); break;
			case "3kol": this.messageA08p03kol = new MessageA08p03kol(); messageCreate(request, response, userMachine, messageA08p03kol); break;
			case "110": this.messageA24p10 = new MessageA24p10(); messageCreate(request, response, userMachine, messageA24p10); break;
			case "116": this.messageA08p16 = new MessageA08p16(); messageCreate(request, response, userMachine, messageA08p16); break;
			case "9": this.messageA13p09 = new MessageA13p09(); messageCreate(request, response, userMachine, messageA13p09); break;
			case "119": this.messageZp9 = new MessageZp9(); messageCreate(request, response, userMachine, messageZp9); break;
			case "1081": this.messageZp1pr = new MessageZp1pr(); messageCreate(request, response, userMachine, messageZp1pr); break;
			case "111": this.messageA24p102 = new MessageA24p102(); messageCreate(request, response, userMachine, messageA24p102); break;
			case "3pr": this.messageA08p03pr = new MessageA08p03pr(); messageCreate(request, response, userMachine, messageA08p03pr); break;
			case "4": this.messageA08p04 = new MessageA08p04(); messageCreate(request, response, userMachine, messageA08p04); break;
			case "112": this.messageA24p10 = new MessageA24p10(); this.messageA24p102 = new MessageA24p102();
			try {
				task.add(userMachine);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					messageA24p10.create(userMachine);
					messageA24p102.create(userMachine);
				
					request.getSession().setAttribute("fileCreate", 
							"50000-" + messageA24p10.getGuidBhs() + ".uprmes" +
							" 50000-" + messageA24p102.getGuidBhs() + ".uprmes" );
					
					new AnswerData().loadToExcel(messageA24p10.getDataList(), userMachine + ".xls");
					request.getRequestDispatcher("successfull.jsp?fileCreate=" + 
							"50000-" + messageA24p10.getGuidBhs() + ".uprmes" +
							" 50000-" + messageA24p102.getGuidBhs() + ".uprmes" 
							).forward(request, response);
					break;
			case "126": this.messageP26 = new MessageP26(); messageCreate(request, response, userMachine, messageP26); break;
			case "127": this.messageP27 = new MessageP27(); messageCreate(request, response, userMachine, messageP27); break;
			case "107": this.messageA03p07 = new MessageA03p07(); messageCreate(request, response, userMachine, messageA03p07); break;
			case "3For": this.messageA08p03For = new MessageA08p03For(); messageCreate(request, response, userMachine, messageA08p03For); break;
			case "1082": this.messageZp1Fiod = new MessageZp1Fiod(); messageCreate(request, response, userMachine, messageZp1Fiod); break;
			case "1v":this.messageA08v01 = new MessageA08v01(); messageCreate(request, response, userMachine, messageA08v01); break;
			
		}
	}

	private void messageCreate(HttpServletRequest request,
			HttpServletResponse response, String userMachine,
			Message message) throws FileNotFoundException, IOException,
			ServletException 
	{
		// создаем запись в xml_task считав с екселя входные данные
		try {
			if (task.add(userMachine))
			{
				// создаем xml запрос 
				if (message.create(userMachine))
				{
					new AnswerData().loadToExcel(message.getDataList(), userMachine + ".xls");
					request.getSession().setAttribute("fileCreate", "50000-" + message.getGuidBhs() + ".uprmes");
					request.setAttribute("fileCreate", "50000-" + message.getGuidBhs() + ".uprmes");
					request.getRequestDispatcher("successfull.jsp?fileCreate=" + "50000-" + message.getGuidBhs() + ".uprmes").forward(request, response);
				}
				else 
				{ 
					request.getRequestDispatcher("unSuccessfull.jsp").forward(request, response);
				}
			}
			else
			{
				request.getRequestDispatcher("unSuccessfull.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
