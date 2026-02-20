<%@ page import="java.util.List" %>
<%@ page import="com.oceanview.reservation.model.RoomType" %><%--
  Created by IntelliJ IDEA.
  User: Rushd
  Date: 20/02/2026
  Time: 8:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<RoomType> roomTypes = (List<RoomType>) request.getAttribute("roomTypes");
    String error = (String) request.getAttribute("error");
%>


<html>
<head>
    <title>New Reservation</title>
</head>
<body>

<div class="container">
    <h2>Ocean View Resort - Create New Reservation</h2>

    <div class="card">
        <% if (error != null) { %>>
        <div class="error"><%= error %></div>
        <% } %>

        <form action="<%= request.getContextPath()%>/reservation" method="POST">
            <h3>Guest Details</h3>

            <div class="row">
                <div class="field">
                    <label for="name">Guest Name</label>
                    <input type="text" id="name" name="name" required maxlength="80">
                </div>

                <div class="field">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" required maxlength="200">
                </div>

                <div class="field">
                    <label for="contactNo">Contact Number</label>
                    <input type="text" id="contactNo" name="contactNo" required maxlength="20">
                </div>


            </div>

            <h3>Booking Details</h3>

            <div class="row">
                <div class="field">
                    <label for="roomTypeId">Room Type</label>
                    <select id="roomTypeId" name="roomTypeId" required>
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

                    <div class="hint">Room availability is checked when you submit</div>
                </div>
            </div>

            <div class="row">

                <div class="field">
                    <label for="checkInDate">Check-In Date</label>
                    <input type="date" id="checkInDate" name="checkInDate" required>
                </div>

                <div class="field">
                    <label for="checkOutDate">Check-Out Date</label>
                    <input type="date" id="checkOutDate" name="checkOutDate" required>
                </div>
            </div>

            <button type="submit" class="btn">Create Reservation</button>
        </form>
    </div>
</div>

<script>
    const checkIn = document.getElementById('checkInDate');
    const checkOut = document.getElementById('checkOutDate');
    checkIn.addEventListener('change', () => {
        checkOut.min = checkout.value;
    })
</script>

</body>
</html>
