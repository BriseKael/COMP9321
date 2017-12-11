<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>HomePage</title>
</head>
<body>

<!-- navigation bar -->
<div align="center">
<a href="./homepage">Home</a>
<a href="./search">Search</a>
</div>
<p>

<!-- notification part -->
<div align="right">
<c:choose>
	<c:when test="${notificationBeans.size() > 0 }">
		<label> you have ${notificationBeans.size() } notifications</label>
	</c:when>
	<c:otherwise>
		<label> you have no notifications</label>
	</c:otherwise>
</c:choose>
</div>
<p>


<!-- post message part -->
<div align="center">
<img alt="Head Photo" src="/comp9321_ass2_upload/${filename }">

<form action="./homepage?action=sendMessage" method="post" enctype="multipart/form-data">
	please input content: <input type="text" name="content">
	<p>
    please choose the file to upload: <input type="file" name="filename"/>
    <p>
    <input type="submit" value="upload"/>  
</form>
</div>
<p>


<!-- friend wall part -->
<div align="center">
	<c:forEach items="${postBeans }" var="postBean" varStatus="status">
		<li>
		nick name: ${nicknames.get(status.index) }
		<br>
		content: ${postBean.getContent() }
		</li>
	</c:forEach>
</div>
<p>


</body>
</html>