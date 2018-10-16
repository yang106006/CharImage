<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body background="<%=request.getContextPath()%>/1.jpg">
	<form action="http://localhost:8080/doDealAndDownLoad" 
		  method="post" enctype="multipart/form-data">
		<input name="uploadImage" type="file" />
		<button type="submit" />提交
	</form>
</body>
</html>