package excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface ExcelTask {
	
	public boolean Show(String excelFile) throws IOException;

	ArrayList<ArrayList<String>> taskCreate(String excelFile)
			throws FileNotFoundException, IOException;

	void answerLoad(String excelFile, ArrayList<ArrayList<String>> answerList,
			int sheetNum) throws FileNotFoundException, IOException;

}
