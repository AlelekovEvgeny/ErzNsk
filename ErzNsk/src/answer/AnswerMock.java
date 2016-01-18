package answer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.sql.DataSource;

public abstract class AnswerMock implements Answer {

	public void loadToExcel(ArrayList<ArrayList<String>> dataList,
			String excelFile) throws FileNotFoundException, IOException {
	}
	
	public void loadToExcel(String userMachine) throws FileNotFoundException, IOException {
	}

}
