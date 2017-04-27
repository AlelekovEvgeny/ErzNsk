package task;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import oracle.ConnectionPoolOracle;
import oracle.TaskOracle;
import excel.ExcelTaskMock;

public class TaskMock implements Task {

	@Override
	public boolean add(String username) throws Exception {
		Statement statement = null;
		Connection conn = null;
		try {
			conn = ConnectionPoolOracle.getConnectionDataSource().getConnection();
			statement = conn.createStatement();
			TaskOracle taskOracle = new TaskOracle();
			// удаляем задание БД
			taskOracle.deleteOldTask(statement, username);
			String excelFile = username + ".xls";
			long timeList0 = System.currentTimeMillis();
			ArrayList<ArrayList<String>> taskList = new ExcelTaskMock().taskCreate(excelFile);
			long timeList1 = System.currentTimeMillis();
			System.out.println("timeList - " + (timeList1 - timeList0));
			// бежим по количеству строк
			long timeInsert0 = System.currentTimeMillis();
			for (int i = 0; i < taskList.size(); i++)
			{
				System.out.println(i + 1 + " ENP load - " + taskList.get(i).get(0));
				
				taskOracle.insertNewTask(statement, taskList.get(i).get(0), taskList.get(i).get(1), taskList.get(i).get(2), username
						 ,taskList.get(i).get(3), taskList.get(i).get(4), taskList.get(i).get(5), taskList.get(i).get(6), taskList.get(i).get(7)
						 ,taskList.get(i).get(8), taskList.get(i).get(9), taskList.get(i).get(10), taskList.get(i).get(11), taskList.get(i).get(12)
						 , taskList.get(i).get(13), taskList.get(i).get(14), taskList.get(i).get(15), taskList.get(i).get(16), taskList.get(i).get(17)
						 , taskList.get(i).get(18), taskList.get(i).get(19)
						);
			}
			long timeInsert1 = System.currentTimeMillis();
			System.out.println("timeInsert - " + (timeInsert1 - timeInsert0));
		}   finally {
			try { 
				if(statement !=null)
				statement.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}		

}
