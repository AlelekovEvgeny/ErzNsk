package answer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import excel.ExcelTaskMock;

public class AnswerData extends AnswerMock {

	@Override
	public void loadToExcel(ArrayList<ArrayList<String>> dataList, String excelFile) 
			throws FileNotFoundException, IOException
	{
		if (dataList != null && !dataList.isEmpty())
		{
			new ExcelTaskMock().answerLoad(excelFile, dataList, 1);
		}
	}

	@Override
	public void loadToExcel() {
	}

}
