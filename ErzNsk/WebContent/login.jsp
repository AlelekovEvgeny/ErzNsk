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
	<link rel="stylesheet" href="css/styles.css" type="text/css"/>
	
	<!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

<style>
body{ margin:0px; background:#000; }
#bg_container{ height:auto;width:auto; overflow:hidden; }
#bg{ width:100%; }
#content{ position:absolute; top:0px; padding:30px; color:#FFF; text-shadow:#000 2px 2px; width:100%; }
</style>
</head>
<body>
<div id="bg_container">
  <video id="bg" src="image/videoplayback1.mp4" autoplay="true" loop="true" muted="true"></video>
</div>
<div id="content">
  <div class="container">

      <form class="form-signin"  method="post" action="login">
        <h3 class="form-signin-heading" >  <i class="fa fa-user text-link">  Вход в систему</i></h3>
        <input type="text" class="form-control"  name="username" id="username"  placeholder="Логин">
        <input type="password" name="password" id="password"  class="form-control" placeholder="Пароль">
       <br> 
        <button class="btn btn-group-sm btn-primary btn-block" id="enterLogin" type="submit"><i class="fa fa-sign-in""></i> Вход</button>
      </form>

    </div> <!-- /container -->
</div>
   
    <script src="js/bootstrap.js"></script>
    <script type="text/javascript">
    $(document).ready(function()
    		{ 
    				var vid = document.getElementById("bg");
    				vid.playbackRate = 1;

    		});

	</script>
</body>
</html>