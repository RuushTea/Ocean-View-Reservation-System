<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.oceanview.reservation.model.Reservation" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
%>

<!DOCTYPE html>
<html>
<head>
    <title>All Reservations</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>

<body class="min-h-screen bg-slate-50 text-slate-900">
<div class="mx-auto max-w-7xl px-4 py-8">

    <div class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <div class="flex items-center justify-between">
            <div>
                <h1 class="text-xl font-bold">All Reservations</h1>
                <p class="mt-1 text-slate-600">View all reservation details in the system.</p>
            </div>
        </div>

        <div class="mt-6 overflow-x-auto">
            <table class="w-full border-collapse">
                <thead>
                <tr class="text-left border-b border-slate-200">
                    <th class="py-2 pr-4">Reservation ID</th>
                    <th class="py-2 pr-4">Guest Name</th>
                    <th class="py-2 pr-4">Contact</th>
                    <th class="py-2 pr-4">Room Number</th>
                    <th class="py-2 pr-4">Room Type</th>
                    <th class="py-2 pr-4">Check-in</th>
                    <th class="py-2 pr-4">Check-out</th>
                    <th class="py-2 pr-4">Status</th>
                </tr>
                </thead>

                <tbody>
                <% if (reservations != null && !reservations.isEmpty()) {
                    for (Reservation r : reservations) { %>

                <tr class="border-b border-slate-100">
                    <td class="py-2 pr-4"><%= r.getReservationId() %></td>
                    <td class="py-2 pr-4"><%= r.getGuest().getName() %></td>
                    <td class="py-2 pr-4"><%= r.getGuest().getContactNo() %></td>
                    <td class="py-2 pr-4"><%= r.getRoom().getRoomNo() %></td>
                    <td class="py-2 pr-4"><%= r.getRoom().getRoomType().getRoomTypeName() %></td>
                    <td class="py-2 pr-4"><%= dateFormat.format(r.getCheckInDate()) %></td>
                    <td class="py-2 pr-4"><%= dateFormat.format(r.getCheckOutDate()) %></td>
                    <td class="py-2 pr-4">
                        <span class="inline-flex items-center rounded-full px-2 py-1 text-xs font-medium
                        <%= "CONFIRMED".equals(r.getStatus())
                        ? "bg-green-100 text-green-800"
                        : "PENDING".equals(r.getStatus())
                        ? "bg-yellow-100 text-yellow-800"
                        : "CANCELLED".equals(r.getStatus())
                        ? "bg-red-100 text-red-800"
                        : "bg-gray-100 text-gray-800" %>">
                            <%= r.getStatus() %>
                        </span>
                    </td>
                </tr>

                <% }
                } else { %>
                <tr>
                    <td colspan="8" class="py-8 text-center text-slate-500">
                        No reservations found in the system.
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>

        <div class="mt-6">
            <a class="text-sm font-semibold text-slate-700 hover:text-slate-900"
               href="<%= request.getContextPath() %>/admin/home">
                Back to dashboard
            </a>
        </div>

    </div>
</div>
</body>
</html>
