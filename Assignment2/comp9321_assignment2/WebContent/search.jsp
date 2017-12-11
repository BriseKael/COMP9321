<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search</title>
</head>
<body>

<!-- navigation bar -->
<div align="center">
<a href="./homepage">Home</a>
<a href="./search">Search</a>
</div>
<p>


<div align="center">

<form action="./result" method="get">
	<label>User Name</label>
	<input name="nickname">
	<p>
	<input type="submit" value="Search">
</form>

</div>

</body>
</html>