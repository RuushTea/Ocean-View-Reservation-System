<%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 03/03/2026
  Time: 1:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.oceanview.reservation.model.Reservation" %>
<%
  List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
  Reservation singleReservation = (Reservation) request.getAttribute("reservation");
  String searchValue = (String) request.getAttribute("searchValue");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Search Results</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="min-h-screen bg-slate-50 text-slate-900">
<div class="mx-auto max-w-6xl px-4 py-8">

    <div class="bg-white border border-slate-200 rounded-2xl shadow-sm p-6">
        <div class="flex items-start justify-between gap-4 mb-6">
            <h1 class="text-2xl font-bold">Search Results</h1>
            <a class="rounded-lg border border-slate-300 bg-white px-4 py-2 text-sm font-semibold hover:bg-slate-100"
               href="<%= request.getContextPath() %>/staff/home?action=searchReservation">
                Back to Search
            </a>
        </div>

        <%-- display single reservation details --%>
        <% if (singleReservation != null) { %>
            <div class="space-y-4">
                <div class="rounded-xl border border-slate-200 bg-slate-50 p-4">
                    <p class="text-sm text-slate-500">Status</p>
                    <p class="text-lg font-semibold"><%= singleReservation.getStatus() %></p>
                </div>

                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div class="rounded-xl border border-slate-200 bg-white p-4">
                        <p class="text-sm font-semibold mb-2">Guest</p>
                        <p><strong>Name:</strong> <%= singleReservation.getGuest().getName() %></p>
                        <p><strong>Contact:</strong> <%= singleReservation.getGuest().getContactNo() %></p>
                        <p><strong>Address:</strong> <%= singleReservation.getGuest().getAddress() %></p>
                    </div>

                    <div class="rounded-xl border border-slate-200 bg-white p-4">
                        <p class="text-sm font-semibold mb-2">Room</p>
                        <p><strong>Room Number:</strong> <%= singleReservation.getRoom().getRoomNo() %></p>
                        <p><strong>Room Type:</strong> <%= singleReservation.getRoom().getRoomType().getRoomTypeName() %></p>
                        <p><strong>Rate:</strong> <%= singleReservation.getRoom().getRoomType().getRatePerNight() %></p>
                    </div>
                </div>

                <div class="rounded-xl border border-slate-200 bg-white p-4">
                    <p><strong>Check-in:</strong> <%= singleReservation.getCheckInDate() %></p>
                    <p><strong>Check-out:</strong> <%= singleReservation.getCheckOutDate() %></p>
                </div>
            </div>

            <%-- action buttons --%>
            <div class="mt-6 flex flex-col sm:flex-row gap-3">
                <form action="<%= request.getContextPath() %>/staff/home" method="post">
                    <input type="hidden" name="action" value="toggleCancel"/>
                    <input type="hidden" name="reservationId" value="<%= singleReservation.getReservationId() %>"/>

                    <% if ("CANCELLED".equalsIgnoreCase(singleReservation.getStatus())) { %>
                    <button class="rounded-lg bg-emerald-600 text-white px-4 py-2 font-semibold hover:bg-emerald-700">
                        Reinstate Reservation
                    </button>
                    <% } else { %>
                    <button class="rounded-lg bg-red-600 text-white px-4 py-2 font-semibold hover:bg-red-700">
                        Cancel Reservation
                    </button>
                    <% } %>
                </form>

                <a href="<%= request.getContextPath() %>/staff/home?action=edit&reservationId=<%= singleReservation.getReservationId() %>"
                   class="rounded-lg border border-slate-300 px-4 py-2 font-semibold hover:bg-slate-100">
                    Edit Reservation
                </a>

                <form method="post" action="<%= request.getContextPath() %>/staff/home" class="w-full sm:w-auto">
                    <input type="hidden" name="action" value="generateBill"/>
                    <input type="hidden" name="reservationNo" value="<%= singleReservation.getReservationId() %>"/>
                    <button class="w-full rounded-lg bg-emerald-600 text-white px-4 py-2 font-semibold hover:bg-emerald-700">
                        Calculate / Print Bill
                    </button>
                </form>
            </div>

        <%-- display multiple reservations list --%>
        <% } else if (reservations != null && !reservations.isEmpty()) { %>
            <h2 class="text-xl font-semibold mb-4">Reservations for Contact: <%= searchValue %></h2>
            
            <div class="overflow-x-auto">
                <table class="w-full border-collapse border border-slate-200">
                    <thead class="bg-slate-50">
                        <tr>
                            <th class="border border-slate-200 px-4 py-3 text-left text-sm font-semibold">Reservation No</th>
                            <th class="border border-slate-200 px-4 py-3 text-left text-sm font-semibold">Guest Name</th>
                            <th class="border border-slate-200 px-4 py-3 text-left text-sm font-semibold">Room</th>
                            <th class="border border-slate-200 px-4 py-3 text-left text-sm font-semibold">Check-in Date</th>
                            <th class="border border-slate-200 px-4 py-3 text-left text-sm font-semibold">Check-out Date</th>
                            <th class="border border-slate-200 px-4 py-3 text-left text-sm font-semibold">Status</th>
                            <th class="border border-slate-200 px-4 py-3 text-left text-sm font-semibold">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Reservation r : reservations) { %>
                        <tr class="hover:bg-slate-50">
                            <td class="border border-slate-200 px-4 py-3"><%= r.getReservationId() %></td>
                            <td class="border border-slate-200 px-4 py-3"><%= r.getGuest().getName() %></td>
                            <td class="border border-slate-200 px-4 py-3">
                                <%= r.getRoom().getRoomNo() %> - <%= r.getRoom().getRoomType().getRoomTypeName() %>
                            </td>
                            <td class="border border-slate-200 px-4 py-3"><%= r.getCheckInDate() %></td>
                            <td class="border border-slate-200 px-4 py-3"><%= r.getCheckOutDate() %></td>
                            <td class="border border-slate-200 px-4 py-3">
                                <span class="px-2 py-1 rounded-full text-xs font-semibold
                                    <% if ("CONFIRMED".equalsIgnoreCase(r.getStatus())) { %>
                                        bg-green-100 text-green-800
                                    <% } else if ("CANCELLED".equalsIgnoreCase(r.getStatus())) { %>
                                        bg-red-100 text-red-800
                                    <% } else { %>
                                        bg-gray-100 text-gray-800
                                    <% } %>
                                ">
                                    <%= r.getStatus() %>
                                </span>
                            </td>
                            <td class="border border-slate-200 px-4 py-3">
                                <form action="<%= request.getContextPath() %>/staff/home" method="post">
                                    <input type="hidden" name="action" value="doSearch"/>
                                    <input type="hidden" name="searchType" value="reservationNo"/>
                                    <input type="hidden" name="searchValue" value="<%= r.getReservationId() %>"/>
                                    <button type="submit" class="text-blue-600 hover:text-blue-800 text-sm font-medium">
                                        View
                                    </button>
                                </form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        <% } else { %>
            <div class="text-center py-8">
                <p class="text-slate-600">No reservations found.</p>
            </div>
        <% } %>
    </div>
</div>
</body>
</html>
