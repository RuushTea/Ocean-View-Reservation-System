<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 03/03/2026
  Time: 1:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.oceanview.reservation.model.Reservation" %>
<%
  List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
  String contactNo = (String) request.getAttribute("contactNo");
%>

<h2>Search Results for Contact No: <%= contactNo %></h2>

<table border="1" cellpadding="8">
  <tr>
    <th>Reservation No</th>
    <th>Guest</th>
    <th>Room</th>
    <th>Check-in</th>
    <th>Check-out</th>
    <th>Status</th>
    <th>Action</th>
  </tr>

  <% for (Reservation r : reservations) { %>
  <tr>
    <td><%= r.getReservationId() %></td>
    <td><%= r.getGuest().getName() %></td>
    <td><%= r.getRoom().getRoomNo() %> - <%= r.getRoom().getRoomType().getRoomTypeName() %></td>
    <td><%= r.getCheckInDate() %></td>
    <td><%= r.getCheckOutDate() %></td>
    <td><%= r.getStatus() %></td>
    <td>
      <a href="<%= request.getContextPath() %>/staff/home?action=search&searchType=reservationId&searchValue=<%= r.getReservationId() %>">
        View
      </a>
    </td>
  </tr>
  <% } %>
</table>