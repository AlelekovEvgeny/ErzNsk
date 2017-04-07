package model.message;

import java.io.IOException;
import java.util.ArrayList;

import help.RandomGUID;

public interface Message {
	
	public boolean create(String userMachine) throws IOException;
	
	public boolean create(String userMachine,ArrayList<ArrayList<String>> listList1, String kluch) throws IOException;
	
	public boolean create(String userMachine,String stList) throws IOException;
	
	public boolean create(String userMachine,String stList,int kluch) throws IOException;
		
	public boolean prepareData(String userMachine);

	public boolean prepareData(String userMachine,ArrayList<ArrayList<String>> listList1);

	RandomGUID getGuidBhs();

	ArrayList<ArrayList<String>> getDataList();

	boolean rememberGuid();

	ArrayList<ArrayList<String>> getDataList(String userMachine);

	boolean writeToArchive();
	
}
