<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>ErzNsk Успешно</title>
	<link rel="stylesheet" href="css/styles.css" type="text/css"/>
</head>
<body>
	<h4 class = 'heads'>Успешно</h4>
	
	<ul>
	<c:if test="${param.zu2File ne null}">
		<li><span>zu2 файл загружен:  </span><span><c:out value="${param.zu2File}" /></span></li>
	</c:if>
	<c:if test="${param.zpFile ne null}">
		<li><span>zp файл загружен:  </span><span><c:out value="${param.zpFile}" /></span></li>
	</c:if>
	<c:if test="${param.appakFile ne null}">
		<li><span>appak файл загружен:  </span><span><c:out value="${param.appakFile}" /></span></li>
	</c:if>
	<c:if test="${param.errorFile ne null}">
		<li><span>error файл загружен:  </span><span><c:out value="${param.errorFile}" /></span></li>
	</c:if>
	<c:if test="${param.fileCreate ne null}">
		<li><span>Файл создан:  </span><span><c:out value="${param.fileCreate}" /></span></li>
	</c:if>
	<c:if test="${param.status18 ne null}">
		<li><span>Выполнено обновление для П03  </span></li>
	</c:if>
	</ul>
	
<!--
	<textarea rows="10" id="wsanswer" readonly onclick='postToServer();'>Нажмите в это поле и дождитесь ответа!
	
	</textarea><br/><br/>
	 <input id="msg" type="hidden" value='<c:out value="${sessionScope.fileCreate}" />' /> 
-->
 <input type = "hidden" name = "username" value = '<c:out value="${sessionScope.username}" />'> 
	Пожалуйста <a href = "/ErzNsk/index.jsp" >перейдите на главную</a>
	
</body>
</html>

