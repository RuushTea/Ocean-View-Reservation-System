<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 02/03/2026
  Time: 7:29 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.oceanview.reservation.model.Reservation" %>
<%@ page import="com.oceanview.reservation.model.Guest" %>
<%@ page import="com.oceanview.reservation.model.Room" %>
<%@ page import="com.oceanview.reservation.model.RoomType" %>

<%
    Reservation reservation = (Reservation) request.getAttribute("reservation");
    if (reservation == null) {
        response.sendRedirect(request.getContextPath() + "/staff/home?action=search");
        return;
    }

    Guest guest = reservation.getGuest();
    Room room = reservation.getRoom();
    RoomType rt = (room != null ? room.getRoomType() : null);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Reservation Details</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="min-h-screen bg-slate-50 text-slate-900">
<div class="mx-auto max-w-4xl px-4 py-8">

    <div class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <div class="flex items-start justify-between gap-4">
            <div>
                <h1 class="text-2xl font-bold">Reservation Details</h1>
                <p class="mt-1 text-slate-600">Reservation No: <strong><%= reservation.getReservationId() %></strong></p>
            </div>

            <a class="rounded-lg border border-slate-300 bg-white px-4 py-2 text-sm font-semibold hover:bg-slate-100"
               href="<%= request.getContextPath() %>/staff/home?action=searchReservation">
                Back
            </a>
        </div>

        <div class="mt-6 grid gap-4">
            <div class="rounded-xl border border-slate-200 bg-slate-50 p-4">
                <p class="text-sm text-slate-500">Status</p>
                <p class="text-lg font-semibold"><%= reservation.getStatus() %></p>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div class="rounded-xl border border-slate-200 bg-white p-4">
                    <p class="text-sm font-semibold mb-2">Guest</p>
                    <p><strong>Name:</strong> <%= (guest != null ? guest.getName() : "") %></p>
                    <p><strong>Contact:</strong> <%= (guest != null ? guest.getContactNo() : "") %></p>
                    <p><strong>Address:</strong> <%= (guest != null ? guest.getAddress() : "") %></p>
                </div>

                <div class="rounded-xl border border-slate-200 bg-white p-4">
                    <p class="text-sm font-semibold mb-2">Room</p>
                    <p><strong>Room Number:</strong> <%= (room != null ? room.getRoomNo() : "") %></p>
                    <p><strong>Room Type:</strong> <%= (rt != null ? rt.getRoomTypeName() : "") %></p>
                    <p><strong>Rate:</strong> <%= (rt != null ? rt.getRatePerNight() : "") %></p>
                </div>
            </div>

            <div class="rounded-xl border border-slate-200 bg-white p-4">
                <p><strong>Check-in:</strong> <%= reservation.getCheckInDate() %></p>
                <p><strong>Check-out:</strong> <%= reservation.getCheckOutDate() %></p>
            </div>
        </div>

        <%--TODO--%>
        <div class="mt-6 flex flex-col sm:flex-row gap-3">
            <form method="post" action="<%= request.getContextPath() %>/staff/home" class="w-full sm:w-auto">
                <input type="hidden" name="action" value="cancelReservation"/>
                <input type="hidden" name="reservationId" value="<%= reservation.getReservationId() %>"/>
                <button class="w-full rounded-lg bg-red-600 text-white px-4 py-2 font-semibold hover:bg-red-700">
                    Cancel Reservation
                </button>
            </form>

            <form method="post" action="<%= request.getContextPath() %>/staff/home" class="w-full sm:w-auto">
                <input type="hidden" name="action" value="generateBill"/>
                <input type="hidden" name="reservationId" value="<%= reservation.getReservationId() %>"/>
                <button class="w-full rounded-lg bg-emerald-600 text-white px-4 py-2 font-semibold hover:bg-emerald-700">
                    Calculate / Print Bill
                </button>
            </form>
        </div>

    </div>
</div>
</body>
</html>