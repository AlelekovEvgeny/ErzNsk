<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="javax.servlet.http.HttpSession" %>

<%

//String us = (String)session.getAttribute("username");
//System.out.println("user "+ us);
//if ( us != null)
//{
//	request.getRequestDispatcher("index.jsp").forward(request, response);
//	response.sendRedirect("index.jsp");
//}
%>
<html>
<head>
	<title>Вход в систему</title>
	<!-- <link rel="stylesheet" href="css/styles.css" type="text/css"/>-->
	
	<!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/stylesnow.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	setTimeout ("$('body').animate({opacity: 0.0}, 0 );", 0);
	setTimeout ("$('body').animate({opacity: 1}, 3000 );", 10);

});
</script>
</head>
<body>



    <div class="container">

      <form class="form-signin"  method="post" action="login">
        <h3 class="form-signin-heading" ><a class="btn btn-lg btn-success" href="#">  <i class="fa fa-user">  Вход в систему</i></h3></a>
        <input type="text" class="form-control"  name="username" id="username"  placeholder="Логин">
        <input type="password" name="password" id="password"  class="form-control" placeholder="Пароль">
       <br> 
        <button class="btn btn-group-sm btn-success btn-block" id="enterLogin" type="submit"><i class="fa fa-sign-in""></i> Вход</button>
      </form><br>
      <h2 id="newyear">C наступающим Новым Годом !!!</h2>

    </div> <!-- /container -->

	<script type="text/javascript" src="script/checkNumber.js"></script>
	<script type="text/javascript" src="script/checkNumberLogin.js"></script>
	
	 <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
   
    <script src="js/bootstrap.js"></script>
    
</body>
</html>