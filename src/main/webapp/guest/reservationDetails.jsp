<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.oceanview.reservation.model.Reservation" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Your Reservation Details - Ocean View Resort</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>

<body class="min-h-screen bg-gradient-to-br from-slate-50 to-slate-200 text-slate-900">

<header class="bg-white shadow-sm border-b border-slate-200">
    <div class="mx-auto max-w-7xl px-6 py-5 flex items-center justify-between">
        <div>
            <h1 class="text-2xl font-bold tracking-tight">Ocean View Resort</h1>
            <p class="text-sm text-slate-500">Guest Reservation Portal</p>
        </div>
        <a href="<%= request.getContextPath() %>/guest/view"
           class="rounded-xl border border-slate-300 bg-white px-6 py-2.5 text-sm font-semibold text-slate-700 hover:bg-slate-100 transition shadow-sm">
            Search Another
        </a>
    </div>
</header>

<%
    List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
%>

<main class="flex-1 flex items-center justify-center px-6 py-16">
    <section class="bg-white rounded-3xl shadow-lg border border-slate-200 max-w-3xl w-full p-10">
        
        <div class="text-center mb-8">
            <h2 class="text-3xl font-bold tracking-tight text-slate-900 mb-2">
                Your Reservations
            </h2>
            <div class="inline-flex items-center bg-blue-50 text-blue-700 px-4 py-2 rounded-full text-sm font-semibold">
                <%= reservations.size() %> Reservation(s) Found
            </div>
        </div>

        <% if (request.getAttribute("success") != null) { %>
            <div class="bg-green-50 border border-green-200 rounded-xl p-4 mb-6">
                <p class="text-green-700 text-sm font-medium text-center">
                    <%= request.getAttribute("success") %>
                </p>
            </div>
        <% } %>

        <% if (reservations != null && !reservations.isEmpty()) { %>
            <% for (Reservation reservation : reservations) { %>
                <% 
                String status = reservation.getStatus();
                String statusClass = "";
                String statusIcon = "";
                
                if ("Confirmed".equals(status)) {
                    statusClass = "bg-green-100 text-green-800";
                    statusIcon = "✓";
                } else if ("Cancelled".equals(status)) {
                    statusClass = "bg-red-100 text-red-800";
                    statusIcon = "✗";
                } else if ("Pending".equals(status)) {
                    statusClass = "bg-yellow-100 text-yellow-800";
                    statusIcon = "⏳";
                } else {
                    statusClass = "bg-gray-100 text-gray-800";
                    statusIcon = "•";
                }
                %>

                <div class="mb-8 border border-slate-200 rounded-2xl p-6">
                    <div class="flex justify-between items-center mb-4">
                        <h3 class="text-xl font-semibold text-slate-900">Reservation #<%= reservation.getReservationId() %></h3>
                        <span class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium <%= statusClass %>">
                            <%= statusIcon %> <%= status %>
                        </span>
                    </div>

                    <div class="grid md:grid-cols-2 gap-6">
                        <div class="space-y-3">
                            <div class="flex justify-between py-2 border-b border-slate-100">
                                <span class="text-slate-600">Guest Name:</span>
                                <span class="font-medium text-slate-900"><%= reservation.getGuest().getName() %></span>
                            </div>
                            <div class="flex justify-between py-2 border-b border-slate-100">
                                <span class="text-slate-600">Contact:</span>
                                <span class="font-medium text-slate-900"><%= reservation.getGuest().getContactNo() %></span>
                            </div>
                        </div>
                        <div class="space-y-3">
                            <div class="flex justify-between py-2 border-b border-slate-100">
                                <span class="text-slate-600">Room Type:</span>
                                <span class="font-medium text-slate-900"><%= reservation.getRoom().getRoomType().getRoomTypeName() %></span>
                            </div>
                            <div class="flex justify-between py-2 border-b border-slate-100">
                                <span class="text-slate-600">Check-in:</span>
                                <span class="font-medium text-slate-900"><%= dateFormat.format(reservation.getCheckInDate()) %></span>
                            </div>
                            <div class="flex justify-between py-2 border-b border-slate-100">
                                <span class="text-slate-600">Check-out:</span>
                                <span class="font-medium text-slate-900"><%= dateFormat.format(reservation.getCheckOutDate()) %></span>
                            </div>
                        </div>
                    </div>
                    
                    <% if ("Confirmed".equals(reservation.getStatus())) { %>
                        <div class="mt-4 pt-4 border-t border-slate-200">
                            <form action="<%= request.getContextPath() %>/guest/cancel" method="POST" onsubmit="return confirmCancel(<%= reservation.getReservationId() %>)">
                                <input type="hidden" name="reservationId" value="<%= reservation.getReservationId() %>">
                                <input type="hidden" name="contactNo" value="<%= reservation.getGuest().getContactNo() %>">
                                <button type="submit" class="bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700 transition font-medium text-sm">
                                    Cancel Reservation
                                </button>
                            </form>
                        </div>
                    <% } %>
                </div>
            <% } %>
        <% } %>

        <div class="mt-8 bg-slate-50 rounded-2xl border border-slate-200 p-6">
            <p class="text-sm text-slate-600 text-center">
                If you need to make changes to any reservation, please contact our front desk.
            </p>
        </div>
    </section>
</main>

<script>
function confirmCancel(reservationId) {
    return confirm('Are you sure you want to cancel reservation #' + reservationId + '? This action cannot be undone.');
}
</script>

<footer class="text-center py-6 text-sm text-slate-500">
    <%= java.time.Year.now() %> Ocean View Resort | Guest Reservation Portal
</footer>

</body>
</html>
