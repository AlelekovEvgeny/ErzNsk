package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileTransfer {

	public static void copyContent(String fromName, String toName)
		throws IOException {
		
		File from_file = new File(fromName);
		
		File to_file = new File(toName);
		
		if (!from_file.exists())
			abort("нет такого исходного файла: " + fromName);
		if (!from_file.isFile())
			abort("невозможно копирование каталога: " + fromName);
		if (!from_file.canRead())
			abort("исходный файл не доступен для чтения: " + fromName);
		
		if (to_file.isDirectory())
			to_file = new File(to_file, from_file.getName());
		
		if (to_file.exists()) {
			if (!to_file.canWrite())
				abort("конечный файл не доступен для записи: " + toName);
			
		} else {
			String parent = to_file.getParent();
			if (parent == null) parent  = System.getProperty("user.dir");
			File dir = new File(parent);
			if (!dir.exists())
				abort("каталог назначения не сущестует: " + parent);
			if (!dir.isFile())
				abort("каталог назначения не является каталогом: " + parent);
			if (!dir.canWrite())
				abort("каталог назначения не доступен для записи: " + parent);
		}
		
		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(from_file);
			to = new FileOutputStream(to_file);
			byte[] buffer = new byte[4096];
			int bytes_read;
			
			while((bytes_read = from.read(buffer)) != -1)
				to.write(buffer, 0, bytes_read);
		} finally {
			if (from != null) try { from.close(); } catch (IOException e) {;}
			if (to != null) try { to.close(); } catch (IOException e) {;}
		}
	}
		
	public static void createByName(String fromName, String toName) {
        try {
            FileChannel srcChannel = new FileInputStream(fromName).getChannel();
            FileChannel dstChannel = new FileOutputStream(toName).getChannel();
            dstChannel.transferFrom(srcChannel, srcChannel.size(), 0);
            srcChannel.close();
            dstChannel.close();            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public boolean copy(String fromName, String toName) {
		createByName(fromName, toName); 
        try {
			copyContent(fromName, toName);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean delete(String fileName) {
		new File(fileName).delete();
		return true;
	}

	private static void abort(String msg) throws IOException {
		throw new IOException("FileCopy: " + msg);
	}	
		
}