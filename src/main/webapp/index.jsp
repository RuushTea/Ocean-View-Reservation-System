<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Ocean View Resort System!" %></h1>
<br/>
<a href="hello-servlet">Click here to go to HelloServlet</a>
<a href="auth">Click here to login</a>
<\<br>

<div>
  <p>Search for Guest with Guest ID</p>
  <form action="${pageContext.request.contextPath}/guest-servlet" method="GET">
    <input type="text" name="guestId" placeholder="Enter Guest ID">
    <input type="submit" value="Search">
  </form>
</div>
</body>
</html>