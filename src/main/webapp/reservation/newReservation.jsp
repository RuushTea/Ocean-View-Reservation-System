<%@ page import="java.util.List" %>
<%@ page import="com.oceanview.reservation.model.RoomType" %><%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 20/02/2026
  Time: 8:10 AM
--%>

<%
    List<RoomType> roomTypes = (List<RoomType>) request.getAttribute("roomTypes");
    String error = (String) request.getAttribute("error");
    String userRole = (String) session.getAttribute("role");
    boolean isStaffOrAdmin = "STAFF".equals(userRole) || "ADMIN".equals(userRole);
%>

<html>
<head>
    <title>New Reservation</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="bg-slate-50 text-slate-800">
<div class="max-w-3xl mx-auto p-6">
    <h2 class="text-2xl font-bold mb-4">Ocean View Resort - <%= isStaffOrAdmin ? "Create Reservation" : "Create Reservation Request" %></h2>

    <div class="bg-white border border-slate-200 rounded-xl shadow-sm p-6">
        <% if (!isStaffOrAdmin) { %>
        <div class="bg-blue-50 border border-blue-200 text-blue-800 rounded-lg p-4 mb-6">
            <div class="flex items-center mb-2">
                <span class="font-semibold">Reservation Request Process</span>
            </div>
            <ul class="list-disc list-inside space-y-1 text-sm">
                <li>Your reservation request will be sent to our staff for approval</li>
                <li>Once it's been approved, you can check your reservation details with your reservation number and contact number</li>
            </ul>
        </div>
        <% } else { %>
        <div class="bg-green-50 border border-green-200 text-green-800 rounded-lg p-4 mb-6">
            <div class="flex items-center mb-2">
                <span class="font-semibold">Direct Reservation Access</span>
            </div>
            <ul class="list-disc list-inside space-y-1 text-sm">
                <li>As a staff or admin, you can create reservations directly without approval</li>
                <li>Reservations will be confirmed immediately if rooms are available</li>
            </ul>
        </div>
        <% } %>
        
        <% if (error != null) { %>
        <div class="mb-4 bg-red-50 border border-red-200 text-red-700 rounded-lg p-3">
            <%= error %>
        </div>
        <% } %>

        <form action="<%= request.getContextPath()%>/<%= isStaffOrAdmin ? "reservation" : "reservationRequest" %>" method="POST" class="space-y-6">
            <input type="hidden" name="action" value="submit">
            <input type="hidden" name="requestedByUserId" value="<%= session.getAttribute("userId") != null ? session.getAttribute("userId") : 1 %>">
            <div>
                <h3 class="text-lg font-semibold mb-3">Guest Details</h3>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                        <label for="name" class="block text-sm font-medium mb-1">Guest Name</label>
                        <input type="text" id="name" name="name" required maxlength="80"
                               class="w-full rounded-lg border border-slate-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                        <input type="hidden" name="guestName" value="">
                    </div>

                    <div>
                        <label for="contactNo" class="block text-sm font-medium mb-1">Contact Number</label>
                        <input type="text" id="contactNo" name="contactNo" required maxlength="20"
                               class="w-full rounded-lg border border-slate-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                    </div>

                    <div class="md:col-span-2">
                        <label for="address" class="block text-sm font-medium mb-1">Address</label>
                        <input type="text" id="address" name="address" required maxlength="200"
                               class="w-full rounded-lg border border-slate-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                    </div>
                </div>
            </div>

            <div>
                <h3 class="text-lg font-semibold mb-3">Booking Details</h3>

                <div class="space-y-4">
                    <div>
                        <label for="roomTypeId" class="block text-sm font-medium mb-1">Room Type</label>
                        <select id="roomTypeId" name="roomTypeId" required
                                class="w-full rounded-lg border border-slate-300 px-3 py-2 bg-white focus:outline-none focus:ring-2 focus:ring-blue-500">
                            <option value="">Select Room Type</option>
                            <%
                                if (roomTypes != null){
                                    for (RoomType rt : roomTypes) {
                            %>
                            <option value="<%= rt.getRoomTypeId()%>">
                                <%= rt.getRoomTypeName() %> (Rate: <%= rt.getRatePerNight() %>)
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                        <p class="text-xs text-slate-500 mt-1"><%= isStaffOrAdmin ? "Room availability is checked and reservation is created immediately." : "Room availability is checked when you submit." %></p>
                    </div>

                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div>
                            <label for="checkInDate" class="block text-sm font-medium mb-1">Check-In Date</label>
                            <input type="date" id="checkInDate" name="checkInDate" required
                                   class="w-full rounded-lg border border-slate-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                        </div>

                        <div>
                            <label for="checkOutDate" class="block text-sm font-medium mb-1">Check-Out Date</label>
                            <input type="date" id="checkOutDate" name="checkOutDate" required
                                   class="w-full rounded-lg border border-slate-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                        </div>
                    </div>
                </div>
            </div>

            <button type="submit"
                    class="w-full md:w-auto px-5 py-2.5 rounded-lg bg-blue-600 text-white font-semibold hover:bg-blue-700">
                <%= isStaffOrAdmin ? "Create Reservation" : "Submit Reservation Request" %>
            </button>
            <button type="reset" class="w-full md:w-auto px-5 py-2.5 rounded-lg bg-red-600 text-white font-semibold hover:bg-red-700">Reset Fields</button>

            <%
                String role = (String) session.getAttribute("role");
                String homeUrl = request.getContextPath() + "/index.jsp";
                if ("ADMIN".equals(role)){
                    homeUrl = request.getContextPath() + "/admin/home";
                } else if("STAFF".equals(role)){
                    homeUrl = request.getContextPath() + "/staff/home";
                }
            %>

            <a href="<%= homeUrl %>"
                    class="w-full md:w-auto px-5 py-2.5 rounded-lg bg-slate-800 text-white font-semibold hover:bg-slate-900 float-right">
                Go Back
            </a>
        </form>
    </div>
</div>

<script>
    const checkIn = document.getElementById('checkInDate');
    const checkOut = document.getElementById('checkOutDate');
    const form = document.querySelector('form');

    // ensure checkout can't be before checkin
    checkIn.addEventListener('change', () => {
        checkOut.min = checkIn.value;
        if (checkOut.value && checkOut.value < checkIn.value) {
            checkOut.value = "";
        }
    });

    // Copy name to guestName for reservation request submissions
    form.addEventListener('submit', (e) => {
        const isStaffOrAdmin = <%= isStaffOrAdmin %>;
        if (!isStaffOrAdmin) {
            const nameField = document.getElementById('name');
            const guestNameField = document.querySelector('input[name="guestName"]');
            guestNameField.value = nameField.value;
        }
    });
</script>

</body>
</html>
