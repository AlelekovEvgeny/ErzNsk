package servlet;

import help.Const;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import answer.AnswerZp;
import excel.ExcelTask;
import excel.ExcelTaskMock;

public class ExcelEditController extends HttpServlet {
	
	private ExcelTask excelTask;
	
	public ExcelEditController() {
		this.excelTask = new ExcelTaskMock();
	}

	public ExcelEditController(ExcelTask excelTask) {
		this.excelTask = excelTask;
	}

	@Override
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String userMachine = request.getParameter("userMachine");
		String excelFile = userMachine + ".xls";
		
		File excelFileEssence = new File(Const.PROGRAM_PATH + excelFile);
		if (excelFileEssence.exists()) {
			new AnswerZp().loadToExcel(userMachine);
		}
		excelTask.Show(excelFile);
		request.getRequestDispatcher("successfull.jsp").forward(request, response);
	}

}