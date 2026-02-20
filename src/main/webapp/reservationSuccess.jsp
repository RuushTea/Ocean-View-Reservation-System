<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 20/02/2026
  Time: 8:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.oceanview.reservation.model.Reservation" %>
<%@ page import="com.oceanview.reservation.model.Guest" %>
<%@ page import="com.oceanview.reservation.model.Room" %>
<%@ page import="com.oceanview.reservation.model.RoomType" %>

<%
    Reservation reservation = (Reservation) request.getAttribute("reservation");
    if (reservation == null){
        response.sendRedirect(request.getContextPath() + "/reservation");
        return;
    }

    Guest guest = reservation.getGuest();
    Room room = reservation.getRoom();
    RoomType rt = reservation.getRoom().getRoomType();
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Reservation Created</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 24px; }
        .container { max-width: 800px; margin: 0 auto; }
        .card { border: 1px solid #ddd; padding: 18px; border-radius: 8px; }
        .success { background: #e6ffed; border: 1px solid #b7f5c6; padding: 12px; border-radius: 6px; color: #0a5d1a; margin-bottom: 14px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { text-align: left; padding: 10px; border-bottom: 1px solid #eee; }
        th { width: 240px; color: #444; }
        .btnrow { margin-top: 16px; display: flex; gap: 10px; }
        .btn { background: #1f6feb; color: white; padding: 10px 14px; border: none; border-radius: 6px; cursor: pointer; text-decoration: none; display: inline-block; }
        .btn.secondary { background: #444; }
        .btn:hover { opacity: 0.95; }
        .mono { font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace; }
    </style>
</head>
<body>
<div class="container">
    <h2>Reservation Confirmation</h2>

    <div class="card">
        <div class="success">
            Reservation created successfully! Your reservation number is:
            <span class="mono"><strong><%= reservation.getReservationId() %></strong></span>
        </div>

        <h3>Reservation Details</h3>
        <table>
            <tr>
                <th>Reservation No</th>
                <td class="mono"><%= reservation.getReservationId() %></td>
            </tr>
            <tr>
                <th>Status</th>
                <td><%= reservation.getStatus() %></td>
            </tr>
            <tr>
                <th>Check-in Date</th>
                <td><%= reservation.getCheckInDate() %></td>
            </tr>
            <tr>
                <th>Check-out Date</th>
                <td><%= reservation.getCheckOutDate() %></td>
            </tr>

            <tr><th colspan="2">Guest</th></tr>
            <tr>
                <th>Guest Name</th>
                <td><%= (guest != null ? guest.getName() : "") %></td>
            </tr>
            <tr>
                <th>Contact No</th>
                <td><%= (guest != null ? guest.getContactNo() : "") %></td>
            </tr>
            <tr>
                <th>Address</th>
                <td><%= (guest != null ? guest.getAddress() : "") %></td>
            </tr>

            <tr><th colspan="2">Room</th></tr>
            <tr>
                <th>Room Number</th>
                <td><%= (room != null ? room.getRoomNo() : "") %></td>
            </tr>
            <tr>
                <th>Room Type</th>
                <td><%= (rt != null ? rt.getRoomTypeName() : "") %></td>
            </tr>
            <tr>
                <th>Rate</th>
                <td><%= (rt != null ? rt.getRatePerNight() : "") %></td>
            </tr>
        </table>

        <div class="btnrow">
            <a class="btn" href="<%= request.getContextPath() %>/reservation">Create Another Reservation</a>
            <a class="btn secondary" href="<%= request.getContextPath() %>/index.jsp">Back to Home</a>
        </div>
    </div>
</div>
</body>
</html>