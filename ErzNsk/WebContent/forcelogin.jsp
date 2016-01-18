<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.username eq null}">
	<jsp:forward page = 'login.jsp'></jsp:forward>
</c:if>