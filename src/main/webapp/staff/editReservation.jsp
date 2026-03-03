<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 02/03/2026
  Time: 9:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.oceanview.reservation.model.Reservation" %>
<%@ page import="com.oceanview.reservation.model.RoomType" %>
<%@ page import="java.util.List" %>

<%
    Reservation reservation = (Reservation) request.getAttribute("reservation");
    List<RoomType> roomTypes = (List<RoomType>) request.getAttribute("roomTypes");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Reservation</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="min-h-screen bg-slate-50 text-slate-900">
<div class="mx-auto max-w-3xl px-4 py-8">
    <div class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <h2 class="text-2xl font-semibold">Edit Reservation</h2>

        <% if (error != null) { %>
        <div class="mt-4 rounded-lg border border-red-200 bg-red-50 px-4 py-2 text-red-700">
            <%= error %>
        </div>
        <% } %>

        <form class="mt-6 space-y-4" method="POST" action="<%= request.getContextPath() %>/staff/home">
            <input type="hidden" name="action" value="update"/>
            <input type="hidden" name="reservationId" value="<%= reservation.getReservationId() %>"/>

            <h3 class="text-lg font-semibold mt-2">Guest Details</h3>

            <div>
                <label class="block text-sm font-medium mb-1">Name</label>
                <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                       name="name" value="<%= reservation.getGuest().getName() %>" required/>
            </div>

            <div>
                <label class="block text-sm font-medium mb-1">Address</label>
                <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                       name="address" value="<%= reservation.getGuest().getAddress() %>" required/>
            </div>

            <div>
                <label class="block text-sm font-medium mb-1">Contact No</label>
                <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                       name="contactNo" value="<%= reservation.getGuest().getContactNo() %>" required/>
            </div>

            <h3 class="text-lg font-semibold mt-4">Booking Details</h3>

            <div>
                <label class="block text-sm font-medium mb-1">Room Type</label>
                <select class="w-full rounded-lg border border-slate-300 px-3 py-2"
                        name="roomTypeId" required>
                    <%
                        int currentTypeId = reservation.getRoom().getRoomType().getRoomTypeId();
                        for (RoomType rt : roomTypes) {
                            boolean selected = (rt.getRoomTypeId() == currentTypeId);
                    %>
                    <option value="<%= rt.getRoomTypeId() %>" <%= selected ? "selected" : "" %>>
                        <%= rt.getRoomTypeName() %> (Rate: <%= rt.getRatePerNight() %>)
                    </option>
                    <% } %>
                </select>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                    <label class="block text-sm font-medium mb-1">Check-in</label>
                    <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                           type="date" name="checkInDate" value="<%= reservation.getCheckInDate() %>" required/>
                </div>
                <div>
                    <label class="block text-sm font-medium mb-1">Check-out</label>
                    <input class="w-full rounded-lg border border-slate-300 px-3 py-2"
                           type="date" name="checkOutDate" value="<%= reservation.getCheckOutDate() %>" required/>
                </div>
            </div>

            <div class="pt-2 flex gap-3">
                <button class="rounded-lg bg-blue-600 px-4 py-2 text-white font-semibold hover:bg-blue-700"
                        type="submit">
                    Save Changes
                </button>
                <a class="rounded-lg border border-slate-300 px-4 py-2 font-semibold hover:bg-slate-100"

                <%--Research to go back to the search page--%>
                   href="<%= request.getContextPath() %>/staff/home?action=doSearch&searchType=reservationNo&searchValue=<%= reservation.getReservationId() %>">
                    Cancel
                </a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
