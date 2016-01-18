package servlet;

import help.Const;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import loadparse.Load;
import loadparse.ZpLoadMock;
import answer.AnswerZp;

public class LoadZpController extends HttpServlet {
	
	private Load zpLoad;
	
	public LoadZpController() {
		this.zpLoad = new ZpLoadMock();
	}

	public LoadZpController(Load zpLoad) {
		this.zpLoad = zpLoad;
	}

	@Override
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException
	{
		if (zpLoad.load(request.getParameter("zpFile"))) 
		{
			String userMachine = request.getParameter("username");
			String excelFile = userMachine + ".xls";
			
			File excelFileEssence = new File(Const.PROGRAM_PATH + excelFile);
			if (excelFileEssence.exists())
			{
				new AnswerZp().loadToExcel(userMachine);
			}
			request.getRequestDispatcher("successfull.jsp").forward(request, response);
		} 
		else
		{
			request.getRequestDispatcher("unSuccessfull.jsp").forward(request, response);
		};
	}

}