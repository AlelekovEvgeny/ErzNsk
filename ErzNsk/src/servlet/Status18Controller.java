package servlet;

import help.Const;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.ConnectOracle;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class Status18Controller extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String s = request.getSession().getAttribute("username") + ".xls";
		File excel = new File(Const.PROGRAM_PATH + s);
		if(!excel.exists()) {
			request.getRequestDispatcher("unSuccessfull.jsp").forward(request, response);
		}
		 
		
		LinkedList<String> l = new LinkedList<String>();
		Statement stat;
		ResultSet res = null;
		try {
			stat = ConnectOracle.connection.createStatement();
			res = stat.executeQuery("select enp from person_enp_output where pack_id=0 and status = 18");
			while(res.next()) {
				l.add(res.getString(1));
			}
			stat.executeQuery("update person_enp_output set pack_id = 100 where pack_id=0 and status = 18");
		} catch (SQLException e) {
			e.printStackTrace();
			request.getRequestDispatcher("unSuccessfull.jsp").forward(request, response);
		}

		
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(Const.PROGRAM_PATH + s));
		HSSFWorkbook wb = new HSSFWorkbook(fs);	
		HSSFSheet sheet = wb.getSheetAt(0);		
		HSSFRow excelRow = null;
		HSSFCell excelCell = null;
		int rows = l.size();
		for(int i = 1; i < rows ; i++) {
			if(i > 1) excelRow = sheet.createRow(i);
			excelRow = sheet.getRow(i);			
			excelCell = excelRow.createCell(0);
		    excelCell = excelRow.getCell(0);
		    excelCell.setCellValue(l.get(i));	
		}
		FileOutputStream fileOut = new FileOutputStream(Const.PROGRAM_PATH + s);
		wb.write(fileOut);
		fileOut.close();
		
		request.getRequestDispatcher("successfull.jsp").forward(request, response);
		
	}

}