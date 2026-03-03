<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 21/02/2026
  Time: 4:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.oceanview.reservation.model.Reservation" %>
<%
    Reservation reservation = (Reservation) request.getAttribute("reservation");
%>

<html>
<head>
    <title>Reservation Details</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="bg-slate-50 min-h-screen flex items-center justify-center">

<div class="bg-white p-8 rounded-xl shadow-md border border-slate-200 w-full max-w-lg">

    <h2 class="text-2xl font-bold mb-4">Reservation Details</h2>

    <p><strong>ID:</strong> <%= reservation.getReservationId() %></p>
    <p><strong>Status:</strong> <%= reservation.getStatus() %></p>
    <p><strong>Check-In:</strong> <%= reservation.getCheckInDate() %></p>
    <p><strong>Check-Out:</strong> <%= reservation.getCheckOutDate() %></p>

    <div class="mt-6">
        <form action="<%= request.getContextPath() %>/reservation" method="POST">
            <input type="hidden" name="action" value="cancel"/>
            <input type="hidden" name="reservationId"
                   value="<%= reservation.getReservationId() %>"/>

            <button type="submit"
                    class="w-full bg-red-600 text-white py-2 rounded-lg hover:bg-red-700">
                Cancel Reservation
            </button>
        </form>
    </div>

</div>

</body>
</html>