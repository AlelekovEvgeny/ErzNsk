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

@WebServlet("/HelpEditOutEnp")
public class HelpEditOutEnp extends HttpServlet {
private static final long serialVersionUID = 1L;

    
    public HelpEditOutEnp() {
        // TODO Auto-generated constructor stub
    }


  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
   List<String> result = new ArrayList<String>();
   result.add("1. Загружаемый фаил должен быть формата xls и состоять минимум из 2-х листов");
   result.add("2. В первом столбике указать ВНУТРЕННИЙ(ИЗ НАШЕЙ БАЗЫ) ЕНП");
   result.add("3. Во втором указать ВНЕШНИЙ ЕНП новый!!!");
   result.add("4. Нажать Отправаить запрос");
   result.add("5. Ответ автоматически скачается вашим браузером в формате xls");
   
   String json = new Gson().toJson(result);
   response.setContentType("application/json");
   response.setCharacterEncoding("UTF-8");
   response.getWriter().write(json); 
 }

  
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  
 }
}
