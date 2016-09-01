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

@WebServlet("/Help_Import_Uprak2")
public class Help_Import_Uprak2 extends HttpServlet {
private static final long serialVersionUID = 1L;

    
    public Help_Import_Uprak2() {
        // TODO Auto-generated constructor stub
    }


  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
   List<String> result = new ArrayList<String>();
   result.add("1. Згружаемый файл должен быть формата uprak2");
   result.add("2. Так же обязательное условие - это присутствие .xml файла с таким же названием как и uprak2");
   result.add("Примечание:");
   result.add("Подружаются два листа: второй и третий. С первым ничего не происходит.");
   result.add("Во второй лист попадают данные с xml файла, в третий соответственно с uprak2");
   result.add("ВНИМАНИЕ:");
   result.add("Для корректной работы П02 и прочих запросов,где происходит сравнение всех трех листов,необходимы правильные данные на первом листе.");
   result.add("");
   result.add("Так же можете обратиться к инструкции пользователя");
   String json = new Gson().toJson(result);
   response.setContentType("application/json");
   response.setCharacterEncoding("UTF-8");
   response.getWriter().write(json); 
 }

  
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
  
 }
}
