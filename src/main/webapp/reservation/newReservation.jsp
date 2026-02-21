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
    <link rel="stylesheet" href="../css/tailwindcssOutput.css">
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

        <form action="<%= request.getContextPath()%>/reservation" method="POST" class="space-y-6">
            <div>
                <h3 class="text-lg font-semibold mb-3">Guest Details</h3>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                        <label for="name" class="block text-sm font-medium mb-1">Guest Name</label>
                        <input type="text" id="name" name="name" required maxlength="80"
                               class="w-full rounded-lg border border-slate-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"/>
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
                        <p class="text-xs text-slate-500 mt-1">Room availability is checked when you submit.</p>
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
                Create Reservation
            </button>
            <button type="reset" class="w-full md:w-auto px-5 py-2.5 rounded-lg bg-red-600 text-white font-semibold hover:bg-red-700">Reset Fields</button>
            <a href="<%= request.getContextPath() %>/index.jsp"
                    class="w-full md:w-auto px-5 py-2.5 rounded-lg bg-slate-800 text-white font-semibold hover:bg-slate-900 float-right">
                Go Back
            </a>
        </form>
    </div>
</div>

<script>
    const checkIn = document.getElementById('checkInDate');
    const checkOut = document.getElementById('checkOutDate');

    // ensure checkout can't be before checkin
    checkIn.addEventListener('change', () => {
        checkOut.min = checkIn.value;
        if (checkOut.value && checkOut.value < checkIn.value) {
            checkOut.value = "";
        }
    });
</script>

</body>
</html>
