package model.handsontable;

import java.util.List;
/*
 * Модель предназначена для парсинга json данных ПРИ ЭКСПОРТЕ. Происходит это 
 * когда нажимает кнопку таблица и таблица сворачивается
 */
public class ListWeb
{

    private List<?> list1;
    private List<?> list2;
    private List<?> list3;
    String gouser;
    String buttonQuery;

	public String getButtonQuery() {
		return buttonQuery;
	}

	public void setButtonQuery(String buttonQuery) {
		this.buttonQuery = buttonQuery;
	}

	public List<?> getList1() {
		return list1;
	}

	public void setList1(List<?> list1) {
		this.list1 = list1;
	}

	

	public List<?> getList2() {
		return list2;
	}

	public void setList2(List<?> list2) {
		this.list2 = list2;
	}

	
	public List<?> getList3() {
		return list3;
	}

	public void setList3(List<?> list3) {
		this.list3 = list3;
	}

	
	public String getGouser() {
		return gouser;
	}

	public void setGouser(String gouser) {
		this.gouser = gouser;
	}

	@Override
	public String toString() {
		return "ListWeb [list1=" + list1 + ", list2=" + list2 + ", list3=" + list3 + ", gouser=" + gouser
				+ ", buttonQuery=" + buttonQuery + "]";
	}


	
	
}
