package excel;

import static help.Const.*;
import help.Const;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelTaskMock implements ExcelTask {
	
	public boolean Show(String excelFile) throws IOException {
		File excelFileEssence = new File(Const.PROGRAM_PATH + excelFile);
		if (!excelFileEssence.exists()) {
			excelFileEssence.createNewFile();
		}
		return true;
	}
	
	@Override
	public ArrayList<ArrayList<String>> taskCreate(String excelFile) throws FileNotFoundException, IOException {
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(Const.PROGRAM_PATH + excelFile));
		HSSFWorkbook wb = new HSSFWorkbook(fs);	
		HSSFSheet sheet = wb.getSheetAt(0);		
		HSSFRow excelRow = null;
		HSSFCell excelCell = null;
		int rows = sheet.getPhysicalNumberOfRows();
		int cols = COLS;
		ArrayList<ArrayList<String>> taskList = new ArrayList<ArrayList<String>>();
		ArrayList<String> taskListRow = new ArrayList<String>();
		for(int i = 1; i < rows ; i++) 
		{
			excelRow = sheet.getRow(i);	
			taskListRow = new ArrayList<String>();
			for(int j = 0; j < cols; j++) 
			{
			    excelCell = excelRow.getCell(j);
			    if (excelCell != null) 
			    {
				    if (excelCell.getCellType() == Cell.CELL_TYPE_NUMERIC)
				    {
				    	taskListRow.add(String.valueOf((int)excelCell.getNumericCellValue()));
				    } 
				    else
				    {
				    	taskListRow.add(excelCell.getStringCellValue());
				    }
			    } 
			    else
			    {
			    	taskListRow.add("");
			    }
			}
			taskList.add(taskListRow);
		}
		FileOutputStream fileOut = new FileOutputStream(Const.PROGRAM_PATH + excelFile);
		wb.write(fileOut);
		fileOut.close();
		return taskList;
	}
	
	@Override
	public void answerLoad(String excelFile, ArrayList<ArrayList<String>> answerList, int sheetNum) throws FileNotFoundException, IOException
	{
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(Const.PROGRAM_PATH + excelFile));
		HSSFWorkbook wb = new HSSFWorkbook(fs);	
		HSSFSheet sheet = wb.getSheetAt(sheetNum);	
		HSSFRow row = null;
		HSSFCell cell = null;
		//  зачищаем лист
		
		
		/*for (int index = sheet.getLastRowNum(); index >= sheet.getFirstRowNum(); index--) {
			System.out.println("ловим ошибку " + index);
		
	        sheet.removeRow( sheet.getRow(index));
	    }*/
		
			
		int rows = answerList.size();
		System.out.println("ПРОВЕРКА "+ answerList);
		int cols = answerList.get(0).size();
		for(int i = 0; i < rows ; i++)
		{
			row = sheet.createRow(i);
			row = sheet.getRow(i);			
			for(int j = 0; j < cols; j++)
			{
				cell = row.createCell(j);
			    cell = row.getCell(j);
			    cell.setCellValue(answerList.get(i).get(j));
			    
			    
			    
			}
		}
		
		int endRow = 0;
		if(rows < LOW) 
		{
			endRow = LOW;
		} else {
			endRow = MID;
		}
		for(int i = rows; i < endRow; i++)
		{
			row = sheet.createRow(i);
			row = sheet.getRow(i);
			sheet.removeRow(row);
		}
		FileOutputStream fileOut = new FileOutputStream(Const.PROGRAM_PATH + excelFile);
		wb.write(fileOut);
		fileOut.close();
	}
	
	public int getTaskNumber(String excelFile) throws FileNotFoundException, IOException {
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(Const.PROGRAM_PATH + excelFile));
		return new HSSFWorkbook(fs).getSheetAt(0).getPhysicalNumberOfRows();
	}
}
