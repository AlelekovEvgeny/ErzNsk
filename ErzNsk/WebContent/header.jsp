<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<div style="background-color:#ffeb73;"class="header">
	<span class = 'links'>Вошли как: <c:out value="${sessionScope.username}" />  </span>
	<a class = 'links' href="logout" class="my-account">Выйти</a>
	<a class = 'links' href="/ErzNsk/readme.jsp">Справка</a>
	<a class = 'links' href="/ErzNsk/index.jsp">Главная</a>
	<br><br>
	<a class = 'links' href="loadCountRab">Количество работающих по дате</a>
	<a class = 'links' href="loadGoznakError">Ошибки Гознака</a>
	<a class = 'links' href="status18?status18=y">Задание П03</a>
</div>