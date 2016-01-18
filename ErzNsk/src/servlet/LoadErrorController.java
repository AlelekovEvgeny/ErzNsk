package servlet;

import help.Const;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import loadparse.ErrorLoadMock;
import loadparse.Load;
import answer.AnswerError;

public class LoadErrorController extends HttpServlet {
	
	private Load errorLoad;
	
	public LoadErrorController() {
		this.errorLoad = new ErrorLoadMock();
	}

	public LoadErrorController(Load errorLoad) {
		this.errorLoad = errorLoad;
	}

	@Override
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		if (errorLoad.load(request.getParameter("errorFile"))) {
			String userMachine = request.getParameter("username");
			String excelFile = userMachine + ".xls";
			
			File excelFileEssence = new File(Const.PROGRAM_PATH + excelFile);
			if (excelFileEssence.exists()) {
				new AnswerError().loadToExcel(userMachine);
			}
			request.getRequestDispatcher("successfull.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("unSuccessfull.jsp").forward(request, response);
		};
	}

}