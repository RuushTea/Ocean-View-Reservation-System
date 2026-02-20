<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Ocean View Resort System!" %></h1>
<br/>
<a href="hello-servlet">Click here to go to HelloServlet</a>
<a href="auth">Click here to login</a>
<br/>

<div>
  <form action="guest-servlet" method="POST">
    <label>Name:</label>
    <input type="text" name="name">

    <label>Address</label>
    <input type="text" name="address">

    <label>Contact Number</label>
    <input type="text" name="contactNo">

    <button type="submit">Add Guest</button>
  </form>
</div>

<br/>

<div>
  <p>Search for Guest with Guest ID</p>
  <form action="${pageContext.request.contextPath}/guest-servlet" method="GET">
    <input type="text" name="guestId" placeholder="Enter Guest ID">
    <input type="submit" value="Search">
  </form>
</div>

<br/>

<div>
  <h2>Create a new reservation</h2>
  <form action="${pageContext.request.contextPath}reservation">
    <label>Guest Contact No</label>
    <input type="text" name="guestContactNo">

    <label>Room Type</label>
    <select name="roomType"></select>

    <label>Check-in Date</label>
    <input type="date" name="checkinDate">

    <label>Check-out Date</label>
    <input type="date" name="checkoutDate">

    <button type="submit">Create Reservation</button>
  </form>
</div>


</body>
</html>