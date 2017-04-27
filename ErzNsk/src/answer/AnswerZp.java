package answer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import oracle.ConnectionPoolOracle;
import oracle.ZpOracle;
import excel.ExcelTaskMock;

public class AnswerZp extends AnswerMock {

	@Override
	public void loadToExcel(String userMachine) throws FileNotFoundException, IOException {
		ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>(); 
		String excelFile = userMachine + ".xls";
		
		Statement statement = null;
		Connection conn = null;
		try {
			conn = ConnectionPoolOracle.getConnectionDataSource().getConnection();
			statement = conn.createStatement();
			// Выполняем запрос (приложение 1)
			ResultSet resultSet = new ZpOracle().selectZp(statement, userMachine);
			
			// опредялеем количество столбцов из resalt set
			ResultSetMetaData metaData = resultSet.getMetaData();
			int numcols = metaData.getColumnCount();
			
			ArrayList<String> dataListRow;
			dataListRow = new ArrayList<String>();
			
			// заполняем коллекцию dataListRow
			for (int i = 0; i < numcols; i++) 
			{
				dataListRow.add(metaData.getColumnLabel(i + 1));
				System.out.println("Печатаем titles "+dataListRow.get(i));
			}
			// добовляем шапку таблицы в коллекцию
			dataList.add(dataListRow);
			
			//вставляем данные из resaltSet в коллецию (1 елемент коллеции= объект{ 1 строка всех столбцов})
			while (resultSet.next()) 
			{
				dataListRow = new ArrayList<String>();
				for (int i = 0; i < numcols; i++)
				{
					if ((i == 4 || i==10 || i==11 || i == 20 || i == 21) && resultSet.getObject(i + 1) != null)
					{
						dataListRow.add(new SimpleDateFormat("dd.MM.yyyy").format(resultSet.getDate(i + 1)));
					} else
					{
						dataListRow.add(resultSet.getString(i + 1));
					}
				}
				dataList.add(dataListRow);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally 
		{
			
			try 
			{
				if(statement != null ) { statement.close(); }
				if(conn !=null)
				{
					conn.close();
					ConnectionPoolOracle.printStatus();
					System.out.println("Метод loadToExcel");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (dataList != null && dataList.size() != 0) {
			new ExcelTaskMock().answerLoad(excelFile, dataList, 2);
		}
	}

	@Override
	public void loadToExcel() {
	}

}
