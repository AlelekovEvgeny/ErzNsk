package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import loadparse.AppakLoadMock;
import loadparse.Load;

public class LoadAppakController extends HttpServlet {
	
	private Load appakLoad;
	
	public LoadAppakController() {
		this.appakLoad = new AppakLoadMock();
	}

	public LoadAppakController(Load appakLoad) {
		this.appakLoad = appakLoad;
	}

	@Override
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		if (appakLoad.load(request.getParameter("appakFile"))) {
			request.getRequestDispatcher("successfull.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("unSuccessfull.jsp").forward(request, response);
		};
	}

}