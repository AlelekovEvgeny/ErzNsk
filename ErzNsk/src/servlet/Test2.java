package servlet;

import java.sql.*;

import oracle.ConnectionPoolOracle_dawn;
import services.Services;

public class Test2 {
	 
	
	public static void main(String[] args) throws Exception {
		/*ConnectionPoolOracle_dawn.setUp();
		Connection connection = ConnectionPoolOracle_dawn.getConnectionDataSource().getConnection();
		
		String jobquery = "begin update_zp3_psr.test_procedure(?, ?, ?, ?); end;";
		CallableStatement callStmt = connection.prepareCall(jobquery);
		
		callStmt.setString(1, "5457910890000455");
		callStmt.registerOutParameter(2, Types.VARCHAR);
		callStmt.registerOutParameter(3, Types.VARCHAR);
		callStmt.registerOutParameter(4, Types.VARCHAR);
		
		callStmt.executeUpdate();
		
		String o_enp = callStmt.getString(2);
		String o_person_surname = callStmt.getString(3);
		String o_person_kindfirstname  = callStmt.getString(4);
		
		System.out.println("Result: o_enp "+o_enp+"\n o_person_surname "+o_person_surname+"\n o_person_kindfirstname "+o_person_kindfirstname);
		
		connection.close();*/
		
		/*Services ser = new Services();
		ser.update_confl_person("5457910890000455");*/
		
		//ConnectionPoolOracle_dawn.setUp();
		Connection connection = ConnectionPoolOracle_dawn.getConnectionDataSource().getConnection();
		
		String jobquery = "begin update_zp3_psr.update_zp3_erznsk(?, ?, ?, ?); end;";
		CallableStatement callStmt = connection.prepareCall(jobquery);
		
		callStmt.setString(1, "5458230894000404");
		callStmt.setString(2, "4258230894000433");
		callStmt.setString(3, "pylypiv");
		callStmt.setString(4, "XIX-ÍÌ ¹ 771905");

		
		callStmt.executeUpdate();
		
		connection.close();
		
	}

}
