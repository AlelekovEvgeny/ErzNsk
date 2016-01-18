package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import loadparse.Load;
import loadparse.Zu2LoadMock;

public class LoadZu2Controller extends HttpServlet {
	
	private Load zu2Load;

	public LoadZu2Controller(Load zu2Load) {
		this.zu2Load = zu2Load;
	}

	public LoadZu2Controller() {
		this.zu2Load = new Zu2LoadMock();
	}

	@Override
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		if (zu2Load.load(request.getParameter("zu2File"))) {
			request.getRequestDispatcher("successfull.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("unSuccessfull.jsp").forward(request, response);
		};
	}

}