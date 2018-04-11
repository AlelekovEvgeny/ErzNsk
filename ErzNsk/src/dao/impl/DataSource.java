package dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import oracle.ConnectionPoolOracle;
import oracle.ConnectionPoolOracle_dawn;
import oracle.TaskOracle;

public class DataSource {
	
	TaskOracle taskoracle = new TaskOracle();
	Connection connection;
	PreparedStatement preparedstatement;
	ResultSet resultSet; 
	
	public DataSource(){
	
	}
	
	
	
	public List<String> getInEnp(String enpout) throws Exception{
		List<String> ls = null;
		
		//ConnectionPoolOracle.printStatus();
		connection = ConnectionPoolOracle.getConnectionDataSource().getConnection();
		//ConnectionPoolOracle.printStatus();
		
		preparedstatement = connection.prepareStatement(taskoracle.queryforZP3(enpout));
		resultSet = preparedstatement.executeQuery();
		
		ls = new ArrayList<String>();
		
        while (resultSet.next())
        {
        	ls.add(resultSet.getString(1));
        	ls.add(resultSet.getString(2));
        }
        
        if (preparedstatement != null) { preparedstatement.close(); }
        if (connection != null) { connection.close();  }
		
        //ConnectionPoolOracle.printStatus();
        return ls;
	}
	
	public void test_confl_person(String enpSeconList) throws SQLException, Exception{
		
		connection = ConnectionPoolOracle_dawn.getConnectionDataSource().getConnection();
		
		CallableStatement callStmt = connection.prepareCall(taskoracle.update_confl_person(enpSeconList));
		
		callStmt.setString(1, enpSeconList);
		callStmt.registerOutParameter(2, Types.VARCHAR);
		callStmt.registerOutParameter(3, Types.VARCHAR);
		callStmt.registerOutParameter(4, Types.VARCHAR);
		callStmt.executeUpdate();
		
		String o_enp = callStmt.getString(2);
		String o_person_surname = callStmt.getString(3);
		String o_person_kindfirstname  = callStmt.getString(4);
		
		System.out.println(o_enp);
		
		if (connection != null) { connection.close();  }
		
	}
	
    /**
     * Метод вызывает процедуру dawn@dame.update_zo3_psr.update_zp3_erznsk. Производит открепление в базе застрахованных,
     * инликацию как конфликтые (для страховых), логирование  
     * @param enpIN - внутркнний енп
     * @param enpOUT_ZP3 - внешний енп, который пришел из zp3
     * @param USERNAME - имя пользователя (для логов)
     * @param POL_ZP3 - полис из zp3. Заносится в логи для детальной индикации. 
     * @throws SQLException
     * @throws Exception
     */
    public void update_confl_person(String enpIN,String enpOUT_ZP3,String USERNAME,String POL_ZP3,String OKATO_npp0,String GUID_npp0,String D12) throws SQLException, Exception{
		
		connection = ConnectionPoolOracle_dawn.getConnectionDataSource().getConnection();
		
		String jobquery = "begin update_zp3_psr.update_zp3_erznsk(?, ?, ?, ?, ?, ?, ?); end;";
		CallableStatement callStmt = connection.prepareCall(jobquery);
		
		callStmt.setString(1, enpIN);
		callStmt.setString(2, enpOUT_ZP3);
		callStmt.setString(3, USERNAME);
		callStmt.setString(4, POL_ZP3);
		callStmt.setString(5, OKATO_npp0);
		callStmt.setString(6, GUID_npp0);
		callStmt.setString(7, D12);
		
		callStmt.executeUpdate();
		
		if (connection != null) { connection.close();  }
		
	}
	

}
