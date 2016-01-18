package file;

import help.Const;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TimeTaskImpl implements TimeTask {

	public int oneTimeTasksRead() {
		String s = "";
		int i;
		FileInputStream fin = null;
		
		try {
			fin = new FileInputStream(Const.PROGRAM_PATH + "onetimetask.erznsk");
		} catch (FileNotFoundException e) {
			System.out.println("Невозможно открыть файл!");
		}
		
		try {
			do {
				i = fin.read();
				if (i != 42) { s += i - 48; } 
			} while (i != 42);
		} catch (IOException e) {
			System.out.println("Ошибка чтения файла");
		}
		
		try { 
			fin.close();
		} catch (IOException e) {
			System.out.println("Ошибка закрытия файла");
		}
		
		return Integer.parseInt(s);
	}
	
	public void oneTimeTasksWrite(long diff) {
		FileOutputStream fout = null;
		
		try {
			fout = new FileOutputStream(Const.PROGRAM_PATH + "onetimetask.erznsk");
		} catch (FileNotFoundException e) {
			System.out.println("Невозможно открыть файл!");
		}
		
		String s = String.valueOf(diff);
		
		try {			
			for (int i = 0; i < s.length(); i++) {
				fout.write(s.charAt(i));
			}
			fout.write('*');
		} catch (IOException e) {
			System.out.println("Ошибка записи файла");
		}
		
		try { 
			fout.close();
		} catch (IOException e) {
			System.out.println("Ошибка закрытия файла");
		}
		
	}

}
