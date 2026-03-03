<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 21/02/2026
  Time: 7:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.reservation.model.Admin" %>
<%
    Admin admin = (Admin) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head><title>Admin Dashboard</title></head>
<body>
<h2>Admin Dashboard</h2>
<p>Welcome, <%= admin != null ? admin.getFullName() : "Admin" %></p>

<ul>
    <li><a href="#">Create Reservation</a></li>
    <li><a href="#">Manage Staff</a></li>
    <li><a href="#">Deactivate Accounts</a></li>
    <li><a href="<%= request.getContextPath() %>/help">Help</a></li>

</ul>

<a href="<%= request.getContextPath() %>/logout">Logout</a>
</body>
</html>
