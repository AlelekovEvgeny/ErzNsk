package answer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.ConnectOracle;
import oracle.ErrorOracle;
import excel.ExcelTaskMock;

public class AnswerError extends AnswerMock {

	@Override
	public void loadToExcel(String userMachine) throws FileNotFoundException, IOException {
		ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>(); 
		String excelFile = userMachine + ".xls";
		
		Statement statement = null;
		try {
			statement = ConnectOracle.connection.createStatement();
			ResultSet resultSet = new ErrorOracle().selectError(statement, userMachine);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int numcols = metaData.getColumnCount();
			ArrayList<String> dataListRow;
			
			dataListRow = new ArrayList<String>();
			for (int i = 0; i < numcols; i++) {
				dataListRow.add(metaData.getColumnLabel(i + 1));
			}
			dataList.add(dataListRow);
			
			while (resultSet.next()) {
				dataListRow = new ArrayList<String>();
				for (int i = 0; i < numcols; i++) {
					dataListRow.add(resultSet.getString(i + 1));
				}
				dataList.add(dataListRow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (dataList != null && dataList.size() != 0) {
			new ExcelTaskMock().answerLoad(excelFile, dataList, 3);
		}
	}

	@Override
	public void loadToExcel() {
	}

}
