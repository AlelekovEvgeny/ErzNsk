package model.handsontable;

import java.util.List;
/*
 * Модель предназначена для парсинга json данных ПРИ ЭКСПОРТЕ в xml файл.
 * Происходит это на первом дисте, когда нажимаем кнопку отправить сформированный запрос и все данные с информацией которая будет вставляться 
 * в xml уходят парситься....
 */

/*
 * Так же использую ее в index.jsp на $('#delrowsA08P02').click(function ()
 */
public class ListWebForXMLQuery
{
    private List list1;

	public List getList1() {
		return list1;
	}

	public void setList1(List list1) {
		this.list1 = list1;
	}

	@Override
	public String toString() {
		return "ListWebForXMLQuery [list1=" + list1 + "]";
	}
	
}
