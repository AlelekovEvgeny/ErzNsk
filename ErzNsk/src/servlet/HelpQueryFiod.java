package servlet;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class ActionServlet
 */

@WebServlet("/HelpQueryFiod")
public class HelpQueryFiod extends HttpServlet {
private static final long serialVersionUID = 1L;

    
    public HelpQueryFiod() {
        // TODO Auto-generated constructor stub
    }


  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
   List<String> result = new ArrayList<String>();
   result.add("1. Загружаемый фаил должен быть формата xls");
   result.add("2. В первом столбике указать Ф И О (уазать через пробел)");
   result.add("3. Во втором указать ДР в формате дд.мм.гггг");
   result.add("4. Нажать Отправаить запрос");
   result.add("5. Ответ автоматически скачается вашим браузером в формате xls");
   
   result.add("ВНИМАНИЕ:");
   result.add("После загрузки ответаб возможно отсутствие енп тк неправильно были поданы данные ФИОД или ВЫ просто опечатались !!!");
   result.add("В скаченном ответе  xls напротив фамилии будет отсутствовать енп. ");
   result.add("Необходимо форматировать ДАТУ в отправляемом экселе тк могут быть не соответствия форматов.");
   result.add("Как это делать:");
   result.add("а.	В соседнем столбце воспользоваться функцией Excel : Пример |20.07.1986|=значен(адрес_ячейки с датой)|");
   result.add("б.	Поротянуть по всем датам");
   result.add("в.	Выделить весь столбик который протянули и произвести : копировать...и вставить через спец.вставку (значения) в стобец с ДАТАМИ");
   result.add("г.	Удаляете столбец с функцией '=значен()' а другому столбцу с пребразованныйми датами задаете форма ячеек дата дд.мм.гггг");
   String json = new Gson().toJson(result);
   response.setContentType("application/json");
   response.setCharacterEncoding("UTF-8");
   response.getWriter().write(json); 
 }

  
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  
 }
}
