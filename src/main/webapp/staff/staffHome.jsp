<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 21/02/2026
  Time: 7:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.reservation.model.Staff" %>
<%
    Staff staff = (Staff) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head><title>Staff Dashboard</title></head>
<body>
<h2>Staff Dashboard</h2>
<p>Welcome, <%= staff != null ? staff.getFullName() : "Staff" %></p>

<ul>
    <li><a href="<%= request.getContextPath() %>/reservation">Create Reservation</a></li>
    <li><a href="<%= request.getContextPath() %>/staff/reservation/search">Search Reservation</a></li>
    <li><a href="<%= request.getContextPath() %>/staff/reservation/cancel">Cancel Reservation</a></li>
    <li><a href="<%= request.getContextPath() %>/staff/bill">Calculate Bill</a></li>
    <li><a href="#">Help Articles</a></li>
</ul>

<a href="<%= request.getContextPath() %>/logout">Logout</a>
</body>
</html>
