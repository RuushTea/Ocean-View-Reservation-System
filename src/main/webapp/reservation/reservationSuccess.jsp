<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 20/02/2026
  Time: 8:10 AM
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
    RoomType rt = (room != null ? room.getRoomType() : null);
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Reservation Created</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="bg-slate-50 text-slate-800">
<div class="max-w-4xl mx-auto p-6">
    <h2 class="text-2xl font-bold mb-4">Reservation Confirmation</h2>

    <div class="bg-white border border-slate-200 rounded-xl shadow-sm p-6">
        <div class="bg-emerald-50 border border-emerald-200 text-emerald-800 rounded-lg p-4 mb-6">
            Reservation created successfully! Your reservation number is:
            <span class="font-mono font-semibold"><%= reservation.getReservationId() %></span>
        </div>

        <h3 class="text-lg font-semibold mb-3">Reservation Details</h3>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div class="bg-slate-50 rounded-lg p-4 border border-slate-200">
                <p class="text-sm text-slate-500">Reservation No</p>
                <p class="font-mono font-semibold"><%= reservation.getReservationId() %></p>
            </div>
            <div class="bg-slate-50 rounded-lg p-4 border border-slate-200">
                <p class="text-sm text-slate-500">Status</p>
                <p class="font-semibold"><%= reservation.getStatus() %></p>
            </div>
            <div class="bg-slate-50 rounded-lg p-4 border border-slate-200">
                <p class="text-sm text-slate-500">Check-in Date</p>
                <p class="font-semibold"><%= reservation.getCheckInDate() %></p>
            </div>
            <div class="bg-slate-50 rounded-lg p-4 border border-slate-200">
                <p class="text-sm text-slate-500">Check-out Date</p>
                <p class="font-semibold"><%= reservation.getCheckOutDate() %></p>
            </div>
        </div>

        <div class="mt-8 grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
                <h4 class="font-semibold mb-2">Guest</h4>
                <div class="bg-white border border-slate-200 rounded-lg p-4 space-y-2">
                    <p><span class="text-slate-500 text-sm">Name:</span> <span class="font-medium"><%= (guest != null ? guest.getName() : "") %></span></p>
                    <p><span class="text-slate-500 text-sm">Contact:</span> <span class="font-medium"><%= (guest != null ? guest.getContactNo() : "") %></span></p>
                    <p><span class="text-slate-500 text-sm">Address:</span> <span class="font-medium"><%= (guest != null ? guest.getAddress() : "") %></span></p>
                </div>
            </div>

            <div>
                <h4 class="font-semibold mb-2">Room</h4>
                <div class="bg-white border border-slate-200 rounded-lg p-4 space-y-2">
                    <p><span class="text-slate-500 text-sm">Room Number:</span> <span class="font-medium"><%= (room != null ? room.getRoomNo() : "") %></span></p>
                    <p><span class="text-slate-500 text-sm">Room Type:</span> <span class="font-medium"><%= (rt != null ? rt.getRoomTypeName() : "") %></span></p>
                    <p><span class="text-slate-500 text-sm">Rate:</span> <span class="font-medium"><%= (rt != null ? rt.getRatePerNight() : "") %></span></p>
                </div>
            </div>
        </div>

        <div class="mt-8 flex flex-col sm:flex-row gap-3">
            <a class="inline-flex justify-center items-center px-4 py-2 rounded-lg bg-blue-600 text-white font-semibold hover:bg-blue-700"
               href="<%= request.getContextPath() %>/reservation">
                Create Another Reservation
            </a>

            <%
                String role = (String) session.getAttribute("role");
                String homeUrl = request.getContextPath() + "/index.jsp";
                if ("ADMIN".equals(role)){
                    homeUrl = request.getContextPath() + "/admin/home";
                } else if("STAFF".equals(role)){
                    homeUrl = request.getContextPath() + "/staff/home";
                }
            %>

            <a class="inline-flex justify-center items-center px-4 py-2 rounded-lg bg-slate-800 text-white font-semibold hover:bg-slate-900"
               href="<%= homeUrl%>">
                Back to Home
            </a>
        </div>
    </div>
</div>
</body>
</html>