package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.ConnectionPoolOracle;
import user.UserService;
import user.UserServiceMock;

public class LoginController extends HttpServlet {
	
	/*static {
		final Thread t = new Thread(new Report());
		t.start();
	}*/
	
	private UserService users;
	//private ExcelTask excelTask;

	public LoginController(UserService userService) {
		
		this.users = userService;
		//this.excelTask = excelTask;
	}

	public LoginController() {
		
		this.users = new UserServiceMock();
		//this.excelTask = new ExcelTaskMock();
	}
	
	@Override
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		
		//валидация логина пароля
		try
		{
				if (users.checkUser(request.getParameter("username"), request.getParameter("password")))
				{
					
					HttpSession session = request.getSession(false);
			        if (session != null) 
			        {
					  session.invalidate();
			        }
			        session = request.getSession(true);
			        synchronized (session) 
			        {
			        	InetAddress addr = InetAddress.getLocalHost();
			    		String hostname = addr.getHostName();
			    		System.out.println("Хост имя "+hostname);
			    		request.getSession().setAttribute("usercomp", hostname);
			    		request.getSession().setAttribute("username", request.getParameter("username"));
			        }
					
					
					
					
					
					// устанавливаем setAttribute машины и логина на вошедшего
					//request=users.setSessionUsername(request);
				/*	
					String userMachine = request.getParameter("username");
					String excelFile = userMachine + ".xls";
					String pathForXlsUser = getDirectoryForUserNameXls("PROGRAM_PATH");
					File excelFileEssence = new File(pathForXlsUser + excelFile);
					
					if (excelFileEssence.exists())
					{
						System.out.println("Выполняется !!!");
						new AnswerZp().loadToExcel(userMachine,dataSource);
					}
					// создаем новый xls
					excelTask.Show(excelFile);
				*/	
					
					
					request.getRequestDispatcher("index.jsp").forward(request, response);
				} else
				{
					request.getRequestDispatcher("unSuccessfullLogin.jsp").forward(request, response);
				}
		} catch (Exception e) {
			e.printStackTrace();
		};
	}

	 
	 /*
	  * Выдергиваем из directories.properties путь где лежат xls пользователей
	  */
	public static String getDirectoryForUserNameXls(String path) throws IOException
	 {
		 Properties properties = new Properties();
		 
		 InputStream is = ConnectionPoolOracle.class.getClassLoader().getResourceAsStream("directories.properties");
		 	//for debag
			//URL url123 = ConnectionPoolOracle.class.getClassLoader().getResource("directories.properties");
			//System.out.println(url123);
		
			properties.load(is);
			
			return properties.getProperty(path);
		 
		 
		 
	 }
	
}