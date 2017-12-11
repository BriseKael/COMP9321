<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Result</title>
</head>
<body>

<!-- navigation bar -->
<div align="center">
<a href="./homepage">Home</a>
<a href="./search">Search</a>
</div>
<p>

<div align="center">
<c:out value="Search for '${param.nick_name }'"></c:out>
<c:out value="Result: ${users.size() }"></c:out>


<ul>
	<c:forEach items="${userBeans }" var="userBean" varStatus="status">
		<li>
			username: ${userBean.getUsername() }
			<br>
			nick name: ${userBean.getNickname() }
			<br>
			email: ${userBean.getEmail() }
			
			<c:if test="${relationTypes.get(status.index).equals(\"UNKNOW\") }">
				<a href="./result?action=add_friend&${searchPattern }">add friend</a>
			</c:if>
			<c:if test="${relationTypes.get(status.index).equals(\"FRIEND\") }">
				<a href="./result?action=add_friend&${searchPattern }">you are friends</a>
			</c:if>
			<c:if test="${relationTypes.get(status.index).equals(\"REQUEST_FRIEND\") }">
				<a href="./result?action=add_friend&${searchPattern }">requesting friends</a>
			</c:if>
		</li>
	</c:forEach>
</ul>


</div>

</body>
</html>