package dao.impl;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.ConnectionPoolOracle;
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
		
		//------------------------------------------------------ сделать правильно коннект 
		javax.sql.DataSource dataSource = ConnectionPoolOracle.setUp();
		connection = dataSource.getConnection();
		//------------------------------------------------------
		
		preparedstatement = connection.prepareStatement(taskoracle.queryforZP3(enpout));
		resultSet = preparedstatement.executeQuery();
		
		ls = new ArrayList<String>();
		
        while (resultSet.next())
        {
        	ls.add(resultSet.getString(1));
        	ls.add(resultSet.getString(2));
        }
        
        if (preparedstatement != null) { preparedstatement.close(); }
        if (connection != null) { connection.close(); }
		
        return ls;
	}
	

}
