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
%>

<html>
<head>
    <title>New Reservation</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/tailwindcssOutput.css">
</head>
<body class="bg-slate-50 text-slate-800">
<div class="max-w-3xl mx-auto p-6">
    <h2 class="text-2xl font-bold mb-4">Ocean View Resort - Create New Reservation</h2>

    <div class="bg-white border border-slate-200 rounded-xl shadow-sm p-6">
        <% if (error != null) { %>
        <div class="mb-4 bg-red-50 border border-red-200 text-red-700 rounded-lg p-3">
            <%= error %>
        </div>
        <% } %>

        <form action="<%= request.getContextPath()%>/reservation" method="POST" class="space-y-6" id="reservationForm">
            <div>
                <h3 class="text-lg font-semibold mb-3">Guest Details</h3>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                        <label for="name" class="block text-sm font-medium mb-1">Guest Name</label>
                        <input type="text" id="name" name="name" required maxlength="80"
                               pattern="[A-Za-z\s]{2,80}"
                               title="Guest name must contain only letters and spaces (2-80 characters)"
                               oninput="this.value = this.value.replace(/[^A-Za-z\s]/g, '').slice(0,80)"
                               class="w-full rounded-lg border border-slate-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                               placeholder="Enter guest name (letters only)"/>
                    </div>

                    <div>
                        <label for="contactNo" class="block text-sm font-medium mb-1">Contact Number</label>
                        <input type="tel" id="contactNo" name="contactNo" required maxlength="15"
                               pattern="[0-9]{8,}"
                               title="Contact number must be more than 8 digits"
                               inputmode="numeric"
                               oninput="this.value = this.value.replace(/[^0-9]/g, '');"
                               class="w-full rounded-lg border border-slate-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                               placeholder="Enter contact number"/>
                    </div>

                    <div class="md:col-span-2">
                        <label for="address" class="block text-sm font-medium mb-1">Address</label>
                        <input type="text" id="address" name="address" required maxlength="200"
                               pattern="[A-Za-z0-9\s\.,#-]{5,200}"
                               title="Address must be at least 5 characters long"
                               class="w-full rounded-lg border border-slate-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                               placeholder="Enter complete address (min 5 characters)"/>
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
                        <p class="text-xs text-slate-500 mt-1">Room availability is checked when you submit.</p>
                    </div>

                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div>
                            <label for="checkInDate" class="block text-sm font-medium mb-1">Check-In Date</label>
                            <%
                                String today = java.time.LocalDate.now().toString();
                                String checkInMin = today;
                                String checkOutMin = today;
                            %>
                            <input type="date" id="checkInDate" name="checkInDate" required
                                   class="w-full rounded-lg border border-slate-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                   min="<%= checkInMin %>"
                                   onchange="document.getElementById('checkOutDate').min = this.value; if(document.getElementById('checkOutDate').value && document.getElementById('checkOutDate').value < this.value) { document.getElementById('checkOutDate').value = ''; }"/>
                        </div>

                        <div>
                            <label for="checkOutDate" class="block text-sm font-medium mb-1">Check-Out Date</label>
                            <input type="date" id="checkOutDate" name="checkOutDate" required
                                   class="w-full rounded-lg border border-slate-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                   min="<%= checkOutMin %>"
                                   onchange="document.getElementById('checkInDate').max = this.value; if(document.getElementById('checkInDate').value && document.getElementById('checkInDate').value > this.value) { document.getElementById('checkInDate').value = ''; }"/>
                        </div>
                    </div>
                </div>
            </div>

            <button type="submit"
                    class="w-full md:w-auto px-5 py-2.5 rounded-lg bg-blue-600 text-white font-semibold hover:bg-blue-700">
                Create Reservation
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


</body>
</html>
