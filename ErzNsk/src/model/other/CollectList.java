package model.other;

import java.util.ArrayList;

public class CollectList
{
	private ArrayList< ArrayList<String> > listRows = new ArrayList<	ArrayList<String>>();

	public CollectList()
	{
	}

	public ArrayList<ArrayList<String>> getListRows() {
		return listRows;
	}

	public void setListRows(ArrayList<String> list) {
		this.listRows.add(list);
	}
}
